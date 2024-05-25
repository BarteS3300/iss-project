package common.jsonprotocol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.business.IObserver;
import common.business.IService;
import common.business.ProjectException;
import common.domain.User;
import common.dto.DTOUtils;
import common.utils.LocalDateTimeTypeAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.HashMap;

public class ProjectClientJsonWorker implements Runnable, IObserver{

    private IService server;

    private Socket connection;

    private BufferedReader input;

    private PrintWriter output;

    private Gson gsonFormatter;

    private volatile boolean connected;

    public ProjectClientJsonWorker(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        gsonFormatter = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
        try {
            output = new PrintWriter(connection.getOutputStream());
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        while(connected){
            try{
                String requestLine = input.readLine();
                Request request = gsonFormatter.fromJson(requestLine, Request.class);
                Response response = handleRequest(request);
                if(response!= null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                Thread.sleep(200);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
        }catch (IOException e){
            System.out.println("Error "+e);
        }
    }

    public void update() throws ProjectException{
        sendResponse(JsonProtocolUtils.createUpdateResponse());
    }

    private Response handleRequest(Request request) {
        Response response = null;
        if (request.getType() == RequestType.LOGIN) {
            System.out.println("Login request ..." + request.getType());
            String username = DTOUtils.getUsernameFromDTO(request.getUser());
            String password = DTOUtils.getPasswordFromDTO(request.getUser());
            try {
                User user = server.logInUser(username, password, this);
                return JsonProtocolUtils.createLoginResponse(user);
            } catch (ProjectException e) {
                connected = false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.LOGOUT) {
            System.out.println("Logout request ...");
            try {
                User user = DTOUtils.getFromDTO(request.getUser());
                server.logOutUser(user, this);
                connected=false;
                return JsonProtocolUtils.createOkResponse();
            } catch (ProjectException e) {
                connected = false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        return response;
    }

    private void sendResponse(Response response){
        String responseString = gsonFormatter.toJson(response);
        System.out.println("Sending response " + responseString);
        synchronized (output){
            output.println(responseString);
            output.flush();
        }
    }
}
