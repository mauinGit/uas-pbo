import Logical.Autentikasi;
import Model.Bus;
import Model.Customer;
import Model.User;
import java.util.ArrayList;
import javax.swing.*;

public class App {
    public static ArrayList<Bus> daftarBus = new ArrayList<>();
    public static void main(String[] args) {
        daftarBus.add(new Bus("Damri"));
        daftarBus.add(new Bus("Kaleng"));

        while (true) {
            String menu = JOptionPane.showInputDialog(
                    "Selamat Datang!!\n" +
                    "1. Login\n" +
                    "2. Register\n" +
                    "3. Keluar"
            );

            switch (menu) {
                case "1":
                    login(); 
                    return;
                case "2":
                    register();
                    break;
                case "3":
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Pilihan tidak valid.");
            }
        }
    }

    private static void login() {
        String email = JOptionPane.showInputDialog("Email:");
        String password = JOptionPane.showInputDialog("Password:");

        User user = Autentikasi.login(email, password);

        if (user != null) {
            JOptionPane.showMessageDialog(null, "Login Berhasil!");

            if (user instanceof Customer) {
                ((Customer) user).setBusList(daftarBus);
            }

            user.dashboard();

        } else {
            JOptionPane.showMessageDialog(null, "Email atau password salah.");
        }
    }

    private static void register() {
        String email = JOptionPane.showInputDialog("Email:");
        String password = JOptionPane.showInputDialog("Password:");

        boolean success = Autentikasi.register(email, password, "user");

        if (success) {
            JOptionPane.showMessageDialog(null, "Register Berhasil!");
        }
    }
}