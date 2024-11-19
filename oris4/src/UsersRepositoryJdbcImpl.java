import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UserRepository {

    private Connection connection;
    private static final String SQL_SELECT_FROM_DRIVER = "select * from driver";
    private static final String SQL_INSERT_USER = "INSERT INTO driver (first_name, last_name, age, email, phone_number, address) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE driver SET first_name = ?, last_name = ?, age = ?, email = ?, phone_number = ?, address = ? WHERE id = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM driver WHERE id = ?";
    private static final String SQL_SELECT_USER_BY_AGE = "SELECT * FROM driver WHERE age = ?";
    private static final String SQL_SELECT_USER_BY_HEIGHT = "SELECT * FROM driver WHERE height = ?";
    private static final String SQL_SELECT_USER_BY_EMAIL = "SELECT * FROM driver WHERE email = ?";
    private static final String SQL_SELECT_USER_BY_PHONE_NUMBER = "SELECT * FROM driver WHERE phone_number = ?";

    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_SELECT_FROM_DRIVER);

        List<User> result = new ArrayList<>();

        while (resultSet.next()) {
            User user = new User(
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getInt("age"),
                    resultSet.getString("height"),
                    resultSet.getString("phone_number"),
                    resultSet.getString("address"));
            result.add(user);
        }
        return result;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(User entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER);
        statement.setString(1, entity.getFirstName());
        statement.setString(2, entity.getLastName());
        statement.setInt(3, entity.getAge());
        statement.setString(4, entity.getEmail());
        statement.setString(5, entity.getPhoneNumber());
        statement.setString(6, entity.getHeight());
        var addedUsers = statement.executeUpdate();
        System.out.println("Было добавлено" + addedUsers + "юзера");
    }

    @Override
    public void update(User entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER);
        statement.setString(1, entity.getFirstName());
        statement.setString(2, entity.getLastName());
        statement.setInt(3, entity.getAge());
        statement.setString(4, entity.getEmail());
        statement.setString(5, entity.getPhoneNumber());
        statement.setString(6, entity.getHeight());
        statement.setLong(7, entity.getId());
    }

    @Override
    public void remove(User entity) throws SQLException {
        removeById(entity.getId());
    }

    @Override
    public void removeById(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER);
        statement.setLong(1, id);
    }

    @Override
    public List<User> findAllByAge(Integer age) throws SQLException {
        List<User> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_AGE);
        statement.setInt(1, age);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            User user = new User(
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getInt("age"),
                    resultSet.getString("email"),
                    resultSet.getString("phone_number"),
                    resultSet.getString("height")
            );
            result.add(user);
        }
        return result;
    }

    @Override
    public List<User> findAllByHeight(Integer height) throws SQLException {
        List<User> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_HEIGHT);
        statement.setInt(1, height);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            User user = new User(
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getInt("age"),
                    resultSet.getString("email"),
                    resultSet.getString("phone_number"),
                    resultSet.getString("height")
            );
            result.add(user);
        }
        return result;
    }

    @Override
    public String addMultipleUsers(ArrayList<User> users) throws SQLException {
        if (users.isEmpty()) return "";
        String SQL_INSERT_MULTIPLE_USERS = "INSERT INTO driver (first_name, last_name, age, email, phone_number, address) VALUES ";
        int size = users.size();
        for (int i = 0; i < size; i++) {
            SQL_INSERT_MULTIPLE_USERS += "(?,?,?,?,?,?)";
            if (i != size - 1) {
                SQL_INSERT_MULTIPLE_USERS += ", ";
            }
        }
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT_MULTIPLE_USERS);
        int index = 0;
        for (User user : users) {
            statement.setString(index * 6 + 1, user.getFirstName());
            statement.setString(index * 6 + 2, user.getLastName());
            statement.setInt(index * 6 +  3, user.getAge());
            statement.setString(index * 6 +  4, user.getEmail());
            statement.setString(index * 6 +  5, user.getPhoneNumber());
            statement.setString(index * 6 + 6, user.getHeight());
            index++;
        }
        return SQL_INSERT_MULTIPLE_USERS;
    }

    public List<User> findAllByEmail(String mail) throws SQLException {
        List<User> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_EMAIL);
        statement.setString(1, mail);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            User user = new User(
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getInt("age"),
                    resultSet.getString("email"),
                    resultSet.getString("phone_number"),
                    resultSet.getString("address")
            );
            result.add(user);
        }
        return result;
    }

    public List<User> findAllByPhoneNumber(String phoneNumber) throws SQLException {
        List<User> result = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_PHONE_NUMBER);
        statement.setString(1, phoneNumber);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            User user = new User(
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getInt("age"),
                    resultSet.getString("email"),
                    resultSet.getString("phone_number"),
                    resultSet.getString("address")
            );
            result.add(user);
        }
        return result;
    }
}