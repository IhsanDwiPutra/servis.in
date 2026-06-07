/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author ACER
 */

import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Image;

public class FormTransaksi extends javax.swing.JFrame {
    
    TransaksiServis trx = new TransaksiServis();
    private int urutPelanggan = 1;
    private int urutTransaksi = 1;

    /**
     * Creates new form FormTransaksi
     */
    public FormTransaksi() {
        initComponents();
        
        // Membuat frame otomatis fullscreen saat dijalankan
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        
        // Membuat tabel tidak bisa di-edit
        tSparepart.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {
                    "No", "Kode Sparepart", "Nama Sparepart", "Qty", "Harga Satuan", "Subtotal"
                }
        ) {
            // Paksa semua sel mengembalikan nilai false (tidak bisa diedit)
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        
        
        // --- SESUAIKAN ULANG LEBAR KOLOMNYA (Sekarang ada Indeks 0 sampai 5) ---
        tSparepart.getColumnModel().getColumn(0).setPreferredWidth(40);  // No
        tSparepart.getColumnModel().getColumn(1).setPreferredWidth(120); // Kode Sparepart
        tSparepart.getColumnModel().getColumn(2).setPreferredWidth(300); // Nama Sparepart
        tSparepart.getColumnModel().getColumn(3).setPreferredWidth(50);  // Qty (Kolom Baru!)
        tSparepart.getColumnModel().getColumn(4).setPreferredWidth(120); // Harga Satuan
        tSparepart.getColumnModel().getColumn(5).setPreferredWidth(150); // Subtotal
        
        ImageIcon icon = new ImageIcon(getClass().getResource("Logo.png"));
        Image img = icon.getImage();
        Image imgScale = img.getScaledInstance(labelLogo.getWidth(), labelLogo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        labelLogo.setIcon(scaledIcon);
        
        // Panggil pembuat ID saat for baru dibuka
        buatIdPelangganOtomatis();
        
        buatNoTransaksiOtomatis();
        tampilTanggaldanWaktu();
    }
    
    private void kalkulasiTotal(){
        double totalJasa = 0;
        double totalPaket = 0;
        
        
        
        // Cek Radio Button mana yang dipilih
        if (rbServis.isSelected()){
            // JIka Transaksi Servis: Hitung Jasa, Abaikan Paket
            
            // --- Logika IF untuk Checkbox Jasa ---
            if (cbInulWin.isSelected()) totalJasa += 150000; // Instal Windows
            if (cbInSoft.isSelected()) totalJasa += 50000; // Instal Software
            if (cbBersih.isSelected()) totalJasa += 75000; // Bersihkan Laptop/PC
            if (cbGantiKey.isSelected()) totalJasa += 100000; // Ganti Keyboard
            if (cbGantiLcd.isSelected()) totalJasa += 300000; // Ganti LCD
            if (cbUpRam.isSelected()) totalJasa += 100000; // Upgrade RAM
            if (cbGantiHdd.isSelected()) totalJasa += 100000; // Ganti HDD/SSD
            if (cbPerbaikiMothe.isSelected()) totalJasa += 250000; // Perbaiki Motherboard
            if (cbSetBios.isSelected()) totalJasa += 50000; // Setting BIOS
            if (cbLain.isSelected()) totalJasa += 50000; // Lainnya
            
            totalPaket = 0;
        } else if (rbRakit.isSelected()){
            // Jika Transaksi Rakit: Hitung Paket Abaikan Jasa
            
            // --- Logika Switch-Case untuk Combobox Paket ---
            int pilihanPaket = cbxPilihPaket.getSelectedIndex();
            
            switch (pilihanPaket) {
                case 0:
                    totalPaket = 8750000; // Paket Gaming
                    // Mengubah teks Label sesuai pilihan
                    lblNamaPaket.setText("Paket Gaming");
                    lblDeskripsi.setText("Performa tinggi untuk gaming & multitasking.");
                    lblHargaPaket.setText("Total Paket: Rp 8.750.000");
                    break;
                case 1:
                    totalPaket = 5500000; // Paket Office
                    lblNamaPaket.setText("Paket Office");
                    lblDeskripsi.setText("Lancar untuk nugas, skripsi, dan kerja kantoran.");
                    lblHargaPaket.setText("Total Paket: Rp 5.500.000");
                    break;
                case 2:
                    totalPaket = 12000000; // Paket Editing
                    lblNamaPaket.setText("Paket Editing");
                    lblDeskripsi.setText("Render cepat untuk editing video & render 3D.");
                    lblHargaPaket.setText("Total Paket: Rp 12.000.000");
                    break;
                default:
                    totalPaket = 0;
                    break;
            }
            totalJasa = 0;
        }
        
        // --- Masukkan Data ke Class OOP (Enkapsulasi) ---
        trx.setTotalJasa(totalJasa);
        trx.setTotalPaket(totalPaket);
        
        // --- Tampilkan ke Textfield ---
        
        // Panggil fungsi hitung tabel sparepart
        hitungSparepart();
        
        // Tampilkkan Grand Total ke TextField Subtotal Sparepart dan TextField Total
        tfTotalJasa.setText(String.valueOf(formatRibuan(trx.getTotalJasa())));
        tfSubSparepart.setText(String.valueOf(formatRibuan(trx.getSubtotalSparepart())));
        tfSubJasa.setText(String.valueOf(formatRibuan(trx.getTotalJasa())));
        tfSubPaket.setText(String.valueOf(formatRibuan(trx.getTotalPaket())));
        tfGrandTotal.setText(String.valueOf(formatRibuan(trx.hitungGrandTotal())));
    }
    
    private void hitungSparepart() {
        // Mengambil "Model" dari tabel untuk bisa membaca isinya
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tSparepart.getModel();
        
        // Mengetahui ada berapa baris di dalam tabel tersebut
        int jumlahBaris = model.getRowCount();
        
        // Penerapan Array (Materi Pertemuan 4)
        // Membuat Array untuk menampung nilai subtotal dari masing-masing baris
        double[] arraySubtotal = new double[jumlahBaris];
        double totalSparepart = 0;
        
        // Penerapan Looping For (Materi Pertemuan 4)
        // Mengulang proses sebanyak jumlah baris di tabel
        for (int i = 0; i < jumlahBaris; i++){
            // Mengambil nilai dari kolom "Subtotal" (indeks kolom dimulai dari 0. No=0, Nama=1, Qty=2, Harga=3, Subtotal=4)
            Object nilaiKolom = model.getValueAt(i, 5);
            
            // Cek agar tidak error jika barisnya kosong
            if (nilaiKolom != null) {
                // Ubah nilainya menjadi String, lalu konversi ke tipe data Double
                String nilaiStr = nilaiKolom.toString().replace(".", "");
                arraySubtotal[i] = Double.parseDouble(nilaiStr);
                
                // Tambahkan nilai Array ke total sparepart
                totalSparepart += arraySubtotal[i];
            }
        }
        
        // Masukkan hasil total perulangan tadi ke dalam class OOP (Enkapsulasi)
        trx.setSubtotalSparepart(totalSparepart);
    }
    
    private void buatIdPelangganOtomatis(){
        try {
            // Ambil tanggal hari ini dengan format TahunBulanTanggal (contoh: 260514)
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyMMdd");
            String tgl = sdf.format(new java.util.Date());
            
            // Format ID: PLG-Tanggal-NomorUrut (contoh: PLG-260514-0001)
            // "%04d" artinya angka akan dicetak dengan 4 digit
            String idBaru = "PLG-" + tgl + "-" + String.format("%04d", urutPelanggan);
            
            // Masukkan ID tersebut ke TextField ID Pelanggan
            tfIdPelanggan.setText(idBaru);
            
        } catch (Exception e) {
            System.out.println("Error buat ID:" + e.getMessage());
        }
    }
    
    private void buatNoTransaksiOtomatis(){
        try {
            // Ambil tanggal dengan format TahunBulanTanggal
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyMMdd");
            String tgl = sdf.format(new java.util.Date());
            
            // Format: TRK-Tanggal-NomorUrut (Contoh: TRK-260514-0001)
            String noTrk = "TRK-" + tgl + "-" + String.format("%04d", urutTransaksi);
            
            // Masukkan ke label
            lNoTransaksi.setText(noTrk);
        } catch (Exception e) {
            System.out.println("Error buat No TRK: " + e.getMessage());
        }
    }
    
    private void tampilTanggaldanWaktu(){
        // Angka 1000 berarti timer akan mengulang perintah setuap 1000 milidetik (1 detik)
        javax.swing.Timer timer = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Format tampilan waktu Tanggal/Bulan/Tahun Jam:Menit:Detik
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String waktuSekarang = sdf.format(new java.util.Date());
                
                // Tampilkan ke label
                lTanggal.setText(waktuSekarang);
            }
        });
        // Jalankan timernya
        timer.start();
    }
    
    // Method khusus untuk menyulap angka menjadi format ribuan dengan titik
    private String formatRibuan(double angka) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#,###");
        java.text.DecimalFormatSymbols dfs = new java.text.DecimalFormatSymbols();
        
        dfs.setGroupingSeparator('.'); // Menggunakan titik untuk pemisah ribuan
        df.setDecimalFormatSymbols(dfs);
        
        return df.format(angka);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGrupJenisTransaksi = new javax.swing.ButtonGroup();
        btnGrupMenu = new javax.swing.ButtonGroup();
        PanelFrame = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tfIdPelanggan = new javax.swing.JTextField();
        tfNama = new javax.swing.JTextField();
        tfNoHP = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        taAlamat = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        lNoTransaksi = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        lTanggal = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        rbServis = new javax.swing.JRadioButton();
        rbRakit = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        cbxPilihPaket = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        lblNamaPaket = new javax.swing.JLabel();
        lblDeskripsi = new javax.swing.JLabel();
        lblHargaPaket = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        lKasir = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tSparepart = new javax.swing.JTable();
        btnTambahSparepart = new javax.swing.JButton();
        btnHapusSparepart = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        cbInulWin = new javax.swing.JCheckBox();
        cbInSoft = new javax.swing.JCheckBox();
        cbBersih = new javax.swing.JCheckBox();
        cbGantiKey = new javax.swing.JCheckBox();
        cbGantiLcd = new javax.swing.JCheckBox();
        cbUpRam = new javax.swing.JCheckBox();
        cbGantiHdd = new javax.swing.JCheckBox();
        cbPerbaikiMothe = new javax.swing.JCheckBox();
        cbSetBios = new javax.swing.JCheckBox();
        cbLain = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        tfTotalJasa = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        tfSubSparepart = new javax.swing.JTextField();
        tfSubJasa = new javax.swing.JTextField();
        tfSubPaket = new javax.swing.JTextField();
        tfGrandTotal = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        tfPembayaran = new javax.swing.JTextField();
        tfKembalian = new javax.swing.JTextField();
        btnSimpanTransaksi = new javax.swing.JButton();
        btnCetakStruk = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        labelLogo = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taCatatan = new javax.swing.JTextArea();
        btnKeluar = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1280, 900));

        PanelFrame.setBackground(new java.awt.Color(255, 255, 255));
        PanelFrame.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DATA PELANGGAN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(21, 100, 230))); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(249, 250));

        jLabel2.setText("ID Pelanggan:");

        jLabel3.setText("Nama:");

        jLabel4.setText("No. HP:");

        jLabel5.setText("Alamat:");

        tfIdPelanggan.setEditable(false);

        tfNoHP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfNoHPKeyTyped(evt);
            }
        });

        taAlamat.setColumns(20);
        taAlamat.setRows(5);
        jScrollPane3.setViewportView(taAlamat);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                    .addComponent(tfNoHP, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                    .addComponent(tfNama, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfIdPelanggan, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(0, 28, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfIdPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfNoHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
        );

        PanelFrame.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 280, -1));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "No. Transaksi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(21, 100, 230))); // NOI18N

        lNoTransaksi.setText("...");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lNoTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lNoTransaksi)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelFrame.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 150, -1));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tanggal", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(21, 100, 230))); // NOI18N

        lTanggal.setText("....");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lTanggal)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelFrame.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, 160, -1));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "JENIS TRANSAKSI", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(21, 100, 230))); // NOI18N

        btnGrupJenisTransaksi.add(rbServis);
        rbServis.setText("Servis / Perbaikan");
        rbServis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbServisActionPerformed(evt);
            }
        });

        btnGrupJenisTransaksi.add(rbRakit);
        rbRakit.setText("Rakit PC (Paket)");
        rbRakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbRakitActionPerformed(evt);
            }
        });

        jLabel6.setText("Pilih Paket Rakit PC");

        cbxPilihPaket.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gaming", "Office", "Editing" }));
        cbxPilihPaket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxPilihPaketActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(242, 255, 255));

        lblNamaPaket.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNamaPaket.setForeground(new java.awt.Color(21, 100, 230));
        lblNamaPaket.setText("Paket Gaming");

        lblDeskripsi.setText("Performa tinggi untuk gaming & multitasking");

        lblHargaPaket.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblHargaPaket.setText("Total Paket: Rp 8.750.000");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHargaPaket)
                    .addComponent(lblDeskripsi)
                    .addComponent(lblNamaPaket))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNamaPaket)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(lblDeskripsi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHargaPaket)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(cbxPilihPaket, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(194, 194, 194))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbServis)
                            .addComponent(rbRakit)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(rbServis)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbRakit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxPilihPaket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        PanelFrame.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 330, 250));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kasir", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(21, 100, 230))); // NOI18N

        lKasir.setText("...");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(lKasir, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lKasir)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        PanelFrame.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, 130, -1));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "KERANJANG SPAREPART", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(21, 100, 230))); // NOI18N

        tSparepart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Nama Sparepart", "Qty", "Harga", "Subtotal"
            }
        ));
        jScrollPane2.setViewportView(tSparepart);
        if (tSparepart.getColumnModel().getColumnCount() > 0) {
            tSparepart.getColumnModel().getColumn(0).setPreferredWidth(10);
            tSparepart.getColumnModel().getColumn(1).setPreferredWidth(200);
            tSparepart.getColumnModel().getColumn(2).setPreferredWidth(35);
            tSparepart.getColumnModel().getColumn(3).setPreferredWidth(100);
            tSparepart.getColumnModel().getColumn(4).setPreferredWidth(100);
        }

        btnTambahSparepart.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTambahSparepart.setForeground(new java.awt.Color(21, 100, 230));
        btnTambahSparepart.setText("+ Tambah Sparepart");
        btnTambahSparepart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahSparepartActionPerformed(evt);
            }
        });

        btnHapusSparepart.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHapusSparepart.setForeground(new java.awt.Color(255, 0, 0));
        btnHapusSparepart.setText("- Hapus Sparepart");
        btnHapusSparepart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusSparepartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnTambahSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnHapusSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahSparepart)
                    .addComponent(btnHapusSparepart))
                .addContainerGap())
        );

        PanelFrame.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 80, 470, 250));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PILIH JASA / PERBAIKAN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(21, 100, 230))); // NOI18N

        cbInulWin.setText("Install Ulang Windows");
        cbInulWin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbInulWinActionPerformed(evt);
            }
        });

        cbInSoft.setText("Install Software");
        cbInSoft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbInSoftActionPerformed(evt);
            }
        });

        cbBersih.setText("Bersihkan Laptop/PC");
        cbBersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBersihActionPerformed(evt);
            }
        });

        cbGantiKey.setText("Ganti Keyboard");
        cbGantiKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGantiKeyActionPerformed(evt);
            }
        });

        cbGantiLcd.setText("Ganti LCD");
        cbGantiLcd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGantiLcdActionPerformed(evt);
            }
        });

        cbUpRam.setText("Upgrade RAM");
        cbUpRam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUpRamActionPerformed(evt);
            }
        });

        cbGantiHdd.setText("Ganti HDD/SSD");
        cbGantiHdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGantiHddActionPerformed(evt);
            }
        });

        cbPerbaikiMothe.setText("Perbaikan Motherboard");
        cbPerbaikiMothe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPerbaikiMotheActionPerformed(evt);
            }
        });

        cbSetBios.setText("Setting BIOS");
        cbSetBios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSetBiosActionPerformed(evt);
            }
        });

        cbLain.setText("Lainnya");
        cbLain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLainActionPerformed(evt);
            }
        });

        jLabel7.setText("Total Jasa");

        tfTotalJasa.setEditable(false);
        tfTotalJasa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tfTotalJasa.setForeground(new java.awt.Color(21, 100, 230));

        jLabel11.setText("Rp 150.000");

        jLabel12.setText("Rp 50.000");

        jLabel13.setText("Rp 75.000");

        jLabel14.setText("Rp 100.000");

        jLabel15.setText("Rp 300.000");

        jLabel16.setText("Rp 100.000");

        jLabel17.setText("Rp 100.000");

        jLabel18.setText("Rp 250.000");

        jLabel19.setText("Rp 50.000");

        jLabel20.setText("Rp 50.000");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbGantiLcd, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cbInulWin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbInSoft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbBersih, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbGantiKey, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(76, 76, 76)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(tfTotalJasa))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbGantiHdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbPerbaikiMothe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbSetBios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbUpRam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbLain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbInulWin)
                    .addComponent(cbUpRam)
                    .addComponent(jLabel11)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbInSoft)
                    .addComponent(cbGantiHdd)
                    .addComponent(jLabel12)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbBersih)
                    .addComponent(cbPerbaikiMothe)
                    .addComponent(jLabel13)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbGantiKey)
                    .addComponent(cbSetBios)
                    .addComponent(jLabel14)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbGantiLcd)
                    .addComponent(cbLain)
                    .addComponent(jLabel15)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tfTotalJasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelFrame.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 330, 620, -1));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "PEMBAYARAN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel21.setText("Subtotal Sparepart");

        jLabel22.setText("Subtotal Jasa");

        jLabel23.setText("Subtotal Paket");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(21, 100, 230));
        jLabel24.setText("TOTAL");

        jLabel25.setText("Rp");

        jLabel26.setText("Rp");

        jLabel27.setText("Rp");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(21, 100, 230));
        jLabel28.setText("Rp");

        tfSubSparepart.setEditable(false);

        tfSubJasa.setEditable(false);

        tfSubPaket.setEditable(false);

        tfGrandTotal.setEditable(false);
        tfGrandTotal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tfGrandTotal.setForeground(new java.awt.Color(21, 100, 230));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24))
                        .addGap(99, 99, 99)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfSubPaket))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfSubJasa, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfGrandTotal))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(77, 77, 77)
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfSubSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(122, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel25)
                    .addComponent(tfSubSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel26)
                    .addComponent(tfSubJasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel27)
                    .addComponent(tfSubPaket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel28)
                    .addComponent(tfGrandTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel29.setText("Pembayaran");

        jLabel30.setText("Kembalian");

        tfPembayaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPembayaranActionPerformed(evt);
            }
        });
        tfPembayaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfPembayaranKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfPembayaranKeyReleased(evt);
            }
        });

        tfKembalian.setEditable(false);

        btnSimpanTransaksi.setBackground(new java.awt.Color(21, 100, 230));
        btnSimpanTransaksi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSimpanTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanTransaksi.setText("Simpan Transaksi");
        btnSimpanTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanTransaksiActionPerformed(evt);
            }
        });

        btnCetakStruk.setBackground(new java.awt.Color(102, 255, 102));
        btnCetakStruk.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCetakStruk.setText("Cetak Struk");
        btnCetakStruk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakStrukActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel29)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel30)
                            .addGap(164, 164, 164)
                            .addComponent(tfKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addComponent(btnSimpanTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCetakStruk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(tfPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(tfKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCetakStruk, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(btnSimpanTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        PanelFrame.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 330, 470, 370));

        jPanel1.setBackground(new java.awt.Color(25, 41, 60));

        jPanel4.setBackground(new java.awt.Color(25, 41, 60));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 517, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(labelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        PanelFrame.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 750));

        jLabel8.setText("© 2026 Servis.in - Sistem Pintar, Servis Tuntas - V.1.0");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel8)
                .addContainerGap(826, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addContainerGap())
        );

        PanelFrame.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 700, 1120, 30));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CATATAN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(21, 100, 230))); // NOI18N

        taCatatan.setColumns(20);
        taCatatan.setRows(5);
        jScrollPane1.setViewportView(taCatatan);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelFrame.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 550, 620, 150));

        btnKeluar.setBackground(new java.awt.Color(255, 0, 0));
        btnKeluar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnKeluar.setForeground(new java.awt.Color(255, 255, 255));
        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });
        PanelFrame.add(btnKeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 30, 150, 40));

        btnReset.setBackground(new java.awt.Color(255, 255, 0));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        PanelFrame.add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 30, 150, 40));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel1.setText("Sistem Pintar, Servis Tuntas");
        PanelFrame.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelFrame, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 728, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(208, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxPilihPaketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxPilihPaketActionPerformed
        kalkulasiTotal();
    }//GEN-LAST:event_cbxPilihPaketActionPerformed

    private void rbServisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbServisActionPerformed
        kalkulasiTotal();
    }//GEN-LAST:event_rbServisActionPerformed

    private void rbRakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbRakitActionPerformed
        kalkulasiTotal();
    }//GEN-LAST:event_rbRakitActionPerformed

    private void btnTambahSparepartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSparepartActionPerformed
        // Memanggil dialog popup (parameter 'this' untuk induknya, 'true' agar layarnya terkunci/modal)
        DialogTambahSparepart dialog = new DialogTambahSparepart(this, true);
        
        // Menampilkan dialog ke tengah layar
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        
        // Cek apakah ada data yang dikirim
        if (dialog.dataBaru != null) {
            // Panggil model tabel
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tSparepart.getModel();
            
            // Bikin nomor urut otomatis berdasarkan jumlah baris yang ada
            int noUrut = model.getRowCount() + 1;
            dialog.dataBaru[0] = noUrut; // Sisipkan nomor urut ke array indeks ke-0
            
            // Ubah angka asli menjadi String yang sudah ada titiknya
            dialog.dataBaru[4] = formatRibuan(Double.parseDouble(dialog.dataBaru[4].toString()));
            dialog.dataBaru[5] = formatRibuan(Double.parseDouble(dialog.dataBaru[5].toString()));
            
            // Masukkan array tersebut sebagai baris baru di tabel
            model.addRow(dialog.dataBaru);
            
            // Panggil kalkulasi supaya Grand Total langsung update
            kalkulasiTotal();
        }
    }//GEN-LAST:event_btnTambahSparepartActionPerformed

    private void cbLainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLainActionPerformed
        kalkulasiTotal();
    }//GEN-LAST:event_cbLainActionPerformed

    private void cbSetBiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSetBiosActionPerformed
        kalkulasiTotal();
    }//GEN-LAST:event_cbSetBiosActionPerformed

    private void cbPerbaikiMotheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPerbaikiMotheActionPerformed
        kalkulasiTotal();
    }//GEN-LAST:event_cbPerbaikiMotheActionPerformed

    private void cbGantiHddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGantiHddActionPerformed
        kalkulasiTotal();
    }//GEN-LAST:event_cbGantiHddActionPerformed

    private void cbUpRamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbUpRamActionPerformed
        kalkulasiTotal();
    }//GEN-LAST:event_cbUpRamActionPerformed

    private void cbGantiLcdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGantiLcdActionPerformed
        kalkulasiTotal();
    }//GEN-LAST:event_cbGantiLcdActionPerformed

    private void cbGantiKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGantiKeyActionPerformed
        kalkulasiTotal();
    }//GEN-LAST:event_cbGantiKeyActionPerformed

    private void cbBersihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBersihActionPerformed
        kalkulasiTotal();
    }//GEN-LAST:event_cbBersihActionPerformed

    private void cbInSoftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbInSoftActionPerformed
        kalkulasiTotal();
    }//GEN-LAST:event_cbInSoftActionPerformed

    private void cbInulWinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbInulWinActionPerformed
        kalkulasiTotal();
    }//GEN-LAST:event_cbInulWinActionPerformed

    private void btnSimpanTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanTransaksiActionPerformed
        // Validasi Data Pelanggan
        if (tfNama.getText().isEmpty() || tfNoHP.getText().isEmpty() || taAlamat.getText().isEmpty()){
            javax.swing.JOptionPane.showMessageDialog(this, "Ada data yang belum diisi!", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            
            // Memaksa kursor mouse kembali ke kotak isian Nama
            tfNama.requestFocus();
            
            return; // Hentikan proses simpan
        }
        
        // Jika aman, simpan data tersebut ke dalam Class OOP
        trx.setIdPelanggan(tfIdPelanggan.getText());
        trx.setNamaPelanggan(tfNama.getText());
        trx.setNoHP(tfNoHP.getText());
        trx.setAlamat(taAlamat.getText());
        trx.setCatatan(taCatatan.getText());


        // Validasi apakah kembalian sudah terisi (artinya transaksi sudah dibayar)
        if (tfKembalian.getText().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selesaikan pembayaran terlebih dahulu!", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
        } else {
            // Tampilkan pesan sukses
            javax.swing.JOptionPane.showMessageDialog(this, "Transaksi Berhasil Disimpan!", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            
            // --- Proses Bersih-Bersih Form ---
            // Kosongkan semua isian teks
            tfIdPelanggan.setText("");
            tfNama.setText("");
            tfNoHP.setText("");
            taAlamat.setText("");
            taCatatan.setText("");
            
            tfTotalJasa.setText("");
            tfSubSparepart.setText("");
            tfSubPaket.setText("");
            tfSubJasa.setText("");
            tfGrandTotal.setText("");
            tfPembayaran.setText("");
            tfKembalian.setText("");
            
            // Reset Jenis Transaksi
            btnGrupJenisTransaksi.clearSelection();
            cbxPilihPaket.setSelectedIndex(0);
            
            // Hapus semua centang jasa
            cbBersih.setSelected(false);
            cbGantiHdd.setSelected(false);
            cbGantiKey.setSelected(false);
            cbGantiLcd.setSelected(false);
            cbInSoft.setSelected(false);
            cbInulWin.setSelected(false);
            cbLain.setSelected(false);
            cbPerbaikiMothe.setSelected(false);
            cbSetBios.setSelected(false);
            cbUpRam.setSelected(false);
            
            // Kosongkan keranjang di tabel
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tSparepart.getModel();
            model.setRowCount(0); // Menghapus semua baris
            
            // Naikkan nomor urut pelanggan untuk transaksi berikutnya
            urutPelanggan++;
            urutTransaksi++;
            
            // Panggil ulang pembuat ID otomatis
            buatIdPelangganOtomatis();
            
            buatNoTransaksiOtomatis();
            
            // Reset hitungan dari nol lagi
            kalkulasiTotal();
        }
    }//GEN-LAST:event_btnSimpanTransaksiActionPerformed

    private void tfPembayaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPembayaranKeyPressed
        // Cek apakah tombol yang ditekan di keyboard adalah tombol  ENTER
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            try {
                // Ambil nilai dari grand total dari class OOP yang sudah dihitung
                double total = trx.hitungGrandTotal();

                // Ambil nilai uang pembayaran yang diketik kasir
                double bayar = Double.parseDouble(tfPembayaran.getText().replace(".", ""));

                // Validasi logika: Apakah uang bayar lebih dari total?
                if (bayar < total) {
                    // Munculkan pop-up peringatan jika uang kurang
                    javax.swing.JOptionPane.showMessageDialog(this, "Pembayaran kurang!!", "Warning", javax.swing.JOptionPane.WARNING_MESSAGE);

                    // Kosongkan kembali kolom bayar dan kembalian
                    tfPembayaran.setText("");
                    tfKembalian.setText("");
                } else {
                    // Jika uang pas atau lebih, hitung kembaliannya
                    double kembalian = bayar - total;

                    // Tampilkan  kembalian ke tfKembalian tanpa angka desimal (.0)
                    tfKembalian.setText(formatRibuan(kembalian));
                }
            } catch (Exception e) {
                // Jaga-jaga jika kasir malah mengetik huruf, bukan angka
                javax.swing.JOptionPane.showMessageDialog(this, "Masukkan nominal angka yang valid!");
                tfPembayaran.setText("");
            }
            
        }
    }//GEN-LAST:event_tfPembayaranKeyPressed

    private void tfPembayaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPembayaranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPembayaranActionPerformed

    private void btnCetakStrukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakStrukActionPerformed
        try {
            // Validasi pastikan transaksi sudah dibayar (Kembalian tidak kosong)
            if (tfKembalian.getText().isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Selesaikan pembayaran sebelum mencetak struk!", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Memanggil pop-up DialogStruk
            DialogStruk dialogStruk = new DialogStruk(this, true);
            
            //Merangkai Teks Struk
            String teks = "=================================================\n";
            teks += "               SERVIS.IN COMPUTER                \n";
            teks += "       Jl. Merdeka No. 10, Malang, Jatim         \n";
            teks += "=================================================\n";
            teks += "No. TRK   : " + lNoTransaksi.getText() + "\n";
            teks += "Tanggal   : " + lTanggal.getText() + "\n";
            teks += "Kasir     : admin\n";
            teks += "=================================================\n";
            teks += "DATA PELANGGAN\n";
            teks += "ID Pelanggan: " + tfIdPelanggan.getText() + "\n";
            teks += "Nama        : " + tfNama.getText() + "\n";
            teks += "=================================================\n";

            teks += "TOTAL PAKET     : Rp " + formatRibuan(trx.getTotalPaket()) + "\n";
            teks += "TOTAL JASA      : Rp " + formatRibuan(trx.getTotalJasa()) + "\n";
            teks += "TOTAL SPAREPART : Rp " + formatRibuan(trx.getSubtotalSparepart()) + "\n";
            teks += "-------------------------------------------------\n";
            teks += "GRAND TOTAL     : Rp " + formatRibuan(trx.hitungGrandTotal()) + "\n";
            teks += "PEMBAYARAN      : Rp " + tfPembayaran.getText() + "\n";
            teks += "KEMBALIAN       : Rp " + tfKembalian.getText() + "\n";
            teks += "=================================================\n";
            teks += "   Terima kasih atas kepercayaan Anda!\n";
            teks += "   Barang/PC yang dibeli tidak dapat dikembalikan.\n";
            teks += "=================================================\n";
            
            // Masukkan teks tersebut ke dalam Text Area di DialogStruk
            dialogStruk.taStruk.setText(teks);
            
            // Tampilkan ke layar
            dialogStruk.setLocationRelativeTo(this);
            dialogStruk.setVisible(true);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal mencetak struk!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCetakStrukActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        int jawab = javax.swing.JOptionPane.showConfirmDialog(null, "Yakin ingin keluar aplikasi?", "Konfirmasi", javax.swing.JOptionPane.YES_NO_OPTION);
        if (jawab == javax.swing.JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
            // --- Proses Bersih-Bersih Form ---
            // Kosongkan semua isian teks
            tfNama.setText("");
            tfNoHP.setText("");
            taAlamat.setText("");
            taCatatan.setText("");
            
            tfTotalJasa.setText("");
            tfSubSparepart.setText("");
            tfSubPaket.setText("");
            tfSubJasa.setText("");
            tfGrandTotal.setText("");
            tfPembayaran.setText("");
            tfKembalian.setText("");
            
            // Reset Jenis Transaksi
            btnGrupJenisTransaksi.clearSelection();
            cbxPilihPaket.setSelectedIndex(0);
            
            // Hapus semua centang jasa
            cbBersih.setSelected(false);
            cbGantiHdd.setSelected(false);
            cbGantiKey.setSelected(false);
            cbGantiLcd.setSelected(false);
            cbInSoft.setSelected(false);
            cbInulWin.setSelected(false);
            cbLain.setSelected(false);
            cbPerbaikiMothe.setSelected(false);
            cbSetBios.setSelected(false);
            cbUpRam.setSelected(false);
            
            // Kosongkan keranjang di tabel
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tSparepart.getModel();
            model.setRowCount(0); // Menghapus semua baris
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnHapusSparepartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusSparepartActionPerformed
        // Ambil model dari tabel
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tSparepart.getModel();

        // Cek baris mana yang sedang diklik/dipilih oleh kasir
        int barisTerpilih = tSparepart.getSelectedRow();

        // Validasi: Jika tidak ada baris yang diklik, tampilkan peringatan
        if (barisTerpilih == -1) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Silahkan pilih/klik salah satu baris sparepart di dalam tabel terlebih dahulu!",
                    "Peringatan",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
        } else {
            // Konfirmasi ulang sebelum benar-benar menghapus
            int konfirmasi = javax.swing.JOptionPane.showConfirmDialog(this, 
                    "Apakah Anda yakin ingin menghapus sparepart ini dari keranjang?",
                    "Konfirmasi Hapus",
                    javax.swing.JOptionPane.YES_NO_OPTION);

            if (konfirmasi == javax.swing.JOptionPane.YES_OPTION){

                // === MULAI LOGIKA PENGEMBALIAN STOK KE ARRAY ===
                try {
                    // Catatan: Sesuaikan angka '1' (Nama) dan '3' (Qty) dengan posisi kolom di JTable kamu!
                    // Misalnya: Kolom 0 = No, Kolom 1 = Nama Barang, Kolom 2 = Harga, Kolom 3 = Qty
                    String namaSparepart = model.getValueAt(barisTerpilih, 2).toString();
                    int qtyKembali = Integer.parseInt(model.getValueAt(barisTerpilih, 3).toString());

                    // Kembalikan Qty ke indeks array yang tepat berdasarkan nama barang
                    if (namaSparepart.contains("SSD NvMe 512GB")) {
                        DialogTambahSparepart.stokGlobal[1] += qtyKembali;
                    } else if (namaSparepart.contains("RAM DDR4 16GB")) {
                        DialogTambahSparepart.stokGlobal[2] += qtyKembali;
                    } else if (namaSparepart.contains("RAM DDR4 8GB")) {
                        DialogTambahSparepart.stokGlobal[3] += qtyKembali;
                    } else if (namaSparepart.contains("HDD 1TB")) {
                        DialogTambahSparepart.stokGlobal[4] += qtyKembali;
                    }
                    // (Silakan lengkapi / ubah nama barang di atas sesuai dengan isi combobox milikmu)

                } catch (Exception e) {
                    System.out.println("Gagal mengembalikan stok: " + e.getMessage());
                }
                // === SELESAI LOGIKA PENGEMBALIAN STOK ===

                // Hapus baris yang dipilih dari model tabel
                model.removeRow(barisTerpilih);

                // Susun/urutkan ulang di kolom "No" (Indeks 0) agar tetap berurutan 1,2,3,...
                for (int i = 0; i < model.getRowCount(); i++) {
                    model.setValueAt(i + 1, i, 0);
                }

                // Panggil ulang method Kalkulasi
                kalkulasiTotal();

                javax.swing.JOptionPane.showMessageDialog(this, "Sparepart berhasil dihapus. Stok dikembalikan ke sistem.");
            }
        }
    }//GEN-LAST:event_btnHapusSparepartActionPerformed

    private void tfPembayaranKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPembayaranKeyReleased
        try {
            // Ambil teks yang sedang diketik, buang titiknya terlebih dahulu
            String teksAsli = tfPembayaran.getText().replace(".", "");
            
            // Cegah error jika kolom kosong
            if (!teksAsli.isEmpty()) {
                // Ubah teks murni menjadi angka
                double nominal = Double.parseDouble(teksAsli);
                
                // Format menggunakan method formatRibuan
                String teksFormat = formatRibuan(nominal);
                tfPembayaran.setText(teksFormat);
            }
        } catch (NumberFormatException e){
            // Abaikan jika kasir mengetik huruf
        }
    }//GEN-LAST:event_tfPembayaranKeyReleased

    private void tfNoHPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNoHPKeyTyped
        // 1. Tangkap karakter dari tombol keyboard yang baru saja ditekan
        char c = evt.getKeyChar();

        // 2. Cek apakah karakter tersebut BUKAN angka (digit) 
        //    dan BUKAN tombol hapus (Backspace/Delete)
        if (!Character.isDigit(c) && c != '\b' && c != '\n') {

            // 3. Batalkan inputan tersebut agar huruf tidak masuk ke dalam text field
            evt.consume();

            // 4. Munculkan pop-up peringatan kepada kasir
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Input tidak valid! No HP hanya boleh diisi dengan angka.", 
                "Peringatan Validasi", 
                javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_tfNoHPKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelFrame;
    private javax.swing.JButton btnCetakStruk;
    private javax.swing.ButtonGroup btnGrupJenisTransaksi;
    private javax.swing.ButtonGroup btnGrupMenu;
    private javax.swing.JButton btnHapusSparepart;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSimpanTransaksi;
    private javax.swing.JButton btnTambahSparepart;
    private javax.swing.JCheckBox cbBersih;
    private javax.swing.JCheckBox cbGantiHdd;
    private javax.swing.JCheckBox cbGantiKey;
    private javax.swing.JCheckBox cbGantiLcd;
    private javax.swing.JCheckBox cbInSoft;
    private javax.swing.JCheckBox cbInulWin;
    private javax.swing.JCheckBox cbLain;
    private javax.swing.JCheckBox cbPerbaikiMothe;
    private javax.swing.JCheckBox cbSetBios;
    private javax.swing.JCheckBox cbUpRam;
    private javax.swing.JComboBox<String> cbxPilihPaket;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JLabel lKasir;
    private javax.swing.JLabel lNoTransaksi;
    private javax.swing.JLabel lTanggal;
    private javax.swing.JLabel labelLogo;
    private javax.swing.JLabel lblDeskripsi;
    private javax.swing.JLabel lblHargaPaket;
    private javax.swing.JLabel lblNamaPaket;
    private javax.swing.JRadioButton rbRakit;
    private javax.swing.JRadioButton rbServis;
    private javax.swing.JTable tSparepart;
    private javax.swing.JTextArea taAlamat;
    private javax.swing.JTextArea taCatatan;
    private javax.swing.JTextField tfGrandTotal;
    private javax.swing.JTextField tfIdPelanggan;
    private javax.swing.JTextField tfKembalian;
    private javax.swing.JTextField tfNama;
    private javax.swing.JTextField tfNoHP;
    private javax.swing.JTextField tfPembayaran;
    private javax.swing.JTextField tfSubJasa;
    private javax.swing.JTextField tfSubPaket;
    private javax.swing.JTextField tfSubSparepart;
    private javax.swing.JTextField tfTotalJasa;
    // End of variables declaration//GEN-END:variables
}
