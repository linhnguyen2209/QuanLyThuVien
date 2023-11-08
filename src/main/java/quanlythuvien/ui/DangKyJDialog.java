/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package quanlythuvien.ui;

import java.awt.Color;
import quanlythuvien.ui.DangKyJDialog;
import quanlythuvien.utils.MsgBox;
import quanlythuvien.utils.XImage;

/**
 *
 * @author thuon
 */
public class DangKyJDialog extends javax.swing.JDialog {

    ThuVienJFrame tvfr;
    String reTenDangNhap = "^[a-zA-Z0-9_-]{2,10}$";

    public DangKyJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        tvfr = (ThuVienJFrame) parent;
        btnDangKy.setContentAreaFilled(false);
        init();
    }

    void init() {
        this.setLocationRelativeTo(null);
        this.setTitle("Đăng ký");
        this.setIconImage(XImage.getAppIcon());
        txtTenDangNhap.setBackground(new Color(236, 238, 238, 0));
        txtHoTen.setBackground(new Color(236, 238, 238, 0));
        txtXacNhanMatKhau.setBackground(new Color(236, 238, 238, 0));
        txtSoDienThoai.setBackground(new Color(236, 238, 238, 0));
        txtEmail.setBackground(new Color(236, 238, 238, 0));
        txtMatKhau.setBackground(new Color(236, 238, 238, 0));
        ChkDieuKhoan.setBackground(new Color(236, 238, 238, 0));
    }

    boolean validateForm() {
        String reTen = "^[\\p{L}\\p{M}\\s]+$"; // tên có dấu.
        String reHoten = "^[a-zA-Z]$";
        String reTenDangNhap = "^[a-zA-Z0-9_-]{2,10}$";
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        
        if (txtHoTen.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập họ tên!");
            return false;
        }
        if (!txtHoTen.getText().matches(reHoten)) {
            MsgBox.alert(this, "Tên đăng nhập không hợp lệ!");
            return false;
        }
        if (txtTenDangNhap.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập tên đăng nhập!");
            return false;
        }
        if (!txtTenDangNhap.getText().matches(reTenDangNhap)) {
            MsgBox.alert(this, "Tên đăng nhập không hợp lệ!");
            return false;
        }
        if(txtSoDienThoai.getText().equals("")){
            MsgBox.alert(this, "Vui lòng nhập số điện thoại!");
            return false;
        }
        if(txtSoDienThoai.getText().equals("")){
            MsgBox.alert(this, "Vui lòng nhập số điện thoại!");
            return false;
        }
        if (new String(txtMatKhau.getPassword()).equals("")) {
            MsgBox.alert(this, "Vui lòng nhập mật khẩu!");
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtSoDienThoai = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtTenDangNhap = new javax.swing.JTextField();
        txtXacNhanMatKhau = new javax.swing.JPasswordField();
        txtMatKhau = new javax.swing.JPasswordField();
        txtHoTen = new javax.swing.JTextField();
        btnDangKy = new javax.swing.JButton();
        ChkDieuKhoan = new javax.swing.JCheckBox();
        lblDangNhap = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSoDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSoDienThoai.setBorder(null);
        getContentPane().add(txtSoDienThoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 230, 190, 30));

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmail.setBorder(null);
        getContentPane().add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 400, 420, 30));

        txtTenDangNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenDangNhap.setBorder(null);
        getContentPane().add(txtTenDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 230, 190, 30));

        txtXacNhanMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtXacNhanMatKhau.setBorder(null);
        getContentPane().add(txtXacNhanMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 320, 190, 30));

        txtMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMatKhau.setBorder(null);
        getContentPane().add(txtMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, 190, 30));

        txtHoTen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtHoTen.setBorder(null);
        getContentPane().add(txtHoTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 420, 30));
        getContentPane().add(btnDangKy, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 480, 340, 60));

        ChkDieuKhoan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ChkDieuKhoan.setForeground(new java.awt.Color(102, 102, 102));
        ChkDieuKhoan.setText("Đồng ý với điều khoản & điều kiện của chúng tôi");
        ChkDieuKhoan.setToolTipText("");
        getContentPane().add(ChkDieuKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 450, -1, -1));

        lblDangNhap.setForeground(new java.awt.Color(51, 51, 255));
        lblDangNhap.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
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
        getContentPane().add(lblDangNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 560, -1, -1));

        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Bạn đã có tài khoản?");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 560, -1, -1));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/bgrSìgnUp.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblDangNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangNhapMouseClicked
        this.dispose();
        tvfr.openDangNhap();
    }//GEN-LAST:event_lblDangNhapMouseClicked

    private void lblDangNhapMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangNhapMouseEntered
        lblDangNhap.setText("<html><u>Đăng nhập</u></html>");
    }//GEN-LAST:event_lblDangNhapMouseEntered

    private void lblDangNhapMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDangNhapMouseExited
        lblDangNhap.setText("Đăng nhập");
    }//GEN-LAST:event_lblDangNhapMouseExited

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
            java.util.logging.Logger.getLogger(DangKyJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DangKyJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DangKyJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DangKyJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DangKyJDialog dialog = new DangKyJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JCheckBox ChkDieuKhoan;
    private javax.swing.JButton btnDangKy;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblDangNhap;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTenDangNhap;
    private javax.swing.JPasswordField txtXacNhanMatKhau;
    // End of variables declaration//GEN-END:variables
}
