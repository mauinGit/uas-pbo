package Logical;

import Configuration.Config;
import Model.Admin;
import Model.Customer;
import Model.User;

import javax.swing.*;
import java.sql.*;

public class Autentikasi {

    public static boolean register(String email, String password, String role) {
        try {
            Connection conn = Config.getConnection();

            String sql = "INSERT INTO user(email, password, role) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, email);
            pst.setString(2, password);
            pst.setString(3, role);

            pst.executeUpdate();
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Register");
            e.printStackTrace();
            return false;
        }
    }

    public static User login(String email, String password) {
        try {
            Connection conn = Config.getConnection();

            String sql = "SELECT * FROM user WHERE email=? AND password=?";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, email);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");

                if (role.equals("admin")) {
                    return new Admin(email);
                } else {
                    return new Customer(email);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Login");
            e.printStackTrace();
        }

        return null;
    }
}
