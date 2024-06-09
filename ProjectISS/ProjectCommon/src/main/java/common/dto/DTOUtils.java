package common.dto;

import common.domain.Item;
import common.domain.Role;
import common.domain.User;

import java.util.List;

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

    public static List<Item> getItemsFromDTO(ItemDTO[] items) {
        List<Item> itemList = new java.util.ArrayList<>();
        for (ItemDTO itemDTO : items) {
            itemList.add(new Item(itemDTO.getId(), itemDTO.getName(), itemDTO.getQuantity(), itemDTO.getPrice()));
        }
        return itemList;
    }

    public static ItemDTO[] getItemsDTO(Item[] items) {
        ItemDTO[] itemDTOs = new ItemDTO[items.length];
        for (int i = 0; i < items.length; i++) {
            itemDTOs[i] = new ItemDTO(items[i].getId(), items[i].getName(), items[i].getQuantity(), items[i].getPrice());
        }
        return itemDTOs;
    }

    public static ItemDTO[] getItemsDTOFromList(List<Item> cart) {
        ItemDTO[] itemsDTO = new ItemDTO[cart.size()];
        for (int i = 0; i < cart.size(); i++) {
            itemsDTO[i] = new ItemDTO(cart.get(i).getId(), cart.get(i).getName(), cart.get(i).getQuantity(), cart.get(i).getPrice());
        }
        return itemsDTO;
    }

    public static List<Item> getItemsListFromDTO(Item[] items) {
        List<Item> itemList = new java.util.ArrayList<>();
        for (Item item : items) {
            itemList.add(new Item(item.getId(), item.getName(), item.getQuantity(), item.getPrice()));
        }
        return itemList;
    }

    public static Item getItemFromDTO(ItemDTO item) {
        return new Item(item.getId(), item.getName(), item.getQuantity(), item.getPrice());
    }

    public static ItemDTO getItemDTO(Item item) {
        return new ItemDTO(item.getId(), item.getName(), item.getQuantity(), item.getPrice());
    }
}
