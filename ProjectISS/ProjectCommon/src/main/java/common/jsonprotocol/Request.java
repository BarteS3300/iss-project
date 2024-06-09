package common.jsonprotocol;
import common.dto.ItemDTO;
import common.dto.UserDTO;

public class Request {

    private RequestType type;

    private UserDTO user;

    private String[] data;

    private ItemDTO[] items;


    public Request() {
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
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

    public ItemDTO[] getItems() {
        return items;
    }
    public void setItems(ItemDTO[] items) {
        this.items = items;
    }

    public void addItem(ItemDTO item){
        if (this.items == null){
            this.items = new ItemDTO[1];
            this.items[0] = item;
        }else{
            ItemDTO[] newItems = new ItemDTO[this.items.length+1];
            for (int i=0;i<this.items.length;i++){
                newItems[i] = this.items[i];
            }
            newItems[this.items.length] = item;
            this.items = newItems;
        }
    }

    public ItemDTO getItem() {
        if(this.items != null && this.items.length > 0)
            return this.items[0];
        return null;
    }
}
