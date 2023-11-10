/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package quanlythuvien.ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import quanlythuvien.dao.LoaiSachDao;
import quanlythuvien.dao.SachDAO;
import quanlythuvien.entity.LoaiSach;
import quanlythuvien.entity.Sach;
import quanlythuvien.utils.XImage;

/**
 *
 * @author Dino
 * Disign By Linh
 * Edit By Dino
 */
public class QuanLyTaiLieuDialog extends javax.swing.JDialog {

    SachDAO SDao = new SachDAO();
    LoaiSachDao LSDao = new LoaiSachDao();
    int row = -1;
    /**
     * Creates new form QuanLyTaiLieuDialog
     */
    public QuanLyTaiLieuDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

     void init() {
        this.setLocation(318,73);
        this.setTitle("Quản lý tài liệu");
        this.setIconImage(XImage.getAppIcon());
         FillTable_QLTlieu();
         fillComboBoxLoaiSach();
    }
    
     List<Sach> list = new ArrayList<>();
     
     void FillTable_QLTlieu(){
         DefaultTableModel model  = (DefaultTableModel) tbl_QLTL.getModel();
         model.setRowCount(0);
         try {
             if(rdoTenSach.isSelected()){
                 list =SDao.selectByTieuDe(txtSearch.getText());
             }else if(rdoNXB.isSelected()){
                 list = SDao.selectByNhaXB(txtSearch.getText());
             }else if(rdoTacGia.isSelected()){
                 list =SDao.selectByTacGia(txtSearch.getText());
             }else if(rdoMaLoaiSach.isSelected()){
                 list = SDao.selectByMaLoaiSach(txtSearch.getText());
             }else{
                 list = SDao.selectAll();
             }
             if (list.size() <= 0) {
                tbl_QLTL.setVisible(false);
                lblKetQua.setText("Tổng số sách " + list.size());
                clearForm();
            } else {
                tbl_QLTL.setVisible(true);
                lblKetQua.setText("Tổng số Sách " + list.size());
            }
             System.out.println("List: " + list.size());
             for(Sach sach:list){
                 model.addRow(new Object[]{
                     sach.getMaSach(),
                     sach.getTieuDe(),
                     sach.getNhaXuatBan(),
                     sach.getTacGia(),
                     sach.getSoTrang(),
                     sach.getSoLuongSach(),
                     sach.getGiaTien(),
                     sach.getNgayNhapKho(),
                     sach.getViTriSach(),
                     sach.getMaLoaiSach()
                 });
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
     
     void fillComboBoxLoaiSach() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboLoaiSach.getModel();
        model.removeAllElements();
        try {
            List<LoaiSach> list = LSDao.selectAll();
            for (LoaiSach ls : list) {
                model.addElement(ls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
     void fillTableLoaiSach() {
        DefaultTableModel model = (DefaultTableModel) tbl_loaisach.getModel();
        model.setRowCount(0);
        try {
            List<LoaiSach> list2 = LSDao.selectAll();
            for (LoaiSach ls : list2) {
                model.addRow(new Object[]{
                    ls.getMaLoaiSach(),
                    ls.getTenLoaiSach()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void setForm(Sach model) {
        txtTieude.setText(model.getTieuDe());
        txtNhaXuatBan.setText(model.getNhaXuatBan());
        txtTacGia.setText(model.getTacGia());
        txtSoTrang.setText(Integer.toString(model.getSoTrang()));
        txt_SoLuongSach.setText(Integer.toString(model.getSoLuongSach()));
        txtGiaTien.setText(Double.toString(model.getGiaTien()));
        txtNgayNhapKho.setText(String.valueOf(model.getNgayNhapKho()));
        txtViTri.setText(model.getViTriSach());
        cboLoaiSach.setSelectedItem(model.getMaLoaiSach());
    }
    
    void clearForm() {
        txtTieude.setText("");
        txtNhaXuatBan.setText("");
        txtTacGia.setText("");
        txtSoTrang.setText("");
        txt_SoLuongSach.setText("");
        txtGiaTien.setText("");
        txtNgayNhapKho.setText("");
        txtViTri.setText("");
        cboLoaiSach.setSelectedIndex(0);
    }
    
    void edit() {
    Integer MaSach = (Integer) tbl_QLTL.getValueAt(row, 0);
    try {
        Sach sh = SDao.selectById(MaSach);
        if (sh != null) {
            setForm(sh);
            // updateStatus(); // You can uncomment this line if needed
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

     
     
     
     
     
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        btn_timkiemRDO = new javax.swing.ButtonGroup();
        jRadioButton1 = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        tabs = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btn_ThemLoaiSach = new javax.swing.JButton();
        btn_SuaLoaiSach = new javax.swing.JButton();
        btn_XoaLoaiSach = new javax.swing.JButton();
        btn_MoiLS = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_loaisach = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        txt_LoaiSachQLi = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txt_QL_MaLoaiSach = new javax.swing.JTextField();
        pnlContain = new javax.swing.JPanel();
        pnlTimKiem = new javax.swing.JPanel();
        btnSearch = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        pnlDanhSach = new javax.swing.JPanel();
        lblKetQua = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_QLTL = new javax.swing.JTable();
        pnlLoaiTimKiem = new javax.swing.JPanel();
        rdoTenSach = new javax.swing.JRadioButton();
        rdoNXB = new javax.swing.JRadioButton();
        rdoTacGia = new javax.swing.JRadioButton();
        rdoMaLoaiSach = new javax.swing.JRadioButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        pnlThongTin = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cboLoaiSach = new javax.swing.JComboBox<>();
        txtGiaTien = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtNgayNhapKho = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtNhaXuatBan = new javax.swing.JTextField();
        txtViTri = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtTieude = new javax.swing.JTextField();
        txtTacGia = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtSoTrang = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txt_SoLuongSach = new javax.swing.JTextField();
        pnlDieuHuong = new javax.swing.JPanel();
        btn_last = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        pnlChucNang2 = new javax.swing.JPanel();
        btn_Home = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        pnlChucNang3 = new javax.swing.JPanel();
        btnNew1 = new javax.swing.JButton();
        btnAdd1 = new javax.swing.JButton();
        btnUpdate1 = new javax.swing.JButton();
        btnDelete1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 153));

        jPanel6.setLayout(new java.awt.GridLayout(1, 4, 5, 5));

        btn_ThemLoaiSach.setText("Thêm");
        jPanel6.add(btn_ThemLoaiSach);

        btn_SuaLoaiSach.setText("Sửa");
        jPanel6.add(btn_SuaLoaiSach);

        btn_XoaLoaiSach.setText("Xóa");
        jPanel6.add(btn_XoaLoaiSach);

        btn_MoiLS.setText("Mới");
        jPanel6.add(btn_MoiLS);

        tbl_loaisach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Loại Sách", "Loại Sách"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbl_loaisach);

        jLabel14.setText("Loại Sách");

        jLabel15.setText("Mã Loại Sách");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(38, 38, 38)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_QL_MaLoaiSach, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                            .addComponent(txt_LoaiSachQLi))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_QL_MaLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_LoaiSachQLi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 325, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27))
        );

        tabs.addTab("Quản lí loại sách", jPanel5);

        pnlContain.setBackground(new java.awt.Color(204, 255, 255));
        pnlContain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlTimKiem.setBackground(new java.awt.Color(204, 255, 255));
        pnlTimKiem.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        pnlTimKiem.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        pnlTimKiem.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 120, -1));
        pnlTimKiem.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 452, -1));

        pnlContain.add(pnlTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 70));

        pnlDanhSach.setBackground(new java.awt.Color(204, 255, 255));
        pnlDanhSach.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách người dùng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        lblKetQua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblKetQua.setForeground(new java.awt.Color(0, 0, 102));
        lblKetQua.setText("Kết quả tìm kiếm \"0\"");

        tbl_QLTL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sách", "Tiêu Đề", "Nhà Xuất Bản", "Tác Giả", "Số Trang", "SL Sách", "Giá Tiền", "Ngày Nhập", "Vị Trí", "Mã Loại Sách"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_QLTL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_QLTLMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_QLTL);

        javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
        pnlDanhSach.setLayout(pnlDanhSachLayout);
        pnlDanhSachLayout.setHorizontalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1068, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblKetQua)
                .addGap(471, 471, 471))
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addComponent(lblKetQua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlContain.add(pnlDanhSach, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1090, 250));

        pnlLoaiTimKiem.setBackground(new java.awt.Color(204, 255, 255));
        pnlLoaiTimKiem.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Theo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        btn_timkiemRDO.add(rdoTenSach);
        rdoTenSach.setText("Tên sách");

        btn_timkiemRDO.add(rdoNXB);
        rdoNXB.setText("NXB");

        btn_timkiemRDO.add(rdoTacGia);
        rdoTacGia.setText("Tác giả");
        rdoTacGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTacGiaActionPerformed(evt);
            }
        });

        btn_timkiemRDO.add(rdoMaLoaiSach);
        rdoMaLoaiSach.setText("Mã loại sách");
        rdoMaLoaiSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoMaLoaiSachActionPerformed(evt);
            }
        });

        jToggleButton1.setText("Sắp xếp");

        javax.swing.GroupLayout pnlLoaiTimKiemLayout = new javax.swing.GroupLayout(pnlLoaiTimKiem);
        pnlLoaiTimKiem.setLayout(pnlLoaiTimKiemLayout);
        pnlLoaiTimKiemLayout.setHorizontalGroup(
            pnlLoaiTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiTimKiemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdoTenSach)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoNXB)
                .addGap(12, 12, 12)
                .addComponent(rdoTacGia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoMaLoaiSach)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        pnlLoaiTimKiemLayout.setVerticalGroup(
            pnlLoaiTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiTimKiemLayout.createSequentialGroup()
                .addGroup(pnlLoaiTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoTenSach)
                    .addComponent(rdoNXB)
                    .addComponent(rdoTacGia)
                    .addComponent(rdoMaLoaiSach)
                    .addComponent(jToggleButton1))
                .addGap(0, 20, Short.MAX_VALUE))
        );

        pnlContain.add(pnlLoaiTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 0, 440, 70));

        pnlThongTin.setBackground(new java.awt.Color(204, 255, 255));
        pnlThongTin.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel16.setText("Giá Tiền");

        jLabel17.setText("Tiêu đề");

        cboLoaiSach.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel18.setText("Ngày Nhập Kho");

        jLabel19.setText("Vị Trí Đặt Sách");

        txtNhaXuatBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNhaXuatBanActionPerformed(evt);
            }
        });

        txtViTri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtViTriActionPerformed(evt);
            }
        });

        jLabel20.setText("Nhà Xuất Bản");

        jLabel21.setText("Loại Sách");

        jLabel22.setText("Tác giả");

        jLabel23.setText("Số Trang");

        jLabel24.setText("Số Lượng Sách");

        javax.swing.GroupLayout pnlThongTinLayout = new javax.swing.GroupLayout(pnlThongTin);
        pnlThongTin.setLayout(pnlThongTinLayout);
        pnlThongTinLayout.setHorizontalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTieude, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel23))
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNhaXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel24))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel16)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlThongTinLayout.createSequentialGroup()
                        .addComponent(txtSoTrang, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlThongTinLayout.createSequentialGroup()
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_SoLuongSach, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(txtGiaTien))
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel19)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinLayout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(jLabel21)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cboLoaiSach, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNgayNhapKho, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                    .addComponent(txtViTri, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        pnlThongTinLayout.setVerticalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(txtNgayNhapKho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTieude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17))
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSoTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel23)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtViTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19))
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(txtNhaXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel24)
                        .addComponent(txt_SoLuongSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22)))
                .addGap(0, 43, Short.MAX_VALUE))
        );

        pnlContain.add(pnlThongTin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 1090, 160));

        pnlDieuHuong.setBackground(new java.awt.Color(204, 255, 255));
        pnlDieuHuong.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        pnlDieuHuong.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_last.setText(">>|");
        btn_last.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lastActionPerformed(evt);
            }
        });
        pnlDieuHuong.add(btn_last, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 70, 30));

        btnFirst.setText("|<<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });
        pnlDieuHuong.add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 70, 30));

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        pnlDieuHuong.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 70, 30));

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        pnlDieuHuong.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 70, 30));

        pnlContain.add(pnlDieuHuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 480, 370, 80));

        pnlChucNang2.setBackground(new java.awt.Color(204, 255, 255));
        pnlChucNang2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng khác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        pnlChucNang2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_Home.setText("Home");
        pnlChucNang2.add(btn_Home, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 110, 30));

        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });
        pnlChucNang2.add(btnThoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 100, 30));

        pnlContain.add(pnlChucNang2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 480, 280, 80));

        pnlChucNang3.setBackground(new java.awt.Color(204, 255, 255));
        pnlChucNang3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        pnlChucNang3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNew1.setText("New");
        btnNew1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNew1ActionPerformed(evt);
            }
        });
        pnlChucNang3.add(btnNew1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 90, 30));

        btnAdd1.setText("Add");
        btnAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd1ActionPerformed(evt);
            }
        });
        pnlChucNang3.add(btnAdd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 90, 30));

        btnUpdate1.setText("Update");
        btnUpdate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate1ActionPerformed(evt);
            }
        });
        pnlChucNang3.add(btnUpdate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 90, 30));

        btnDelete1.setText("Delete");
        btnDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete1ActionPerformed(evt);
            }
        });
        pnlChucNang3.add(btnDelete1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 90, 30));

        pnlContain.add(pnlChucNang3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 440, 80));

        tabs.addTab("Quản lý tài liệu", pnlContain);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 0));
        jLabel2.setText("Quản lý tài liệu");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/logoLibNgang.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(329, 329, 329)
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
                this.dispose();
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed

    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed

    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed

    }//GEN-LAST:event_btnFirstActionPerformed

    private void btn_lastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lastActionPerformed

    }//GEN-LAST:event_btn_lastActionPerformed

    private void rdoTacGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTacGiaActionPerformed

    }//GEN-LAST:event_rdoTacGiaActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        FillTable_QLTlieu();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtNhaXuatBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNhaXuatBanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNhaXuatBanActionPerformed

    private void txtViTriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtViTriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtViTriActionPerformed

    private void btnNew1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNew1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNew1ActionPerformed

    private void btnAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAdd1ActionPerformed

    private void btnUpdate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdate1ActionPerformed

    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDelete1ActionPerformed

    private void rdoMaLoaiSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoMaLoaiSachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoMaLoaiSachActionPerformed

    private void tbl_QLTLMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_QLTLMousePressed
         if (evt.getClickCount() == 2) {
            this.row = tbl_QLTL.rowAtPoint(evt.getPoint());
            edit();
        }
    }//GEN-LAST:event_tbl_QLTLMousePressed

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
            java.util.logging.Logger.getLogger(QuanLyTaiLieuDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyTaiLieuDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyTaiLieuDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyTaiLieuDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyTaiLieuDialog dialog = new QuanLyTaiLieuDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAdd1;
    private javax.swing.JButton btnDelete1;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnNew1;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnUpdate1;
    private javax.swing.JButton btn_Home;
    private javax.swing.JButton btn_MoiLS;
    private javax.swing.JButton btn_SuaLoaiSach;
    private javax.swing.JButton btn_ThemLoaiSach;
    private javax.swing.JButton btn_XoaLoaiSach;
    private javax.swing.JButton btn_last;
    private javax.swing.ButtonGroup btn_timkiemRDO;
    private javax.swing.JComboBox<String> cboLoaiSach;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel lblKetQua;
    private javax.swing.JPanel pnlChucNang2;
    private javax.swing.JPanel pnlChucNang3;
    private javax.swing.JPanel pnlContain;
    private javax.swing.JPanel pnlDanhSach;
    private javax.swing.JPanel pnlDieuHuong;
    private javax.swing.JPanel pnlLoaiTimKiem;
    private javax.swing.JPanel pnlThongTin;
    private javax.swing.JPanel pnlTimKiem;
    private javax.swing.JRadioButton rdoMaLoaiSach;
    private javax.swing.JRadioButton rdoNXB;
    private javax.swing.JRadioButton rdoTacGia;
    private javax.swing.JRadioButton rdoTenSach;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tbl_QLTL;
    private javax.swing.JTable tbl_loaisach;
    private javax.swing.JTextField txtGiaTien;
    private javax.swing.JTextField txtNgayNhapKho;
    private javax.swing.JTextField txtNhaXuatBan;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoTrang;
    private javax.swing.JTextField txtTacGia;
    private javax.swing.JTextField txtTieude;
    private javax.swing.JTextField txtViTri;
    private javax.swing.JTextField txt_LoaiSachQLi;
    private javax.swing.JTextField txt_QL_MaLoaiSach;
    private javax.swing.JTextField txt_SoLuongSach;
    // End of variables declaration//GEN-END:variables
}
