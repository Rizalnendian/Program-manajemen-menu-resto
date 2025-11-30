import java.util.*;
import java.io.*;

public class Menu {
    private List<MenuItem> items;

    public Menu() {
        items = new ArrayList<>();
    }

    public void tambahItem(MenuItem item) {
        items.add(item);
    }

    public List<MenuItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void tampilSemuaMenu() {
        if (items.isEmpty()) {
            System.out.println("Menu kosong.");
            return;
        }
        int idx = 1;
        for (MenuItem m : items) {
            System.out.print(idx + ". ");
            m.tampilMenu();
            idx++;
        }
    }

    public MenuItem ambilItem(int index) throws MenuItemNotFoundException {
        if (index < 0 || index >= items.size()) {
            throw new MenuItemNotFoundException("Item menu pada indeks " + index + " tidak ditemukan.");
        }
        return items.get(index);
    }


    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (MenuItem m : items) {
                bw.write(m.toDataString());
                bw.newLine();
            }
        }
    }


    public void loadFromFile(String filename) throws IOException {
        items.clear();
        File f = new File(filename);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split("\\|");
                if (parts.length == 0) continue;
                String tipe = parts[0];
                try {
                    switch (tipe) {
                        case "Makanan":

                            if (parts.length >= 5) {
                                String nama = parts[1];
                                double harga = Double.parseDouble(parts[2]);
                                String kategori = parts[3];
                                String jenis = parts[4];
                                tambahItem(new Makanan(nama, harga, kategori, jenis));
                            }
                            break;
                        case "Minuman":

                            if (parts.length >= 5) {
                                String nama = parts[1];
                                double harga = Double.parseDouble(parts[2]);
                                String kategori = parts[3];
                                String jenis = parts[4];
                                tambahItem(new Minuman(nama, harga, kategori, jenis));
                            }
                            break;
                        case "Diskon":

                            if (parts.length >= 4) {
                                String nama = parts[1];
                                double percent = Double.parseDouble(parts[2]);
                                String kategori = parts[3];
                                tambahItem(new Diskon(nama, percent, kategori));
                            }
                            break;
                        default:

                            break;
                    }
                } catch (NumberFormatException nfe) {

                }
            }
        }
    }
}
