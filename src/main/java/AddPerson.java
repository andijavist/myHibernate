import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class AddPerson {

        private static final String URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
        private static final String LOGIN = "postgres";
        private static final String PASSWORD = "postgres";
    public static void main(String[] args) {
            Person person = new Person();
            person.setName("Kot");
            person.setLastName("Kotikov");
            create(person);
    }

    private static void create(Person person) {
        try {
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            Class.forName("org.postgresql.Driver");

            Connection connection = DriverManager.getConnection(URL,LOGIN,PASSWORD);
            PreparedStatement statement = connection.prepareStatement
                    ("insert into users (first_name, last_name) values (?, ?)");

            String name = person.getName();
            statement.setString(1, name);

            String lastName = person.getLastName();
            statement.setString(2, lastName);

            statement.executeUpdate();

            System.out.println("Пользователь добавлен");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}


