package View;

import Configuration.Config;
import Model.Bus;
import Model.Customer;
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class MilihKursi extends JFrame {

    private Bus bus;
    private String email;
    private Customer customer;

    public MilihKursi(Bus bus, String email, Customer customer) {
        this.bus = bus;
        this.email = email;
        this.customer = customer;

        loadBookedSeats();

        setTitle("Pilih Kursi - " + bus.getNama());
        setSize(450, 700);

        JPanel seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(0, 5, 5, 5));
        add(seatPanel, BorderLayout.CENTER);

        generateSeatButtons(seatPanel);

        JButton backButton = new JButton("Kembali");
        backButton.addActionListener(e -> {
            dispose(); 
            customer.dashboard();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void loadBookedSeats() {
        try {
            Connection conn = Config.getConnection();

            String sql = "SELECT kursi_number FROM booking_kursi WHERE bus_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, bus.getId());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int seatNum = rs.getInt("kursi_number");
                bus.isiSeat(seatNum);
            }

            pst.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateSeatButtons(JPanel panel) {
        int totalSeat = bus.getTotalSeat();
        int seatIndex = 0;

        while (seatIndex < totalSeat) {
            panel.add(createSeatButton(++seatIndex));
            panel.add(createSeatButton(++seatIndex));
            panel.add(new JLabel(" "));
            if (seatIndex < totalSeat) panel.add(createSeatButton(++seatIndex)); else panel.add(new JLabel());
            if (seatIndex < totalSeat) panel.add(createSeatButton(++seatIndex)); else panel.add(new JLabel());
        }
    }

    private JButton createSeatButton(int seatNumber) {
        JButton btn = new JButton(String.valueOf(seatNumber));
        btn.setOpaque(true);
        btn.setBorderPainted(false);

        updateColor(btn, seatNumber);

        btn.addActionListener(e -> {
            if (bus.seatKosong(seatNumber)) {

                bus.isiSeat(seatNumber);
                updateSeatInDatabase(seatNumber);
                updateColor(btn, seatNumber);
                      
                showFormDanStruk(seatNumber);       

                JOptionPane.showMessageDialog(this,
                        "Seat " + seatNumber + " berhasil dipesan!");

            } else {
                JOptionPane.showMessageDialog(this,
                        "Seat " + seatNumber + " sudah terisi!");
            }
        });

        return btn;
    }

    private void updateColor(JButton btn, int seatNumber) {
        if (bus.seatKosong(seatNumber)) {
            btn.setBackground(Color.GREEN);
        } else {
            btn.setBackground(Color.RED);
        }
    }

    private void updateSeatInDatabase(int seatNumber) {
        try {
            Connection conn = Config.getConnection();
            String sql = "INSERT INTO booking_kursi (bus_id, kursi_number, email) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, bus.getId());
            pst.setInt(2, seatNumber);
            pst.setString(3, email);

            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showFormDanStruk(int seatNumber) {
        String namaPenumpang = JOptionPane.showInputDialog(this,
                "Masukkan nama penumpang:");
        if (namaPenumpang == null || namaPenumpang.trim().isEmpty()) return;

        String noHp = JOptionPane.showInputDialog(this,
                "Masukkan nomor HP:");
        if (noHp == null || noHp.trim().isEmpty()) return;

        int hargaPerSeat = 0;
        if (bus.getNama().equalsIgnoreCase("Damri")) {
            hargaPerSeat = 15000;
        } else if (bus.getNama().equalsIgnoreCase("Kaleng")) {
            hargaPerSeat = 10000;
        }

        int totalBayar = hargaPerSeat;

        String struk =
                "==============================\n" +
                "        E-TIKET BUS\n" +
                "==============================\n" +
                "Nama Penumpang : " + namaPenumpang + "\n" +
                "No HP          : " + noHp + "\n" +
                "Bus            : " + bus.getNama() + "\n" +
                "Kursi          : " + seatNumber + "\n" +
                "Total Bayar    : Rp " + totalBayar + "\n" +
                "==============================";

        JOptionPane.showMessageDialog(this, struk, "STRUK TIKET", JOptionPane.INFORMATION_MESSAGE);
    }
}