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

    /**
     * Creates new form FormTransaksi
     */
    public FormTransaksi() {
        initComponents();
        
        // Membuat frame otomatis fullscreen saat dijalankan
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        
        ImageIcon icon = new ImageIcon(getClass().getResource("Logo.png"));
        Image img = icon.getImage();
        Image imgScale = img.getScaledInstance(labelLogo.getWidth(), labelLogo.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        labelLogo.setIcon(scaledIcon);
        
        tbtnTransaksi.setSelected(true);
        tbtnTransaksi.setBackground(new java.awt.Color(13, 110, 253));
    }
    
    private void resetWarnaMenu(){
        // List semua tombol sidebar
        Color warnaAsal = new java.awt.Color(25, 41, 60); // warna gelap sidebar
        
        tbtnDashboard.setBackground(warnaAsal);
        tbtnTransaksi.setBackground(warnaAsal);
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
        } else if (jRadioButton2.isSelected()){
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
                    lblDeskripsi.setText("Lanca untuk nugas, skripsi, dan kerja kantoran.");
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
        tfTotalJasa.setText(String.valueOf(trx.getTotalJasa()));
        tfSubSparepart.setText(String.valueOf(trx.getSubtotalSparepart()));
        tfSubJasa.setText(String.valueOf(trx.getTotalJasa()));
        tfSubPaket.setText(String.valueOf((int) trx.getTotalPaket()));
        tfGrandTotal.setText(String.valueOf((int) trx.hitungGrandTotal()));
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
            Object nilaiKolom = model.getValueAt((i), 4);
            
            // Cek agar tidak error jika barisnya kosong
            if (nilaiKolom != null) {
                // Ubah nilainya menjadi String, lalu konversi ke tipe data Double
                String nilaiStr = nilaiKolom.toString().replace(".", ""); // Hapus titik jika ada format ribuan
                arraySubtotal[i] = Double.parseDouble(nilaiStr);
                
                // Tambahkan nilai Array ke total sparepart
                totalSparepart += arraySubtotal[i];
            }
        }
        
        // Masukkan hasil total perulangan tadi ke dalam class OOP (Enkapsulasi)
        trx.setSubtotalSparepart(totalSparepart);
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
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        rbServis = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        cbxPilihPaket = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        lblNamaPaket = new javax.swing.JLabel();
        lblDeskripsi = new javax.swing.JLabel();
        lblHargaPaket = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tSparepart = new javax.swing.JTable();
        btnTambahSparepart = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
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
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        tbtnDashboard = new javax.swing.JToggleButton();
        tbtnTransaksi = new javax.swing.JToggleButton();
        tbtnPelanggan = new javax.swing.JToggleButton();
        tbtnSparepart = new javax.swing.JToggleButton();
        tbtnSparepart1 = new javax.swing.JToggleButton();
        labelLogo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PanelFrame.setBackground(new java.awt.Color(255, 255, 255));
        PanelFrame.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DATA PELANGGAN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        jLabel2.setText("ID Pelanggan");

        jLabel3.setText("Nama");

        jLabel4.setText("No. HP");

        jLabel5.setText("Alamat");

        jTextField1.setText("jTextField1");

        jTextField2.setText("jTextField2");

        jTextField3.setText("jTextField3");

        jTextField4.setText("jTextField4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                            .addComponent(jTextField1))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        PanelFrame.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 280, -1));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "No. Transaksi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        jLabel31.setText("TRX-2505017-0001");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelFrame.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 150, -1));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tanggal", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        jLabel32.setText("17/05/2025 10:30:15");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelFrame.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, -1, -1));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "JENIS TRANSAKSI", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        btnGrupJenisTransaksi.add(rbServis);
        rbServis.setText("Servis / Perbaikan");
        rbServis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbServisActionPerformed(evt);
            }
        });

        btnGrupJenisTransaksi.add(jRadioButton2);
        jRadioButton2.setText("Rakit PC (Paket)");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        jLabel6.setText("Pilih Paket Rakit PC");

        cbxPilihPaket.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gaming", "Office", "Editing" }));
        cbxPilihPaket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxPilihPaketActionPerformed(evt);
            }
        });

        lblNamaPaket.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNamaPaket.setForeground(new java.awt.Color(0, 0, 204));
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
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
                        .addGap(200, 200, 200))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbServis)
                            .addComponent(jRadioButton2)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(rbServis)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxPilihPaket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        PanelFrame.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, -1, -1));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kasir", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        jLabel33.setText("admin");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel33)
                .addGap(0, 66, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        PanelFrame.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, -1, -1));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "KERANJANG SPAREPART", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

        tSparepart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "SSD NvMe 512Gb", "1", "750.000", "750.000", null},
                {"2", "RAM DDR4 16GB", "1", "550.000", "550.000", null},
                {"3", "Keyboard Gaming RGB", "1", "350.000", null, null}
            },
            new String [] {
                "No", "Nama Sparepart", "Qty", "Harga", "Subtotal", ""
            }
        ));
        jScrollPane2.setViewportView(tSparepart);
        if (tSparepart.getColumnModel().getColumnCount() > 0) {
            tSparepart.getColumnModel().getColumn(0).setPreferredWidth(30);
            tSparepart.getColumnModel().getColumn(1).setPreferredWidth(200);
            tSparepart.getColumnModel().getColumn(2).setPreferredWidth(35);
            tSparepart.getColumnModel().getColumn(3).setPreferredWidth(100);
            tSparepart.getColumnModel().getColumn(4).setPreferredWidth(100);
            tSparepart.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        btnTambahSparepart.setText("+ Tambah Sparepart");
        btnTambahSparepart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahSparepartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                    .addComponent(btnTambahSparepart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 45, Short.MAX_VALUE)
                .addComponent(btnTambahSparepart)
                .addContainerGap())
        );

        PanelFrame.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 80, 390, -1));

        jButton11.setText("Cetak Struk");
        PanelFrame.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, 116, 40));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PILIH JASA / PERBAIKAN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 204))); // NOI18N

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
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(tfTotalJasa)
                        .addContainerGap())
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
                            .addComponent(jLabel20))
                        .addContainerGap())))
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

        PanelFrame.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 310, 610, -1));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pembayaran", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel21.setText("Subtotal Sparepart");

        jLabel22.setText("Subtotal Jasa");

        jLabel23.setText("Subtotal Paket");

        jLabel24.setText("TOTAL");

        jLabel25.setText("Rp");

        jLabel26.setText("Rp");

        jLabel27.setText("Rp");

        jLabel28.setText("Rp");

        tfSubSparepart.setEditable(false);

        tfSubJasa.setEditable(false);

        tfSubPaket.setEditable(false);

        tfGrandTotal.setEditable(false);

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
                .addContainerGap(58, Short.MAX_VALUE))
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

        jTextField6.setText("jTextField6");

        jTextField7.setText("jTextField7");

        jButton10.setText("Simpan Transaksi");

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
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel30)
                            .addGap(164, 164, 164)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelFrame.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 320, 410, -1));

        jPanel1.setBackground(new java.awt.Color(25, 41, 60));

        jPanel4.setBackground(new java.awt.Color(25, 41, 60));

        tbtnDashboard.setBackground(new java.awt.Color(25, 41, 60));
        btnGrupMenu.add(tbtnDashboard);
        tbtnDashboard.setForeground(new java.awt.Color(255, 255, 255));
        tbtnDashboard.setText("Dashboard");
        tbtnDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnDashboardActionPerformed(evt);
            }
        });

        tbtnTransaksi.setBackground(new java.awt.Color(25, 41, 60));
        btnGrupMenu.add(tbtnTransaksi);
        tbtnTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        tbtnTransaksi.setText("Transaksi");
        tbtnTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnTransaksiActionPerformed(evt);
            }
        });

        tbtnPelanggan.setBackground(new java.awt.Color(25, 41, 60));
        btnGrupMenu.add(tbtnPelanggan);
        tbtnPelanggan.setForeground(new java.awt.Color(255, 255, 255));
        tbtnPelanggan.setText("Pelanggan");
        tbtnPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnPelangganActionPerformed(evt);
            }
        });

        tbtnSparepart.setBackground(new java.awt.Color(25, 41, 60));
        btnGrupMenu.add(tbtnSparepart);
        tbtnSparepart.setForeground(new java.awt.Color(255, 255, 255));
        tbtnSparepart.setText("Sparepart");
        tbtnSparepart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnSparepartActionPerformed(evt);
            }
        });

        tbtnSparepart1.setBackground(new java.awt.Color(25, 41, 60));
        btnGrupMenu.add(tbtnSparepart1);
        tbtnSparepart1.setForeground(new java.awt.Color(255, 255, 255));
        tbtnSparepart1.setText("Sparepart");
        tbtnSparepart1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtnSparepart1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tbtnSparepart1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbtnSparepart, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbtnPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbtnTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tbtnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tbtnDashboard)
                .addGap(42, 42, 42)
                .addComponent(tbtnTransaksi)
                .addGap(42, 42, 42)
                .addComponent(tbtnPelanggan)
                .addGap(42, 42, 42)
                .addComponent(tbtnSparepart)
                .addGap(42, 42, 42)
                .addComponent(tbtnSparepart1)
                .addContainerGap(189, Short.MAX_VALUE))
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
                .addContainerGap(241, Short.MAX_VALUE))
        );

        PanelFrame.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 906));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(PanelFrame, javax.swing.GroupLayout.PREFERRED_SIZE, 1687, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelFrame, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 661, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxPilihPaketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxPilihPaketActionPerformed
        kalkulasiTotal();
    }//GEN-LAST:event_cbxPilihPaketActionPerformed

    private void rbServisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbServisActionPerformed
        kalkulasiTotal();
    }//GEN-LAST:event_rbServisActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        kalkulasiTotal();
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void btnTambahSparepartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSparepartActionPerformed
        // Memanggil dialog popup (parameter 'this' untuk induknya, 'true' agar layarnya terkunci/modal)
        DialogTambahSparepart dialog = new DialogTambahSparepart(this, true);
        
        // Menampilkan dialog ke tengah layar
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnTambahSparepartActionPerformed

    private void tbtnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnDashboardActionPerformed
        resetWarnaMenu();
        tbtnDashboard.setBackground(new java.awt.Color(13, 110, 253)); // Warna biru aktif
    }//GEN-LAST:event_tbtnDashboardActionPerformed

    private void tbtnTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnTransaksiActionPerformed
        resetWarnaMenu();
        tbtnTransaksi.setBackground(new java.awt.Color(13, 110, 253)); // Warna biru aktif
    }//GEN-LAST:event_tbtnTransaksiActionPerformed

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

    private void tbtnPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnPelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbtnPelangganActionPerformed

    private void tbtnSparepartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnSparepartActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbtnSparepartActionPerformed

    private void tbtnSparepart1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtnSparepart1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbtnSparepart1ActionPerformed

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
    private javax.swing.ButtonGroup btnGrupJenisTransaksi;
    private javax.swing.ButtonGroup btnGrupMenu;
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
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
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
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JLabel labelLogo;
    private javax.swing.JLabel lblDeskripsi;
    private javax.swing.JLabel lblHargaPaket;
    private javax.swing.JLabel lblNamaPaket;
    private javax.swing.JRadioButton rbServis;
    private javax.swing.JTable tSparepart;
    private javax.swing.JToggleButton tbtnDashboard;
    private javax.swing.JToggleButton tbtnPelanggan;
    private javax.swing.JToggleButton tbtnSparepart;
    private javax.swing.JToggleButton tbtnSparepart1;
    private javax.swing.JToggleButton tbtnTransaksi;
    private javax.swing.JTextField tfGrandTotal;
    private javax.swing.JTextField tfSubJasa;
    private javax.swing.JTextField tfSubPaket;
    private javax.swing.JTextField tfSubSparepart;
    private javax.swing.JTextField tfTotalJasa;
    // End of variables declaration//GEN-END:variables
}
