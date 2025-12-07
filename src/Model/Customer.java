package Model;

import View.MilihKursi;
import java.util.ArrayList;
import javax.swing.*;

public class Customer extends User {

    private ArrayList<Bus> busList;
    private String email;

    public Customer(String email) {
        super(email, "user");
        this.email = email;
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
                break;

            case "2":
                return;

            default:
                JOptionPane.showMessageDialog(null, "Pilihan tidak valid");
                dashboard();
        }
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

