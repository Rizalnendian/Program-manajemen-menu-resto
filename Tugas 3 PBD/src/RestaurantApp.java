import java.util.*;
import java.io.*;

public class RestaurantApp {
    private static final String MENU_FILE = "menu.txt";
    private static final String STRUK_FOLDER = "struk";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();


        new File(STRUK_FOLDER).mkdirs();


        try {
            menu.loadFromFile(MENU_FILE);
            System.out.println("Menu dimuat dari " + MENU_FILE);
        } catch (IOException e) {
            System.out.println("Gagal memuat menu: " + e.getMessage());
        }

        boolean running = true;
        while (running) {
            System.out.println("\n=== RESTAURANT MANAGEMENT ===");
            System.out.println("1. Tambah item ke menu");
            System.out.println("2. Tampilkan menu restoran");
            System.out.println("3. Terima pesanan pelanggan");
            System.out.println("4. Simpan menu ke file");
            System.out.println("5. Muat menu dari file");
            System.out.println("6. Keluar");
            System.out.print("Pilih (1-6): ");
            String pilihan = sc.nextLine().trim();

            switch (pilihan) {
                case "1":
                    tambahItemMenu(menu, sc);
                    break;
                case "2":
                    menu.tampilSemuaMenu();
                    break;
                case "3":
                    terimaPesanan(menu, sc);
                    break;
                case "4":
                    try {
                        menu.saveToFile(MENU_FILE);
                        System.out.println("Menu tersimpan ke " + MENU_FILE);
                    } catch (IOException e) {
                        System.out.println("Gagal menyimpan menu: " + e.getMessage());
                    }
                    break;
                case "5":
                    try {
                        menu.loadFromFile(MENU_FILE);
                        System.out.println("Menu dimuat dari " + MENU_FILE);
                    } catch (IOException e) {
                        System.out.println("Gagal memuat menu: " + e.getMessage());
                    }
                    break;
                case "6":
                    System.out.println("Keluar. Terima kasih!");
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }

        sc.close();
    }

    private static void tambahItemMenu(Menu menu, Scanner sc) {
        System.out.println("Tambah item: (1) Makanan (2) Minuman (3) Diskon");
        System.out.print("Pilih tipe (1-3): ");
        String tipe = sc.nextLine().trim();
        try {
            switch (tipe) {
                case "1":
                    System.out.print("Nama makanan: ");
                    String namaM = sc.nextLine();
                    double hargaM = inputDouble(sc, "Harga: ");
                    System.out.print("Kategori: ");
                    String kategoriM = sc.nextLine();
                    System.out.print("Jenis makanan (mis. Utama/Camilan): ");
                    String jenisM = sc.nextLine();
                    menu.tambahItem(new Makanan(namaM, hargaM, kategoriM, jenisM));
                    System.out.println("Makanan ditambahkan.");
                    break;
                case "2":
                    System.out.print("Nama minuman: ");
                    String namaMin = sc.nextLine();
                    double hargaMin = inputDouble(sc, "Harga: ");
                    System.out.print("Kategori: ");
                    String kategoriMin = sc.nextLine();
                    System.out.print("Jenis minuman (mis. Panas/Dingin): ");
                    String jenisMin = sc.nextLine();
                    menu.tambahItem(new Minuman(namaMin, hargaMin, kategoriMin, jenisMin));
                    System.out.println("Minuman ditambahkan.");
                    break;
                case "3":
                    System.out.print("Nama diskon (mis. Diskon Siang): ");
                    String namaD = sc.nextLine();
                    double percent = inputDouble(sc, "Persentase diskon (mis. 10 untuk 10%): ");
                    System.out.print("Kategori: ");
                    String kategoriD = sc.nextLine();
                    menu.tambahItem(new Diskon(namaD, percent, kategoriD));
                    System.out.println("Diskon ditambahkan.");
                    break;
                default:
                    System.out.println("Tipe tidak dikenal.");
            }
        } catch (InputMismatchException ime) {
            System.out.println("Input salah.");
            sc.nextLine();
        }
    }

    private static double inputDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String line = sc.nextLine().trim();
                return Double.parseDouble(line);
            } catch (NumberFormatException nfe) {
                System.out.println("Masukkan angka yang valid.");
            }
        }
    }

    private static void terimaPesanan(Menu menu, Scanner sc) {
        System.out.print("Nama pelanggan: ");
        String nama = sc.nextLine();
        Pesanan pesanan = new Pesanan(nama);

        boolean selesai = false;
        while (!selesai) {
            System.out.println("\nDaftar Menu:");
            menu.tampilSemuaMenu();
            System.out.println("Masukkan nomor item untuk ditambahkan ke pesanan (0 untuk selesai): ");
            System.out.print("Nomor: ");
            String in = sc.nextLine().trim();
            int nomor;
            try {
                nomor = Integer.parseInt(in);
            } catch (NumberFormatException nfe) {
                System.out.println("Masukkan nomor valid.");
                continue;
            }
            if (nomor == 0) {
                selesai = true;
                break;
            }
            try {
                MenuItem item = menu.ambilItem(nomor - 1);
                pesanan.tambahPesanan(item);
                System.out.println(item.getNama() + " ditambahkan ke pesanan.");
            } catch (MenuItemNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("\n" + pesanan.generateStruk());
        // Simpan struk ke file
        String filename = STRUK_FOLDER + File.separator + "struk_" + System.currentTimeMillis() + ".txt";
        try {
            pesanan.saveStrukToFile(filename);
            System.out.println("Struk tersimpan ke " + filename);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan struk: " + e.getMessage());
        }
    }
}
