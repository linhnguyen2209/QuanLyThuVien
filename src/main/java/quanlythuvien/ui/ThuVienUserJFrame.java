/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package quanlythuvien.ui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JDialog;
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

        if (dialog.length > 0) { // nếu có dialog thì không set pnl
            dialog[0].setVisible(true);
        } else {
            pnlCurrent.setVisible(true);
        }
        btnCurrent.setBackground(new Color(0, 153, 51));
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
        btnTraCuu = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnTroGiup = new javax.swing.JButton();
        btnLichSuMuonTra = new javax.swing.JButton();
        btnGioiThieu = new javax.swing.JButton();
        pnlTrangChuUser = new javax.swing.JPanel();
        lblBgrTrangChuUser = new javax.swing.JLabel();
        pnlGioiThieu = new javax.swing.JPanel();
        lblBgrGioiThieu = new javax.swing.JLabel();

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

        btnTraCuu.setBackground(new java.awt.Color(0, 102, 153));
        btnTraCuu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTraCuu.setForeground(new java.awt.Color(255, 255, 204));
        btnTraCuu.setText("TRA CỨU SÁCH");
        btnTraCuu.setBorder(null);
        btnTraCuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraCuuActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/logoLibNgang.png"))); // NOI18N

        btnTroGiup.setBackground(new java.awt.Color(0, 102, 153));
        btnTroGiup.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTroGiup.setForeground(new java.awt.Color(255, 255, 204));
        btnTroGiup.setText("TRỢ GIÚP");
        btnTroGiup.setBorder(null);

        btnLichSuMuonTra.setBackground(new java.awt.Color(0, 102, 153));
        btnLichSuMuonTra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLichSuMuonTra.setForeground(new java.awt.Color(255, 255, 204));
        btnLichSuMuonTra.setText("LỊCH SỬ MƯỢN TRẢ");
        btnLichSuMuonTra.setBorder(null);

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
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 179, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        pnlTrangChuUser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBgrTrangChuUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBgrTrangChuUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/bgrTrangChu2.png"))); // NOI18N
        pnlTrangChuUser.add(lblBgrTrangChuUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 680));

        pnlGioiThieu.setBackground(new java.awt.Color(255, 255, 255));
        pnlGioiThieu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBgrGioiThieu.setBackground(new java.awt.Color(255, 255, 255));
        lblBgrGioiThieu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBgrGioiThieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/bgrGioiThieu.png"))); // NOI18N
        pnlGioiThieu.add(lblBgrGioiThieu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 1100, 690));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1112, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addContainerGap(218, Short.MAX_VALUE)
                    .addComponent(pnlTrangChuUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(220, 220, 220)
                    .addComponent(pnlGioiThieu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(pnlTrangChuUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(pnlGioiThieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

    private void btnTrangChuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrangChuActionPerformed
        checkBtnFocus(btnTrangChu, pnlTrangChuUser);
        pnlClicked = pnlTrangChuUser;
        btnClicked = btnTrangChu;
    }//GEN-LAST:event_btnTrangChuActionPerformed

    private void btnTraCuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraCuuActionPerformed
        JDialog dl = new TraCuuSachJDialog(this, true);
        checkBtnFocus(btnTraCuu, pnlTrangChuUser, dl);
        pnlClicked = pnlTrangChuUser;
        btnClicked = btnTraCuu;
    }//GEN-LAST:event_btnTraCuuActionPerformed

    private void btnGioiThieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGioiThieuActionPerformed
        checkBtnFocus(btnGioiThieu, pnlGioiThieu);
        btnClicked = btnGioiThieu;
        pnlClicked = pnlGioiThieu;
    }//GEN-LAST:event_btnGioiThieuActionPerformed

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
    private javax.swing.JButton btnLichSuMuonTra;
    private javax.swing.JButton btnTraCuu;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnTroGiup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblBgrGioiThieu;
    private javax.swing.JLabel lblBgrTrangChuUser;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPanel pnlGioiThieu;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlTrangChuUser;
    // End of variables declaration//GEN-END:variables
}
