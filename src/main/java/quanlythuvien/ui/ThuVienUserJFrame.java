/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package quanlythuvien.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.Timer;
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
public class ThuVienUserJFrame extends javax.swing.JFrame {

    Component btnClicked; // lưu btn đã click trước
    Component pnlClicked;
    NguoiDungDAO ndDAO = new NguoiDungDAO();
    Timer timer;
    int currentIndex = 0;
    boolean checkSlideCuoi = false;
    List<JPanel> listPanelTrangChu = new ArrayList<>();

    public ThuVienUserJFrame() {
        initComponents();
        init();
        Auth.checkLoginOneTime = true;
        btnClicked = btnTrangChu;
        pnlClicked = pnlTrangChuUser;
    }

    void init() {
        this.setLocationRelativeTo(null);
        this.setTitle("Quản lý thư viện");
        this.setIconImage(XImage.getAppIcon());
        lblUser.setText(Auth.user.getMaNguoiDung());
        showSlider();
    }

    void showSlider() {
        listPanelTrangChu.add(pnlTrangChuCon1);
        listPanelTrangChu.add(pnlTrangChuCon2);
        listPanelTrangChu.add(pnlTrangChuCon3);
        listPanelTrangChu.add(pnlTrangChuCon4);
        timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (currentIndex == 0) {
                    checkSlideCuoi = false;
                }
                if (currentIndex == listPanelTrangChu.size() - 1) {
                    checkSlideCuoi = true;
                }
                updatePanelShow();
                CheckBtnSlider();
                if (checkSlideCuoi == false) {
                    currentIndex++;
                } else {
                    currentIndex--;
                }

            }
        });
        timer.start();
    }

    void updatePanelShow() {
        listPanelTrangChu.get(currentIndex).setVisible(true);
        if (currentIndex != 0) {
            listPanelTrangChu.get(currentIndex - 1).setVisible(false);
        }
    }

    void CheckBtnSlider() {
        btnNextSlide.setEnabled(true);
        btnPrevSlide.setEnabled(true);
        if (currentIndex == 0) {
            btnPrevSlide.setEnabled(false);
        }
        if (currentIndex == listPanelTrangChu.size() - 1) {
            btnNextSlide.setEnabled(false);
        }
    }

    void openMainFrame() {
        new ThuVienMainJFrame().setVisible(true);
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

        jPanel = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JPanel();
        btnTrangChu = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblUser = new javax.swing.JLabel();
        btnDangXuat = new javax.swing.JButton();
        btnTraCuu = new javax.swing.JButton();
        lblLogo = new javax.swing.JLabel();
        btnTroGiup = new javax.swing.JButton();
        btnLichSuMuonTra = new javax.swing.JButton();
        btnGioiThieu = new javax.swing.JButton();
        pnlContainer = new javax.swing.JPanel();
        pnlTrangChuUser = new javax.swing.JPanel();
        btnNextSlide = new javax.swing.JButton();
        btnPrevSlide = new javax.swing.JButton();
        pnlTrangChuCon1 = new javax.swing.JPanel();
        btnKhamPha = new javax.swing.JButton();
        lblBgrTrangChuUser1 = new javax.swing.JLabel();
        pnlTrangChuCon2 = new javax.swing.JPanel();
        lblBgrTrangChuUser2 = new javax.swing.JLabel();
        pnlTrangChuCon3 = new javax.swing.JPanel();
        lblBgrTrangChuUser3 = new javax.swing.JLabel();
        pnlTrangChuCon4 = new javax.swing.JPanel();
        lblBgrTrangChuUser4 = new javax.swing.JLabel();
        pnlGioiThieu = new javax.swing.JPanel();
        lblBgrGioiThieu = new javax.swing.JLabel();
        pnlThongTinCaNhan = new javax.swing.JPanel();
        lblHoSo = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        txtMaND = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblSDT = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        lblTen = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        lblDoiMatKhau = new javax.swing.JLabel();
        lblPass = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        lblVaiTro = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel.setBackground(new java.awt.Color(255, 255, 255));
        jPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(6, 6, 6, 6, new java.awt.Color(153, 153, 153)));
        jPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlMenu.setBackground(new java.awt.Color(0, 102, 153));
        pnlMenu.setForeground(new java.awt.Color(255, 255, 255));

        btnTrangChu.setBackground(new java.awt.Color(0, 153, 51));
        btnTrangChu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTrangChu.setForeground(new java.awt.Color(255, 255, 204));
        btnTrangChu.setText("TRANG CHỦ");
        btnTrangChu.setBorder(null);
        btnTrangChu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        lblUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        btnDangXuat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });

        btnTraCuu.setBackground(new java.awt.Color(0, 102, 153));
        btnTraCuu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTraCuu.setForeground(new java.awt.Color(255, 255, 204));
        btnTraCuu.setText("TRA CỨU SÁCH");
        btnTraCuu.setBorder(null);
        btnTraCuu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTraCuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraCuuActionPerformed(evt);
            }
        });

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/logoLibNgang.png"))); // NOI18N

        btnTroGiup.setBackground(new java.awt.Color(0, 102, 153));
        btnTroGiup.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTroGiup.setForeground(new java.awt.Color(255, 255, 204));
        btnTroGiup.setText("TRỢ GIÚP");
        btnTroGiup.setBorder(null);
        btnTroGiup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTroGiup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTroGiupActionPerformed(evt);
            }
        });

        btnLichSuMuonTra.setBackground(new java.awt.Color(0, 102, 153));
        btnLichSuMuonTra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLichSuMuonTra.setForeground(new java.awt.Color(255, 255, 204));
        btnLichSuMuonTra.setText("LỊCH SỬ MƯỢN TRẢ");
        btnLichSuMuonTra.setBorder(null);
        btnLichSuMuonTra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnGioiThieu.setBackground(new java.awt.Color(0, 102, 153));
        btnGioiThieu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGioiThieu.setForeground(new java.awt.Color(255, 255, 204));
        btnGioiThieu.setText("GIỚI THIỆU");
        btnGioiThieu.setBorder(null);
        btnGioiThieu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGioiThieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGioiThieuActionPerformed(evt);
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
                    .addComponent(btnTraCuu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTroGiup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLichSuMuonTra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGioiThieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(btnTraCuu, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLichSuMuonTra, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGioiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTroGiup, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
                .addComponent(lblLogo)
                .addContainerGap())
        );

        jPanel.add(pnlMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, -1, 690));

        pnlContainer.setLayout(new java.awt.CardLayout());

        pnlTrangChuUser.setOpaque(false);
        pnlTrangChuUser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNextSlide.setBackground(new java.awt.Color(153, 153, 153));
        btnNextSlide.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnNextSlide.setText(">");
        btnNextSlide.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        btnNextSlide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNextSlideMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNextSlideMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnNextSlideMousePressed(evt);
            }
        });
        btnNextSlide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextSlideActionPerformed(evt);
            }
        });
        pnlTrangChuUser.add(btnNextSlide, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 330, 50, 90));

        btnPrevSlide.setBackground(new java.awt.Color(153, 153, 153));
        btnPrevSlide.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnPrevSlide.setText("<");
        btnPrevSlide.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        btnPrevSlide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPrevSlideMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPrevSlideMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnPrevSlideMousePressed(evt);
            }
        });
        btnPrevSlide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevSlideActionPerformed(evt);
            }
        });
        pnlTrangChuUser.add(btnPrevSlide, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 330, 50, 90));

        pnlTrangChuCon1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnKhamPha.setBackground(new java.awt.Color(255, 153, 102));
        btnKhamPha.setFont(new java.awt.Font("Unispace", 1, 29)); // NOI18N
        btnKhamPha.setText("KHÁM PHÁ NGAY");
        btnKhamPha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnKhamPhaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnKhamPhaMouseExited(evt);
            }
        });
        btnKhamPha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhamPhaActionPerformed(evt);
            }
        });
        pnlTrangChuCon1.add(btnKhamPha, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 410, 300, 80));

        lblBgrTrangChuUser1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBgrTrangChuUser1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/bgrTrangChuUser.png"))); // NOI18N
        pnlTrangChuCon1.add(lblBgrTrangChuUser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 680));

        pnlTrangChuUser.add(pnlTrangChuCon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, -1));

        lblBgrTrangChuUser2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBgrTrangChuUser2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/bgrGame.png"))); // NOI18N
        lblBgrTrangChuUser2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblBgrTrangChuUser2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBgrTrangChuUser2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlTrangChuCon2Layout = new javax.swing.GroupLayout(pnlTrangChuCon2);
        pnlTrangChuCon2.setLayout(pnlTrangChuCon2Layout);
        pnlTrangChuCon2Layout.setHorizontalGroup(
            pnlTrangChuCon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1106, Short.MAX_VALUE)
            .addGroup(pnlTrangChuCon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlTrangChuCon2Layout.createSequentialGroup()
                    .addComponent(lblBgrTrangChuUser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        pnlTrangChuCon2Layout.setVerticalGroup(
            pnlTrangChuCon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 680, Short.MAX_VALUE)
            .addGroup(pnlTrangChuCon2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlTrangChuCon2Layout.createSequentialGroup()
                    .addComponent(lblBgrTrangChuUser2, javax.swing.GroupLayout.PREFERRED_SIZE, 679, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 1, Short.MAX_VALUE)))
        );

        pnlTrangChuUser.add(pnlTrangChuCon2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 680));

        lblBgrTrangChuUser3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBgrTrangChuUser3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/bgrDoremon.png"))); // NOI18N
        lblBgrTrangChuUser3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout pnlTrangChuCon3Layout = new javax.swing.GroupLayout(pnlTrangChuCon3);
        pnlTrangChuCon3.setLayout(pnlTrangChuCon3Layout);
        pnlTrangChuCon3Layout.setHorizontalGroup(
            pnlTrangChuCon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1106, Short.MAX_VALUE)
            .addGroup(pnlTrangChuCon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlTrangChuCon3Layout.createSequentialGroup()
                    .addComponent(lblBgrTrangChuUser3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        pnlTrangChuCon3Layout.setVerticalGroup(
            pnlTrangChuCon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 682, Short.MAX_VALUE)
            .addGroup(pnlTrangChuCon3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlTrangChuCon3Layout.createSequentialGroup()
                    .addComponent(lblBgrTrangChuUser3, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pnlTrangChuUser.add(pnlTrangChuCon3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 680));

        lblBgrTrangChuUser4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBgrTrangChuUser4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/bgrSGK.png"))); // NOI18N
        lblBgrTrangChuUser4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout pnlTrangChuCon4Layout = new javax.swing.GroupLayout(pnlTrangChuCon4);
        pnlTrangChuCon4.setLayout(pnlTrangChuCon4Layout);
        pnlTrangChuCon4Layout.setHorizontalGroup(
            pnlTrangChuCon4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1106, Short.MAX_VALUE)
            .addGroup(pnlTrangChuCon4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlTrangChuCon4Layout.createSequentialGroup()
                    .addComponent(lblBgrTrangChuUser4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        pnlTrangChuCon4Layout.setVerticalGroup(
            pnlTrangChuCon4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 680, Short.MAX_VALUE)
            .addGroup(pnlTrangChuCon4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblBgrTrangChuUser4, javax.swing.GroupLayout.PREFERRED_SIZE, 680, Short.MAX_VALUE))
        );

        pnlTrangChuUser.add(pnlTrangChuCon4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 680));

        pnlContainer.add(pnlTrangChuUser, "card2");

        pnlGioiThieu.setBackground(new java.awt.Color(255, 255, 255));
        pnlGioiThieu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBgrGioiThieu.setBackground(new java.awt.Color(255, 255, 255));
        lblBgrGioiThieu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBgrGioiThieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/bgrGioiThieu.png"))); // NOI18N
        pnlGioiThieu.add(lblBgrGioiThieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 680));

        pnlContainer.add(pnlGioiThieu, "card3");

        pnlThongTinCaNhan.setBackground(new java.awt.Color(255, 255, 255));

        lblHoSo.setBackground(new java.awt.Color(255, 153, 51));
        lblHoSo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblHoSo.setForeground(new java.awt.Color(255, 153, 51));
        lblHoSo.setText("Hồ sơ của tôi");

        lblUserName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUserName.setText("Tên đăng nhập:");

        txtMaND.setEditable(false);

        lblEmail.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblEmail.setText("Email:");

        txtEmail.setEditable(false);

        lblSDT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSDT.setText("SĐT:");

        lblTen.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTen.setText("Họ tên:");

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

        lblPass.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPass.setText("Mật khẩu:");

        btnUpdate.setBackground(new java.awt.Color(255, 153, 0));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Lưu thay đổi");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 204));

        lblAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/a6.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 217, Short.MAX_VALUE)
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
                    .addComponent(lblHoSo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinCaNhanLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTen, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPass, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUserName))
                        .addGap(38, 38, 38)
                        .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTen)
                            .addComponent(txtEmail)
                            .addComponent(txtSDT)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlThongTinCaNhanLayout.createSequentialGroup()
                                .addComponent(txtMaND, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtMatKhau)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinCaNhanLayout.createSequentialGroup()
                        .addGap(642, 642, 642)
                        .addComponent(lblDoiMatKhau)))
                .addGap(109, 109, 109))
        );
        pnlThongTinCaNhanLayout.setVerticalGroup(
            pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinCaNhanLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(lblHoSo, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinCaNhanLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblUserName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMaND, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTen, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblPass, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(txtMatKhau)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinCaNhanLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinCaNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDoiMatKhau))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(213, Short.MAX_VALUE))
        );

        pnlContainer.add(pnlThongTinCaNhan, "card4");

        jPanel.add(pnlContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 12, -1, 680));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1339, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        dangXuat();
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void btnTrangChuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrangChuActionPerformed
        checkBtnFocus(btnTrangChu, pnlTrangChuUser);
        pnlClicked = pnlTrangChuUser;
        btnClicked = btnTrangChu;
    }//GEN-LAST:event_btnTrangChuActionPerformed

    private void btnTraCuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraCuuActionPerformed
        JDialog dl = new TraCuuSachJDialog(this, true);
        checkBtnFocus(btnTraCuu, pnlTrangChuUser, dl);
        btnClicked = btnTraCuu;
        checkBtnFocus(btnTrangChu, pnlTrangChuUser);// khi thoát dialog thì quay về lại trang chủ
        pnlClicked = pnlTrangChuUser;
        btnClicked = btnTrangChu;
    }//GEN-LAST:event_btnTraCuuActionPerformed

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

    private void lblDoiMatKhauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDoiMatKhauMouseClicked
        new DoiMatKhauJDialog(this, true).setVisible(true);
        showDetailTTTaiKhoan();
    }//GEN-LAST:event_lblDoiMatKhauMouseClicked

    private void lblDoiMatKhauMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDoiMatKhauMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblDoiMatKhauMouseEntered

    private void lblDoiMatKhauMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDoiMatKhauMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblDoiMatKhauMouseExited

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        updateTTTaiKhoan();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void lblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUserMouseClicked
        checkBtnFocus(null, pnlThongTinCaNhan);
        pnlClicked = pnlThongTinCaNhan;
        showDetailTTTaiKhoan();
    }//GEN-LAST:event_lblUserMouseClicked

    private void btnNextSlideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextSlideActionPerformed

    }//GEN-LAST:event_btnNextSlideActionPerformed

    private void btnPrevSlideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevSlideActionPerformed

    }//GEN-LAST:event_btnPrevSlideActionPerformed

    private void btnPrevSlideMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevSlideMouseEntered
        timer.stop();
        btnPrevSlide.setBackground(new Color(102, 102, 102, 5));
    }//GEN-LAST:event_btnPrevSlideMouseEntered

    private void btnPrevSlideMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevSlideMouseExited
        timer.start();
        btnPrevSlide.setBackground(new Color(153, 153, 153, 5));
    }//GEN-LAST:event_btnPrevSlideMouseExited

    private void btnNextSlideMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextSlideMouseEntered
        timer.stop();
        btnNextSlide.setBackground(new Color(102, 102, 102, 5));
    }//GEN-LAST:event_btnNextSlideMouseEntered

    private void btnNextSlideMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextSlideMouseExited
        timer.start();
        btnNextSlide.setBackground(new Color(153, 153, 153, 5));
    }//GEN-LAST:event_btnNextSlideMouseExited

    private void btnPrevSlideMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevSlideMousePressed
        if (currentIndex != 0) {
            currentIndex--;
            updatePanelShow();
            CheckBtnSlider();
        }
    }//GEN-LAST:event_btnPrevSlideMousePressed

    private void btnNextSlideMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextSlideMousePressed
        if (currentIndex != listPanelTrangChu.size() - 1) {
            currentIndex++;
            updatePanelShow();
            CheckBtnSlider();
        }
    }//GEN-LAST:event_btnNextSlideMousePressed

    private void btnKhamPhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhamPhaActionPerformed
        JDialog dl = new TraCuuSachJDialog(this, true);
        checkBtnFocus(btnTraCuu, pnlTrangChuUser, dl);
        btnClicked = btnTraCuu;
        checkBtnFocus(btnTrangChu, pnlTrangChuUser);// khi thoát dialog thì quay về lại trang chủ
        pnlClicked = pnlTrangChuUser;
        btnClicked = btnTrangChu;
    }//GEN-LAST:event_btnKhamPhaActionPerformed

    private void btnKhamPhaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKhamPhaMouseEntered
        btnKhamPha.setBackground(new Color(255, 102, 0));
    }//GEN-LAST:event_btnKhamPhaMouseEntered

    private void btnKhamPhaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKhamPhaMouseExited
        btnKhamPha.setBackground(new Color(255, 153, 102));
    }//GEN-LAST:event_btnKhamPhaMouseExited

    private void lblBgrTrangChuUser2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBgrTrangChuUser2MouseClicked

    }//GEN-LAST:event_lblBgrTrangChuUser2MouseClicked

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
            java.util.logging.Logger.getLogger(ThuVienUserJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThuVienUserJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThuVienUserJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThuVienUserJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new ThuVienUserJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnGioiThieu;
    private javax.swing.JButton btnKhamPha;
    private javax.swing.JButton btnLichSuMuonTra;
    private javax.swing.JButton btnNextSlide;
    private javax.swing.JButton btnPrevSlide;
    private javax.swing.JButton btnTraCuu;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnTroGiup;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JPanel jPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblBgrGioiThieu;
    private javax.swing.JLabel lblBgrTrangChuUser1;
    private javax.swing.JLabel lblBgrTrangChuUser2;
    private javax.swing.JLabel lblBgrTrangChuUser3;
    private javax.swing.JLabel lblBgrTrangChuUser4;
    private javax.swing.JLabel lblDoiMatKhau;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblHoSo;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblTen;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JLabel lblVaiTro;
    private javax.swing.JPanel pnlContainer;
    private javax.swing.JPanel pnlGioiThieu;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlThongTinCaNhan;
    private javax.swing.JPanel pnlTrangChuCon1;
    private javax.swing.JPanel pnlTrangChuCon2;
    private javax.swing.JPanel pnlTrangChuCon3;
    private javax.swing.JPanel pnlTrangChuCon4;
    private javax.swing.JPanel pnlTrangChuUser;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaND;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
