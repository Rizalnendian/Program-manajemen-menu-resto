
public class Makanan extends MenuItem {
    private String jenisMakanan;

    public Makanan(String nama, double harga, String kategori, String jenisMakanan) {
        super(nama, harga, kategori);
        this.jenisMakanan = jenisMakanan;
    }

    public String getJenisMakanan() { return jenisMakanan; }
    public void setJenisMakanan(String jenisMakanan) { this.jenisMakanan = jenisMakanan; }

    @Override
    public void tampilMenu() {
        System.out.printf("Makanan - %s | Kategori: %s | Jenis: %s | Harga: Rp %.2f%n",
                getNama(), getKategori(), jenisMakanan, getHarga());
    }

    @Override
    public String toDataString() {
        return String.format("Makanan|%s|%f|%s|%s", getNama(), getHarga(), getKategori(), jenisMakanan);
    }
}
