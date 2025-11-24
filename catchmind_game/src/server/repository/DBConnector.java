package server.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final String DB_DRIVER_CLASS = "org.mariadb.jdbc.Driver";
    private static final String DB_URL = "jdbc:mariadb://192.168.74.1:3306/catchmind_words";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "jungyuminee";

    public static Connection connectDB() {
        Connection conn = null;

        try {
            Class.forName(DB_DRIVER_CLASS);
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("DB 연결 성공");

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("DB 연결 실패");
            e.printStackTrace();
        }

        return conn;
    }

    public static void main(String[] args) {
        connectDB();
    }
}
