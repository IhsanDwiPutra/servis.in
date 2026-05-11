public class TransaksiServis {
    // Variabel private agar tidak bisa diubah langsungs dari luar (Enkapsulasi)
    private double totalJasa;
    private double totalPaket;
    private double subtotalSparepart;
    private double uangPembayaran;

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
    
}
