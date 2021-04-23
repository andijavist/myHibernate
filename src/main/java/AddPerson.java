import java.sql.*;

public class AddPerson {

        //первую строку просто надо запомнить или содрать тут https://jdbc.postgresql.org/documentation/head/connect.html
        //препод вместо localhost почему то пишет 127.0.0.1 (коненчо одно и то же, но почему то именно IP, а не DNS)
        private static final String URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
        private static final String LOGIN = "postgres";
        private static final String PASSWORD = "postgres";
        protected void doAdd(String name, String surname) {
            //ОЧЕНЬ ОБЯЗАТЕЛЬНАЯ ТЕМА ДЯЛ ЗАГРУЗЧИКА КЛАССОВ!!!!!!!!!!!!!!!!
            try {
                Class.forName("org.postgresql.Driver");
                Connection connection = DriverManager.getConnection(URL,LOGIN,PASSWORD);
                PreparedStatement statement = connection.prepareStatement("insert into users " +

                        "(first_name, last_name) values (?, ?)");
                System.out.println("Enter author name:");
                //TODO
                String param = scanner.nextLine();

                statement.setString(1, param);

                System.out.println("Enter author last name:");
                param = scanner.nextLine();
                statement.setString(2, param);

                statement.executeUpdate();

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }


        }
}
