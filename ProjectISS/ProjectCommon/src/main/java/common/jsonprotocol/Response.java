package common.jsonprotocol;

import common.dto.ItemDTO;
import common.dto.UserDTO;

public class Response {

    private ResponseType type;

    private UserDTO user;

    private String[] data;

    private String errorMessage;

    private ItemDTO[] items;

    public Response() {
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

   public UserDTO getUser() {
            return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ItemDTO[] getItems() {
        return items;
    }

    public void setItems(ItemDTO[] items) {
        this.items = items;
    }
}
