
public class Diskon extends MenuItem {
    private double diskonPercent;

    public Diskon(String nama, double diskonPercent, String kategori) {

        super(nama, 0.0, kategori);
        this.diskonPercent = diskonPercent;
    }

    public double getDiskonPercent() { return diskonPercent; }
    public void setDiskonPercent(double diskonPercent) { this.diskonPercent = diskonPercent; }

    @Override
    public void tampilMenu() {
        System.out.printf("Diskon - %s | Kategori: %s | Diskon: %.2f%% %n",
                getNama(), getKategori(), diskonPercent);
    }

    @Override
    public String toDataString() {
        return String.format("Diskon|%s|%f|%s", getNama(), diskonPercent, getKategori());
    }
}
