package io.hexlet;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void save(User user) throws SQLException {
        if (user.getId() == null) {
            var sql = "INSERT INTO users (username, phone) VALUES (?, ?)";
            try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPhone());
                statement.executeUpdate();
                var generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("DB have not returned an id after saving an entity");
                }
            }
        } else {
            var sql2 = "UPDATE users SET username = ?, phone = ? WHERE id = ?";
            try (var statement = connection.prepareStatement(sql2)) {
                statement.setString(1, user.getUsername());
                statement.setString(2, user.getPhone());
                statement.setLong(3, user.getId());
                statement.executeUpdate();
            }
            }
        }

    public Optional<User> find(Long id) throws SQLException {
        var sql = "SELECT * FROM users WHERE id = ?";
        try (var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                var username = resultSet.getString("username");
                var phone = resultSet.getString("phone");
                var user = new User(username, phone);
                user.setId(id);
                return Optional.of(user);
            }
            return Optional.empty();
        }
    }

    public void printTable() throws SQLException{
        var sql3 = "SELECT * FROM users";
        try (var statement3 = connection.createStatement()) {
            // Здесь вы видите указатель на набор данных в памяти СУБД
            var resultSet = statement3.executeQuery(sql3);
            // Набор данных — это итератор
            // Мы перемещаемся по нему с помощью next() и каждый раз получаем новые значения
            while (resultSet.next()) {
                System.out.printf("%s %s\n",
                        resultSet.getString("username"), resultSet.getString("phone"));
            }
        }
    }

    public void createTable() throws SQLException{
        var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
        // Чтобы выполнить запрос, создадим объект statement
        try (var statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    public void delete(Long id) throws SQLException {
        var sql = "DELETE FROM users WHERE id = ?";
        try (var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
