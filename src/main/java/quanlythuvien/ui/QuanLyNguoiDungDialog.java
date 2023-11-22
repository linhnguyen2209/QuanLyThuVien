/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package quanlythuvien.ui;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import quanlythuvien.dao.LoaiNguoiDungDAO;
import quanlythuvien.dao.NguoiDungDAO;
import quanlythuvien.entity.LoaiNguoiDung;
import quanlythuvien.entity.NguoiDung;
import quanlythuvien.utils.Auth;
import quanlythuvien.utils.MsgBox;
import quanlythuvien.utils.XImage;

/**
 *
 * @author Administrator
 */
public class QuanLyNguoiDungDialog extends javax.swing.JDialog {

    NguoiDungDAO ngDAO = new NguoiDungDAO();
    LoaiNguoiDungDAO lNDDao = new LoaiNguoiDungDAO();
    List<NguoiDung> list = new ArrayList<>();
    int row = -1;

    public QuanLyNguoiDungDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    void init() {
//        this.setLocation(318,73);
        this.setLocation(325, 74);
        this.setTitle("Quản lý người dùng");
        this.setIconImage(XImage.getAppIcon());
        fillTableNgDung();
        fillComboBoxTypeOfUser();
        firt();
//        lblKetQua.setVisible(false);
    }

    void fillTableNgDung() {
        DefaultTableModel model = (DefaultTableModel) tblNguoiDung.getModel();
        model.setRowCount(0);
        try {
            if (rdoTenNguoiDung.isSelected()) {
                list = ngDAO.selectByTenNguoiDung(txtSearch.getText());
            } else if (rdoMaNguoiDung.isSelected()) {
                list = ngDAO.selectByMaOfUser(txtSearch.getText());
            } else {
                list = ngDAO.selectAll();
            }
            if (list.size() <= 0) {
                tblNguoiDung.setVisible(false);
                lblKetQua.setText("Tổng số người dùng " + list.size());
                clearForm();
            } else {
                tblNguoiDung.setVisible(true);
                lblKetQua.setText("Tổng số người dùng " + list.size());
            }
            if (Auth.isLibrarian()) { // đối với librarian thì chỉ lấy tài khoản user th
                list = filterUser(list);
            }
            for (NguoiDung nguoiDung : list) {
                String role;
                switch (nguoiDung.getMaLoaiNguoiDung()) {
                    case "LND001":
                        role = "Admin";
                        break;
                    case "LND002":
                        role = "Librarian";
                        break;
                    default:
                        role = "User";
                        break;
                }
                model.addRow(new Object[]{
                    nguoiDung.getMaNguoiDung(),
                    role,
                    nguoiDung.getTenNguoiDung(),
                    nguoiDung.getEmail(),
                    nguoiDung.getSdt(),
                    nguoiDung.getMatKhau()});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<NguoiDung> filterUser(List<NguoiDung> list) {
        List<NguoiDung> listND = new ArrayList<>();
        if (list != null) {
            for (NguoiDung nd : list) {
                if (nd.getMaLoaiNguoiDung().equals("LND003")) {
                    listND.add(nd);
                }
            }
        }
        return listND;
    }

    void fillComboBoxTypeOfUser() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboLoaiNgDung.getModel();
        model.removeAllElements();
        try {
            List<LoaiNguoiDung> list = lNDDao.selectAll();
            for (LoaiNguoiDung loaiNguoiDung : list) {
                if (Auth.isLibrarian()) {
                    if (loaiNguoiDung.getMaLoaiNguoiDung().equals("LND003")) {
                        model.addElement(loaiNguoiDung);
                        return;
                    }
                } else {
                    model.addElement(loaiNguoiDung);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setForm(NguoiDung model) {
        txtMaNgDung.setText(model.getMaNguoiDung());
        txtHoTen.setText(model.getTenNguoiDung());
        txtEmail.setText(model.getEmail());
        txtSoDienThoai.setText(model.getSdt());
        txtMatKhau.setText(model.getMatKhau());
        txtXacNhanMK.setText(model.getMatKhau());

        switch (model.getMaLoaiNguoiDung()) {
            case "LND001":
                cboLoaiNgDung.setSelectedIndex(0);
                break;
            case "LND002":
                cboLoaiNgDung.setSelectedIndex(1);
                break;
            default:
                cboLoaiNgDung.setSelectedIndex(2);
                break;
        }
    }

    NguoiDung getForm() {
        NguoiDung model = new NguoiDung();
        model.setMaNguoiDung(txtMaNgDung.getText());
        switch (cboLoaiNgDung.getSelectedIndex()) {
            case 0:
                model.setMaLoaiNguoiDung("LND001");
                break;
            case 1:
                model.setMaLoaiNguoiDung("LND002");
                break;
            default:
                model.setMaLoaiNguoiDung("LND003");
                break;
        }
        model.setTenNguoiDung(txtHoTen.getText());
        model.setEmail(txtEmail.getText());
        model.setSdt(txtSoDienThoai.getText());
        model.setMatKhau(new String(txtMatKhau.getPassword()));
        return model;
    }

    void edit() {
        String maND = (String) tblNguoiDung.getValueAt(row, 0);
        try {
            NguoiDung ngd = ngDAO.selectById(maND);
            if (ngd != null) {
                setForm(ngd);
                updateStatus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void clearForm() {
        txtMaNgDung.setText("");
        txtHoTen.setText("");
        txtEmail.setText("");
        txtSoDienThoai.setText("");
        txtMatKhau.setText("");
        txtXacNhanMK.setText("");
        cboLoaiNgDung.setSelectedIndex(0);
        this.row = -1;
        updateStatus();
    }

    void updateStatus() {
        boolean edit = this.row >= 0;
        txtMaNgDung.setEditable(!edit);
        //Khi insert thì không update, delete
        btnAdd.setEnabled(!edit);
        btnUpdate.setEnabled(edit);
        btnDelete.setEnabled(edit);
    }

    void insert() {
        NguoiDung nd = getForm();
        String matKhau_XN = new String(txtXacNhanMK.getPassword());
        if (!matKhau_XN.equals(new String(txtMatKhau.getPassword()))) {
            MsgBox.alert(this, "Xác nhận mật khẩu không chính xác!");
        } else {
            try {
                ngDAO.insert(nd);
                fillTableNgDung();
                clearForm();
                MsgBox.alert(this, "Thêm thành công");
            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.alert(this, "Thêm thất bại!");
            }
        }
    }

    void update() {
        NguoiDung nd = getForm();
        String matKhau_XN = new String(txtXacNhanMK.getPassword());
        if (!matKhau_XN.equals(new String(txtMatKhau.getPassword()))) {
            MsgBox.alert(this, "Xác nhận mật khẩu không chính xác!");
        } else {
            try {
                ngDAO.update(nd);
                fillTableNgDung();
                MsgBox.alert(this, "Cập nhật thành công");
            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.alert(this, "Cập nhật thất bại!");
            }
        }
    }

    void delete() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền xoá!");
        } else {
            try {
                if (MsgBox.confirm(this, "Bạn thực sự muốn xoá người dùng này?")) {
                    String maND = txtMaNgDung.getText();
                    ngDAO.delete(maND);
                    this.fillTableNgDung();
                    this.clearForm();
                    MsgBox.alert(this, "Xoá thành công!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.alert(this, "Xoá thất bại!");
            }
        }
    }

    void firt() {
        row = 0;
        edit();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loaiTimKiem = new javax.swing.ButtonGroup();
        pnlContain = new javax.swing.JPanel();
        pnlTimKiem = new javax.swing.JPanel();
        btnSearch = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        pnlDanhSach = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNguoiDung = new javax.swing.JTable();
        lblKetQua = new javax.swing.JLabel();
        lblQLND = new javax.swing.JLabel();
        pnlLoaiTimKiem = new javax.swing.JPanel();
        rdoMaNguoiDung = new javax.swing.JRadioButton();
        rdoTenNguoiDung = new javax.swing.JRadioButton();
        rdoAll = new javax.swing.JRadioButton();
        pnlThongTin = new javax.swing.JPanel();
        lblMaND = new javax.swing.JLabel();
        txtMaNgDung = new javax.swing.JTextField();
        lblVaiTro = new javax.swing.JLabel();
        cboLoaiNgDung = new javax.swing.JComboBox<>();
        txtHoTen = new javax.swing.JTextField();
        lblTen = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        lblSDT = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();
        lblPass = new javax.swing.JLabel();
        lblXacNhanMK = new javax.swing.JLabel();
        txtXacNhanMK = new javax.swing.JPasswordField();
        pnlChucNang1 = new javax.swing.JPanel();
        btnNew = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        pnlChucNang2 = new javax.swing.JPanel();
        btnHome = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1100, 690));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1100, 690));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        pnlContain.add(pnlTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 620, 70));

        pnlDanhSach.setBackground(new java.awt.Color(204, 255, 255));
        pnlDanhSach.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách người dùng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tblNguoiDung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Người Dùng", "Vai trò", "Họ Tên", "Email", "Số Điện Thoại", "Mật khẩu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNguoiDung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblNguoiDungMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblNguoiDung);
        if (tblNguoiDung.getColumnModel().getColumnCount() > 0) {
            tblNguoiDung.getColumnModel().getColumn(2).setPreferredWidth(150);
        }

        lblKetQua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblKetQua.setForeground(new java.awt.Color(0, 0, 102));
        lblKetQua.setText("Kết quả tìm kiếm \"0\"");

        javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
        pnlDanhSach.setLayout(pnlDanhSachLayout);
        pnlDanhSachLayout.setHorizontalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap(501, Short.MAX_VALUE)
                .addComponent(lblKetQua)
                .addGap(451, 451, 451))
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addComponent(lblKetQua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pnlContain.add(pnlDanhSach, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 1080, 300));

        lblQLND.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblQLND.setForeground(new java.awt.Color(51, 153, 255));
        lblQLND.setText("Quản lý người dùng");
        pnlContain.add(lblQLND, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, 230, 30));

        pnlLoaiTimKiem.setBackground(new java.awt.Color(204, 255, 255));
        pnlLoaiTimKiem.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Theo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        loaiTimKiem.add(rdoMaNguoiDung);
        rdoMaNguoiDung.setText("Mã người dùng");

        loaiTimKiem.add(rdoTenNguoiDung);
        rdoTenNguoiDung.setText("Tên người dùng");

        loaiTimKiem.add(rdoAll);
        rdoAll.setText("Tất cả");
        rdoAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLoaiTimKiemLayout = new javax.swing.GroupLayout(pnlLoaiTimKiem);
        pnlLoaiTimKiem.setLayout(pnlLoaiTimKiemLayout);
        pnlLoaiTimKiemLayout.setHorizontalGroup(
            pnlLoaiTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiTimKiemLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(rdoMaNguoiDung)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoTenNguoiDung)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoAll, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(118, Short.MAX_VALUE))
        );
        pnlLoaiTimKiemLayout.setVerticalGroup(
            pnlLoaiTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiTimKiemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLoaiTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoMaNguoiDung)
                    .addComponent(rdoTenNguoiDung)
                    .addComponent(rdoAll))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pnlContain.add(pnlLoaiTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 70, 440, 70));

        pnlThongTin.setBackground(new java.awt.Color(204, 255, 255));
        pnlThongTin.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        lblMaND.setText("Mã người dùng:");

        lblVaiTro.setText("Vai trò");

        cboLoaiNgDung.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLoaiNgDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiNgDungActionPerformed(evt);
            }
        });

        lblTen.setText("Họ Tên:");

        lblEmail.setText("Email:");

        lblSDT.setText("Số điện thoại:");

        lblPass.setText("Mật khẩu:");

        lblXacNhanMK.setText("Xác nhận mật khẩu:");

        javax.swing.GroupLayout pnlThongTinLayout = new javax.swing.GroupLayout(pnlThongTin);
        pnlThongTin.setLayout(pnlThongTinLayout);
        pnlThongTinLayout.setHorizontalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblSDT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlThongTinLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTen)
                            .addComponent(lblMaND, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmail))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNgDung, javax.swing.GroupLayout.Alignment.LEADING))))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPass)
                    .addComponent(lblXacNhanMK)
                    .addComponent(lblVaiTro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtXacNhanMK, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboLoaiNgDung, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        pnlThongTinLayout.setVerticalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaND)
                    .addComponent(txtMaNgDung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPass)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTen)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblXacNhanMK)
                    .addComponent(txtXacNhanMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblVaiTro)
                    .addComponent(cboLoaiNgDung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSDT)
                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        pnlContain.add(pnlThongTin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 730, 230));

        pnlChucNang1.setBackground(new java.awt.Color(204, 255, 255));
        pnlChucNang1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        pnlChucNang1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNew.setText("Mới");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        pnlChucNang1.add(btnNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 150, 40));

        btnAdd.setText("Thêm");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        pnlChucNang1.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 140, 40));

        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        pnlChucNang1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 150, 40));

        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        pnlChucNang1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 140, 40));

        pnlContain.add(pnlChucNang1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 440, 340, 140));

        pnlChucNang2.setBackground(new java.awt.Color(204, 255, 255));
        pnlChucNang2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng khác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        pnlChucNang2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnHome.setBackground(new java.awt.Color(0, 204, 204));
        btnHome.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnHome.setText("Home");
        pnlChucNang2.add(btnHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 33, 150, 30));

        btnThoat.setBackground(new java.awt.Color(255, 51, 51));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });
        pnlChucNang2.add(btnThoat, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 33, 140, 30));

        pnlContain.add(pnlChucNang2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 580, 340, 90));

        getContentPane().add(pnlContain, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 710));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboLoaiNgDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiNgDungActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLoaiNgDungActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        if (!rdoMaNguoiDung.isSelected() && !rdoTenNguoiDung.isSelected() && !rdoAll.isSelected()) {
            MsgBox.alert(this, "Vui lòng chọn loại tìm kiếm!");
        } else {
            fillTableNgDung();
            if (rdoMaNguoiDung.isSelected()) {
                NguoiDung nd = ngDAO.selectById(txtSearch.getText());
                if (nd != null) {
                    setForm(nd);
                } else {
                    clearForm();
                }
            }
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void tblNguoiDungMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguoiDungMousePressed
        this.row = tblNguoiDung.rowAtPoint(evt.getPoint());
        edit();
    }//GEN-LAST:event_tblNguoiDungMousePressed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        insert();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearForm();
    }//GEN-LAST:event_btnNewActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        loaiTimKiem.clearSelection();
    }//GEN-LAST:event_formMouseClicked

    private void rdoAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoAllActionPerformed
        fillTableNgDung();
    }//GEN-LAST:event_rdoAllActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnThoatActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLyNguoiDungDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyNguoiDungDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyNguoiDungDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyNguoiDungDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyNguoiDungDialog dialog = new QuanLyNguoiDungDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboLoaiNgDung;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblKetQua;
    private javax.swing.JLabel lblMaND;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblQLND;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblTen;
    private javax.swing.JLabel lblVaiTro;
    private javax.swing.JLabel lblXacNhanMK;
    private javax.swing.ButtonGroup loaiTimKiem;
    private javax.swing.JPanel pnlChucNang1;
    private javax.swing.JPanel pnlChucNang2;
    private javax.swing.JPanel pnlContain;
    private javax.swing.JPanel pnlDanhSach;
    private javax.swing.JPanel pnlLoaiTimKiem;
    private javax.swing.JPanel pnlThongTin;
    private javax.swing.JPanel pnlTimKiem;
    private javax.swing.JRadioButton rdoAll;
    private javax.swing.JRadioButton rdoMaNguoiDung;
    private javax.swing.JRadioButton rdoTenNguoiDung;
    private javax.swing.JTable tblNguoiDung;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNgDung;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JPasswordField txtXacNhanMK;
    // End of variables declaration//GEN-END:variables
}
