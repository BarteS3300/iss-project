package server.business;

import common.business.IObserver;
import common.business.IService;
import common.business.ProjectException;
import common.domain.Item;
import common.domain.Order;
import common.domain.User;
import server.repository.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.StreamSupport;

public class Service implements IService{

    private IUserRepository userRepository;

    private IItemRepository itemRepository;

    private OrderRepository orderRepository;

    private Map<Long, IObserver> loggedClients = new HashMap<>();

    public Service(IUserRepository userRepository, IItemRepository itemRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }


    @Override
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

    @Override
    public void updateUser(User user, IObserver client) throws ProjectException {
        if(loggedClients.get(user.getId())==null)
            throw new ProjectException("Agent " + user.getUsername() +" not logged in.");
        loggedClients.put(user.getId(), client);

    }

    @Override
    public void addItem(Item item) throws ProjectException {
        itemRepository.save(item);
        notifyAgents();
    }

    @Override
    public List<Item> getAllItems() throws ProjectException {
        Iterable<Item> items = itemRepository.getAll();
        return StreamSupport.stream(items.spliterator(), false)
                .toList();
    }

    @Override
    public void orderItems(List<Item> cart, User user) throws ProjectException {
        for(Item item : cart){
            Item dbItem = itemRepository.findOne(item.getId()).orElseThrow(() -> new ProjectException("Item not found"));
            if(dbItem.getQuantity() < item.getQuantity())
                throw new ProjectException("Not enough items in stock");
            dbItem.setQuantity(dbItem.getQuantity() - item.getQuantity());
            itemRepository.update(dbItem);
            notifyAgents();
        }
        orderRepository.addOrder(user, cart);
    }

    @Override
    public void deleteItem(long id) throws ProjectException {
        itemRepository.delete(id);
        notifyAgents();
    }

    @Override
    public void updateItem(Item item) throws ProjectException {
        itemRepository.update(item);
        notifyAgents();
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
