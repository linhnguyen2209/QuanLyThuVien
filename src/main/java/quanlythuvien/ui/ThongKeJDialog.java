package quanlythuvien.ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import quanlythuvien.dao.PhieuMuonDAO;
import quanlythuvien.dao.PhieuTraDAO;
import quanlythuvien.dao.ThongKeDAO;
import quanlythuvien.utils.ExportFile;
import quanlythuvien.utils.MsgBox;
import quanlythuvien.utils.XImage;

/**
 *
 * @author NGUYENDINHNGHI
 * @author TRANTHITHUHA
 *
 */
public class ThongKeJDialog extends javax.swing.JDialog {

    ThongKeDAO tkDao = new ThongKeDAO();
    PhieuMuonDAO pmDao = new PhieuMuonDAO();
    boolean isBtnFilter = false;

    public ThongKeJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    void init() {
//        this.setLocation(318, 73);
        this.setLocation(325, 74);
        this.setTitle("Thống Kê - Báo Cáo");
        this.setIconImage(XImage.getAppIcon());
        fillComboboxNam();
        fillComboxThang(cboThangBatDau);
        fillComboxThang(cboThangKetThuc);
        txtTongTienPhatCacNam.setEditable(false);
        txtTongTienPhatTheoNam.setEditable(false);
        cboThangKetThuc.setSelectedItem(12);
        filter();
        fillComboxThang(cboThangSachMuon);
    }

    void fillComboboxNam() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNam.getModel();
        DefaultComboBoxModel modelNamSach = (DefaultComboBoxModel) cboNamSachMuon.getModel();
        modelNamSach.removeAllElements();
        model.removeAllElements();
        List<Integer> list = pmDao.selectYear();
        for (Integer nam : list) {
            model.addElement(nam);
            modelNamSach.addElement(nam);
        }
    }

    void fillComboxThang(JComboBox cbo) {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbo.getModel();
        model.removeAllElements();
        for (int i = 1; i <= 12; i++) {
            model.addElement(i);
        }
    }

    void fillTable(List<Object[]> list) {
        DefaultTableModel model = (DefaultTableModel) tblThongKeMuonTra.getModel();
        model.setRowCount(0);
        long tongTienPhat = 0;
        for (Object[] row : list) {
            if (rdoTatCaCacNam.isSelected()) {
                tongTienPhat += Long.parseLong(row[7].toString());
                txtTongTienPhatCacNam.setText(tongTienPhat + "");
                txtTongTienPhatTheoNam.setText(0 + "");
            } else if (isBtnFilter) {
                tongTienPhat += Long.parseLong(row[7].toString());
                txtTongTienPhatTheoNam.setText(tongTienPhat + "");
                txtTongTienPhatCacNam.setText(0 + "");
            }
            model.addRow(new Object[]{row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8]});
        }
    }

    void filter() {
        Integer nam = Integer.valueOf(cboNam.getSelectedItem() + "");
        Integer thangBD = Integer.valueOf(cboThangBatDau.getSelectedItem() + "");
        Integer thangKT = Integer.valueOf(cboThangKetThuc.getSelectedItem() + "");
        String tinhTrang = "";
        String maDocGia = txtMaDocGiaTimKiem.getText().trim();
        if (rdoQuaHan.isSelected()) {
            tinhTrang = "Trả Quá hạn";
        } else if (rdoDungHan.isSelected()) {
            tinhTrang = "Trả Đúng hạn";
        } else if (rdoDenHanVaChuaTra.isSelected()) {
            tinhTrang = "Đến hạn mà chưa trả sách";
        } else if (rdoQuaHanVaChuaTra.isSelected()) {
            tinhTrang = "Quá hạn nhưng Chưa trả sách";
        } else if (rdoChuaDenHan.isSelected()) {
            tinhTrang = "Chưa đến hạn trả sách";
        } else {
            tinhTrang = "";
        }
        List<Object[]> list = tkDao.getMuonTraTheoLoai(nam, thangBD, thangKT, tinhTrang, maDocGia);
        System.out.println("listsizefilter:" + list.size());
        fillTable(list);
    }

    void fillComboBoxNgaySachMuon() {
        int nam = Integer.parseInt(cboNamSachMuon.getSelectedItem() + "");
        int thang = Integer.parseInt(cboThangSachMuon.getSelectedItem() + "");
        int dMax = 0;
        switch (thang) {
            case 4:
            case 6:
            case 9:
            case 11:
                dMax = 30;
                break;
            case 2:
                boolean flag = ((nam % 4 == 0 && nam % 100 != 0) || nam % 400 == 0);
                if (flag) {
                    dMax = 29;
                } else {
                    dMax = 28;
                }
                break;
            default:
                dMax = 31;
                break;
        }
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNgaySachMuon.getModel();
        model.removeAllElements();
        for (int i = 1; i <= dMax; i++) {
            model.addElement(i);
        }

    }

    void fillTableSoLuongSachMuon(List<Object[]> list) {
        DefaultTableModel model = (DefaultTableModel) tblMuonSach.getModel();
        model.setRowCount(0);
        for (Object[] row : list) {
            model.addRow(new Object[]{row[0], row[1], row[2]});
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        lblTKVBC = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        btnThoat1 = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        pnlThongTin = new javax.swing.JPanel();
        lblNam = new javax.swing.JLabel();
        lblThangBatDau = new javax.swing.JLabel();
        cboNam = new javax.swing.JComboBox<>();
        cboThangBatDau = new javax.swing.JComboBox<>();
        lblLocTatCa = new javax.swing.JLabel();
        lblThangKetThuc = new javax.swing.JLabel();
        cboThangKetThuc = new javax.swing.JComboBox<>();
        rdoTatCaCacNam = new javax.swing.JRadioButton();
        btnFilter = new javax.swing.JButton();
        pnlRdo = new javax.swing.JPanel();
        rdoQuaHan = new javax.swing.JRadioButton();
        rdoChuaDenHan = new javax.swing.JRadioButton();
        rdoDenHanVaChuaTra = new javax.swing.JRadioButton();
        rdoDungHan = new javax.swing.JRadioButton();
        rdoQuaHanVaChuaTra = new javax.swing.JRadioButton();
        txtMaDocGiaTimKiem = new javax.swing.JTextField();
        lblMaDocGia = new javax.swing.JLabel();
        btnXuatFile = new javax.swing.JButton();
        pnlTongTienPhat = new javax.swing.JPanel();
        lblTongTienPhatTheoNam = new javax.swing.JLabel();
        lblTongTienPhatCacNam = new javax.swing.JLabel();
        txtTongTienPhatTheoNam = new javax.swing.JTextField();
        txtTongTienPhatCacNam = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThongKeMuonTra = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        muon = new javax.swing.JPanel();
        cboNamSachMuon = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cboNgaySachMuon = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cboThangSachMuon = new javax.swing.JComboBox<>();
        cboTheo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMuonSach = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setSize(new java.awt.Dimension(1100, 660));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTKVBC.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTKVBC.setForeground(new java.awt.Color(51, 102, 255));
        lblTKVBC.setText("Thống kê và báo cáo");
        getContentPane().add(lblTKVBC, new org.netbeans.lib.awtextra.AbsoluteConstraints(401, 6, -1, 45));

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/logoLibNgang.png"))); // NOI18N
        getContentPane().add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, -1));

        btnThoat1.setBackground(new java.awt.Color(255, 51, 51));
        btnThoat1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThoat1.setText("X");
        btnThoat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoat1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnThoat1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1044, 0, 50, 40));

        pnlThongTin.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlThongTin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlThongTinMouseClicked(evt);
            }
        });

        lblNam.setText("Năm:");

        lblThangBatDau.setText("Tháng Bắt Đầu :");

        cboNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cboThangBatDau.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblLocTatCa.setText("Lọc tất cả  :");

        lblThangKetThuc.setText("Tháng Kết Thúc :");

        cboThangKetThuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        buttonGroup1.add(rdoTatCaCacNam);
        rdoTatCaCacNam.setText("Tất cả theo từng năm");
        rdoTatCaCacNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTatCaCacNamActionPerformed(evt);
            }
        });

        btnFilter.setText("Lọc");
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });

        pnlRdo.setBorder(javax.swing.BorderFactory.createTitledBorder("Tình trạng trả sách"));

        buttonGroup2.add(rdoQuaHan);
        rdoQuaHan.setText("Quá hạn");

        buttonGroup2.add(rdoChuaDenHan);
        rdoChuaDenHan.setText("Chưa đến hạn");

        buttonGroup2.add(rdoDenHanVaChuaTra);
        rdoDenHanVaChuaTra.setText("Đến hạn và chưa trả");

        buttonGroup2.add(rdoDungHan);
        rdoDungHan.setText("Đúng hạn");

        buttonGroup2.add(rdoQuaHanVaChuaTra);
        rdoQuaHanVaChuaTra.setText("Quá hạn và chưa trả");

        javax.swing.GroupLayout pnlRdoLayout = new javax.swing.GroupLayout(pnlRdo);
        pnlRdo.setLayout(pnlRdoLayout);
        pnlRdoLayout.setHorizontalGroup(
            pnlRdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRdoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(rdoQuaHan, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rdoDungHan, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoChuaDenHan, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rdoDenHanVaChuaTra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdoQuaHanVaChuaTra)
                .addGap(21, 21, 21))
        );
        pnlRdoLayout.setVerticalGroup(
            pnlRdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRdoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRdoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoQuaHan)
                    .addComponent(rdoChuaDenHan)
                    .addComponent(rdoDenHanVaChuaTra)
                    .addComponent(rdoDungHan)
                    .addComponent(rdoQuaHanVaChuaTra))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        lblMaDocGia.setText("Mã Độc giả:");

        btnXuatFile.setText("Xuất FIle ");
        btnXuatFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatFileActionPerformed(evt);
            }
        });

        pnlTongTienPhat.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTongTienPhatTheoNam.setText("Tổng tiền phạt trong năm:");

        lblTongTienPhatCacNam.setText("Tổng tiền phạt từng năm:");

        javax.swing.GroupLayout pnlTongTienPhatLayout = new javax.swing.GroupLayout(pnlTongTienPhat);
        pnlTongTienPhat.setLayout(pnlTongTienPhatLayout);
        pnlTongTienPhatLayout.setHorizontalGroup(
            pnlTongTienPhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTongTienPhatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTongTienPhatTheoNam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTongTienPhatTheoNam, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 366, Short.MAX_VALUE)
                .addComponent(lblTongTienPhatCacNam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTongTienPhatCacNam, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlTongTienPhatLayout.setVerticalGroup(
            pnlTongTienPhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTongTienPhatLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(pnlTongTienPhatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTongTienPhatTheoNam)
                    .addComponent(lblTongTienPhatCacNam)
                    .addComponent(txtTongTienPhatTheoNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTongTienPhatCacNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblThongKeMuonTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã PM", "Mã Độc giả", "Tên Độc giả", "Ngày mượn", "Ngày hẹn trả", "Ngày trả", "Số ngày trễ", "Tiền phạt", "Tình trạng trả sách"
            }
        ));
        jScrollPane1.setViewportView(tblThongKeMuonTra);
        if (tblThongKeMuonTra.getColumnModel().getColumnCount() > 0) {
            tblThongKeMuonTra.getColumnModel().getColumn(0).setPreferredWidth(25);
            tblThongKeMuonTra.getColumnModel().getColumn(1).setPreferredWidth(40);
        }

        javax.swing.GroupLayout pnlThongTinLayout = new javax.swing.GroupLayout(pnlThongTin);
        pnlThongTin.setLayout(pnlThongTinLayout);
        pnlThongTinLayout.setHorizontalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnXuatFile)
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(pnlTongTienPhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinLayout.createSequentialGroup()
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addComponent(lblMaDocGia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMaDocGiaTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pnlRdo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(lblNam)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblThangBatDau)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboThangBatDau, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblThangKetThuc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboThangKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(164, 164, 164)
                .addComponent(lblLocTatCa)
                .addGap(30, 30, 30)
                .addComponent(rdoTatCaCacNam)
                .addGap(23, 23, 23))
        );
        pnlThongTinLayout.setVerticalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblLocTatCa)
                        .addComponent(rdoTatCaCacNam))
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblThangBatDau)
                        .addComponent(cboThangBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblThangKetThuc)
                        .addComponent(cboThangKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNam)))
                .addGap(9, 9, 9)
                .addComponent(pnlRdo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFilter)
                    .addComponent(txtMaDocGiaTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaDocGia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXuatFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTongTienPhat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Thống kê tình trạng mượn và trả sách", pnlThongTin);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cboNamSachMuon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNamSachMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNamSachMuonActionPerformed(evt);
            }
        });

        jLabel1.setText("Năm:");

        jLabel2.setText("Ngày:");

        cboNgaySachMuon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNgaySachMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNgaySachMuonActionPerformed(evt);
            }
        });

        jLabel3.setText("Tháng:");

        cboThangSachMuon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboThangSachMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboThangSachMuonActionPerformed(evt);
            }
        });

        cboTheo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tổng các Năm", "Trong Năm" }));
        cboTheo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTheoActionPerformed(evt);
            }
        });

        jLabel4.setText("Theo:");

        tblMuonSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sách", "Tên sách", "Số lượng mượn"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblMuonSach);
        if (tblMuonSach.getColumnModel().getColumnCount() > 0) {
            tblMuonSach.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

        javax.swing.GroupLayout muonLayout = new javax.swing.GroupLayout(muon);
        muon.setLayout(muonLayout);
        muonLayout.setHorizontalGroup(
            muonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(muonLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 830, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(muonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(muonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboThangSachMuon, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboNgaySachMuon, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboTheo, javax.swing.GroupLayout.Alignment.TRAILING, 0, 125, Short.MAX_VALUE)
                    .addComponent(cboNamSachMuon, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        muonLayout.setVerticalGroup(
            muonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(muonLayout.createSequentialGroup()
                .addGroup(muonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(muonLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(muonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboNamSachMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(muonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboThangSachMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(15, 15, 15)
                        .addGroup(muonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboNgaySachMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(muonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboTheo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addGroup(muonLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jPanel2.add(muon, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Thống kê số lượng sách mượn", jPanel1);

        getContentPane().add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, 630));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        buttonGroup1.clearSelection();
        buttonGroup2.clearSelection();
    }//GEN-LAST:event_formMouseClicked

    private void btnThoat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoat1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnThoat1ActionPerformed

    private void cboTheoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTheoActionPerformed
        List<Object[]> list = new ArrayList<>();
        if (cboTheo.getSelectedIndex() == 0) {
            list = tkDao.getSoLuongMuonSachTungNam();
        } else {
            Integer nam = (Integer) cboNamSachMuon.getSelectedItem();
            list = tkDao.getSoLuongMuonSachTrongNam(nam);
        }
        fillTableSoLuongSachMuon(list);
    }//GEN-LAST:event_cboTheoActionPerformed

    private void cboThangSachMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboThangSachMuonActionPerformed
        if (cboThangSachMuon.getSelectedItem() != null) {
            fillComboBoxNgaySachMuon();
        }
        Integer nam = (Integer) cboNamSachMuon.getSelectedItem();
        System.out.println("nam: "+ nam);
        Integer thang = (Integer) cboThangSachMuon.getSelectedItem();
        System.out.println("thang "+ thang);
        List<Object[]> list = tkDao.getSoLuongMuonSachTheoNamThang(nam, thang);
        fillTableSoLuongSachMuon(list);
    }//GEN-LAST:event_cboThangSachMuonActionPerformed

    private void cboNgaySachMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNgaySachMuonActionPerformed
        Integer nam = (Integer) cboNamSachMuon.getSelectedItem();
        Integer thang = (Integer) cboThangSachMuon.getSelectedItem();
        Integer ngay = (Integer) cboNgaySachMuon.getSelectedItem();
        List<Object[]> list = tkDao.getSoLuongMuonSachTheoNamThangNgay(nam, thang, ngay);
        fillTableSoLuongSachMuon(list);
    }//GEN-LAST:event_cboNgaySachMuonActionPerformed

    private void cboNamSachMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNamSachMuonActionPerformed
        
    }//GEN-LAST:event_cboNamSachMuonActionPerformed

    private void pnlThongTinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlThongTinMouseClicked
        buttonGroup1.clearSelection();
        buttonGroup2.clearSelection();
    }//GEN-LAST:event_pnlThongTinMouseClicked

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
        if (tblThongKeMuonTra.getRowCount() == 0) {
            MsgBox.alert(this, "Không có dữ liệu để xuất!");
        } else {
            String tieuDe = "";
            if (rdoDungHan.isSelected()) {
                tieuDe = "Trả sách đúng hạn";
            } else if (rdoQuaHan.isSelected()) {
                tieuDe = "Trả sách quá hạn";
            } else if (rdoChuaDenHan.isSelected()) {
                tieuDe = "Chưa đến hạn trả sách";
            } else if (rdoDenHanVaChuaTra.isSelected()) {
                tieuDe = "Đến hạn và chưa trả";
            } else if (rdoQuaHanVaChuaTra.isSelected()) {
                tieuDe = "Quá hạn và chưa trả sách";
            } else {
                tieuDe = "Tình hình mượn trả";
            }
            ExportFile.exportToExcel(this, tblThongKeMuonTra, tieuDe);
        }
    }//GEN-LAST:event_btnXuatFileActionPerformed

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        isBtnFilter = true;
        filter();
    }//GEN-LAST:event_btnFilterActionPerformed

    private void rdoTatCaCacNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTatCaCacNamActionPerformed
        List<Object[]> list = tkDao.getMuonTraTheoCacNam();
        fillTable(list);
        isBtnFilter = false;
    }//GEN-LAST:event_rdoTatCaCacNamActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ThongKeJDialog dialog = new ThongKeJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnThoat1;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JComboBox<String> cboNamSachMuon;
    private javax.swing.JComboBox<String> cboNgaySachMuon;
    private javax.swing.JComboBox<String> cboThangBatDau;
    private javax.swing.JComboBox<String> cboThangKetThuc;
    private javax.swing.JComboBox<String> cboThangSachMuon;
    private javax.swing.JComboBox<String> cboTheo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblLocTatCa;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMaDocGia;
    private javax.swing.JLabel lblNam;
    private javax.swing.JLabel lblTKVBC;
    private javax.swing.JLabel lblThangBatDau;
    private javax.swing.JLabel lblThangKetThuc;
    private javax.swing.JLabel lblTongTienPhatCacNam;
    private javax.swing.JLabel lblTongTienPhatTheoNam;
    private javax.swing.JPanel muon;
    private javax.swing.JPanel pnlRdo;
    private javax.swing.JPanel pnlThongTin;
    private javax.swing.JPanel pnlTongTienPhat;
    private javax.swing.JRadioButton rdoChuaDenHan;
    private javax.swing.JRadioButton rdoDenHanVaChuaTra;
    private javax.swing.JRadioButton rdoDungHan;
    private javax.swing.JRadioButton rdoQuaHan;
    private javax.swing.JRadioButton rdoQuaHanVaChuaTra;
    private javax.swing.JRadioButton rdoTatCaCacNam;
    private javax.swing.JTable tblMuonSach;
    private javax.swing.JTable tblThongKeMuonTra;
    private javax.swing.JTextField txtMaDocGiaTimKiem;
    private javax.swing.JTextField txtTongTienPhatCacNam;
    private javax.swing.JTextField txtTongTienPhatTheoNam;
    // End of variables declaration//GEN-END:variables
}
