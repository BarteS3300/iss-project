package common.business;

import common.domain.User;

public interface IService {

    User logInUser(String username, String password, IObserver client) throws ProjectException;

    void logOutUser(User user, IObserver client) throws ProjectException;
}
