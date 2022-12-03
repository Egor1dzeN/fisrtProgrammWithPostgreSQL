import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static boolean isEnter = false;
    static final public String user = "postgres";
    static final public String pass = "Partner25";
    static final public String url = "jdbc:postgresql://localhost:5432/postgres";

    public static void main(String[] args) throws SQLException, InterruptedException {
        Scanner in = new Scanner(System.in);
        Connection connection = DriverManager.getConnection(url, user, pass);
        Statement statement = connection.createStatement();
        String loginEnter = "";
        while (true) {
            System.out.flush();
            System.out.println("1. Войти\n" +
                    "2. Регистрация\n" +
                    "3. Выход\n" + (isEnter ? "Вы вошли " + loginEnter : "Вы не вошли"));
            int command = in.nextInt();
            if (command == 3) {
                System.out.println("До свидания");
                return;
            }
            while (command == 1) {
                System.out.print("Введите логин: ");
                String login = in.next();
                System.out.print("Введите пароль: ");
                String password = in.next();
                String SQL_ENTER = "select * from db where login = '" + login + "'";
                ResultSet result = statement.executeQuery(SQL_ENTER);
                int commandEnter = 0;
                while (result.next()) {
                    String password1 = result.getString("password");
                    if (password1.equals(password)) {
                        System.out.println("Вы вошли");
                        System.out.println("Добрый день " + login);
                        TimeUnit.SECONDS.sleep(1);
                        loginEnter = login;
                        isEnter = true;
                        commandEnter = 2;
                    } else {
                        isEnter = false;
                        System.err.println("Логин или пароль не правильный");
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("1. Войти еще раз\n" + "2. Выйти в главное меню");
                        commandEnter = in.nextInt();
                        if (commandEnter == 1) {
                            break;
                        }
                    }
                }
                if (commandEnter == 2) {
                    break;
                }
            }
            while (command == 2) {
                System.out.print("Придумайте логин: ");
                String login = in.next();
                System.out.print("Придумайте пароль: ");
                String password = in.next();
                System.out.print("Подтвердите пароль: ");
                String password1 = in.next();
                boolean commaneRegister = true;
                if (password1.equals(password)) {
                    String SQL_LOGIN = "select count(*) from db where login = '" + login + "'";
                    ResultSet result1 = statement.executeQuery(SQL_LOGIN);
                    result1.next();
                    if (result1.getInt("count") == 0) {
                        String SQL_Registration = "select count(*) from db";
                        ResultSet result = statement.executeQuery(SQL_Registration);
                        result.next();
                        int kolid = result.getInt("count") + 1;
                        String SQL_Registration1 = "insert into db (id, login, password) values (" + Integer.toString(kolid) + ",'" + login + "','" + password + "')";
                        statement.executeUpdate(SQL_Registration1);
                        System.out.println("Вы зарегестрированы");
                        isEnter = true;
                        loginEnter = login;
                        break;
                    } else {
                        System.err.println("Пользователь с таким логином уже существует(");
                        TimeUnit.SECONDS.sleep(1);
                        commaneRegister = false;
                    }
                } else {
                    System.err.println("Пароли не совпадают(");
                    TimeUnit.SECONDS.sleep(1);
                    commaneRegister = false;
                }
                System.out.println("1. Попытаться еще раз\n" + "2. Выйти в главное меню");
                int commandRegister = in.nextInt();
                if (commandRegister == 2) {
                    break;
                }
            }
        }
    }
}