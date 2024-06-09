package server;

import common.business.IService;
import common.domain.Item;
import common.domain.Order;
import common.domain.User;
import common.utils.AbstractServer;
import common.utils.ProjectJsonConcurrentServer;
import common.utils.ServerException;
import server.business.Service;
import server.repository.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

public class StartJsonServer {
    private static int dafaultPort = 30053;
    public static void main(String[] args){
        Properties serverProps = new Properties();
        try{
            serverProps.load(StartJsonServer.class.getResourceAsStream("/projectserver.properties"));
            System.out.println("Server properties set.");
            serverProps.list(System.out);
        } catch (IOException e){
            System.err.println("Cannot find projectserver.properties " + e);
            return;
        }

        IUserRepository userRepository = new UserHibernateRepository();
        IItemRepository itemRepository = new ItemHibernateRepository();
        OrderRepository orderRepository = new OrderRepository(serverProps);
        userRepository.getAll().forEach(System.out::println);
        itemRepository.getAll().forEach(System.out::println);
        IService projectServerImpl = new Service(userRepository, itemRepository, orderRepository);

//        List<Item> items = (List<Item>) itemRepository.getAll();
//        User user = (User) userRepository.findOneByUsername("a").get();
//        Order testOrder = new Order(user, items);
//        orderRepository.addOrder(user, items);

        int projectServerPort = dafaultPort;
        try{
            projectServerPort = Integer.parseInt(serverProps.getProperty("project.server.port"));
        }catch(NumberFormatException nef){
            System.err.println("Wrong Port Number" + nef.getMessage());
            System.err.println("Using default port " + dafaultPort);
        }
        AbstractServer server = new ProjectJsonConcurrentServer(projectServerPort, projectServerImpl);
        try{
            server.start();
        }catch (ServerException e){
            System.err.println("Error starting the server" + e.getMessage());
        }

    }
}
