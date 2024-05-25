package server.business;

import common.business.IObserver;
import common.business.IService;
import common.business.ProjectException;
import common.domain.User;
import server.repository.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.StreamSupport;

public class Service implements IService{

    private IUserRepository userRepository;

    private Map<Long, IObserver> loggedClients = new HashMap<>();

    public Service(IUserRepository userRepository){
        this.userRepository = userRepository;
    }


    public User logInUser(String username, String password, IObserver client) throws ProjectException {
        Optional<User> opAgent = userRepository.findOneByUsername(username);
        if (opAgent.isEmpty())
            throw new ServiceException("No agent with that username");
        User user = opAgent.get();
        if (Objects.equals(user.getPassword(), password)) {
            if(loggedClients.get(user.getId())!=null)
                throw new ProjectException("Agent already logged in.");
            loggedClients.put(user.getId(), client);
            return user;
        }
        else{
            throw new ServiceException("Incorrect password");
        }
    }

    @Override
    public void logOutUser(User user, IObserver client) throws ProjectException {
        IObserver localClient = loggedClients.remove(user.getId());
        if (localClient == null)
            throw new ProjectException("Agent " + user.getUsername() +" not logged in.");
    }


    private final int defaultThreadsNo = 5;
    private void notifyAgents() throws ProjectException{
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);
                for(IObserver client : loggedClients.values()){
                    executor.execute(() -> {
                        try {
                            client.update();
                        } catch (ProjectException e) {
                            System.err.println("Error notifying agent " + e);
                        }
                    });
                }
                executor.shutdown();
    }
}
