import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.sql.*;

public class AddPerson {

        private static final String URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
        private static final String LOGIN = "postgres";
        private static final String PASSWORD = "postgres";
    public static void main(String[] args) {
            Person person = new Person();
            String classOf = person.getClass().getName();
            person.setName("Bob");
            person.setLastName("Cays");
            create(person,classOf);
    }

    private static void create(Object object, String classOf) {

        try {
            Class<?> objectClass = Class.forName(classOf);
            Field[] fields=objectClass.getDeclaredFields();
            for (Field f:fields) {
                System.out.println(f);
            }

            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            Class.forName("org.postgresql.Driver");

            Connection connection = DriverManager.getConnection(URL,LOGIN,PASSWORD);
            PreparedStatement statement = connection.prepareStatement
                    ("insert into users (first_name, last_name) values (?, ?)");

            String name = (String)fields[0].get(objectClass.cast(object));
            statement.setString(1, name);

            String lastName = (String)fields[1].get(objectClass.cast(object));
            statement.setString(2, lastName);

            statement.executeUpdate();

            System.out.println("Пользователь добавлен");

        } catch (ClassNotFoundException | SQLException | NullPointerException | ClassCastException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}


