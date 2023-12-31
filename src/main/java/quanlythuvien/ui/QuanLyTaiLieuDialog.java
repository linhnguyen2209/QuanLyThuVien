package quanlythuvien.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import quanlythuvien.dao.LoaiSachDao;
import quanlythuvien.dao.SachDAO;
import quanlythuvien.entity.LoaiSach;
import quanlythuvien.entity.Sach;
import quanlythuvien.utils.Auth;
import quanlythuvien.utils.MsgBox;
import quanlythuvien.utils.ValidationForm;
import quanlythuvien.utils.XDate;
import quanlythuvien.utils.XImage;

/**
 *
 * @author Dino Design Linh Code Dino
 */
public class QuanLyTaiLieuDialog extends javax.swing.JDialog {

    SachDAO SDao = new SachDAO();
    LoaiSachDao LSDao = new LoaiSachDao();
    int row = -1;
    int rowLS = -1;

    public QuanLyTaiLieuDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    void init() {
//        this.setLocation(318, 73);
        this.setLocation(325, 74);
        this.setTitle("Quản lý tài liệu");
        this.setIconImage(XImage.getAppIcon());
        FillTable_QLTlieu();
        fillComboBoxLoaiSach();
        fillTableLoaiSach();
        updateStatusLS();
        updateStatusS();
    }

    List<Sach> list = new ArrayList<>();

    void FillTable_QLTlieu() {
        DefaultTableModel model = (DefaultTableModel) tblQLTL.getModel();
        model.setRowCount(0);
        try {
            if (rdoTenSach.isSelected()) {
                list = SDao.selectByTieuDe(txtSearch.getText());
            } else if (rdoNXB.isSelected()) {
                list = SDao.selectByNhaXB(txtSearch.getText());
            } else if (rdoTacGia.isSelected()) {
                list = SDao.selectByTacGia(txtSearch.getText());
            } else if (rdoMaLoaiSach.isSelected()) {
                list = SDao.selectByMaLoaiSach(txtSearch.getText());
            } else {
                list = SDao.selectAll();
            }
            if (list.size() <= 0) {
                tblQLTL.setVisible(false);
                lblKetQua.setText("Tổng số sách " + list.size());
                clearForm();
            } else {
                tblQLTL.setVisible(true);
                lblKetQua.setText("Tổng số Sách " + list.size());
            }
            System.out.println("List: " + list.size());
            for (Sach sach : list) {
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
                model.addElement(ls.getMaLoaiSach());
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
        cboLoaiSach.setSelectedItem(model.getMaLoaiSach().trim());
        System.out.println("MLS" + model.getMaLoaiSach());
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
        tblQLTL.clearSelection();
        tblQLTL.clearSelection();
        row = -1;
        updateStatusS();
    }

    Sach getForm() {
        Sach model = new Sach();
        if (row >= 0) {
            model.setMaSach(Integer.parseInt(tblQLTL.getValueAt(row, 0) + ""));
        }
        model.setTieuDe(txtTieude.getText());
        model.setNhaXuatBan(txtNhaXuatBan.getText());
        model.setTacGia(txtTacGia.getText());
        try {
            // Xử lý trường số trang
            int soTrang = Integer.parseInt(txtSoTrang.getText());
            model.setSoTrang(soTrang);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            // Xử lý trường số lượng sách
            int soLuongSach = Integer.parseInt(txt_SoLuongSach.getText());
            model.setSoLuongSach(soLuongSach);
        } catch (NumberFormatException e) {

            e.printStackTrace();
        }
        try {
            // Xử lý trường giá tiền
            double giaTien = Double.parseDouble(txtGiaTien.getText());
            model.setGiaTien(giaTien);
        } catch (NumberFormatException e) {

            e.printStackTrace();
        }
        // Xử lý trường ngày nhập kho
        Date ngayNhapKho = XDate.toDate(txtNgayNhapKho.getText(), "yyyy-MM-dd");
        if (ngayNhapKho.compareTo(new Date()) > 0) {
            MsgBox.alert(this, "Ngày nhập kho vượt quá ngày hiện tại!");
        }
        model.setNgayNhapKho(ngayNhapKho);
        model.setViTriSach(txtViTri.getText());
        // Lấy giá trị được chọn từ ComboBox
        Object selectedLoaiSach = cboLoaiSach.getSelectedItem();
        if (selectedLoaiSach != null) {
            model.setMaLoaiSach(selectedLoaiSach.toString().trim());
        }
        return model;
    }

    void edit() {
        Integer MaSach = (Integer) tblQLTL.getValueAt(row, 0);
        try {
            Sach sh = SDao.selectById(MaSach);
            if (sh != null) {
                setForm(sh);
                updateStatusS();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateStatusS() {
        boolean edit = this.row >= 0;
        boolean first = this.row == 0;
        boolean last = this.row == tblQLTL.getRowCount() - 1;
        btnAdd1.setEnabled(!edit);
        btnUpdate1.setEnabled(edit);
        btnDelete1.setEnabled(edit);
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btn_last.setEnabled(edit && !last);
    }

    boolean ValidateForm() {
        String TIEU_DE_PATTERN = "^[\\p{L}\\p{M}\\p{N}\\s]+$";
        String VI_TRI_PATTERN = "^[\\p{L}\\p{M}\\p{N}\\s]+$";

        if (!Pattern.matches(TIEU_DE_PATTERN, txtTieude.getText())) {
            JOptionPane.showMessageDialog(this, "Tiêu đề không hợp lệ");
            return false;
        }
        if (!ValidationForm.isTen(this, txtNhaXuatBan, "Nhà Xuất Bản")) {
            return false;
        }
        if (!ValidationForm.isTen(this, txtTacGia, "Tác giả")) {
            return false;
        }
        if (!ValidationForm.isSo(this, txtSoTrang, "Số trang")) {
            return false;
        }
        if (!ValidationForm.isSo(this, txt_SoLuongSach, "Số lượng sách")) {
            return false;
        }
        //giá tiền
        try {
            double giaTien = Double.parseDouble(txtGiaTien.getText());
            if (giaTien <= 0) {
                JOptionPane.showMessageDialog(this, "Giá tiền phải lớn hơn 0");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá tiền phải là số");
            return false;
        }
        if (!ValidationForm.isDate(txtNgayNhapKho, this, "Ngày Nhập Kho")) {
            return false;
        }
        //vị trí đặt sách
        if (!Pattern.matches(VI_TRI_PATTERN, txtViTri.getText())) {
            JOptionPane.showMessageDialog(this, "Vị trí không hợp lệ");
            return false;
        }

        return true;
    }

    void insertSach() {
        Sach sach = getForm();
        try {
            SDao.insert(sach);
            FillTable_QLTlieu();
            clearForm();
            MsgBox.alert(this, "Thêm thành công !");
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Thêm mới thất bại !");
        }
    }

    void updateSach() {
        Sach sch = getForm();
        try {
            SDao.update(sch);
            FillTable_QLTlieu();
            MsgBox.alert(this, "Update thành công !");
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Update thất bại !");
        }
    }

    void deleteSach() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền xóa !");
        } else {
            try {
                if (MsgBox.confirm(this, "Bạn thực xự muốn xóa ?")) {
                    int maSach = Integer.parseInt(tblQLTL.getValueAt(row, 0) + "");
                    SDao.delete(maSach);
                    MsgBox.alert(this, "Xóa thành công!");
                    FillTable_QLTlieu();
                    clearForm();
                }

            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.alert(this, "Xóa thất bại!");
            }
        }
    }

    /**
     * ************************************************************************************************************************
     */
    // QlLoaiSach
    void setFormLS(LoaiSach model) {
        txtQL_MaLoaiSach.setText(model.getMaLoaiSach());
        txtTenLoaiSachQLi.setText(model.getTenLoaiSach());
    }

    void clearFormLoaiSach() {
        txtQL_MaLoaiSach.setText("");
        txtTenLoaiSachQLi.setText("");
        tbl_loaisach.clearSelection();
        rowLS = -1;
        updateStatusLS();
    }

    void editLoaiSach() {
        String Ls = (String) tbl_loaisach.getValueAt(rowLS, 0);
        try {
            LoaiSach loaiS = LSDao.selectById(Ls);
            if (loaiS != null) {
                setFormLS(loaiS);
                updateStatusLS();
            }
        } catch (Exception e) {
        }
    }

    LoaiSach getFormLS() {
        LoaiSach model = new LoaiSach();
        model.setMaLoaiSach(txtQL_MaLoaiSach.getText());
        model.setTenLoaiSach(txtTenLoaiSachQLi.getText());
        return model;
    }

    boolean ValidateLS() {
        // loại sách
        if (!ValidationForm.isMa(this, txtQL_MaLoaiSach, "Mã loại sách")) {
            return false;
        }
        if (!ValidationForm.isTen(this, txtTenLoaiSachQLi, "Tên Loại Sách")) {
            return false;
        }
        return true;
    }

    void insertLS() {
        LoaiSach lss = getFormLS();
        try {
            LSDao.insert(lss);
            fillTableLoaiSach();
            clearForm();
            MsgBox.alert(this, "Thêm thành công !");
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Thêm mới thất bại !");
        }
    }

    void updateLS() {
        LoaiSach lss = getFormLS();
        try {
            LSDao.update(lss);
            fillTableLoaiSach();
            MsgBox.alert(this, "Update thành công !");
            clearFormLoaiSach();

        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Update thất bại !");
        }
    }

    void deleteLS() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không đủ quyền hạn để thực thi !");
        } else {
            try {
                if (MsgBox.confirm(this, "Bạn thực xự muốn xóa ?")) {
                    String MaLoaiSach = txtQL_MaLoaiSach.getText();
                    LSDao.delete(MaLoaiSach);
                    fillTableLoaiSach();
                    clearFormLoaiSach();
                    MsgBox.alert(this, "Xóa thành công!");
                }

            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.alert(this, "Xóa thất bại!");
            }
        }
    }

    void updateStatusLS() {
        boolean edit = this.rowLS >= 0;
        boolean first = this.rowLS == 0;
        boolean last = this.rowLS == tblQLTL.getRowCount() - 1;
        btn_ThemLoaiSach.setEnabled(!edit);
        btn_SuaLoaiSach.setEnabled(edit);
        btn_XoaLoaiSach.setEnabled(edit);
        btnFirstLS.setEnabled(edit && !first);
        btnPrevLS.setEnabled(edit && !first);
        btnNextLS.setEnabled(edit && !last);
        btn_lastLS.setEnabled(edit && !last);
    }

    private void first() {
        row = 0;
        tblQLTL.setRowSelectionInterval(row, row);
        edit();
    }

    private void prev() {
        if (row > 0) {
            row--;
            tblQLTL.setRowSelectionInterval(row, row);
            edit();
        }
    }

    private void next() {
        if (row < tblQLTL.getRowCount() - 1) {
            row++;
            tblQLTL.setRowSelectionInterval(row, row);
            edit();
        }
    }

    private void last() {
        row = tblQLTL.getRowCount() - 1;
        tblQLTL.setRowSelectionInterval(row, row);
        edit();
    }

    void firstLS() {
        rowLS = 0;
        tbl_loaisach.setRowSelectionInterval(rowLS, rowLS);
        editLoaiSach();
    }

    void prevLS() {
        if (rowLS > 0) {
            rowLS--;
            tbl_loaisach.setRowSelectionInterval(rowLS, rowLS);
            editLoaiSach();
        }
    }

    void nextLS() {
        if (rowLS < tbl_loaisach.getRowCount() - 1) {
            rowLS++;
            tbl_loaisach.setRowSelectionInterval(rowLS, rowLS);
            editLoaiSach();
        }
    }

    void lastLS() {
        rowLS = tbl_loaisach.getRowCount() - 1;
        tbl_loaisach.setRowSelectionInterval(rowLS, rowLS);
        editLoaiSach();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        btn_timkiemRDO = new javax.swing.ButtonGroup();
        jRadioButton1 = new javax.swing.JRadioButton();
        pnlQLTL = new javax.swing.JPanel();
        tabs = new javax.swing.JTabbedPane();
        pnlContain = new javax.swing.JPanel();
        pnlTimKiem = new javax.swing.JPanel();
        btnSearch = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        pnlDanhSach = new javax.swing.JPanel();
        lblKetQua = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblQLTL = new javax.swing.JTable();
        pnlLoaiTimKiem = new javax.swing.JPanel();
        rdoTenSach = new javax.swing.JRadioButton();
        rdoNXB = new javax.swing.JRadioButton();
        rdoTacGia = new javax.swing.JRadioButton();
        rdoMaLoaiSach = new javax.swing.JRadioButton();
        pnlThongTin = new javax.swing.JPanel();
        lblGiaTien = new javax.swing.JLabel();
        lblTieuDe = new javax.swing.JLabel();
        cboLoaiSach = new javax.swing.JComboBox<>();
        txtGiaTien = new javax.swing.JTextField();
        lblNgayNhapKho = new javax.swing.JLabel();
        txtNgayNhapKho = new javax.swing.JTextField();
        lblViTri = new javax.swing.JLabel();
        txtNhaXuatBan = new javax.swing.JTextField();
        txtViTri = new javax.swing.JTextField();
        lblNXB = new javax.swing.JLabel();
        lblLoaiSach = new javax.swing.JLabel();
        lblTacGia = new javax.swing.JLabel();
        txtTieude = new javax.swing.JTextField();
        txtTacGia = new javax.swing.JTextField();
        lblSoTrang = new javax.swing.JLabel();
        txtSoTrang = new javax.swing.JTextField();
        lblSLSach = new javax.swing.JLabel();
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
        pnlQLLoaiSach = new javax.swing.JPanel();
        lblQLLS = new javax.swing.JLabel();
        pnlDieuHuong1 = new javax.swing.JPanel();
        btn_lastLS = new javax.swing.JButton();
        btnFirstLS = new javax.swing.JButton();
        btnPrevLS = new javax.swing.JButton();
        btnNextLS = new javax.swing.JButton();
        pnlMaLoaiSach = new javax.swing.JPanel();
        lblQL_MaLoaiSach = new javax.swing.JLabel();
        txtQL_MaLoaiSach = new javax.swing.JTextField();
        lblTenLoaiSachQLi = new javax.swing.JLabel();
        txtTenLoaiSachQLi = new javax.swing.JTextField();
        pnlChucNang = new javax.swing.JPanel();
        btn_SuaLoaiSach = new javax.swing.JButton();
        btn_ThemLoaiSach = new javax.swing.JButton();
        btn_XoaLoaiSach = new javax.swing.JButton();
        btn_MoiLS = new javax.swing.JButton();
        pnlChucNang4 = new javax.swing.JPanel();
        btn_HomeLS = new javax.swing.JButton();
        btnThoatLS = new javax.swing.JButton();
        pnlLoaiSach = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_loaisach = new javax.swing.JTable();
        lblQLTL = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();

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
        setUndecorated(true);

        pnlQLTL.setBackground(new java.awt.Color(0, 204, 153));
        pnlQLTL.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        pnlContain.add(pnlTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 620, 70));

        pnlDanhSach.setBackground(new java.awt.Color(204, 255, 255));
        pnlDanhSach.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách tài liệu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        lblKetQua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblKetQua.setForeground(new java.awt.Color(0, 0, 102));
        lblKetQua.setText("Kết quả tìm kiếm \"0\"");

        tblQLTL.setModel(new javax.swing.table.DefaultTableModel(
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
        tblQLTL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblQLTLMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tblQLTL);

        javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
        pnlDanhSach.setLayout(pnlDanhSachLayout);
        pnlDanhSachLayout.setHorizontalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1058, Short.MAX_VALUE)
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

        pnlContain.add(pnlDanhSach, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 1080, 250));

        pnlLoaiTimKiem.setBackground(new java.awt.Color(204, 255, 255));
        pnlLoaiTimKiem.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Theo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        btn_timkiemRDO.add(rdoTenSach);
        rdoTenSach.setText("Tên sách");

        btn_timkiemRDO.add(rdoNXB);
        rdoNXB.setText("NXB");

        btn_timkiemRDO.add(rdoTacGia);
        rdoTacGia.setText("Tác giả");

        btn_timkiemRDO.add(rdoMaLoaiSach);
        rdoMaLoaiSach.setText("Mã loại sách");

        javax.swing.GroupLayout pnlLoaiTimKiemLayout = new javax.swing.GroupLayout(pnlLoaiTimKiem);
        pnlLoaiTimKiem.setLayout(pnlLoaiTimKiemLayout);
        pnlLoaiTimKiemLayout.setHorizontalGroup(
            pnlLoaiTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiTimKiemLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(rdoTenSach)
                .addGap(38, 38, 38)
                .addComponent(rdoNXB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(rdoTacGia)
                .addGap(38, 38, 38)
                .addComponent(rdoMaLoaiSach)
                .addGap(23, 23, 23))
        );
        pnlLoaiTimKiemLayout.setVerticalGroup(
            pnlLoaiTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiTimKiemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLoaiTimKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoMaLoaiSach)
                    .addComponent(rdoTacGia)
                    .addComponent(rdoNXB)
                    .addComponent(rdoTenSach))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pnlContain.add(pnlLoaiTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 0, 440, 70));

        pnlThongTin.setBackground(new java.awt.Color(204, 255, 255));
        pnlThongTin.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        lblGiaTien.setText("Giá Tiền");

        lblTieuDe.setText("Tiêu đề");

        cboLoaiSach.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblNgayNhapKho.setText("Ngày Nhập Kho");

        lblViTri.setText("Vị Trí Đặt Sách");

        lblNXB.setText("Nhà Xuất Bản");

        lblLoaiSach.setText("Loại Sách");

        lblTacGia.setText("Tác giả");

        lblSoTrang.setText("Số Trang");

        lblSLSach.setText("Số Lượng Sách");

        javax.swing.GroupLayout pnlThongTinLayout = new javax.swing.GroupLayout(pnlThongTin);
        pnlThongTin.setLayout(pnlThongTinLayout);
        pnlThongTinLayout.setHorizontalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(lblTieuDe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTieude, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSoTrang))
                    .addGroup(pnlThongTinLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addComponent(lblNXB)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNhaXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addComponent(lblTacGia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblSLSach))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(lblGiaTien)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlThongTinLayout.createSequentialGroup()
                        .addComponent(txtSoTrang, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(lblNgayNhapKho))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlThongTinLayout.createSequentialGroup()
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_SoLuongSach, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(txtGiaTien))
                        .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlThongTinLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(lblViTri)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinLayout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(lblLoaiSach)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cboLoaiSach, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNgayNhapKho, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                    .addComponent(txtViTri, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        pnlThongTinLayout.setVerticalGroup(
            pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinLayout.createSequentialGroup()
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNgayNhapKho)
                        .addComponent(txtNgayNhapKho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTieude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTieuDe))
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSoTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSoTrang)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtViTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblViTri))
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblNXB)
                        .addComponent(txtNhaXuatBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblSLSach)
                        .addComponent(txt_SoLuongSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cboLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtGiaTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTacGia)))
                .addGap(0, 43, Short.MAX_VALUE))
        );

        pnlContain.add(pnlThongTin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 1080, 160));

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

        btn_Home.setBackground(new java.awt.Color(0, 204, 204));
        btn_Home.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_Home.setText("Home");
        btn_Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HomeActionPerformed(evt);
            }
        });
        pnlChucNang2.add(btn_Home, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 110, 30));

        btnThoat.setBackground(new java.awt.Color(255, 0, 0));
        btnThoat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
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

        btnNew1.setText("Mới");
        btnNew1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNew1ActionPerformed(evt);
            }
        });
        pnlChucNang3.add(btnNew1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 90, 30));

        btnAdd1.setText("Thêm");
        btnAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd1ActionPerformed(evt);
            }
        });
        pnlChucNang3.add(btnAdd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 90, 30));

        btnUpdate1.setText("Sửa");
        btnUpdate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate1ActionPerformed(evt);
            }
        });
        pnlChucNang3.add(btnUpdate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 90, 30));

        btnDelete1.setText("Xóa");
        btnDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelete1ActionPerformed(evt);
            }
        });
        pnlChucNang3.add(btnDelete1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 90, 30));

        pnlContain.add(pnlChucNang3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 430, 80));

        tabs.addTab("Quản lý tài liệu", pnlContain);

        pnlQLLoaiSach.setBackground(new java.awt.Color(204, 255, 255));

        lblQLLS.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblQLLS.setForeground(new java.awt.Color(0, 153, 102));
        lblQLLS.setText("QUẢN LÍ LOẠI SÁCH");

        pnlDieuHuong1.setBackground(new java.awt.Color(204, 255, 255));
        pnlDieuHuong1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        pnlDieuHuong1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_lastLS.setText(">>|");
        btn_lastLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lastLSActionPerformed(evt);
            }
        });
        pnlDieuHuong1.add(btn_lastLS, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, 70, 30));

        btnFirstLS.setText("|<<");
        btnFirstLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstLSActionPerformed(evt);
            }
        });
        pnlDieuHuong1.add(btnFirstLS, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 70, 30));

        btnPrevLS.setText("<<");
        btnPrevLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevLSActionPerformed(evt);
            }
        });
        pnlDieuHuong1.add(btnPrevLS, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 70, 30));

        btnNextLS.setText(">>");
        btnNextLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextLSActionPerformed(evt);
            }
        });
        pnlDieuHuong1.add(btnNextLS, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 70, 30));

        pnlMaLoaiSach.setBackground(new java.awt.Color(204, 255, 255));
        pnlMaLoaiSach.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblQL_MaLoaiSach.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblQL_MaLoaiSach.setText("Mã Loại Sách");

        lblTenLoaiSachQLi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTenLoaiSachQLi.setText("Tên Loại Sách");

        javax.swing.GroupLayout pnlMaLoaiSachLayout = new javax.swing.GroupLayout(pnlMaLoaiSach);
        pnlMaLoaiSach.setLayout(pnlMaLoaiSachLayout);
        pnlMaLoaiSachLayout.setHorizontalGroup(
            pnlMaLoaiSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMaLoaiSachLayout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addGroup(pnlMaLoaiSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(lblTenLoaiSachQLi)
                    .addComponent(lblQL_MaLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67)
                .addGroup(pnlMaLoaiSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtQL_MaLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenLoaiSachQLi))
                .addContainerGap(138, Short.MAX_VALUE))
        );
        pnlMaLoaiSachLayout.setVerticalGroup(
            pnlMaLoaiSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMaLoaiSachLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlMaLoaiSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtQL_MaLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblQL_MaLoaiSach))
                .addGap(32, 32, 32)
                .addGroup(pnlMaLoaiSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenLoaiSachQLi, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTenLoaiSachQLi))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pnlChucNang.setBackground(new java.awt.Color(204, 255, 255));
        pnlChucNang.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức Năng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        btn_SuaLoaiSach.setText("Sửa");
        btn_SuaLoaiSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaLoaiSachActionPerformed(evt);
            }
        });

        btn_ThemLoaiSach.setText("Thêm");
        btn_ThemLoaiSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemLoaiSachActionPerformed(evt);
            }
        });

        btn_XoaLoaiSach.setText("Xóa");
        btn_XoaLoaiSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaLoaiSachActionPerformed(evt);
            }
        });

        btn_MoiLS.setText("Mới");
        btn_MoiLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_MoiLSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlChucNangLayout = new javax.swing.GroupLayout(pnlChucNang);
        pnlChucNang.setLayout(pnlChucNangLayout);
        pnlChucNangLayout.setHorizontalGroup(
            pnlChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChucNangLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btn_ThemLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btn_SuaLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_XoaLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_MoiLS, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );
        pnlChucNangLayout.setVerticalGroup(
            pnlChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChucNangLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(pnlChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_ThemLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_SuaLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_XoaLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_MoiLS, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pnlChucNang4.setBackground(new java.awt.Color(204, 255, 255));
        pnlChucNang4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Chức năng khác", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        pnlChucNang4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_HomeLS.setBackground(new java.awt.Color(0, 204, 204));
        btn_HomeLS.setText("Home");
        btn_HomeLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HomeLSActionPerformed(evt);
            }
        });
        pnlChucNang4.add(btn_HomeLS, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 140, 30));

        btnThoatLS.setBackground(new java.awt.Color(255, 0, 0));
        btnThoatLS.setText("Thoát");
        btnThoatLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatLSActionPerformed(evt);
            }
        });
        pnlChucNang4.add(btnThoatLS, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 130, 30));

        pnlLoaiSach.setBackground(new java.awt.Color(204, 255, 255));
        pnlLoaiSach.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh Sách Loại Sách", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        tbl_loaisach.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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
        tbl_loaisach.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbl_loaisach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_loaisachMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_loaisach);

        javax.swing.GroupLayout pnlLoaiSachLayout = new javax.swing.GroupLayout(pnlLoaiSach);
        pnlLoaiSach.setLayout(pnlLoaiSachLayout);
        pnlLoaiSachLayout.setHorizontalGroup(
            pnlLoaiSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlLoaiSachLayout.setVerticalGroup(
            pnlLoaiSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiSachLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlQLLoaiSachLayout = new javax.swing.GroupLayout(pnlQLLoaiSach);
        pnlQLLoaiSach.setLayout(pnlQLLoaiSachLayout);
        pnlQLLoaiSachLayout.setHorizontalGroup(
            pnlQLLoaiSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLLoaiSachLayout.createSequentialGroup()
                .addGroup(pnlQLLoaiSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlQLLoaiSachLayout.createSequentialGroup()
                        .addGap(453, 453, 453)
                        .addComponent(lblQLLS))
                    .addGroup(pnlQLLoaiSachLayout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(pnlQLLoaiSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLLoaiSachLayout.createSequentialGroup()
                                .addComponent(pnlLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlQLLoaiSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(pnlChucNang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pnlDieuHuong1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pnlChucNang4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(pnlMaLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        pnlQLLoaiSachLayout.setVerticalGroup(
            pnlQLLoaiSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLLoaiSachLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblQLLS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlMaLoaiSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlQLLoaiSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlQLLoaiSachLayout.createSequentialGroup()
                        .addComponent(pnlChucNang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlDieuHuong1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlChucNang4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlLoaiSach, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        tabs.addTab("Quản lí loại sách", pnlQLLoaiSach);

        pnlQLTL.add(tabs, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1100, 620));

        lblQLTL.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblQLTL.setForeground(new java.awt.Color(255, 255, 0));
        lblQLTL.setText("Quản lý tài liệu");
        pnlQLTL.add(lblQLTL, new org.netbeans.lib.awtextra.AbsoluteConstraints(476, 6, -1, 54));

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/logoLibNgang.png"))); // NOI18N
        pnlQLTL.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 147, 38));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlQLTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlQLTL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        MsgBox.confirm(this, "Bạn thật sự muốn thoát !");
        {
            this.dispose();
        }

    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btn_lastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lastActionPerformed
        last();
    }//GEN-LAST:event_btn_lastActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        FillTable_QLTlieu();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnNew1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNew1ActionPerformed
        clearForm();
    }//GEN-LAST:event_btnNew1ActionPerformed

    private void btnAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd1ActionPerformed
        if (ValidateForm()) {
            insertSach();
        }
    }//GEN-LAST:event_btnAdd1ActionPerformed

    private void btnUpdate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate1ActionPerformed
        if (ValidateForm()) {
            updateSach();
        }
    }//GEN-LAST:event_btnUpdate1ActionPerformed

    private void btnDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelete1ActionPerformed
        int selectedRow = tblQLTL.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn ít nhất một dòng để xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else {
            deleteSach();
        }
    }//GEN-LAST:event_btnDelete1ActionPerformed

    private void tblQLTLMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblQLTLMousePressed
        if (evt.getClickCount() == 1) {
            this.row = tblQLTL.rowAtPoint(evt.getPoint());
            edit();
        }
    }//GEN-LAST:event_tblQLTLMousePressed

    private void btn_ThemLoaiSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemLoaiSachActionPerformed
        if (ValidateLS()) {
            List<LoaiSach> listAll = LSDao.selectAll();
            for (LoaiSach nd : listAll) {
                if (txtQL_MaLoaiSach.getText().equals(nd.getMaLoaiSach())) {
                    MsgBox.alert(this, "Mã loại sách đã tồn tại");
                    return;
                }
            }
            insertLS();
        }
    }//GEN-LAST:event_btn_ThemLoaiSachActionPerformed

    private void btn_SuaLoaiSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaLoaiSachActionPerformed
        if (ValidateLS()) {
            updateLS();
        }
    }//GEN-LAST:event_btn_SuaLoaiSachActionPerformed

    private void btn_XoaLoaiSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaLoaiSachActionPerformed
        deleteLS();
    }//GEN-LAST:event_btn_XoaLoaiSachActionPerformed

    private void btn_MoiLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_MoiLSActionPerformed
        clearFormLoaiSach();
    }//GEN-LAST:event_btn_MoiLSActionPerformed

    private void tbl_loaisachMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_loaisachMousePressed
        if (evt.getClickCount() == 1) {
            this.rowLS = tbl_loaisach.rowAtPoint(evt.getPoint());
            editLoaiSach();
        }
    }//GEN-LAST:event_tbl_loaisachMousePressed

    private void btn_lastLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lastLSActionPerformed
        lastLS();
    }//GEN-LAST:event_btn_lastLSActionPerformed

    private void btnFirstLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstLSActionPerformed
        firstLS();
    }//GEN-LAST:event_btnFirstLSActionPerformed

    private void btnPrevLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevLSActionPerformed
        prevLS();
    }//GEN-LAST:event_btnPrevLSActionPerformed

    private void btnNextLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextLSActionPerformed
        nextLS();
    }//GEN-LAST:event_btnNextLSActionPerformed

    private void btnThoatLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatLSActionPerformed
        MsgBox.confirm(this, "Bạn thật sự muốn thoát !");
        {
            this.dispose();
        }

    }//GEN-LAST:event_btnThoatLSActionPerformed

    private void btn_HomeLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HomeLSActionPerformed
        if (Auth.isManager()) {
            new ThuVienQuanLyJFrame().setVisible(true);
            this.dispose();
        } else {
            new ThuVienUserJFrame().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btn_HomeLSActionPerformed

    private void btn_HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HomeActionPerformed
        if (Auth.isManager()) {
            new ThuVienQuanLyJFrame().setVisible(true);
            this.dispose();
        } else {
            new ThuVienUserJFrame().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btn_HomeActionPerformed

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
    private javax.swing.JButton btnFirstLS;
    private javax.swing.JButton btnNew1;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNextLS;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnPrevLS;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnThoatLS;
    private javax.swing.JButton btnUpdate1;
    private javax.swing.JButton btn_Home;
    private javax.swing.JButton btn_HomeLS;
    private javax.swing.JButton btn_MoiLS;
    private javax.swing.JButton btn_SuaLoaiSach;
    private javax.swing.JButton btn_ThemLoaiSach;
    private javax.swing.JButton btn_XoaLoaiSach;
    private javax.swing.JButton btn_last;
    private javax.swing.JButton btn_lastLS;
    private javax.swing.ButtonGroup btn_timkiemRDO;
    private javax.swing.JComboBox<String> cboLoaiSach;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblGiaTien;
    private javax.swing.JLabel lblKetQua;
    private javax.swing.JLabel lblLoaiSach;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblNXB;
    private javax.swing.JLabel lblNgayNhapKho;
    private javax.swing.JLabel lblQLLS;
    private javax.swing.JLabel lblQLTL;
    private javax.swing.JLabel lblQL_MaLoaiSach;
    private javax.swing.JLabel lblSLSach;
    private javax.swing.JLabel lblSoTrang;
    private javax.swing.JLabel lblTacGia;
    private javax.swing.JLabel lblTenLoaiSachQLi;
    private javax.swing.JLabel lblTieuDe;
    private javax.swing.JLabel lblViTri;
    private javax.swing.JPanel pnlChucNang;
    private javax.swing.JPanel pnlChucNang2;
    private javax.swing.JPanel pnlChucNang3;
    private javax.swing.JPanel pnlChucNang4;
    private javax.swing.JPanel pnlContain;
    private javax.swing.JPanel pnlDanhSach;
    private javax.swing.JPanel pnlDieuHuong;
    private javax.swing.JPanel pnlDieuHuong1;
    private javax.swing.JPanel pnlLoaiSach;
    private javax.swing.JPanel pnlLoaiTimKiem;
    private javax.swing.JPanel pnlMaLoaiSach;
    private javax.swing.JPanel pnlQLLoaiSach;
    private javax.swing.JPanel pnlQLTL;
    private javax.swing.JPanel pnlThongTin;
    private javax.swing.JPanel pnlTimKiem;
    private javax.swing.JRadioButton rdoMaLoaiSach;
    private javax.swing.JRadioButton rdoNXB;
    private javax.swing.JRadioButton rdoTacGia;
    private javax.swing.JRadioButton rdoTenSach;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblQLTL;
    private javax.swing.JTable tbl_loaisach;
    private javax.swing.JTextField txtGiaTien;
    private javax.swing.JTextField txtNgayNhapKho;
    private javax.swing.JTextField txtNhaXuatBan;
    private javax.swing.JTextField txtQL_MaLoaiSach;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSoTrang;
    private javax.swing.JTextField txtTacGia;
    private javax.swing.JTextField txtTenLoaiSachQLi;
    private javax.swing.JTextField txtTieude;
    private javax.swing.JTextField txtViTri;
    private javax.swing.JTextField txt_SoLuongSach;
    // End of variables declaration//GEN-END:variables
}
