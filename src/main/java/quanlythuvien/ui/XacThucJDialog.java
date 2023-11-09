package quanlythuvien.ui;

import java.awt.Color;
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import quanlythuvien.dao.NguoiDungDAO;
import quanlythuvien.entity.NguoiDung;
import quanlythuvien.ui.*;
import quanlythuvien.utils.Auth;
import quanlythuvien.utils.MsgBox;
import quanlythuvien.utils.XImage;

public class XacThucJDialog extends javax.swing.JDialog {

    ThuVienMainJFrame tvfr;
    NguoiDungDAO ndDAO = new NguoiDungDAO();
    NguoiDung nd;
    String tenDangNhap;
    String email;
    String maXacThuc;
    boolean checkGuiMail = false; // để đề phòng người dùng nhấn lien tục vào lbl đếm giây
    Session s;

    public XacThucJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        tvfr = (ThuVienMainJFrame) parent;
        init();
    }

    void init() {
        this.setLocationRelativeTo(null);
        this.setTitle("Xác thực");
        this.setIconImage(XImage.getAppIcon());
        btnTiepTheo1.setContentAreaFilled(false);
        btnTiepTheo2.setContentAreaFilled(false);
        txtTenDangNhap.setBackground(new Color(236, 238, 238, 0));
        txtMaXacThuc.setBackground(new Color(236, 238, 238, 0));
        if (DangKyJDialog.checkSignUp == true) { // kiểm tra có đang xác thực đăng ký hay khồn
            pnlXacThuc1.setVisible(false);
            pnlXacThuc2.setVisible(true);
            email = Auth.nguoiDungDangKy.getEmail();
            randomSo();// tạo và gửi mã xác thực
        } else {
            pnlXacThuc2.setVisible(false);
        }

        if (Auth.isLogin()) { // kiểmm tra xem nếu đang login thì k cho sửa tên dn đề phòng đg đăng nhập tài khoản này mà sửa tài khoản khác
            txtTenDangNhap.setText(Auth.user.getMaNguoiDung());
            txtTenDangNhap.setEditable(false);
        }
    }

    void xacThuc1() {
        tenDangNhap = txtTenDangNhap.getText();
        nd = ndDAO.selectById(tenDangNhap);
        String reTenDangNhap = "^[a-zA-Z0-9_-]{2,10}$";
        if (tenDangNhap.equals("")) {
            MsgBox.alert(this, "Vui lòng nhập tên đăng nhập!");
        } else if (!txtTenDangNhap.getText().matches(reTenDangNhap)) {
            MsgBox.alert(this, "Tên đăng nhập không hợp lệ!");
        } else {
            if (nd == null) {
                MsgBox.alert(this, "Sai tên người dùng!");
            } else {
                email = nd.getEmail();
                pnlXacThuc2.setVisible(true);
                pnlXacThuc1.setVisible(false);
                randomSo();// tạo và gửi mã xác thực
            }
        }
    }

    void xacThuc2() {
        if (DangKyJDialog.checkSignUp == false) {// của xác thực bình thường
            if (txtMaXacThuc.getText().equals("")) {
                MsgBox.alert(this, "Vui lòng nhập mã xác thực!");
            } else if (maXacThuc.equals(txtMaXacThuc.getText())) {
                Auth.userXacThuc = nd;
                this.dispose();
                tvfr.openDoiMatKhau();
            } else {
                MsgBox.alert(this, "Mã xác thực không chính xác!");

            }
        } else {// của xác thực đăng ký
            if (maXacThuc.equals(txtMaXacThuc.getText())) {
                ndDAO.insert(Auth.nguoiDungDangKy);
                MsgBox.alert(this, "Đăng ký thành công!");
                DangKyJDialog.checkSignUp = false;
                this.dispose();
                tvfr.openDangNhap();
            } else {
                MsgBox.alert(this, "Mã xác thực không chính xác!");
            }

        }
    }

    public void randomSo() {
//        int so1 = (int) (1 + Math.round(Math.random() * 8));
//        int so2 = (int) (1 + Math.round(Math.random() * 8));
//        int so3 = (int) (1 + Math.round(Math.random() * 8));
//        int so4 = (int) (1 + Math.round(Math.random() * 8));
//        int so5 = (int) (1 + Math.round(Math.random() * 8));
//        maXacThuc = so1 + "" + so2 + "" + so3 + "" + so4 + "" + so5 + "";
        maXacThuc = String.valueOf(10000 + new Random().nextInt(90000)); //nextInt lấy số nn từ 0-89999 cộng thêm 10000 = 99999
        System.out.println(maXacThuc);
        Thread thr = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 60; i >= 0; i--) {
                    try {
                        if (i <= 9) {
                            lblSoGiay.setText("0" + i + "s"); // thêm sổ 0 ở đầu khi còn 1 chữ số
                        } else {
                            lblSoGiay.setText(i + "s");
                        }
                        if (i == 60) {
                            sendEmail();
                        }
                        if (i == 0) {
                            maXacThuc = null;
                            lblSoGiay.setText("Gửi lại");
                            checkGuiMail = false;
                            return;
                        }
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thr.start();
    }

    public void sendEmail() {

        // Cài đặt cấu hình
        Properties p = new Properties();
        p.put("mail.smtp.host", "smtp.gmail.com"); //SMTP HOST, host là sever chủ để gửi mail
        p.put("mail.smtp.port", 587); // TLS 587, SSL 465
        p.put("mail.smtp.auth", "true"); // ý nghĩa là khi sử dụng cái host ở dòng 1 để gửi mail thì có cần đăng nhập hay k (true là có).
        p.put("mail.smtp.starttls.enable", "true"); // định nghĩa giao thức cần sử dụng TLS

        // Tạo phiên làm việc
        s = Session.getInstance(p, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication("linhli2k4@gmail.com", "kylvjqxjqgrogfcr");
            }
        });

        try {
            // Tạo đối tượng MimeMessage
            MimeMessage mimeMessage = new MimeMessage(s);

            // Đặt thông tin người gửi, người nhận, tiêu đề và nội dung email
            mimeMessage.setFrom(new InternetAddress("linhli2k4@gmail.com"));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mimeMessage.setSubject("Mã xác thực của thư viện:");
            mimeMessage.setContent("<html>Mã là: " + "<b>" + maXacThuc + "</b></html>", "text/html");

            // Gửi email
            Transport.send(mimeMessage);

            System.out.println("Email đã được gửi thành công!");
            checkGuiMail = true;
        } catch (MessagingException e) {
            e.printStackTrace();
            MsgBox.alert(this, "Lỗi gửi mail");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSlider1 = new javax.swing.JSlider();
        layer = new javax.swing.JLayeredPane();
        pnlXacThuc1 = new javax.swing.JPanel();
        txtTenDangNhap = new javax.swing.JTextField();
        lblDangKy1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblDangNhap1 = new javax.swing.JLabel();
        btnTiepTheo1 = new javax.swing.JButton();
        lblBgrBox1 = new javax.swing.JLabel();
        pnlXacThuc2 = new javax.swing.JPanel();
        txtMaXacThuc = new javax.swing.JTextField();
        lblSoGiay = new javax.swing.JLabel();
        lblMessage = new javax.swing.JLabel();
        lblDangKy2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblDangNhap2 = new javax.swing.JLabel();
        btnTiepTheo2 = new javax.swing.JButton();
        lblBgrBox2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        layer.setToolTipText("");

        pnlXacThuc1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTenDangNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenDangNhap.setBorder(null);
        pnlXacThuc1.add(txtTenDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 330, 40));

        lblDangKy1.setForeground(new java.awt.Color(51, 51, 255));
        lblDangKy1.setText("Đăng Ký");
        lblDangKy1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDangKy1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDangKy1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDangKy1MouseExited(evt);
            }
        });
        pnlXacThuc1.add(lblDangKy1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 390, -1, -1));

        jLabel5.setForeground(new java.awt.Color(0, 0, 255));
        jLabel5.setText("|");
        pnlXacThuc1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 390, -1, -1));

        lblDangNhap1.setForeground(new java.awt.Color(51, 51, 255));
        lblDangNhap1.setText("Đăng nhập");
        lblDangNhap1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDangNhap1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDangNhap1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDangNhap1MouseExited(evt);
            }
        });
        pnlXacThuc1.add(lblDangNhap1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 390, -1, -1));

        btnTiepTheo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTiepTheo1ActionPerformed(evt);
            }
        });
        pnlXacThuc1.add(btnTiepTheo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 340, 70));

        lblBgrBox1.setForeground(new java.awt.Color(102, 102, 102));
        lblBgrBox1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBgrBox1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/bgrBox1.png"))); // NOI18N
        pnlXacThuc1.add(lblBgrBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 437, -1));

        pnlXacThuc2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtMaXacThuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaXacThuc.setAlignmentX(2.0F);
        txtMaXacThuc.setBorder(null);
        txtMaXacThuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaXacThucActionPerformed(evt);
            }
        });
        txtMaXacThuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMaXacThucKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMaXacThucKeyReleased(evt);
            }
        });
        pnlXacThuc2.add(txtMaXacThuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 200, 40));

        lblSoGiay.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblSoGiay.setForeground(new java.awt.Color(102, 102, 255));
        lblSoGiay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSoGiay.setText("00s");
        lblSoGiay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSoGiayMouseClicked(evt);
            }
        });
        pnlXacThuc2.add(lblSoGiay, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, 70, 60));

        lblMessage.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblMessage.setForeground(new java.awt.Color(255, 51, 51));
        lblMessage.setText("Vui lòng nhập vào mã đã được gửi đến email của bạn!");
        pnlXacThuc2.add(lblMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, -1, -1));

        lblDangKy2.setForeground(new java.awt.Color(51, 51, 255));
        lblDangKy2.setText("Đăng Ký");
        lblDangKy2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDangKy2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDangKy2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDangKy2MouseExited(evt);
            }
        });
        pnlXacThuc2.add(lblDangKy2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 390, -1, -1));

        jLabel6.setForeground(new java.awt.Color(0, 0, 255));
        jLabel6.setText("|");
        pnlXacThuc2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 390, -1, -1));

        lblDangNhap2.setForeground(new java.awt.Color(51, 51, 255));
        lblDangNhap2.setText("Đăng nhập");
        lblDangNhap2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDangNhap2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDangNhap2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDangNhap2MouseExited(evt);
            }
        });
        pnlXacThuc2.add(lblDangNhap2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 390, -1, -1));

        btnTiepTheo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTiepTheo2ActionPerformed(evt);
            }
        });
        pnlXacThuc2.add(btnTiepTheo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, 340, 70));

        lblBgrBox2.setForeground(new java.awt.Color(102, 102, 102));
        lblBgrBox2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBgrBox2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/bgrBox2.png"))); // NOI18N
        pnlXacThuc2.add(lblBgrBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, -1));

        layer.setLayer(pnlXacThuc1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layer.setLayer(pnlXacThuc2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layerLayout = new javax.swing.GroupLayout(layer);
        layer.setLayout(layerLayout);
        layerLayout.setHorizontalGroup(
            layerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layerLayout.createSequentialGroup()
                .addComponent(pnlXacThuc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
            .addGroup(layerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layerLayout.createSequentialGroup()
                    .addComponent(pnlXacThuc2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layerLayout.setVerticalGroup(
            layerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layerLayout.createSequentialGroup()
                .addComponent(pnlXacThuc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 7, Short.MAX_VALUE))
            .addGroup(layerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layerLayout.createSequentialGroup()
                    .addComponent(pnlXacThuc2, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        getContentPane().add(layer, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 70, 440, 450));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/bgrDatLaiMatKhau0.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblDangNhap1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangNhap1MouseEntered
        lblDangNhap1.setText("<html><u>Đăng nhập</u></html>");
    }//GEN-LAST:event_lblDangNhap1MouseEntered

    private void lblDangNhap1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangNhap1MouseExited
        lblDangNhap1.setText("Đăng nhập");
    }//GEN-LAST:event_lblDangNhap1MouseExited

    private void lblDangKy1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangKy1MouseEntered
        lblDangKy1.setText("<html><u>Đăng ký</u></html>");
    }//GEN-LAST:event_lblDangKy1MouseEntered

    private void lblDangKy1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangKy1MouseExited
        lblDangKy1.setText("Đăng ký");
    }//GEN-LAST:event_lblDangKy1MouseExited

    private void lblDangKy2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangKy2MouseEntered
        lblDangKy2.setText("<html><u>Đăng ký</u></html>");
    }//GEN-LAST:event_lblDangKy2MouseEntered

    private void lblDangKy2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangKy2MouseExited
        lblDangKy2.setText("Đăng ký");
    }//GEN-LAST:event_lblDangKy2MouseExited

    private void lblDangNhap2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangNhap2MouseEntered
        lblDangNhap2.setText("<html><u>Đăng nhập</u></html>");
    }//GEN-LAST:event_lblDangNhap2MouseEntered

    private void lblDangNhap2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangNhap2MouseExited
        lblDangNhap2.setText("Đăng nhập");
    }//GEN-LAST:event_lblDangNhap2MouseExited

    private void txtMaXacThucKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaXacThucKeyPressed

    }//GEN-LAST:event_txtMaXacThucKeyPressed

    private void txtMaXacThucKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaXacThucKeyReleased
        if (txtMaXacThuc.getText().equals("")) {
            lblMessage.setVisible(true);
        } else {
            lblMessage.setVisible(false);
        }
    }//GEN-LAST:event_txtMaXacThucKeyReleased

    private void btnTiepTheo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTiepTheo1ActionPerformed
        xacThuc1();
    }//GEN-LAST:event_btnTiepTheo1ActionPerformed

    private void txtMaXacThucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaXacThucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaXacThucActionPerformed

    private void lblDangNhap2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangNhap2MouseClicked
        this.dispose();
        tvfr.openDangNhap();
    }//GEN-LAST:event_lblDangNhap2MouseClicked

    private void lblDangNhap1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangNhap1MouseClicked
        this.dispose();
        tvfr.openDangNhap();
    }//GEN-LAST:event_lblDangNhap1MouseClicked

    private void lblDangKy1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangKy1MouseClicked
        this.dispose();
        tvfr.openDangKy();
    }//GEN-LAST:event_lblDangKy1MouseClicked

    private void lblDangKy2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangKy2MouseClicked
        this.dispose();
        tvfr.openDangKy();
    }//GEN-LAST:event_lblDangKy2MouseClicked

    private void btnTiepTheo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTiepTheo2ActionPerformed
        xacThuc2();
    }//GEN-LAST:event_btnTiepTheo2ActionPerformed

    private void lblSoGiayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSoGiayMouseClicked
        if (checkGuiMail == false) {
            txtMaXacThuc.setText("");
            randomSo();
        }
    }//GEN-LAST:event_lblSoGiayMouseClicked

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
            java.util.logging.Logger.getLogger(XacThucJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(XacThucJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(XacThucJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(XacThucJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                XacThucJDialog dialog = new XacThucJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnTiepTheo1;
    private javax.swing.JButton btnTiepTheo2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JLayeredPane layer;
    private javax.swing.JLabel lblBgrBox1;
    private javax.swing.JLabel lblBgrBox2;
    private javax.swing.JLabel lblDangKy1;
    private javax.swing.JLabel lblDangKy2;
    private javax.swing.JLabel lblDangNhap1;
    private javax.swing.JLabel lblDangNhap2;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblSoGiay;
    private javax.swing.JPanel pnlXacThuc1;
    private javax.swing.JPanel pnlXacThuc2;
    private javax.swing.JTextField txtMaXacThuc;
    private javax.swing.JTextField txtTenDangNhap;
    // End of variables declaration//GEN-END:variables
}
