package common.jsonprotocol;

import common.domain.User;
import common.dto.DTOUtils;
import common.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class JsonProtocolUtils {

    public static Response createErrorResponse(String errorMessage){
        Response resp = new Response();
        resp.setType(ResponseType.ERROR);
        resp.setErrorMessage(errorMessage);
        return resp;
    }

    public static Response createOkResponse(){
        Response resp = new Response();
        resp.setType(ResponseType.OK);
        return resp;
    }

    public static  Response createUpdateResponse(){
        Response resp = new Response();
        resp.setType(ResponseType.UPDATE);
        return resp;
    }

    public static Request createLoginRequest(String username, String password){
        Request req = new Request();
        req.setType(RequestType.LOGIN);
        req.setUser(new UserDTO(username, password));
        return req;
    }

    public static Response createLoginResponse(User user){
        Response resp = new Response();
        resp.setType(ResponseType.OK);
        resp.setUser(DTOUtils.getDTO(user));
        return resp;
    }


    public static Request createLogoutRequest(User user){
        Request req = new Request();
        req.setType(RequestType.LOGOUT);
        req.setUser(DTOUtils.getDTO(user));
        return req;
    }
}
