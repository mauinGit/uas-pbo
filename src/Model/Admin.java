package Model;

import javax.swing.*;
import java.sql.*;
import Configuration.Config;

public class Admin extends User {

    public Admin(String email) {
        super(email, "admin");
    }

    @Override
    public void dashboard() {
        while (true) {
            String menu = JOptionPane.showInputDialog(
                    "--- Dashboard Admin ---\n" +
                    "1. Lihat Status Kursi Bus\n" +
                    "2. Reset Kursi Bus\n" +
                    "3. Logout"
            );

            if (menu == null) return;

            switch (menu) {
                case "1":
                    lihatStatusKursi();
                    break;

                case "2":
                    resetKursi();
                    break;

                case "3":
                    return;
            }
        }
    }

    private void lihatStatusKursi() {
        try {
            String bus = JOptionPane.showInputDialog("1. Damri\n2. Kaleng\nPilih bus:");
            int busId = bus.equals("1") ? 1 : 2;

            Connection conn = Config.getConnection();
            String sql = "SELECT kursi_number, email FROM booking_kursi WHERE bus_id = ? ORDER BY kursi_number";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, busId);
            ResultSet rs = pst.executeQuery();

            String info = "Seat yang sudah dibooking:\n\n";
            boolean ada = false;

            while (rs.next()) {
                ada = true;
                info += "Seat " + rs.getInt("kursi_number") +
                        " → " + rs.getString("email") + "\n";
            }

            if (!ada) info = "Semua seat masih kosong.";

            JOptionPane.showMessageDialog(null, info);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetKursi() {
        try {
            String bus = JOptionPane.showInputDialog("1. Damri\n2. Kaleng\nPilih bus:");
            int busId = bus.equals("1") ? 1 : 2;

            int confirm = JOptionPane.showConfirmDialog(null,
                    "Yakin ingin reset semua seat bus ini?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) return;

            Connection conn = Config.getConnection();
            String sql = "DELETE FROM booking_kursi WHERE bus_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, busId);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null,
                    "Semua seat pada bus ini berhasil di-reset!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
