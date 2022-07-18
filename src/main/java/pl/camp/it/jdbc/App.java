package pl.camp.it.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static Connection connection;

    public static void main(String[] args) {
        User user = new User(26, "Wiesiek", "Jakis", "wiesio01", "wiesiek11");
        connect();
        //persistUser(user);
        //deleteUser(user);
        //updateUser(user);
        //persistUser2(user);
        List<User> users = getAllUsers2();
        System.out.println(users);

        User user2 = getUserByLogin2("wiesio01");
        System.out.println(user2);
        user2.setPassword("tajnehaslo");
        //updateUser2(user2);
        deleteUser2(user2);
        disconnect();
    }

    public static void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            App.connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/test5", "root", "");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void persistUser(User user) {
        try {
            String sql = new StringBuilder().append("INSERT INTO tuser (name, surname, login, password) VALUES ('")
                    .append(user.getName())
                    .append("','")
                    .append(user.getSurname())
                    .append("','")
                    .append(user.getLogin())
                    .append("','")
                    .append(user.getPassword())
                    .append("');").toString();

            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void persistUser2(User user) {
        try {
            String sql = "INSERT INTO tuser (name, surname, login, password) VALUES (?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteUser(User user) {
        try {
            String sql = "DELETE FROM tuser WHERE id = " + user.getId();
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteUser2(User user) {
        try {
            String sql = "DELETE FROM tuser WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateUser(User user) {
        try {
            String sql = new StringBuilder().append("UPDATE tuser ")
                    .append("SET name = '")
                    .append(user.getName())
                    .append("', surname = '")
                    .append(user.getSurname())
                    .append("', login = '")
                    .append(user.getLogin())
                    .append("', password = '")
                    .append(user.getPassword())
                    .append("' WHERE id = ")
                    .append(user.getId())
                    .append(";").toString();

            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateUser2(User user) {
        try {
            String sql = "UPDATE tuser SET name = ?, surname = ?, login = ?, password = ? WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tuser;";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                result.add(new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("login"),
                        rs.getString("password")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static List<User> getAllUsers2() {
        List<User> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tuser;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                result.add(new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("login"),
                        rs.getString("password")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static User getUserByLogin(String login) {
        try {
            String sql = "SELECT * FROM tuser WHERE login = '" + login + "';";
            System.out.println(sql);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            if(rs.next()) {
                return new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("login"),
                        rs.getString("password"));
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getUserByLogin2(String login) {
        try {
            String sql = "SELECT * FROM tuser WHERE login = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) {
                return new User(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("login"),
                        rs.getString("password"));
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void disconnect() {
        try {
            App.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
