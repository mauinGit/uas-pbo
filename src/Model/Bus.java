package Model;

public class Bus {
    private int id;
    private String nama;
    private int totalSeat;
    private boolean[] seats;  

    public Bus(String nama) {
        this.nama = nama;

        if (nama.equalsIgnoreCase("Damri")) {
            this.id = 1;
            this.totalSeat = 32;
        } 
        else if (nama.equalsIgnoreCase("Kaleng")) {
            this.id = 2;
            this.totalSeat = 20;
        } 
        this.seats = new boolean[this.totalSeat];
    }

    public String getNama() {
        return nama;
    }

    public int getId() { 
        return id; 
    }

    public int getTotalSeat() {
        return totalSeat;
    }

    public boolean[] getSeats() {
        return seats;
    }

    public boolean seatKosong(int nomor) {
        return !seats[nomor - 1];
    }

    public void isiSeat(int nomor) {
        seats[nomor - 1] = true;
    }
}
