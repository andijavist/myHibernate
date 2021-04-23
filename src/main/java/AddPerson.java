import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Locale;

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
            Class.forName("org.postgresql.Driver");

            String table =classOf.toLowerCase();
            Connection connection = DriverManager.getConnection(URL,LOGIN,PASSWORD);
            for (int i = 1, j=0; j < fields.length; i++, j++) {

            String currentFeild = (String)fields[j].getName();
                String currentFeildContent = (String)fields[j].get(objectClass.cast(object));

                PreparedStatement statement = getPreparedStatement(table, connection, currentFeild);
                statement.setString(i,currentFeildContent);

            statement.executeUpdate();
            }

            System.out.println("Пользователь добавлен");

        } catch (ClassNotFoundException
                | SQLException
                | NullPointerException
                | ClassCastException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static PreparedStatement getPreparedStatement(String table, Connection connection, String currentFeild) throws SQLException {
        PreparedStatement statement = connection.prepareStatement
            ("insert into "+ table +" ("
                               + currentFeild +
                                        ") values (?)");
        return statement;
    }
}


