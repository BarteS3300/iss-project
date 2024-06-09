package common.domain;

import java.util.List;

public class Order implements common.domain.Entity<Long>{

    private Long id;
    private User user;
    private List<Item> items;

    public Order(User user, List<Item> items) {
        this.user = user;
        this.items = items;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
