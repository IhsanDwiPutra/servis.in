public class TransaksiServis {
    // Variabel private agar tidak bisa diubah langsungs dari luar (Enkapsulasi)
    private double totalJasa;
    private double totalPaket;
    private double subtotalSparepart;
    private double uangPembayaran;
    
    // Data Pelanggan
    private String idPelanggan;
    private String namaPelanggan;
    private String noHP;
    private String alamat;
    private String catatan;

    public double getTotalJasa() {
        return totalJasa;
    }

    public void setTotalJasa(double totalJasa) {
        this.totalJasa = totalJasa;
    }

    public double getTotalPaket() {
        return totalPaket;
    }

    public void setTotalPaket(double totalPaket) {
        this.totalPaket = totalPaket;
    }

    public double getSubtotalSparepart() {
        return subtotalSparepart;
    }

    public void setSubtotalSparepart(double subtotalSparepart) {
        this.subtotalSparepart = subtotalSparepart;
    }

    public double getUangPembayaran() {
        return uangPembayaran;
    }

    public void setUangPembayaran(double uangPembayaran) {
        this.uangPembayaran = uangPembayaran;
    }
    
    // Method untuk menghitung Total Keseluruhan
    public double hitungGrandTotal(){
        return totalJasa + totalPaket + subtotalSparepart;
    }
    
    // Method untuk menghitung Kembalian
    public double hitungKembalian(){
        return uangPembayaran - hitungGrandTotal();
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getNoHP() {
        return noHP;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
    
    
    
}
