package quanlythuvien.ui;

import java.awt.Color;
import quanlythuvien.dao.NguoiDungDAO;
import quanlythuvien.entity.NguoiDung;
import quanlythuvien.utils.Auth;
import quanlythuvien.utils.MsgBox;
import quanlythuvien.utils.XImage;

/**
 *
 * @author Linh
 */
public class DoiMatKhauJDialog extends javax.swing.JDialog {

    ThuVienMainJFrame tvfr;
    ThuVienQuanLyJFrame tvqlfr;
    ThuVienUserJFrame tvufr;
    NguoiDungDAO ndDAO = new NguoiDungDAO();

    public DoiMatKhauJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        if (!Auth.isLogin()) {
            tvfr = (ThuVienMainJFrame) parent;
        } else {
            if (Auth.isLibrarian() || Auth.isManager()) {
                tvqlfr = (ThuVienQuanLyJFrame) parent;
            } else {
                tvufr = (ThuVienUserJFrame) parent;
            }
            lblDangKy.setVisible(false);
            lblDangNhap.setVisible(false);
        }
        init();
    }

    void init() {
        this.setLocation(399, 101);
        this.setTitle("Đổi mật khẩu");
        this.setIconImage(XImage.getAppIcon());
        btnXacNhan.setContentAreaFilled(false);
        txtMatKhauMoi.setBackground(new Color(236, 238, 238, 0));
        txtNhapLaiMatKhauMoi.setBackground(new Color(236, 238, 238, 0));
    }

    void doiMatKhau() {
        if (new String(txtMatKhauMoi.getPassword()).equals("")) {
            MsgBox.alert(this, "Vui lòng nhập mật khẩu mới!");
        } else if (new String(txtNhapLaiMatKhauMoi.getPassword()).equals("")) {
            MsgBox.alert(this, "Vui lòng nhập lại mật khẩu mới!");
        } else if (!new String(txtMatKhauMoi.getPassword()).equals(new String(txtNhapLaiMatKhauMoi.getPassword()))) {
            MsgBox.alert(this, "Mật khẩu không trùng khớp!");
        } else {

            if (Auth.isLogin()) {// nếu login rồi thí set lại auth và đóng ng lại qua login
                NguoiDung nd = Auth.user;
                NguoiDung nd1 = new NguoiDung(nd.getMaNguoiDung(), nd.getMaLoaiNguoiDung(), nd.getTenNguoiDung(), nd.getEmail(), nd.getSdt(), new String(txtMatKhauMoi.getPassword()));
                ndDAO.update(nd1);
                MsgBox.alert(this, "Đổi mật khẩu thành công!");
                Auth.user = nd1;
                Auth.userXacThuc = null; // reset
                this.dispose();
            } else { // chưa login
                NguoiDung nd = Auth.userXacThuc;
                NguoiDung nd1 = new NguoiDung(nd.getMaNguoiDung(), nd.getMaLoaiNguoiDung(), nd.getTenNguoiDung(), nd.getEmail(), nd.getSdt(), new String(txtMatKhauMoi.getPassword()));
                ndDAO.update(nd1);
                MsgBox.alert(this, "Đổi mật khẩu thành công!");
                Auth.userXacThuc = null;
                this.dispose();
                tvfr.openDangNhap();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtNhapLaiMatKhauMoi = new javax.swing.JPasswordField();
        txtMatKhauMoi = new javax.swing.JPasswordField();
        lblDangNhap = new javax.swing.JLabel();
        lblNganGiua = new javax.swing.JLabel();
        lblDangKy = new javax.swing.JLabel();
        btnXacNhan = new javax.swing.JButton();
        lblBgr = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtNhapLaiMatKhauMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNhapLaiMatKhauMoi.setBorder(null);
        getContentPane().add(txtNhapLaiMatKhauMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 330, 350, 40));

        txtMatKhauMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMatKhauMoi.setBorder(null);
        getContentPane().add(txtMatKhauMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 200, 350, 40));

        lblDangNhap.setForeground(new java.awt.Color(51, 51, 255));
        lblDangNhap.setText("Đăng nhập");
        lblDangNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDangNhapMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDangNhapMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDangNhapMouseExited(evt);
            }
        });
        getContentPane().add(lblDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 510, -1, -1));

        lblNganGiua.setForeground(new java.awt.Color(0, 0, 255));
        lblNganGiua.setText("|");
        getContentPane().add(lblNganGiua, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 510, -1, -1));

        lblDangKy.setForeground(new java.awt.Color(51, 51, 255));
        lblDangKy.setText("Đăng Ký");
        lblDangKy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblDangKyMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDangKyMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDangKyMouseExited(evt);
            }
        });
        getContentPane().add(lblDangKy, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 510, -1, -1));

        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });
        getContentPane().add(btnXacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 420, 340, 60));

        lblBgr.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBgr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/bgrChangePass.png"))); // NOI18N
        lblBgr.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(lblBgr, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblDangNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangNhapMouseClicked
        Auth.userXacThuc = null;
        this.dispose();
        tvfr.openDangNhap();
    }//GEN-LAST:event_lblDangNhapMouseClicked

    private void lblDangNhapMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangNhapMouseEntered
        lblDangNhap.setText("<html><u>Đăng nhập</u></html>");
    }//GEN-LAST:event_lblDangNhapMouseEntered

    private void lblDangNhapMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangNhapMouseExited
        lblDangNhap.setText("Đăng nhập");
    }//GEN-LAST:event_lblDangNhapMouseExited

    private void lblDangKyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangKyMouseClicked
        Auth.userXacThuc = null;
        this.dispose();
        tvfr.openDangKy();
    }//GEN-LAST:event_lblDangKyMouseClicked

    private void lblDangKyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangKyMouseEntered
        lblDangKy.setText("<html><u>Đăng ký</u></html>");
    }//GEN-LAST:event_lblDangKyMouseEntered

    private void lblDangKyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangKyMouseExited
        lblDangKy.setText("Đăng ký");
    }//GEN-LAST:event_lblDangKyMouseExited

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Auth.userXacThuc = null;
    }//GEN-LAST:event_formWindowClosing

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        doiMatKhau();
    }//GEN-LAST:event_btnXacNhanActionPerformed

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
            java.util.logging.Logger.getLogger(DoiMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DoiMatKhauJDialog dialog = new DoiMatKhauJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JLabel lblBgr;
    private javax.swing.JLabel lblDangKy;
    private javax.swing.JLabel lblDangNhap;
    private javax.swing.JLabel lblNganGiua;
    private javax.swing.JPasswordField txtMatKhauMoi;
    private javax.swing.JPasswordField txtNhapLaiMatKhauMoi;
    // End of variables declaration//GEN-END:variables
}
