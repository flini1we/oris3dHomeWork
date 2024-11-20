import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainRepository {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            if (connection == null) {
                throw new SQLException("Ошибка подключения к базе данных");
            }

            UserRepository userRepository = new UsersRepositoryJdbcImpl(connection);

            System.out.println("Введите кол-во юзеров которые хотите добавить");
            int number = scanner.nextInt();
            scanner.nextLine();

            ArrayList<User> usersList = new ArrayList<>();

            for (int i = 0; i < number; i++) {
                System.out.println("Введите данные о юзере " + (i + 1));

                Long id = scanner.nextLong();
                scanner.nextLine();
                System.out.println("Введите имя:");
                String firstName = scanner.nextLine();
                System.out.println("Введите фамилию:");
                String lastName = scanner.nextLine();
                System.out.println("Введите возраст:");
                int age = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Введите email:");
                String email = scanner.nextLine();
                System.out.println("Введите номер телефона:");
                String phoneNumber = scanner.nextLine();
                System.out.println("Введите рост:");
                String height = scanner.nextLine();

                User currentUser = new User(id, firstName, lastName, age, email, phoneNumber, height);
                usersList.add(currentUser);
            }

            userRepository.addMultipleUsers(usersList);
            System.out.println("Добавленные пользователи:");

            for (User user : usersList) {
                System.out.println(user.getId() + " " + user.getFirstName() + " " + user.getLastName() + " " + user.getAge() + " " + user.getEmail() + " " + user.getPhoneNumber() + " " + user.getHeight());
            }
            System.out.println("------------------");
            List<User> findByAge = userRepository.findAllByAge(19);
            for (User user : findByAge) {
                System.out.println(user.getId() + " " + user.getFirstName() + " " + user.getLastName() + " " + user.getAge());
            }

        }
    }
}