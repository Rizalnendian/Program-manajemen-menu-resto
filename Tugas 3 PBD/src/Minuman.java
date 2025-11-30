
public class Minuman extends MenuItem {
    private String jenisMinuman;

    public Minuman(String nama, double harga, String kategori, String jenisMinuman) {
        super(nama, harga, kategori);
        this.jenisMinuman = jenisMinuman;
    }

    public String getJenisMinuman() { return jenisMinuman; }
    public void setJenisMinuman(String jenisMinuman) { this.jenisMinuman = jenisMinuman; }

    @Override
    public void tampilMenu() {
        System.out.printf("Minuman - %s | Kategori: %s | Jenis: %s | Harga: Rp %.2f%n",
                getNama(), getKategori(), jenisMinuman, getHarga());
    }

    @Override
    public String toDataString() {
        return String.format("Minuman|%s|%f|%s|%s", getNama(), getHarga(), getKategori(), jenisMinuman);
    }
}
