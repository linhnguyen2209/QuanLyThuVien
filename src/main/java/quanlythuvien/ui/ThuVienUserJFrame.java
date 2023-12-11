package quanlythuvien.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import quanlythuvien.dao.NguoiDungDAO;
import quanlythuvien.dao.PhieuMuonChiTietDAO;
import quanlythuvien.dao.PhieuMuonDAO;
import quanlythuvien.dao.PhieuTraDAO;
import quanlythuvien.entity.NguoiDung;
import quanlythuvien.entity.PhieuMuon;
import quanlythuvien.entity.PhieuMuonChiTiet;
import quanlythuvien.entity.PhieuTra;
import quanlythuvien.utils.Auth;
import quanlythuvien.utils.MsgBox;
import quanlythuvien.utils.XDate;
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

    //
    PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO();
    PhieuTraDAO phieuTraDAO = new PhieuTraDAO();
    PhieuMuonChiTietDAO phieuMuonChiTietDAO = new PhieuMuonChiTietDAO();
    List<PhieuMuon> listPM = new ArrayList<>();
    DefaultTableModel tblModelPMCT;
    int row = -1;
    int indexPMCT = -1;

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

    void checkBtnFocus(Component btnCurrent, Component pnlCurrent, JDialog... dialog
    ) {
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
// form thông tin cá nhân.
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

    //Lịch sửa mượn trả
    void lichSuMuonTra() {
        fillComboBoxNam();
        fillComboBoxThang();
        fillTablePhieuMuon();
        fillTablePhieuTra();
    }

    // Lịch sử mượn trả.
    void fillTablePhieuMuon() {
        DefaultTableModel model = (DefaultTableModel) tblPhieuMuon.getModel();
        model.setRowCount(0);
        if (cboNam.getSelectedIndex() != 0 && cboThang.getSelectedIndex() == 0) {
            listPM = phieuMuonDAO.selectByYear(Auth.user.getMaNguoiDung(), Integer.parseInt(cboNam.getSelectedItem() + ""));
        } else if (cboNam.getSelectedIndex() != 0 && cboThang.getSelectedIndex() != 0) {
            listPM = phieuMuonDAO.selectByMonthYear(Auth.user.getMaNguoiDung(), Integer.parseInt(cboThang.getSelectedItem() + ""), Integer.parseInt(cboNam.getSelectedItem() + ""));
        } else {
            listPM = phieuMuonDAO.selectByIDND(Auth.user.getMaNguoiDung());
        }
        if (listPM != null) {
            for (PhieuMuon phieuMuon : listPM) {
                Object[] row = {phieuMuon.getMaPhieuMuon(), XDate.toString(phieuMuon.getNgayMuon(), "yyyy/MM/dd"),
                    XDate.toString(phieuMuon.getNgayHenTra(), "yyyy/MM/dd"), phieuMuon.getTongSoLuongSachMuon(), phieuMuon.getMaNguoiDung(), phieuMuon.getGhiChu()};
                model.addRow(row);
            }
        } else {
            model.setRowCount(0);
        }

    }

    void fillComboBoxNam() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNam.getModel();
        model.removeAllElements();
        model.addElement("Năm");
        List<Integer> list = phieuMuonDAO.selectYear();
        if (!list.isEmpty()) {
            for (Integer year : list) {
                model.addElement(year);
            }
        }
    }

    void fillComboBoxThang() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboThang.getModel();
        model.removeAllElements();
        model.addElement("Tháng");
        for (int i = 1; i <= 12; i++) {
            model.addElement(i);
        }
    }

    void setForm(PhieuMuon model
    ) {
        // form pmct
        txtMaPhieuMuon_PMCT.setText(String.valueOf((model.getMaPhieuMuon())));
        fillTableChiTietPhieuMuon();
        // form p mượn
        txtMaPhieuMuon.setText(String.valueOf((model.getMaPhieuMuon())));
        Date ngayMuon = model.getNgayMuon();
        txtNgayMuon.setText(XDate.toString(ngayMuon, "yyyy/MM/dd"));
        Date ngayTra = model.getNgayHenTra();
        txtNgayHenTra.setText(XDate.toString(ngayTra, "yyyy/MM/dd"));
        txtTongSoLuongSachMuon.setText(String.valueOf(model.getTongSoLuongSachMuon()));
        txtMaNguoiDung.setText(model.getMaNguoiDung());
        txtGhiChu.setText(model.getGhiChu());
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

        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);

    }

    private void first() {
        row = 0;
        tblPhieuMuon.setRowSelectionInterval(row, row);
        edit();
        tblPhieuTra.setRowSelectionInterval(row, row);
        showDetailPhieuTra();
    }

    private void prev() {
        if (row > 0) {
            row--;
            tblPhieuMuon.setRowSelectionInterval(row, row);
            edit();
            tblPhieuTra.setRowSelectionInterval(row, row);
            showDetailPhieuTra();
        }
    }

    private void next() {
        if (row < tblPhieuMuon.getRowCount() - 1) {
            row++;
            tblPhieuMuon.setRowSelectionInterval(row, row);
            edit();
            tblPhieuTra.setRowSelectionInterval(row, row);
            showDetailPhieuTra();
        }
    }

    private void last() {
        row = tblPhieuMuon.getRowCount() - 1;
        tblPhieuMuon.setRowSelectionInterval(row, row);
        edit();
        tblPhieuTra.setRowSelectionInterval(row, row);
        showDetailPhieuTra();
    }
// chi tiết phiếu mượn
    void fillTableChiTietPhieuMuon() {
        tblModelPMCT = (DefaultTableModel) tblChiTietPhieuMuon.getModel();
        tblModelPMCT.setRowCount(0);
        List<PhieuMuonChiTiet> listCTPM = phieuMuonChiTietDAO.selectBy_IDPM(Integer.parseInt(txtMaPhieuMuon_PMCT.getText()));
        System.out.println(listCTPM.size());
        for (PhieuMuonChiTiet pmct : listCTPM) {
            Object[] row = {pmct.getMaChiTietPhieuMuon(), pmct.getMaPhieuMuon(), pmct.getMaSach(), pmct.getSlSachMuonMoiLoai()};
            tblModelPMCT.addRow(row);
        }
        updateStatusPMCT();
    }

    void showDetailPhieuMuonChiTiet() {
        PhieuMuonChiTiet model = phieuMuonChiTietDAO.selectById(Integer.valueOf(tblChiTietPhieuMuon.getValueAt(indexPMCT, 0) + ""));
        txtMaPhieuMuonChiTiet_PMCT.setText(model.getMaChiTietPhieuMuon() + "");
        txtMaSach.setText(model.getMaSach() + "");
        txtSoLuong.setText(model.getSlSachMuonMoiLoai() + "");
        updateStatusPMCT();
    }

    void updateStatusPMCT() {
        boolean edit = this.indexPMCT >= 0;
        boolean first = this.indexPMCT == 0;
        boolean last = this.indexPMCT == tblChiTietPhieuMuon.getRowCount() - 1;

        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);

    }

    private void firstCTPM() {
        indexPMCT = 0;
        tblChiTietPhieuMuon.setRowSelectionInterval(indexPMCT, indexPMCT);
        showDetailPhieuMuonChiTiet();
    }

    private void prevCTPM() {
        if (indexPMCT > 0) {
            indexPMCT--;
            tblChiTietPhieuMuon.setRowSelectionInterval(indexPMCT, indexPMCT);
            showDetailPhieuMuonChiTiet();
        }
    }

    private void nextCTPM() {
        if (indexPMCT < tblChiTietPhieuMuon.getRowCount() - 1) {
            indexPMCT++;
            tblChiTietPhieuMuon.setRowSelectionInterval(indexPMCT, indexPMCT);
            showDetailPhieuMuonChiTiet();
        }
    }

    private void lastCTPM() {
        indexPMCT = tblChiTietPhieuMuon.getRowCount() - 1;
        tblChiTietPhieuMuon.setRowSelectionInterval(indexPMCT, indexPMCT);
        showDetailPhieuMuonChiTiet();
    }
    
    // form p trả
    void fillTablePhieuTra() {
        DefaultTableModel model = (DefaultTableModel) tblPhieuTra.getModel();
        model.setRowCount(0);
        try {
            List<PhieuTra> list = phieuTraDAO.selectByIdUser(Auth.user.getMaNguoiDung());
            if (list != null) {
                for (PhieuTra phieuTra : list) {
                    // kiểm tra ngày trả nếu phiếu trả vừa được tạo hoặc chưa trả thì sẽ lấy ngày hiện tại là ngày trả để khi update trạng thái không cần update ngày và tránh lỗi null
                    String date;
                    if (phieuTra.getNgayTra() == null) {
                        date = "";
                    } else {
                        date = XDate.toString(phieuTra.getNgayTra(), "yyyy/MM/dd");
                    }
                    Object[] row = {phieuTra.getMaPhieuTra(), phieuTra.getMaPhieuMuon(), date,
                        phieuTra.isTrangThai() ? "Đã trả" : "Chưa trả", phieuTra.getMaNguoiDung(), phieuTra.getGhiChu()};
                    model.addRow(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void showDetailPhieuTra() {
        PhieuTra model = phieuTraDAO.selectById(Integer.parseInt(tblPhieuTra.getValueAt(row, 0) + ""));
        txtMaPhieuTra_Tra.setText(model.getMaPhieuTra() + "");
        txtMaPhieuMuon_Tra.setText(model.getMaPhieuMuon() + "");
        txtMaDocGia_Tra.setText(model.getMaNguoiDung());
        // kiểm tra ngày trả nếu phiếu trả vừa được tạo hoặc chưa trả thì sẽ lấy ngày hiện tại là ngày trả để khi update trạng thái không cần update ngày và tránh lỗi null
        Date date;
        if (model.getNgayTra() == null) {
            date = new Date();
        } else {
            date = model.getNgayTra();
        }
        txtNgayTra_Tra.setText(XDate.toString(date, "yyyy-MM-dd"));
        txaGhiChu_Tra.setText(model.getGhiChu());
        chkDaTra_Tra.setSelected(model.isTrangThai());
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
        pnlLichSuMuonTra = new javax.swing.JPanel();
        tabs = new javax.swing.JTabbedPane();
        pnlPhieuMuon = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnXemPhieuTra = new javax.swing.JButton();
        btnXemPMChiTiet = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        txtMaNguoiDung = new javax.swing.JTextField();
        txtTongSoLuongSachMuon = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNgayHenTra = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNgayMuon = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaPhieuMuon = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhieuMuon = new javax.swing.JTable();
        cboThang = new javax.swing.JComboBox<>();
        cboNam = new javax.swing.JComboBox<>();
        pnlChiTietPM = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaPhieuMuon_PMCT = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtMaPhieuMuonChiTiet_PMCT = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtMaSach = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblChiTietPhieuMuon = new javax.swing.JTable();
        btnXemPhieuMuon = new javax.swing.JButton();
        btnFirstPMCT = new javax.swing.JButton();
        btnPrevPMCT = new javax.swing.JButton();
        btnNextPMCT = new javax.swing.JButton();
        btnLastPMCT = new javax.swing.JButton();
        pnlTraSach = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtMaPhieuMuon_Tra = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtNgayTra_Tra = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtMaDocGia_Tra = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txaGhiChu_Tra = new javax.swing.JTextArea();
        chkDaTra_Tra = new javax.swing.JCheckBox();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtMaPhieuTra_Tra = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        btnFirstPMCT1 = new javax.swing.JButton();
        btnPrevPMCT1 = new javax.swing.JButton();
        btnNextPMCT1 = new javax.swing.JButton();
        btnLastPMCT1 = new javax.swing.JButton();
        btnXemPhieuMuon_Tra = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPhieuTra = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();

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
        btnLichSuMuonTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLichSuMuonTraActionPerformed(evt);
            }
        });

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

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/businessman1.png"))); // NOI18N

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
                .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
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
                            .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
                            .addComponent(txtMatKhau)
                            .addComponent(txtMaND)))
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

        tabs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabsMouseClicked(evt);
            }
        });

        pnlPhieuMuon.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));

        btnFirst.setText("|<<");
        btnFirst.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setText("<<");
        btnPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setText(">>|");
        btnLast.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnXemPhieuTra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXemPhieuTra.setText("XEM PHIẾU TRẢ");
        btnXemPhieuTra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXemPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemPhieuTraActionPerformed(evt);
            }
        });

        btnXemPMChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXemPMChiTiet.setText("XEM PHIẾU MƯỢN CHI TIẾT");
        btnXemPMChiTiet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXemPMChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemPMChiTietActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 191, Short.MAX_VALUE)
                .addComponent(btnXemPMChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXemPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnXemPhieuTra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnXemPMChiTiet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25))
        );

        pnlPhieuMuon.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 1080, 100));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin"));

        jLabel7.setText("Mã Độc giả:");

        jLabel8.setText("Ghi chú");

        txtGhiChu.setEditable(false);
        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane3.setViewportView(txtGhiChu);

        txtMaNguoiDung.setEditable(false);

        txtTongSoLuongSachMuon.setEditable(false);

        jLabel6.setText("Tổng số lượng:");

        txtNgayHenTra.setEditable(false);

        jLabel5.setText("Ngày hẹn trả");

        txtNgayMuon.setEditable(false);

        jLabel4.setText("Ngày mượn");

        jLabel3.setText("Mã Phiếu mượn");

        txtMaPhieuMuon.setEditable(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtMaNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtNgayHenTra, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(txtNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTongSoLuongSachMuon)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))
                .addGap(73, 73, 73))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNgayMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNgayHenTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTongSoLuongSachMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pnlPhieuMuon.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 1080, 150));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách"));

        tblPhieuMuon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã PM", "Ngày mượn", "Ngày hẹn trả", "SL mượn", "Mã Độc giả", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

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

        cboThang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboThang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboThangActionPerformed(evt);
            }
        });

        cboNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1048, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cboThang, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboThang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        pnlPhieuMuon.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 330));

        tabs.addTab("Phiếu mượn", pnlPhieuMuon);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin phiếu mượn chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel2.setText("Mã Phiếu mượn:");

        txtMaPhieuMuon_PMCT.setEditable(false);

        jLabel12.setText("Mã Phiếu mượn chi tiết:");

        txtMaPhieuMuonChiTiet_PMCT.setEditable(false);

        jLabel1.setText("Mã sách:");

        txtMaSach.setEditable(false);

        jLabel15.setText("Số lượng");

        txtSoLuong.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(jLabel12)
                    .addComponent(txtMaPhieuMuon_PMCT, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                    .addComponent(txtMaPhieuMuonChiTiet_PMCT))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSoLuong)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel15))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtMaSach))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaPhieuMuon_PMCT, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaPhieuMuonChiTiet_PMCT, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(txtSoLuong))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin phiếu mượn chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        tblChiTietPhieuMuon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Chi tiết PM", "Mã Phiếu Mượn", "Mã Sách", "Số lượng sách mượn"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChiTietPhieuMuon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietPhieuMuonMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblChiTietPhieuMuon);

        btnXemPhieuMuon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXemPhieuMuon.setText("Xem phiếu mượn");
        btnXemPhieuMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemPhieuMuonActionPerformed(evt);
            }
        });

        btnFirstPMCT.setText("|<");
        btnFirstPMCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstPMCTActionPerformed(evt);
            }
        });

        btnPrevPMCT.setText("<<");
        btnPrevPMCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevPMCTActionPerformed(evt);
            }
        });

        btnNextPMCT.setText(">>");
        btnNextPMCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextPMCTActionPerformed(evt);
            }
        });

        btnLastPMCT.setText(">|");
        btnLastPMCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastPMCTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(btnFirstPMCT, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrevPMCT, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNextPMCT, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLastPMCT, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXemPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLastPMCT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnXemPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnFirstPMCT)
                        .addComponent(btnNextPMCT)
                        .addComponent(btnPrevPMCT)))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout pnlChiTietPMLayout = new javax.swing.GroupLayout(pnlChiTietPM);
        pnlChiTietPM.setLayout(pnlChiTietPMLayout);
        pnlChiTietPMLayout.setHorizontalGroup(
            pnlChiTietPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChiTietPMLayout.createSequentialGroup()
                .addGroup(pnlChiTietPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlChiTietPMLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        pnlChiTietPMLayout.setVerticalGroup(
            pnlChiTietPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChiTietPMLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addContainerGap())
        );

        tabs.addTab("Chi tiết phiếu mượn", pnlChiTietPM);

        pnlTraSach.setBackground(new java.awt.Color(204, 255, 204));
        pnlTraSach.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Chi tiết"));

        jLabel19.setText("Mã Phiếu mượn:");

        txtMaPhieuMuon_Tra.setEditable(false);

        jLabel17.setText("Ngày trả:");

        txtNgayTra_Tra.setEditable(false);

        jLabel20.setText("Mã Độc giả:");

        txtMaDocGia_Tra.setEditable(false);

        jLabel21.setText("Ghi chú:");

        txaGhiChu_Tra.setColumns(20);
        txaGhiChu_Tra.setRows(5);
        jScrollPane7.setViewportView(txaGhiChu_Tra);

        chkDaTra_Tra.setText("Đã trả sách");

        jLabel22.setText("Trạng thái:");

        jLabel23.setText("Mã Phiếu Trả:");

        txtMaPhieuTra_Tra.setEditable(false);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaPhieuTra_Tra)
                    .addComponent(txtMaPhieuMuon_Tra)
                    .addComponent(txtNgayTra_Tra)
                    .addComponent(chkDaTra_Tra, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE))
                .addGap(92, 92, 92)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                    .addComponent(txtMaDocGia_Tra))
                .addGap(15, 15, 15))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtMaPhieuMuon_Tra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(txtNgayTra_Tra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chkDaTra_Tra)
                            .addComponent(jLabel22)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtMaDocGia_Tra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21)))
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtMaPhieuTra_Tra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel23)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        pnlTraSach.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 1060, 230));

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Bảng phiếu trả"));

        btnFirstPMCT1.setText("|<");

        btnPrevPMCT1.setText("<<");

        btnNextPMCT1.setText(">>");

        btnLastPMCT1.setText(">|");

        btnXemPhieuMuon_Tra.setBackground(new java.awt.Color(204, 255, 255));
        btnXemPhieuMuon_Tra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXemPhieuMuon_Tra.setText("XEM PHIẾU MƯỢN");
        btnXemPhieuMuon_Tra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemPhieuMuon_TraActionPerformed(evt);
            }
        });

        tblPhieuTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã phiếu trả", "Mã phiếu mượn", "Ngày trả", "Trạng thái", "Mã người dùng", "Ghi chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPhieuTra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuTraMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblPhieuTra);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(btnFirstPMCT1)
                        .addGap(18, 18, 18)
                        .addComponent(btnPrevPMCT1)
                        .addGap(18, 18, 18)
                        .addComponent(btnNextPMCT1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLastPMCT1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXemPhieuMuon_Tra, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1031, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirstPMCT1)
                    .addComponent(btnPrevPMCT1)
                    .addComponent(btnNextPMCT1)
                    .addComponent(btnLastPMCT1)
                    .addComponent(btnXemPhieuMuon_Tra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlTraSach.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 1060, 330));

        tabs.addTab("Phiếu trả", pnlTraSach);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 0, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("LỊCH SỬ MƯỢN TRẢ");

        javax.swing.GroupLayout pnlLichSuMuonTraLayout = new javax.swing.GroupLayout(pnlLichSuMuonTra);
        pnlLichSuMuonTra.setLayout(pnlLichSuMuonTraLayout);
        pnlLichSuMuonTraLayout.setHorizontalGroup(
            pnlLichSuMuonTraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLichSuMuonTraLayout.createSequentialGroup()
                .addGap(446, 446, 446)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlLichSuMuonTraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlLichSuMuonTraLayout.setVerticalGroup(
            pnlLichSuMuonTraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLichSuMuonTraLayout.createSequentialGroup()
                .addGap(0, 9, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlContainer.add(pnlLichSuMuonTra, "card5");

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

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        updateTTTaiKhoan();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void lblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblUserMouseClicked
        checkBtnFocus(null, pnlThongTinCaNhan);
        pnlClicked = pnlThongTinCaNhan;
        showDetailTTTaiKhoan();
    }//GEN-LAST:event_lblUserMouseClicked

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
        if (currentIndex > 0) {
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

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void tblPhieuMuonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuMuonMousePressed
        if (evt.getClickCount() == 1) {
            this.row = tblPhieuMuon.rowAtPoint(evt.getPoint());
            tblPhieuTra.setRowSelectionInterval(row, row);
            showDetailPhieuTra();
            edit();
        }
    }//GEN-LAST:event_tblPhieuMuonMousePressed

    private void btnXemPMChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemPMChiTietActionPerformed
        if (row < 0) {
            MsgBox.alert(this, "Vui lòng chọn phiếu mượn muốn xem");
        } else {
            fillTableChiTietPhieuMuon();
            tabs.setSelectedIndex(1);
        }
    }//GEN-LAST:event_btnXemPMChiTietActionPerformed

    private void btnXemPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemPhieuTraActionPerformed
        if (row < 0) {
            MsgBox.alert(this, "Vui lòng chọn phiếu mượn muốn xem");
        } else {
            tabs.setSelectedIndex(2);
        }
    }//GEN-LAST:event_btnXemPhieuTraActionPerformed

    private void btnXemPhieuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemPhieuMuonActionPerformed
        tabs.setSelectedIndex(0);
    }//GEN-LAST:event_btnXemPhieuMuonActionPerformed

    private void btnFirstPMCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstPMCTActionPerformed
        firstCTPM();
    }//GEN-LAST:event_btnFirstPMCTActionPerformed

    private void btnNextPMCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextPMCTActionPerformed
        nextCTPM();
    }//GEN-LAST:event_btnNextPMCTActionPerformed

    private void btnPrevPMCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevPMCTActionPerformed
        prevCTPM();
    }//GEN-LAST:event_btnPrevPMCTActionPerformed

    private void btnLastPMCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastPMCTActionPerformed
        lastCTPM();
    }//GEN-LAST:event_btnLastPMCTActionPerformed

    private void tblChiTietPhieuMuonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietPhieuMuonMouseClicked
        indexPMCT = tblChiTietPhieuMuon.getSelectedRow();
        if (indexPMCT >= 0) {
            showDetailPhieuMuonChiTiet();
        }
    }//GEN-LAST:event_tblChiTietPhieuMuonMouseClicked

    private void tblPhieuTraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuTraMouseClicked
        row = tblPhieuTra.getSelectedRow();
        if (row >= 0) {
            showDetailPhieuTra();
            tblPhieuMuon.setRowSelectionInterval(row, row);
            edit();
        }
    }//GEN-LAST:event_tblPhieuTraMouseClicked

    private void btnXemPhieuMuon_TraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemPhieuMuon_TraActionPerformed
        if (row < 0) {
            MsgBox.alert(this, "Vui lòng chọn phiếu trả muốn xem");
        } else {
            tabs.setSelectedIndex(0);
        }
    }//GEN-LAST:event_btnXemPhieuMuon_TraActionPerformed

    private void tabsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabsMouseClicked
        if (tabs.getSelectedIndex() == 1 && txtMaPhieuMuon_PMCT.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng chọn phiếu mượn cần xem!");
            tabs.setSelectedIndex(0);
        }
    }//GEN-LAST:event_tabsMouseClicked

    private void btnLichSuMuonTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLichSuMuonTraActionPerformed
        lichSuMuonTra();
        checkBtnFocus(btnLichSuMuonTra, pnlLichSuMuonTra);
        pnlClicked = pnlLichSuMuonTra;
        btnClicked = btnLichSuMuonTra;
    }//GEN-LAST:event_btnLichSuMuonTraActionPerformed

    private void cboNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNamActionPerformed
        if (cboNam.getItemCount() > 0) {
            fillTablePhieuMuon();
        }
    }//GEN-LAST:event_cboNamActionPerformed

    private void cboThangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboThangActionPerformed
        if (cboNam.getSelectedIndex() != 0) {
            if (cboThang.getItemCount() > 0) {
                fillTablePhieuMuon();
            }
        }
    }//GEN-LAST:event_cboThangActionPerformed

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
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnFirstPMCT;
    private javax.swing.JButton btnFirstPMCT1;
    private javax.swing.JButton btnGioiThieu;
    private javax.swing.JButton btnKhamPha;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLastPMCT;
    private javax.swing.JButton btnLastPMCT1;
    private javax.swing.JButton btnLichSuMuonTra;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNextPMCT;
    private javax.swing.JButton btnNextPMCT1;
    private javax.swing.JButton btnNextSlide;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnPrevPMCT;
    private javax.swing.JButton btnPrevPMCT1;
    private javax.swing.JButton btnPrevSlide;
    private javax.swing.JButton btnTraCuu;
    private javax.swing.JButton btnTrangChu;
    private javax.swing.JButton btnTroGiup;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnXemPMChiTiet;
    private javax.swing.JButton btnXemPhieuMuon;
    private javax.swing.JButton btnXemPhieuMuon_Tra;
    private javax.swing.JButton btnXemPhieuTra;
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JComboBox<String> cboThang;
    private javax.swing.JCheckBox chkDaTra_Tra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
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
    private javax.swing.JPanel pnlChiTietPM;
    private javax.swing.JPanel pnlContainer;
    private javax.swing.JPanel pnlGioiThieu;
    private javax.swing.JPanel pnlLichSuMuonTra;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlPhieuMuon;
    private javax.swing.JPanel pnlThongTinCaNhan;
    private javax.swing.JPanel pnlTraSach;
    private javax.swing.JPanel pnlTrangChuCon1;
    private javax.swing.JPanel pnlTrangChuCon2;
    private javax.swing.JPanel pnlTrangChuCon3;
    private javax.swing.JPanel pnlTrangChuCon4;
    private javax.swing.JPanel pnlTrangChuUser;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblChiTietPhieuMuon;
    private javax.swing.JTable tblPhieuMuon;
    private javax.swing.JTable tblPhieuTra;
    private javax.swing.JTextArea txaGhiChu_Tra;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaDocGia_Tra;
    private javax.swing.JTextField txtMaND;
    private javax.swing.JTextField txtMaNguoiDung;
    private javax.swing.JTextField txtMaPhieuMuon;
    private javax.swing.JTextField txtMaPhieuMuonChiTiet_PMCT;
    private javax.swing.JTextField txtMaPhieuMuon_PMCT;
    private javax.swing.JTextField txtMaPhieuMuon_Tra;
    private javax.swing.JTextField txtMaPhieuTra_Tra;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtNgayHenTra;
    private javax.swing.JTextField txtNgayMuon;
    private javax.swing.JTextField txtNgayTra_Tra;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTongSoLuongSachMuon;
    // End of variables declaration//GEN-END:variables
}
