package Configuration;
import java.sql.Connection;
import java.sql.DriverManager;

public class Config{
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3307/pbo_lastboss";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connect;

    public static Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            connect = DriverManager.getConnection(URL, USER, PASSWORD);
            System.err.println("Koneksi Database Berhasil");
        } catch (Exception e) {
            System.err.println("Koneksi Database Gagal");
            e.printStackTrace(); 
        }
        return connect;
    }
}