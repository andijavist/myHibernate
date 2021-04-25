import java.lang.reflect.Field;
import java.sql.*;

public class AddPerson {

    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        Person person = new Person();
        String classOf = person.getClass().getName();
        person.setPersonName("Gerasim");
        person.setPersonLastName("Bredpitov");
        create(person, classOf);
    }

    private static void create(Object object, String classOf) {

            String currentFieldContent;

        try {
            Class<?> objectClass = Class.forName(classOf);
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);

            Field[] fields = objectClass.getDeclaredFields();
            String table = objectClass.getAnnotation(NameTable.class).name();
            //creating table
            createTable(connection, fields, table);
            //making sql query
            String sqlQuery = getSQLQuery(fields, table);
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            //inserting data into table
            for (int i = 1, j = 0; j < fields.length; i++, j++) {
                currentFieldContent = (String) fields[j].get(objectClass.cast(object));
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

    private static void createTable(Connection connection, Field[] field, String table){
        try {
            Statement statement = connection.createStatement();
            StringBuffer query = new StringBuffer();
            query.append("CREATE TABLE " + table + " (id serial not NULL, ");
    //TODO table exist
            //example
//            String SQL = "CREATE TABLE developers " +
//                    "(id INTEGER not NULL, " +
//                    " name VARCHAR(50), " +
//                    " specialty VARCHAR (50), " +
//                    " salary INTEGER not NULL, " +
//                    " PRIMARY KEY (id))";
            for (int i = 0; i < field.length; i++)
                    query.append(field[i].getAnnotation(NameColumn.class).name()+" text,");
            query.append(" PRIMARY KEY (id))");
            statement.executeUpdate(query.toString());

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static String getSQLQuery(Field[] field, String table) {
        StringBuffer query = new StringBuffer();
        query.append("insert into " + table + " (");
        for (int i = 0; i < field.length; i++) {
            //TODO else+analyze Register
            query.append(field[i].getAnnotation(NameColumn.class).name());
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


