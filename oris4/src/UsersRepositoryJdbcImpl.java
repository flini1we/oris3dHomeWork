import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UserRepository {

    private Connection connection;

    private static final String SQL_SELECT_FROM_USERS = "SELECT * FROM users";
    private static final String SQL_INSERT_USERS = "INSERT INTO users (first_name, last_name, age, email, phone_number, height) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_USERS = "UPDATE users SET first_name = ?, last_name = ?, age = ?, email = ?, phone_number = ?, height = ? WHERE id = ?";
    private static final String SQL_DELETE_USERS = "DELETE FROM users WHERE id = ?";
    private static final String SQL_SELECT_USER_BY_AGE = "SELECT * FROM users WHERE age = ?";
    private static final String SQL_SELECT_USER_BY_HEIGHT = "SELECT * FROM users WHERE height = ?";
    private static final String SQL_SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String SQL_SELECT_USER_BY_PHONE_NUMBER = "SELECT * FROM users WHERE phone_number = ?";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";

    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_FROM_USERS)) {

            List<User> result = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7));
                result.add(user);
            }
            return result;
        }
    }

    @Override
    public Optional<User> findById(Long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User(
                            resultSet.getLong(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7));
                    return Optional.of(user);
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public void save(User entity) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USERS)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setInt(3, entity.getAge());
            statement.setString(4, entity.getEmail());
            statement.setString(5, entity.getPhoneNumber());
            statement.setString(6, entity.getHeight());
            int addedUsers = statement.executeUpdate();
            System.out.println("Было добавлено " + addedUsers + " юзеров");
        }
    }

    @Override
    public void update(User entity) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USERS)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setInt(3, entity.getAge());
            statement.setString(4, entity.getEmail());
            statement.setString(5, entity.getPhoneNumber());
            statement.setString(6, entity.getHeight());
            statement.setLong(7, entity.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void remove(User entity) throws SQLException {
        removeById(entity.getId());
    }

    @Override
    public void removeById(Long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USERS)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public List<User> findAllByAge(Integer age) throws SQLException {
        List<User> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_AGE)) {
            statement.setInt(1, age);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getLong(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7)
                    );
                    result.add(user);
                }
            }
        }
        return result;
    }

    @Override
    public List<User> findAllByHeight(Integer height) throws SQLException {
        List<User> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_HEIGHT)) {
            statement.setInt(1, height);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getLong(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7)
                    );
                    result.add(user);
                }
            }
        }
        return result;
    }

    @Override
    public String addMultipleUsers(ArrayList<User> users) throws SQLException {
        if (users.isEmpty()) return "";
        String SQL_INSERT_MULTIPLE_USERS = "INSERT INTO users (first_name, last_name, age, email, phone_number, height) VALUES ";

        int size = users.size();
        for (int i = 0; i < size; i++) {
            SQL_INSERT_MULTIPLE_USERS += "(?,?,?,?,?,?)";
            if (i != size - 1) {
                SQL_INSERT_MULTIPLE_USERS += ", ";
            }
        }

        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_MULTIPLE_USERS)) {
            int index = 0;
            for (User user : users) {
                statement.setString(index * 6 + 1, user.getFirstName());
                statement.setString(index * 6 + 2, user.getLastName());
                statement.setInt(index * 6 + 3, user.getAge());
                statement.setString(index * 6 + 4, user.getEmail());
                statement.setString(index * 6 + 5, user.getPhoneNumber());
                statement.setString(index * 6 + 6, user.getHeight());
                index++;
            }
            statement.executeUpdate();
        }
        return SQL_INSERT_MULTIPLE_USERS;
    }

    public List<User> findAllByEmail(String email) throws SQLException {
        List<User> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_EMAIL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getLong(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7)
                    );
                    result.add(user);
                }
            }
        }
        return result;
    }

    public List<User> findAllByPhoneNumber(String phoneNumber) throws SQLException {
        List<User> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_PHONE_NUMBER)) {
            statement.setString(1, phoneNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getLong(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7)
                    );
                    result.add(user);
                }
            }
        }
        return result;
    }
}