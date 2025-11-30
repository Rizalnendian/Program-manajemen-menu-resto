import java.util.*;
import java.io.*;

public class Pesanan {
    private List<MenuItem> daftarPesanan;
    private String namaPelanggan;

    public Pesanan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
        daftarPesanan = new ArrayList<>();
    }

    public void tambahPesanan(MenuItem item) {
        daftarPesanan.add(item);
    }

    public List<MenuItem> getDaftarPesanan() {
        return Collections.unmodifiableList(daftarPesanan);
    }


    public double hitungTotal() {
        double subtotal = 0.0;
        double totalDiskonPercent = 0.0;

        for (MenuItem m : daftarPesanan) {
            if (m instanceof Diskon) {
                totalDiskonPercent += ((Diskon) m).getDiskonPercent();
            } else {
                subtotal += m.getHarga();
            }
        }

        if (totalDiskonPercent > 100) totalDiskonPercent = 100;
        double diskonAmount = subtotal * (totalDiskonPercent / 100.0);
        return subtotal - diskonAmount;
    }

    public String generateStruk() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== STRUK PESANAN =====\n");
        sb.append("Nama Pelanggan: ").append(namaPelanggan).append("\n");
        sb.append("-------------------------\n");
        for (MenuItem m : daftarPesanan) {
            if (m instanceof Diskon) {
                sb.append(String.format("[Diskon] %s - %.2f%%\n", m.getNama(), ((Diskon)m).getDiskonPercent()));
            } else {
                sb.append(String.format("%s - Rp %.2f\n", m.getNama(), m.getHarga()));
            }
        }
        sb.append("-------------------------\n");
        sb.append(String.format("Total Bayar: Rp %.2f\n", hitungTotal()));
        sb.append("=========================\n");
        return sb.toString();
    }

    public void saveStrukToFile(String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            bw.write(generateStruk());
        }
    }
}
