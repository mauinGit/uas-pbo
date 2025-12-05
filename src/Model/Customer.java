package Model;

import java.util.ArrayList;
import javax.swing.*;

import View.MilihKursi;

public class Customer extends User {

    private ArrayList<Bus> busList;
    private String email;

    public Customer(String username) {
        super(username, "user");
        this.email = username;
    }

    public void setBusList(ArrayList<Bus> busList) {
        this.busList = busList;
    }

    @Override
    public void dashboard() {

        String menu = JOptionPane.showInputDialog(
                "--- Dashboard Customer ---\n" +
                "1. Pilih Kursi\n" +
                "2. Logout"
        );

        if (menu == null) return;

        switch (menu) {
            case "1":
                pilihKursi();
                break; // SELESAI → tidak kembali ke dashboard

            case "2":
                return;

            default:
                JOptionPane.showMessageDialog(null, "Pilihan tidak valid");
                dashboard();
        }
    }

    private void tampilkanBus() {
        String info = "";
        for (int i = 0; i < busList.size(); i++) {
            Bus b = busList.get(i);
            info += (i + 1) + ". " + b.getNama() +
                    " (" + b.getTotalSeat() + " seat)\n";
        }

        JOptionPane.showMessageDialog(null, info);
    }

    private void pilihKursi() { 
        String list = "";
        for (int i = 0; i < busList.size(); i++) {
            Bus b = busList.get(i);
            list += (i + 1) + ". " + b.getNama() +
                    " (" + b.getTotalSeat() + " seat)\n";
        }

        String pilih = JOptionPane.showInputDialog("Pilih bus:\n" + list);
        if (pilih == null) return;

        int index = Integer.parseInt(pilih) - 1;
        Bus bus = busList.get(index);

        new MilihKursi(bus, this.email, this);
    }
}
