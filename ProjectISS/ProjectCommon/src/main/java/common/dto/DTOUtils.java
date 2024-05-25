package common.dto;

import common.domain.Role;
import common.domain.User;

public class DTOUtils {

    public static String getUsernameFromDTO(UserDTO user){
        return user.getUsername();
    }

    public static String getPasswordFromDTO(UserDTO user){
        return user.getPassword();
    }

    public static UserDTO getUsernameAndPasswordDTO(String username, String password){
        return new UserDTO(username, password);
    }

    public static UserDTO getDTO(User user){
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getRole().name());
    }

    public static User getFromDTO(UserDTO user){
        return new User(user.getId(), user.getUsername(), user.getPassword(), Role.valueOf(user.getRole()));
    }
}
