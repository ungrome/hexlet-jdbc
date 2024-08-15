package io.hexlet;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Application {
    // Нужно указывать базовое исключение,
    // потому что выполнение запросов может привести к исключениям
    public static void main(String[] args) throws SQLException {
        // Создаем соединение с базой в памяти
        // База создается прямо во время выполнения этой строчки
        // Здесь hexlet_test — это имя базы данных
        var conn = DriverManager.getConnection("jdbc:h2:mem:hexlet_test");

        var usersDao = new UserDAO(conn);

        usersDao.createTable();
        Map<String,String> data = Map.of(
                "Arbuzova", "2345223",
                "Bystryakov", "4644788",
                "Vorobyeva", "4748599"
                );
        for (Map.Entry<String, String> entry: data.entrySet()) {
            var user = new User(entry.getKey(), entry.getValue());
            usersDao.save(user);
        }
        usersDao.printTable();
        var user1 = new User("Voschikov", "2495254");
        usersDao.save(user1);
        usersDao.delete(2L);
        usersDao.printTable();
    }
}