import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainRepository {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        UserRepository userRepository = new UsersRepositoryJdbcImpl(connection);

        List<User> users = userRepository.findAll();
        users.forEach(user -> System.out.println(user.getFirstName()));

        System.out.println("Введите кол-во юзеров которые хотите добавить");
        int number = scanner.nextInt();
        ArrayList<User> usersList = new ArrayList<>();

        for (int i = 0; i < number; i++) {
            System.out.println("Введите данные о юзере " + (i + 1));

            Long id = scanner.nextLong();
            String firstName = scanner.nextLine();
            String lastName = scanner.nextLine();
            int age = scanner.nextInt();
            String email = scanner.nextLine();
            String phoneNumber = scanner.nextLine();
            String height = scanner.nextLine();

            User currenUser = new User(id, firstName, lastName, age, email, phoneNumber, height);
            usersList.add(currenUser);
        }

        String sqlInjection = userRepository.addMultipleUsers(usersList);
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInjection);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Long id = resultSet.getLong(1);
            String firstName = resultSet.getString(2);
            System.out.println(id + " " + firstName);
        }
    }
}