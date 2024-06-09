package common.business;

import common.domain.Item;
import common.domain.User;

import java.util.List;

public interface IService {

    User logInUser(String username, String password, IObserver client) throws ProjectException;

    void logOutUser(User user, IObserver client) throws ProjectException;

    void updateUser(User user, IObserver client) throws ProjectException;

    void addItem(Item item) throws ProjectException;

    List<Item> getAllItems() throws ProjectException;

    void orderItems(List<Item> cart, User user) throws ProjectException;

    void deleteItem(long id) throws ProjectException;

    void updateItem(Item item) throws ProjectException;
}
