package common.jsonprotocol;

import common.domain.Item;
import common.domain.User;
import common.dto.DTOUtils;
import common.dto.ItemDTO;
import common.dto.UserDTO;

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

    public static Request createUpdateRequest(User user) {
        Request req = new Request();
        req.setType(RequestType.WINDOW);
        req.setUser(DTOUtils.getDTO(user));
        return req;
    }

    public static Request createGetAllItemsRequest(){
        Request req = new Request();
        req.setType(RequestType.GET_ITEMS);
        return req;
    }

    public static Response createGetItemsResponse(List<Item> items) {
        Response resp = new Response();
        resp.setType(ResponseType.OK);
        resp.setItems(DTOUtils.getItemsDTOFromList(items));
        return resp;
    }

    public static Request createOrderRequest(UserDTO dto, List<Item> itemsDTOFromList) {
        Request req = new Request();
        req.setType(RequestType.MAKE_ORDER);
        req.setUser(dto);
        req.setItems(DTOUtils.getItemsDTOFromList(itemsDTOFromList));
        return req;
    }

    public static Request createAddItemRequest(Item item) {
        Request req = new Request();
        req.setType(RequestType.ADD_ITEM);
        req.addItem(DTOUtils.getItemDTO(item));
        return req;
    }

    public static Request createDeleteItemRequest(long id) {
        Request req = new Request();
        req.setType(RequestType.REMOVE_ITEM);
        req.addItem(new ItemDTO(id));
        return req;
    }

    public static Request createUpdateItemRequest(Item item) {
        Request req = new Request();
        req.setType(RequestType.UPDATE_ITEM);
        req.addItem(DTOUtils.getItemDTO(item));
        return req;
    }
}
