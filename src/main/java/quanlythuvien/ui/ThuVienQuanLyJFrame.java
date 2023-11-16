/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package quanlythuvien.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JDialog;
import quanlythuvien.dao.NguoiDungDAO;
import quanlythuvien.entity.NguoiDung;
import quanlythuvien.ui.*;
import quanlythuvien.utils.Auth;
import quanlythuvien.utils.MsgBox;
import quanlythuvien.utils.XImage;

/**
 *
 * @author thuon
 */
public class ThuVienQuanLyJFrame extends javax.swing.JFrame {

    Component btnClicked; // lưu btn đã click trước
    Component pnlClicked; //lưu pnl đã click trước
    NguoiDungDAO ndDAO = new NguoiDungDAO();

    public ThuVienQuanLyJFrame() {
        initComponents();
        init();
        Auth.checkLoginOneTime = true;
        lblUser.setText(Auth.user.getMaNguoiDung());
        btnClicked = btnTrangChu; // ban đầu nmặc định chọn trang chủ
        pnlClicked = pnlTrangChuQuanLy;
    }

    void init() {
        this.setLocationRelativeTo(null);
        this.setTitle("Quản lý thư viện");
        this.setIconImage(XImage.getAppIcon());
    }

    void openMainFrame() {
        new ThuVienMainJFrame().setVisible(true);
    }

    void openTraCuuSach() {
        new TraCuuSachJDialog(this, true).setVisible(true);
    }

    void openQuanLyNguoiDung() {
        new QuanLyNguoiDungDialog(this, true).setVisible(true);
    }

    void openQuanLyTaiLieu() {
        new QuanLyTaiLieuDialog(this, true).setVisible(true);
    }

    void openQuanLyMuonTra() {
        new QuanLyMuonTraJDialog(this, true).setVisible(true);
    }

    void openThongKe() {
        new ThongKeJDialog(this, true).setVisible(true);
    }

    void dangXuat() {
        boolean choose = MsgBox.confirm(this, "Bạn chắc chắn muốn đăng xuất!");
        if (choose) {
            Auth.clear();
            this.dispose();
            openMainFrame();
        }
    }

    void checkBtnFocus(Component btnCurrent, Component pnlCurrent, JDialog... dialog) {
        pnlClicked.setVisible(false);
        btnClicked.setBackground(new Color(0, 102, 153));

        if (btnCurrent != null) { // khi click vào thông tin cá nhan
            btnCurrent.setBackground(new Color(0, 153, 51));
        }
        if (dialog.length > 0) { // nếu có dialog thì không set pnl
            dialog[0].setVisible(true);
        } else {
            pnlCurrent.setVisible(true);
        }
        
    }

    void showDetailTTTaiKhoan() {
        NguoiDung nd = Auth.user;

        txtMaND.setText(nd.getMaNguoiDung());
        txtTen.setText(nd.getTenNguoiDung());
        txtEmail.setText(nd.getEmail());
        txtSDT.setText(nd.getSdt());
        txtMatKhau.setText(nd.getMatKhau());
        if (nd.getMaLoaiNguoiDung().equals("LND001")) {
            lblVaiTro.setText("Quản lý");
        } else if (nd.getMaLoaiNguoiDung().equals("LND002")) {
            lblVaiTro.setText("Thủ thư");
        } else {
            lblVaiTro.setText("");
        }
    }

    void updateTTTaiKhoan() {
        if (validateFormTTTaiKhoan()) {
            NguoiDung nd = new NguoiDung(Auth.user.getMaNguoiDung(), Auth.user.getMaLoaiNguoiDung(), txtTen.getText(), txtEmail.getText(), txtSDT.getText(), Auth.user.getMatKhau());
            ndDAO.update(nd);
            Auth.user = nd;
            MsgBox.alert(this, "Cập nhật thành công!");
        }
    }

    boolean validateFormTTTaiKhoan() {
        String reTen = "^[\\p{L}\\p{M}\\s]+$"; // tên có dấu.
        String reSoDienThoai = "^(\\+84|0)[0-9]{9}$";
        String reEmail = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";

        if (txtTen.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập họ tên!");
            return false;
        }
        if (!txtTen.getText().matches(reTen)) {
            MsgBox.alert(this, "Tên đăng nhập không hợp lệ!");
            return false;
        }
        if (txtSDT.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập số điện thoại!");
            return false;
        }
        if (!txtSDT.getText().matches(reSoDienThoai)) {
            MsgBox.alert(this, "Số điện thoại không hợp lệ!");
            return false;
        }
        if (txtSDT.getText().length() > 10) {
            MsgBox.alert(this, "Số điện thoại không hợp lệ!");
            return false;
        }
        if (txtEmail.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập Email!");
            return false;
        }
        if (!txtEmail.getText().matches(reEmail)) {
            MsgBox.alert(this, "Email không hợp lệ!");
            return false;
        }
        List<NguoiDung> list = ndDAO.selectAll();
        for (NguoiDung nguoiD : list) { // kiểm tra trùng sdt, email
            if (nguoiD.getSdt().equals(txtSDT.getText()) && !Auth.user.getSdt().equals(txtSDT.getText())) {
                MsgBox.alert(this, "Số điện thoại đã tồn tại!");
                return false;
            }
            if (nguoiD.getEmail().equals(txtEmail.getText()) && !Auth.user.getEmail().equals(txtEmail.getText())) {
                MsgBox.alert(this, "Email đã tồn tại!");
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblUser = new javax.swing.JLabel();
        btnDangXuat = new javax.swing.JButton();
        btnGioiThieu = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnTroGiup = new javax.swing.JButton();
        pnlContainer = new javax.swing.JPanel();
        pnlTrangChuQuanLy = new javax.swing.JPanel();
        btnTraCuuSach = new javax.swing.JButton();
        btnQuanLyMuonTra = new javax.swing.JButton();
        btnQuanLyTaiLieu = new javax.swing.JButton();
        btnQuanLyNguoiDung = new javax.swing.JButton();
        btnThongKe = new javax.swing.JButton();
        lblBgrTrangChu2 = new javax.swing.JLabel();
        pnlGioiThieu = new javax.swing.JPanel();
        lblBgrGioiThieu = new javax.swing.JLabel();
        pnlThongTinCaNhan = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaND = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        lblDoiMatKhau = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblVaiTro = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(6, 6, 6, 6, new java.awt.Color(153, 153, 153)));

        pnlMenu.setBackground(new java.awt.Color(0, 102, 153));
        pnlMenu.setForeground(new java.awt.Color(255, 255, 255));

        btnTrangChu.setBackground(new java.awt.Color(0, 153, 51));
        btnTrangChu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTrangChu.setForeground(new java.awt.Color(255, 255, 204));
        btnTrangChu.setText("TRANG CHỦ");
        btnTrangChu.setBorder(null);
        btnTrangChu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrangChuActionPerformed(evt);
            }
        });

        lblUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUser.setForeground(new java.awt.Color(255, 255, 204));
        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/user.png"))); // NOI18N
        lblUser.setText("USER NAME");
        lblUser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblUser.setIconTextGap(10);
        lblUser.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblUserMouseClicked(evt);
            }
        });

        btnDangXuat.setBackground(new java.awt.Color(0, 153, 153));
        btnDangXuat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDangXuat.setForeground(new java.awt.Color(255, 255, 204));
        btnDangXuat.setText("ĐĂNG XUẤT");
        btnDangXuat.setBorder(null);
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });

        btnGioiThieu.setBackground(new java.awt.Color(0, 102, 153));
        btnGioiThieu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGioiThieu.setForeground(new java.awt.Color(255, 255, 204));
        btnGioiThieu.setText("GIỚI THIỆU");
        btnGioiThieu.setBorder(null);
        btnGioiThieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGioiThieuActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/logoLibNgang.png"))); // NOI18N

        btnTroGiup.setBackground(new java.awt.Color(0, 102, 153));
        btnTroGiup.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTroGiup.setForeground(new java.awt.Color(255, 255, 204));
        btnTroGiup.setText("TRỢ GIÚP");
        btnTroGiup.setBorder(null);
        btnTroGiup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTroGiupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTrangChu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDangXuat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGioiThieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTroGiup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTrangChu, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGioiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTroGiup, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlContainer.setLayout(new java.awt.CardLayout());

        pnlTrangChuQuanLy.setBackground(new java.awt.Color(255, 255, 255));
        pnlTrangChuQuanLy.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnTraCuuSach.setBackground(new java.awt.Color(255, 255, 240));
        btnTraCuuSach.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTraCuuSach.setForeground(new java.awt.Color(102, 102, 102));
        btnTraCuuSach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/btnTraCuu.png"))); // NOI18N
        btnTraCuuSach.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));
        btnTraCuuSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraCuuSachActionPerformed(evt);
            }
        });
        pnlTrangChuQuanLy.add(btnTraCuuSach, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 220, 220));

        btnQuanLyMuonTra.setBackground(new java.awt.Color(255, 255, 240));
        btnQuanLyMuonTra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnQuanLyMuonTra.setForeground(new java.awt.Color(102, 102, 102));
        btnQuanLyMuonTra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/btnMuonTra.png"))); // NOI18N
        btnQuanLyMuonTra.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));
        btnQuanLyMuonTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyMuonTraActionPerformed(evt);
            }
        });
        pnlTrangChuQuanLy.add(btnQuanLyMuonTra, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, 220, 220));

        btnQuanLyTaiLieu.setBackground(new java.awt.Color(255, 255, 240));
        btnQuanLyTaiLieu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnQuanLyTaiLieu.setForeground(new java.awt.Color(102, 102, 102));
        btnQuanLyTaiLieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/btnTaiLieu.png"))); // NOI18N
        btnQuanLyTaiLieu.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));
        btnQuanLyTaiLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyTaiLieuActionPerformed(evt);
            }
        });
        pnlTrangChuQuanLy.add(btnQuanLyTaiLieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 380, 220, 220));

        btnQuanLyNguoiDung.setBackground(new java.awt.Color(255, 255, 240));
        btnQuanLyNguoiDung.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnQuanLyNguoiDung.setForeground(new java.awt.Color(102, 102, 102));
        btnQuanLyNguoiDung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/btnNguoiDung.png"))); // NOI18N
        btnQuanLyNguoiDung.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));
        btnQuanLyNguoiDung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuanLyNguoiDungActionPerformed(evt);
            }
        });
        pnlTrangChuQuanLy.add(btnQuanLyNguoiDung, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 90, 220, 220));

        btnThongKe.setBackground(new java.awt.Color(255, 255, 240));
        btnThongKe.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThongKe.setForeground(new java.awt.Color(102, 102, 102));
        btnThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/btnThongKe.png"))); // NOI18N
        btnThongKe.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(255, 255, 255)));
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });
        pnlTrangChuQuanLy.add(btnThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 380, 220, 220));

        lblBgrTrangChu2.setBackground(new java.awt.Color(255, 255, 255));
        lblBgrTrangChu2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBgrTrangChu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/bgrTrangChuPhu.png"))); // NOI18N
        pnlTrangChuQuanLy.add(lblBgrTrangChu2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 690));

        pnlContainer.add(pnlTrangChuQuanLy, "card2");

        pnlGioiThieu.setBackground(new java.awt.Color(255, 255, 255));
        pnlGioiThieu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBgrGioiThieu.setBackground(new java.awt.Color(255, 255, 255));
        lblBgrGioiThieu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBgrGioiThieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/bgrGioiThieu.png"))); // NOI18N
        pnlGioiThieu.add(lblBgrGioiThieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 690));

        pnlContainer.add(pnlGioiThieu, "card3");

        pnlThongTinCaNhan.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setBackground(new java.awt.Color(255, 153, 51));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 153, 51));
        jLabel2.setText("Hồ sơ của tôi");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Tên đăng nhập:");

        txtMaND.setEditable(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Email:");

        txtEmail.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("SĐT:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Họ tên:");

        lblDoiMatKhau.setForeground(new java.awt.Color(51, 51, 255));
        lblDoiMatKhau.setText("Đổi mật khẩu");
        lblDoiMatKhau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDoiMatKhauMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDoiMatKhauMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDoiMatKhauMouseExited(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Mật khẩu:");

        btnUpdate.setBackground(new java.awt.Color(255, 153, 0));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Lưu thay đổi");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/a6.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 217, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblVaiTro.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblVaiTro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        txtMatKhau.setEditable(false);

        javax.swing.GroupLayout pnlThongTinCaNhanLayout = new javax.swing.GroupLayout(pnlThongTinCaNhan);
        pnlThongTinCaNhan.setLayout(pnlThongTinCaNhanLayout);
        pnlThongTinCaNhanLayout.setHorizontalGroup(
            pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinCaNhanLayout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblVaiTro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlThongTinCaNhanLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(38, 38, 38)
                        .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTen)
                            .addComponent(txtEmail)
                            .addComponent(txtSDT)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMaND)
                            .addComponent(txtMatKhau)))
                    .addGroup(pnlThongTinCaNhanLayout.createSequentialGroup()
                        .addGap(642, 642, 642)
                        .addComponent(lblDoiMatKhau)))
                .addGap(109, 109, 109))
        );
        pnlThongTinCaNhanLayout.setVerticalGroup(
            pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinCaNhanLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinCaNhanLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMaND, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(txtMatKhau)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinCaNhanLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDoiMatKhau))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(223, Short.MAX_VALUE))
        );

        pnlContainer.add(pnlThongTinCaNhan, "card4");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        dangXuat();
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void btnTraCuuSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraCuuSachActionPerformed
        openTraCuuSach();
    }//GEN-LAST:event_btnTraCuuSachActionPerformed

    private void btnTrangChuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrangChuActionPerformed
        checkBtnFocus(btnTrangChu, pnlTrangChuQuanLy);
        btnClicked = btnTrangChu;
        pnlClicked = pnlTrangChuQuanLy;
    }//GEN-LAST:event_btnTrangChuActionPerformed

    private void btnQuanLyMuonTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyMuonTraActionPerformed
        openQuanLyMuonTra();
    }//GEN-LAST:event_btnQuanLyMuonTraActionPerformed

    private void btnGioiThieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGioiThieuActionPerformed
        checkBtnFocus(btnGioiThieu, pnlGioiThieu);
        btnClicked = btnGioiThieu;
        pnlClicked = pnlGioiThieu;
    }//GEN-LAST:event_btnGioiThieuActionPerformed

    private void btnTroGiupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTroGiupActionPerformed
        checkBtnFocus(btnTroGiup, pnlGioiThieu);
        btnClicked = btnTroGiup;
        pnlClicked = pnlGioiThieu;
        try {
            Desktop.getDesktop().browse(new File("Star_Library/index.html").toURI());
        } catch (IOException ex) {
            MsgBox.alert(this, "Không tìm thấy file hướng dẫn!");
        }
    }//GEN-LAST:event_btnTroGiupActionPerformed

    private void btnQuanLyNguoiDungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyNguoiDungActionPerformed
        openQuanLyNguoiDung();
    }//GEN-LAST:event_btnQuanLyNguoiDungActionPerformed

    private void btnQuanLyTaiLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuanLyTaiLieuActionPerformed
        openQuanLyTaiLieu();
    }//GEN-LAST:event_btnQuanLyTaiLieuActionPerformed

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
        openThongKe();
    }//GEN-LAST:event_btnThongKeActionPerformed

    private void lblDoiMatKhauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDoiMatKhauMouseClicked
        new DoiMatKhauJDialog(this, true).setVisible(true);
        showDetailTTTaiKhoan();
    }//GEN-LAST:event_lblDoiMatKhauMouseClicked

    private void lblDoiMatKhauMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDoiMatKhauMouseEntered
        lblDoiMatKhau.setText("<html><u>Đổi mật khẩu</u></html>");
    }//GEN-LAST:event_lblDoiMatKhauMouseEntered

    private void lblDoiMatKhauMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDoiMatKhauMouseExited
        lblDoiMatKhau.setText("Đổi mật khẩu");
    }//GEN-LAST:event_lblDoiMatKhauMouseExited

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        updateTTTaiKhoan();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void lblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUserMouseClicked
        checkBtnFocus(null, pnlThongTinCaNhan);
        pnlClicked = pnlThongTinCaNhan;
        showDetailTTTaiKhoan();
    }//GEN-LAST:event_lblUserMouseClicked

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
            java.util.logging.Logger.getLogger(ThuVienQuanLyJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThuVienQuanLyJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThuVienQuanLyJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThuVienQuanLyJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThuVienQuanLyJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnGioiThieu;
    private javax.swing.JButton btnQuanLyMuonTra;
    private javax.swing.JButton btnQuanLyNguoiDung;
    private javax.swing.JButton btnQuanLyTaiLieu;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JButton btnTraCuuSach;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnTroGiup;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblBgrGioiThieu;
    private javax.swing.JLabel lblBgrTrangChu2;
    private javax.swing.JLabel lblDoiMatKhau;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblVaiTro;
    private javax.swing.JPanel pnlContainer;
    private javax.swing.JPanel pnlGioiThieu;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlThongTinCaNhan;
    private javax.swing.JPanel pnlTrangChuQuanLy;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaND;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
