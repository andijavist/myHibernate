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
        create(person, classOf);
    }

    private static void create(Object object, String classOf) {

        try {
            Class<?> objectClass = Class.forName(classOf);
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            Field[] fields = objectClass.getDeclaredFields();
            String table = classOf.toLowerCase();
            String sqlQuery = getSQLQuery(fields, table);
            System.out.println(sqlQuery);
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            String currentFieldContent;
            for (int i = 1, j = 0; j < fields.length; i++, j++) {
                currentFieldContent = (String) fields[j].get(objectClass.cast(object));
                System.out.println(currentFieldContent);
                statement.setString(i, currentFieldContent);
//                TODO statement.addBatch();
            }
//            TODO statement.executeBatch();
            statement.executeUpdate();
            System.out.println("Пользователь добавлен");

        } catch (ClassNotFoundException
                | SQLException
                | NullPointerException
                | ClassCastException
                | IllegalAccessException
                e) {
            e.printStackTrace();
        }
    }

    private static String getSQLQuery(Field[] field, String table) {
        StringBuffer query = new StringBuffer();
        query.append("insert into " + table + " (");
        for (int i = 0; i < field.length; i++) {
            query.append(field[i].getName().toLowerCase());
            if (i != field.length - 1)
                query.append(", ");
        }
        query.append(") values (");
        for (int i = 0; i < field.length; i++) {
            if (i != field.length - 1)
                query.append("?,");
            if (i == field.length - 1)
                query.append("?");
        }
        query.append(")");
        query.toString();
        return query.toString();
    }
}


