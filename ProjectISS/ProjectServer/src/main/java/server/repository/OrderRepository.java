package server.repository;

import common.domain.Item;
import common.domain.User;
import server.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

public class OrderRepository {

    private JdbcUtils dbUtils;

    public OrderRepository(Properties props) {
        dbUtils = new JdbcUtils(props);
    }

    public void addOrder(User user, List<Item> items) {
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement statent = connection.prepareStatement("insert into Orders (user) values (?)")) {
            statent.setLong(1, user.getId());
            int result = statent.executeUpdate();
            if (result == 1) {
                PreparedStatement statement1 = connection.prepareStatement("SELECT last_insert_rowid()");
                ResultSet resultSet = statement1.executeQuery();
                if (resultSet.next()) {
                    Long id = resultSet.getLong(1);
                    PreparedStatement statement2 = connection.prepareStatement("insert into OrderItems (orderId, itemId, quantity) values (?, ?, ?)");
                    for (Item item : items) {
                        statement2.setLong(1, id);
                        statement2.setLong(2, item.getId());
                        statement2.setInt(3, item.getQuantity());
                        statement2.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
