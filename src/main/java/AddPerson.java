import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class AddPerson {

        private static final String URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
        private static final String LOGIN = "postgres";
        private static final String PASSWORD = "postgres";

    public static void main(String[] args) {

            try {
                BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
                Class.forName("org.postgresql.Driver");

                Connection connection = DriverManager.getConnection(URL,LOGIN,PASSWORD);
                PreparedStatement statement = connection.prepareStatement
                        ("insert into users (first_name, last_name) values (?, ?)");

                System.out.println("Enter author name:");
                String name = read.readLine();
                statement.setString(1, name);

                System.out.println("Enter author last name:");
                String lastName = read.readLine();
                statement.setString(2, lastName);

                statement.executeUpdate();

            } catch (ClassNotFoundException | SQLException | IOException e) {
                e.printStackTrace();
            }
    }
        }


