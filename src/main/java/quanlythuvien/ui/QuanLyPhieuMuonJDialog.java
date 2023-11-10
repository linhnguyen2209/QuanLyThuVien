package quanlythuvien.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import quanlythuvien.dao.PhieuMuonDAO;
import quanlythuvien.dao.PhieuTraDAO;
import quanlythuvien.entity.PhieuMuon;
import quanlythuvien.entity.PhieuTra;
import quanlythuvien.utils.XDate;
import quanlythuvien.utils.XImage;

/**
 *
 * @author thuon
 */
public class QuanLyPhieuMuonJDialog extends javax.swing.JDialog {

    PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO();
    PhieuTraDAO phieuTraDAO = new PhieuTraDAO();
    int row = -1;

    public QuanLyPhieuMuonJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    void init() {
        this.setLocation(318, 73);
        this.setTitle("Quản lý phiếu mượn");
        this.setIconImage(XImage.getAppIcon());
        fillTablePhieuMuon();
        fillTablePhieuTra();
    }

    void fillTablePhieuMuon() {
        DefaultTableModel model = (DefaultTableModel) tblPhieuMuon.getModel();
        model.setRowCount(0);
        try {
            List<PhieuMuon> list = phieuMuonDAO.selectAll();
            for (PhieuMuon phieuMuon : list) {
                Object[] row = {phieuMuon.getMaPhieuMuon(), XDate.toString(phieuMuon.getNgayMuon(), "yyyy/MM/dd"),
                    XDate.toString(phieuMuon.getNgayHenTra(), "yyyy/MM/dd"), phieuMuon.getTongSoLuongSachMuon(), phieuMuon.getMaNguoiDung(), phieuMuon.getGhiChu()};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void fillTablePhieuTra() {
        DefaultTableModel model = (DefaultTableModel) tblPhieuTra.getModel();
        model.setRowCount(0);
        try {
            List<PhieuTra> list = phieuTraDAO.selectAll();
            for (PhieuTra phieuTra : list) {
                Object[] row = {phieuTra.getMaPhieuTra(), phieuTra.getMaPhieuMuon(), XDate.toString(phieuTra.getNgayTra(), "yyyy/MM/dd"),
                    phieuTra.isTrangThai() ? "Đã trả" : "Chưa trả", phieuTra.getMaNguoiDung(), phieuTra.getGhiChu()};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setForm(PhieuMuon model) {
        txtMaPhieuMuon.setText(String.valueOf((model.getMaPhieuMuon())));
        Date ngayMuon = model.getNgayMuon();
        txtNgayMuon.setText(XDate.toString(ngayMuon, "yyyy/MM/dd"));
        Date ngayTra = model.getNgayHenTra();
        txtNgayHenTra.setText(XDate.toString(ngayTra, "yyyy/MM/dd"));
        txtTongSoLuongSachMuon.setText(String.valueOf(model.getTongSoLuongSachMuon()));
        txtMaNguoiDung.setText(model.getMaNguoiDung());
        txtGhiChu.setText(model.getGhiChu());
    }

    void clearForm() {
        txtMaPhieuMuon.setText("");
        txtNgayMuon.setText("");
        txtNgayHenTra.setText("");
        txtTongSoLuongSachMuon.setText("");
        txtMaNguoiDung.setText("");
        txtGhiChu.setText("");
        this.row = -1;
        updateStatus();
    }

    void edit() {
        Integer maPM = (Integer) tblPhieuMuon.getValueAt(row, 0);
        try {
            PhieuMuon phieuMuon = phieuMuonDAO.selectById(maPM);
            if (phieuMuon != null) {
                setForm(phieuMuon);
                updateStatus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateStatus() {
        boolean edit = this.row >= 0;
        boolean first = this.row == 0;
        boolean last = this.row == tblPhieuMuon.getRowCount() - 1;

        txtMaPhieuMuon.setEditable(!edit);
        txtMaNguoiDung.setEditable(!edit);
        //Khi insert thì không update, delete
        btn_Them.setEnabled(!edit);
        btn_Sua.setEnabled(edit);
        btn_Xoa.setEnabled(edit);

        btn_first.setEnabled(edit && !first);
        btn_prev.setEnabled(edit && !first);
        btn_next.setEnabled(edit && !last);
        btn_last.setEnabled(edit && !last);

    }

    private void first() {
        row = 0;
        edit();
    }

    private void prev() {
        if (row > 0) {
            row--;
            edit();
        }
    }

    private void next() {
        if (row < tblPhieuMuon.getRowCount() - 1) {
            row++;
            edit();
        }
    }

    private void last() {
        row = tblPhieuMuon.getRowCount() - 1;
        edit();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhieuMuon = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNgayMuon = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMaPhieuMuon = new javax.swing.JTextField();
        txtNgayHenTra = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTongSoLuongSachMuon = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtMaNguoiDung = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btn_Them = new javax.swing.JButton();
        btn_Sua = new javax.swing.JButton();
        btn_Xoa = new javax.swing.JButton();
        btn_Moi = new javax.swing.JButton();
        btn_first = new javax.swing.JButton();
        btn_prev = new javax.swing.JButton();
        btn_next = new javax.swing.JButton();
        btn_last = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPhieuTra = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblPhieuMuon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã phiếu mượn", "Ngày mượn", "Ngày hẹn trả", "Tổng SL sách mượn", "Mã người dùng", "Ghi chú"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPhieuMuon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblPhieuMuonMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblPhieuMuon);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1082, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("Danh Sách Phiếu Mượn", jPanel1);

        jLabel3.setText("Mã Phiếu mượn");

        jLabel4.setText("Ngày mượn");

        jLabel5.setText("Ngày hẹn trả");

        jLabel6.setText("Tổng số lượng sách mượn");

        jLabel7.setText("Mã người dùng");

        jLabel8.setText("Ghi chú");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 153, 255));
        jLabel16.setText("Chế độ chỉnh sửa");

        btn_Them.setText("Thêm");

        btn_Sua.setText("Sửa");

        btn_Xoa.setText("Xóa");

        btn_Moi.setText("Mới");
        btn_Moi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_MoiActionPerformed(evt);
            }
        });

        btn_first.setText("|<<");
        btn_first.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_firstActionPerformed(evt);
            }
        });

        btn_prev.setText("<<");
        btn_prev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_prevActionPerformed(evt);
            }
        });

        btn_next.setText(">>");
        btn_next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nextActionPerformed(evt);
            }
        });

        btn_last.setText(">>|");
        btn_last.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lastActionPerformed(evt);
            }
        });

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane3.setViewportView(txtGhiChu);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNgayMuon)
                    .addComponent(txtMaPhieuMuon)
                    .addComponent(txtNgayHenTra)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPane3))
                    .addComponent(txtTongSoLuongSachMuon)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_Them)
                        .addGap(5, 5, 5)
                        .addComponent(btn_Sua)
                        .addGap(5, 5, 5)
                        .addComponent(btn_Xoa)
                        .addGap(5, 5, 5)
                        .addComponent(btn_Moi))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(362, 362, 362)
                        .addComponent(btn_first)
                        .addGap(5, 5, 5)
                        .addComponent(btn_prev)
                        .addGap(5, 5, 5)
                        .addComponent(btn_next)
                        .addGap(5, 5, 5)
                        .addComponent(btn_last))
                    .addComponent(txtMaNguoiDung)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(353, 353, 353)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(308, 308, 308))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(345, 345, 345)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(52, 52, 52))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(txtNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTongSoLuongSachMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNgayHenTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtMaNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Them)
                    .addComponent(btn_Sua)
                    .addComponent(btn_Xoa)
                    .addComponent(btn_Moi)
                    .addComponent(btn_first)
                    .addComponent(btn_prev)
                    .addComponent(btn_next)
                    .addComponent(btn_last))
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        tabs.addTab("Chỉnh Sửa", jPanel2);

        jPanel6.setLayout(new java.awt.GridLayout(1, 4, 5, 5));

        tblPhieuTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã phiếu trả", "Mã phiếu mượn", "Ngày trả", "Trạng thái", "Mã người dùng", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblPhieuTra);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 153, 255));
        jLabel17.setText("Danh Sách Mượn quá Hạn");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(390, 390, 390)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(341, 341, 341)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(299, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        tabs.addTab("Quản Lí Phiếu Trả", jPanel3);

        getContentPane().add(tabs, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1090, 570));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/logoLibNgang.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 12, 240, 110));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 153, 255));
        jLabel18.setText("Quản lý phiếu mượn");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, 230, 60));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_MoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_MoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btn_MoiActionPerformed

    private void btn_firstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_firstActionPerformed
        first();
    }//GEN-LAST:event_btn_firstActionPerformed

    private void btn_prevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_prevActionPerformed
        prev();
    }//GEN-LAST:event_btn_prevActionPerformed

    private void btn_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nextActionPerformed
        next();
    }//GEN-LAST:event_btn_nextActionPerformed

    private void btn_lastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lastActionPerformed
        last();
    }//GEN-LAST:event_btn_lastActionPerformed

    private void tblPhieuMuonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuMuonMousePressed
        if (evt.getClickCount() == 2) {
            this.row = tblPhieuMuon.rowAtPoint(evt.getPoint());
            edit();
        }
    }//GEN-LAST:event_tblPhieuMuonMousePressed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyPhieuMuonJDialog dialog = new QuanLyPhieuMuonJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_Moi;
    private javax.swing.JButton btn_Sua;
    private javax.swing.JButton btn_Them;
    private javax.swing.JButton btn_Xoa;
    private javax.swing.JButton btn_first;
    private javax.swing.JButton btn_last;
    private javax.swing.JButton btn_next;
    private javax.swing.JButton btn_prev;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblPhieuMuon;
    private javax.swing.JTable tblPhieuTra;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaNguoiDung;
    private javax.swing.JTextField txtMaPhieuMuon;
    private javax.swing.JTextField txtNgayHenTra;
    private javax.swing.JTextField txtNgayMuon;
    private javax.swing.JTextField txtTongSoLuongSachMuon;
    // End of variables declaration//GEN-END:variables
}
