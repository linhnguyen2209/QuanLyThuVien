package quanlythuvien.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;
import quanlythuvien.dao.PhieuMuonChiTietDAO;
import quanlythuvien.dao.PhieuMuonDAO;
import quanlythuvien.dao.PhieuTraDAO;
import quanlythuvien.dao.SachDAO;
import quanlythuvien.entity.PhieuMuon;
import quanlythuvien.entity.PhieuMuonChiTiet;
import quanlythuvien.entity.PhieuTra;
import quanlythuvien.entity.Sach;
import quanlythuvien.utils.Auth;
import quanlythuvien.utils.MsgBox;
import quanlythuvien.utils.ValidatorForm;
import quanlythuvien.utils.XDate;
import quanlythuvien.utils.XImage;

/**
 *
 * @author thuon
 */
public class QuanLyMuonTraJDialog extends javax.swing.JDialog {

    PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO();
    PhieuTraDAO phieuTraDAO = new PhieuTraDAO();
    SachDAO sDAO = new SachDAO();
    PhieuMuonChiTietDAO phieuMuonChiTietDAO = new PhieuMuonChiTietDAO();
    List<PhieuMuonChiTiet> listCTPM;
    List<PhieuMuon> listPM;
    DefaultListModel modelSachKho, modelSachChon;
    DefaultTableModel tblModelPMCT;
    int row = -1;
    int indexPMCT = -1;
    int soSachMuonBanDau = 0;

    public QuanLyMuonTraJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    void init() {
        this.setLocation(318, 73);
        this.setTitle("Quản lý phiếu mượn");
        this.setIconImage(XImage.getAppIcon());
        fillTablePhieuMuon();
        fillTablePhieuTra();
        fillJlistSach();
        updateStatus();
    }

    void fillTablePhieuMuon() {
        DefaultTableModel model = (DefaultTableModel) tblPhieuMuon.getModel();
        model.setRowCount(0);
        try {
            listPM = phieuMuonDAO.selectAll();
            for (PhieuMuon phieuMuon : listPM) {
                Object[] row = {phieuMuon.getMaPhieuMuon(), XDate.toString(phieuMuon.getNgayMuon(), "yyyy/MM/dd"),
                    XDate.toString(phieuMuon.getNgayHenTra(), "yyyy/MM/dd"), phieuMuon.getTongSoLuongSachMuon(), phieuMuon.getMaNguoiDung(), phieuMuon.getGhiChu()};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setForm(PhieuMuon model) {
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

    PhieuMuon getForm() {
        PhieuMuon model = new PhieuMuon();
        if (row >= 0) {
            model.setMaPhieuMuon(Integer.parseInt(tblPhieuMuon.getValueAt(row, 0) + ""));
        }
        model.setNgayMuon(new Date());
        model.setNgayHenTra(XDate.add(3));
        model.setTongSoLuongSachMuon(Integer.parseInt(txtTongSoLuongSachMuon.getText()));
        model.setMaNguoiDung(txtMaNguoiDung.getText());
        model.setGhiChu(txtGhiChu.getText());

        return model;
    }

    void clearForm() {
        tblPhieuMuon.clearSelection();
        txtMaPhieuMuon.setText("");
        txtNgayMuon.setText("");
        txtNgayHenTra.setText("");
        txtTongSoLuongSachMuon.setText(0 + "");
        txtMaNguoiDung.setText("");
        txtGhiChu.setText("");
        this.row = -1;
        updateStatus();
        clearFormTra();
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

        //form p mượn chi tiết
        txtMaPhieuMuon_PMCT.setEditable(false);
        //form p trả
        txtMaDocGia_Tra.setEditable(false);
        txtMaPhieuMuon_Tra.setEditable(false);
        //form p mượn
        txtNgayHenTra.setEditable(false);
        txtNgayMuon.setEditable(false);
        txtTongSoLuongSachMuon.setEditable(false);
        txtMaPhieuMuon.setEditable(false);
        txtMaNguoiDung.setEditable(edit);
        //Khi insert thì không update, delete
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);

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

    void insert() {
        PhieuMuon pm = getForm();

        try {
            phieuMuonDAO.insert(pm);
            fillTablePhieuMuon();
            clearForm();
            MsgBox.alert(this, "Thêm thành công");
            tblPhieuMuon.setRowSelectionInterval(listPM.size() - 1, listPM.size() - 1);
            txtMaPhieuMuon_PMCT.setText(tblPhieuMuon.getValueAt(listPM.size() - 1, 0) + "");
            fillTableChiTietPhieuMuon();
            fillTablePhieuTra();
            clearFormPMCT();
            tabs.setSelectedIndex(1);
            MsgBox.alert(this, "Vui lòng thêm phiếu mượn chi tiết!");

        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Thêm thất bại!");
        }
    }

    void update() {
        PhieuMuon pm = getForm();
        try {
            phieuMuonDAO.update(pm);
            fillTablePhieuMuon();
            fillTablePhieuTra();
            MsgBox.alert(this, "Cập nhật thành công");
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alert(this, "Cập nhật thất bại!");
        }
    }

    void delete() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền xoá!");
        } else {
            try {
                if (MsgBox.confirm(this, "Bạn thực sự muốn xoá phiếu mượn này?")) {
                    String maPM = txtMaPhieuMuon.getText();
                    Integer maNDintInteger = Integer.parseInt(maPM);
                    phieuMuonDAO.delete(maNDintInteger);
                    this.fillTablePhieuMuon();
                    fillTableChiTietPhieuMuon();
                    fillTablePhieuTra();
                    clearForm();
                    clearFormPMCT();
                    clearFormTra();
                    MsgBox.alert(this, "Xoá thành công!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.alert(this, "Xoá thất bại!");
            }
        }
    }

    // form pmct
    void fillJlistSach() {
        modelSachKho = new DefaultListModel();
        modelSachKho.removeAllElements();
        List<Sach> listSach = new ArrayList<>();
        String key = txtTimKiemMaSach.getText();
        if (!key.equals("")) {
            Sach s = sDAO.selectById(Integer.valueOf(key));
            if (s != null) {
                listSach.add(s);
            } else {
                listSach.clear();
            }
        } else {
            listSach = sDAO.selectAll();
        }
        for (Sach sach : listSach) {
            modelSachKho.addElement(sach.getMaSach());
        }
        listSachTrongKho.setModel(modelSachKho);
        modelSachChon = new DefaultListModel();
        listSachChon.setModel(modelSachChon);
    }

    void chonSachMuon() {
        if (listSachTrongKho.isSelectionEmpty()) {
            MsgBox.alert(this, "Vui lòng chọn sách!");
        } else {
            Object[] values = listSachTrongKho.getSelectedValues();
            for (Object value : values) {
                if (sDAO.selectById(Integer.parseInt(value + "")).getSoLuongSach() != 0) {
                    modelSachChon.addElement(value);
                    txtTongSachChon.setText(modelSachChon.size() + "");
                } else {
                    MsgBox.alert(this, "Sách có mã " + value + "(đã hết)");
                }
            }
            listSachTrongKho.clearSelection();
        }
    }

    void huyChonSachMuon() {
        if (listSachChon.isSelectionEmpty()) {
            MsgBox.alert(this, "Vui lòng chọn sách hủy mượn!");
        } else {
            Object[] values = listSachChon.getSelectedValues();
            for (Object value : values) {
                modelSachChon.removeElement(value);
            }
        }
    }

    void huyChonTatCaSachMuon() {
        modelSachChon.removeAllElements();
    }

    void fillTableChiTietPhieuMuon() {
        tblModelPMCT = (DefaultTableModel) tblChiTietPhieuMuon.getModel();
        tblModelPMCT.setRowCount(0);
        listCTPM = phieuMuonChiTietDAO.selectBy_IDPM(Integer.parseInt(txtMaPhieuMuon_PMCT.getText()));
        System.out.println(listCTPM.size());
        for (PhieuMuonChiTiet pmct : listCTPM) {
            Object[] row = {pmct.getMaChiTietPhieuMuon(), pmct.getMaPhieuMuon(), pmct.getMaSach(), pmct.getSlSachMuonMoiLoai()};
            tblModelPMCT.addRow(row);
        }
    }

    void showDetailPhieuMuonChiTiet() {
        PhieuMuonChiTiet model = phieuMuonChiTietDAO.selectById(Integer.parseInt(tblChiTietPhieuMuon.getValueAt(indexPMCT, 0) + ""));
        modelSachChon.removeAllElements();
        for (int i = 1; i <= model.getSlSachMuonMoiLoai(); i++) {// nếu mượn 1 quyển với số lượng nhiều hơn 1 thì add nhiều
            modelSachChon.addElement(model.getMaSach());
        }
        txtTongSachChon.setText(model.getSlSachMuonMoiLoai() + "");
        txtMaPhieuMuonChiTiet_PMCT.setText(model.getMaChiTietPhieuMuon() + "");
    }

    PhieuMuonChiTiet checkMaSachDaTonTai(int maSach) { // kiểm tra xem sách này đã đc mượn để set lại số lượng nếu mượn r thì trả về pmct để tăng số lượng else trả về null
        if (listCTPM == null) {
            return null;
        }
        for (PhieuMuonChiTiet pmct : listCTPM) {
            if (maSach == pmct.getMaSach()) {
                return pmct;
            }
        }
        return null;
    }

    void clearFormPMCT() {
        txtMaPhieuMuonChiTiet_PMCT.setText("");
        txtTongSachChon.setText("");
        modelSachChon.removeAllElements();
        tblChiTietPhieuMuon.clearSelection();
        listSachTrongKho.clearSelection();
        listSachChon.clearSelection();
        indexPMCT = -1;
        updateStatusPMCT();
    }

    void insertPMCT() {
        PhieuMuonChiTiet pmct = new PhieuMuonChiTiet();
        Object[] maSachMuonObject = modelSachChon.toArray();
        // Chuyển đổi từ Object[] sang Integer[]
        Integer[] maSachMuon = Arrays.copyOf(maSachMuonObject, maSachMuonObject.length, Integer[].class);
        Arrays.sort(maSachMuon); // sắp xếp lại để lấy cho đúng số lượng sách giống nhau
        if (maSachMuon.length > 0) {
            try {
                for (int i = 0; i < maSachMuon.length; i++) {
                    pmct.setMaPhieuMuon(Integer.parseInt(txtMaPhieuMuon_PMCT.getText()));
                    pmct.setMaSach(maSachMuon[i]);
                    PhieuMuonChiTiet pMCT = checkMaSachDaTonTai(maSachMuon[i]);
                    if (pMCT != null) { // nếu có trong bảng r thì tăng số lượng lên th
                        pmct.setMaChiTietPhieuMuon(pMCT.getMaChiTietPhieuMuon());
                        pmct.setSlSachMuonMoiLoai(pMCT.getSlSachMuonMoiLoai() + 1);
                        phieuMuonChiTietDAO.update(pmct);
                    } else {
                        pmct.setSlSachMuonMoiLoai(1);
                        phieuMuonChiTietDAO.insert(pmct);
                    }
                    fillTableChiTietPhieuMuon();
                    // update số lượng sách trong kho
                    Sach sach = sDAO.selectById(maSachMuon[i]);
                    sach.setSoLuongSach(sach.getSoLuongSach() - 1);
                    sDAO.update(sach);
                }
                // set lại số lượng cho pm
                PhieuMuon pm = phieuMuonDAO.selectById(Integer.parseInt(txtMaPhieuMuon_PMCT.getText()));
                int tong = 0;
                for (PhieuMuonChiTiet pmct1 : listCTPM) {
                    tong += pmct1.getSlSachMuonMoiLoai();
                }
                pm.setTongSoLuongSachMuon(tong);
                phieuMuonDAO.update(pm);
                fillTablePhieuMuon();
                updateStatusPMCT();
                MsgBox.alert(this, "Thêm thành công");

            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.alert(this, "Thêm thất bại!");
            }
        } else {
            MsgBox.alert(this, "Vui lòng chọn sách muốn mượn!");
        }
    }

    void updatePMCT() {
        if (indexPMCT >= 0) {
            PhieuMuonChiTiet pMCT = phieuMuonChiTietDAO.selectById(Integer.parseInt(tblChiTietPhieuMuon.getValueAt(indexPMCT, 0) + ""));
            if (modelSachChon.size() <= 0) {
                MsgBox.alert(this, "Vui lòng chọn sách muốn sửa!");
            } else if (txtTongSachChon.getText().equals("") || Integer.parseInt(txtTongSachChon.getText()) == 0) {
                MsgBox.alert(this, "Vui lòng nhập số lượng muốn sửa!");
            } else {
                if (modelSachChon.size() > 1) {
                    MsgBox.alert(this, "Mỗi phiếu chỉ được update 1 sách!");
                } else {
                    try {
                        pMCT.setSlSachMuonMoiLoai(Integer.parseInt(txtTongSachChon.getText()));
                        pMCT.setMaSach((int) modelSachChon.get(0));
                        phieuMuonChiTietDAO.update(pMCT);
                        fillTableChiTietPhieuMuon();
                        // update số lượng sách trong kho
                        int maSach = Integer.parseInt(tblChiTietPhieuMuon.getValueAt(indexPMCT, 2) + "");
                        Sach sach = sDAO.selectById(maSach);
                        if (soSachMuonBanDau < Integer.parseInt(txtTongSachChon.getText())) {
                            sach.setSoLuongSach(sach.getSoLuongSach() + Integer.parseInt(txtTongSachChon.getText()) - soSachMuonBanDau);
                        } else if (soSachMuonBanDau > Integer.parseInt(txtTongSachChon.getText())) {
                            sach.setSoLuongSach(sach.getSoLuongSach() + soSachMuonBanDau - Integer.parseInt(txtTongSachChon.getText()));
                        } else {
                            sach.setSoLuongSach(sach.getSoLuongSach());
                        }
                        sDAO.update(sach);
                        // set lại số lượng cho pm
                        PhieuMuon pm = phieuMuonDAO.selectById(Integer.parseInt(txtMaPhieuMuon_PMCT.getText()));
                        int tong = 0;
                        for (PhieuMuonChiTiet pmct1 : listCTPM) {
                            tong += pmct1.getSlSachMuonMoiLoai();
                        }
                        pm.setTongSoLuongSachMuon(tong);
                        phieuMuonDAO.update(pm);
                        fillTablePhieuMuon();
                        MsgBox.alert(this, "Cập nhật thành công");
                    } catch (Exception e) {
                        e.printStackTrace();
                        MsgBox.alert(this, "Cập nhật thất bại!");
                    }
                }
            }
        } else {
            MsgBox.alert(this, "Vui lòng chọn dòng muốn sửa");
        }
    }

    void deletePMCT() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền xoá!");
        } else {
            if (indexPMCT < 0) {
                MsgBox.alert(this, "Bạn chưa chọn dòng để xóa");
            } else {
                try {
                    if (MsgBox.confirm(this, "Bạn thực sự muốn xoá phiếu này?")) {
                        String maPMCT = txtMaPhieuMuonChiTiet_PMCT.getText();
                        Integer maPMCT_Int = Integer.parseInt(maPMCT);
                        phieuMuonChiTietDAO.delete(maPMCT_Int);

                        // cập nhật số lượng sách trong kho
                        int maSach = Integer.parseInt(tblChiTietPhieuMuon.getValueAt(indexPMCT, 2) + "");
                        Sach s = sDAO.selectById(maSach);
                        s.setSoLuongSach(s.getSoLuongSach() + Integer.parseInt(tblChiTietPhieuMuon.getValueAt(indexPMCT, 3) + ""));
                        sDAO.update(s);
                        PhieuMuon pm = phieuMuonDAO.selectById(Integer.parseInt(txtMaPhieuMuon_PMCT.getText()));
                        this.fillTableChiTietPhieuMuon();
                        this.clearFormPMCT();
// set lại số lượng cho pm
                        int tong = 0;
                        for (PhieuMuonChiTiet pmct1 : listCTPM) {
                            tong += pmct1.getSlSachMuonMoiLoai();
                        }
                        pm.setTongSoLuongSachMuon(tong);
                        phieuMuonDAO.update(pm);
                        fillTablePhieuMuon();
                        updateStatusPMCT();
                        MsgBox.alert(this, "Xoá thành công!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    MsgBox.alert(this, "Xoá thất bại!");
                }
            }
        }
    }

    void updateStatusPMCT() {
        boolean edit = this.indexPMCT >= 0;
        boolean first = this.indexPMCT == 0;
        boolean last = this.indexPMCT == tblPhieuMuon.getRowCount() - 1;

        //Khi insert thì không update, delete
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);

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
        if (indexPMCT < tblPhieuMuon.getRowCount() - 1) {
            indexPMCT++;
            tblChiTietPhieuMuon.setRowSelectionInterval(indexPMCT, indexPMCT);
            showDetailPhieuMuonChiTiet();
        }
    }

    private void lastCTPM() {
        indexPMCT = tblPhieuMuon.getRowCount() - 1;
        tblChiTietPhieuMuon.setRowSelectionInterval(indexPMCT, indexPMCT);
        showDetailPhieuMuonChiTiet();
    }

    // form p trả
    void fillTablePhieuTra() {
        DefaultTableModel model = (DefaultTableModel) tblPhieuTra.getModel();
        model.setRowCount(0);
        try {
            List<PhieuTra> list = phieuTraDAO.selectAll();
            for (PhieuTra phieuTra : list) {
                // kiểm tra ngày trả nếu phiếu trả vừa được tạo hoặc chưa trả thì sẽ lấy ngày hiện tại là ngày trả để khi update trạng thái không cần update ngày và tránh lỗi null
                Date date;
                if (phieuTra.getNgayTra() == null) {
                    date = new Date();
                } else {
                    date = phieuTra.getNgayTra();
                }
                Object[] row = {phieuTra.getMaPhieuTra(), phieuTra.getMaPhieuMuon(), XDate.toString(date, "yyyy/MM/dd"),
                    phieuTra.isTrangThai() ? "Đã trả" : "Chưa trả", phieuTra.getMaNguoiDung(), phieuTra.getGhiChu()};
                model.addRow(row);
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

    PhieuTra getForm_PhieuTra() {
        PhieuTra ptr = new PhieuTra();
        ptr.setMaPhieuTra(Integer.parseInt(txtMaPhieuTra_Tra.getText()));
        ptr.setMaPhieuMuon(Integer.parseInt(txtMaPhieuMuon_Tra.getText()));
        ptr.setMaNguoiDung(txtMaDocGia_Tra.getText());
        ptr.setNgayTra(XDate.toDate(txtNgayTra_Tra.getText(), "yyyy-MM-dd"));
        ptr.setGhiChu(txaGhiChu_Tra.getText());
        ptr.setTrangThai(chkDaTra_Tra.isSelected());
        return ptr;
    }

    boolean validateForm() {
        StringBuilder sb = new StringBuilder();
        ValidatorForm.isDate(txtNgayTra_Tra, sb, "Vui lòng nhập đúng định dạng ngày yyyy-MM-dd");
        if (sb.length() > 0) {
            MsgBox.alert(this, sb.toString());
            return false;
        }
        return true;
    }

    void clearFormTra() {
        fillTablePhieuTra();
        txtMaPhieuMuon_Tra.setText("");
        txtNgayTra_Tra.setText("");
        txtMaDocGia_Tra.setText("");
        txaGhiChu_Tra.setText("");
        chkDaTra_Tra.setSelected(false);
        tblPhieuTra.clearSelection();
        row = -1;
    }

    void updatePhieuTra() {
        if (row > 0) {
            if (validateForm()) {
                PhieuTra ptr = getForm_PhieuTra();
                try {
                    phieuTraDAO.update(ptr);
                    fillTablePhieuTra();
                    MsgBox.alert(this, "Cập nhật thành công!");
                } catch (Exception e) {
                    e.printStackTrace();
                    MsgBox.alert(this, "Cập nhật thất bại!");
                }
            }
        } else {
            MsgBox.alert(this, "Vui lòng chọn phiếu trả muốn cập nhật!");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        pnlPhieuMuon = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
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
        jPanel5 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnXemPMChiTiet = new javax.swing.JButton();
        btnXemPhieuTra = new javax.swing.JButton();
        pnlChiTietPM = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaPhieuMuon_PMCT = new javax.swing.JTextField();
        btnXemPhieuMuon = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtMaPhieuMuonChiTiet_PMCT = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtTimKiemMaSach = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listSachChon = new javax.swing.JList<>();
        btnAllRight = new javax.swing.JButton();
        btnRight = new javax.swing.JButton();
        btnLeft = new javax.swing.JButton();
        btnAllLeft = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        listSachTrongKho = new javax.swing.JList<>();
        btnReset = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTongSachChon = new javax.swing.JTextField();
        pnChucNang = new javax.swing.JPanel();
        btnThemPMCT = new javax.swing.JButton();
        btnXoaPMCT = new javax.swing.JButton();
        btnSuaPMCT = new javax.swing.JButton();
        btnMoiPMCT = new javax.swing.JButton();
        btnFirstPMCT = new javax.swing.JButton();
        btnNextPMCT = new javax.swing.JButton();
        btnPrevPMCT = new javax.swing.JButton();
        btnLastPMCT = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblChiTietPhieuMuon = new javax.swing.JTable();
        pnlTraSach = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPhieuTra = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        btnNew_Tra = new javax.swing.JButton();
        btnUpdate_Tra = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnHome_Tra = new javax.swing.JButton();
        btnThoat_Tra = new javax.swing.JButton();
        btnXemPhieuMuon_Tra = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabs.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tabsAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                tabsAncestorMoved(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        tabs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabsMouseClicked(evt);
            }
        });

        pnlPhieuMuon.setBackground(new java.awt.Color(255, 255, 255));
        pnlPhieuMuon.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnFirst.setText("|<<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setText(">>|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnlPhieuMuon.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 1080, 80));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin"));

        jLabel7.setText("Mã Đọc giả:");

        jLabel8.setText("Ghi chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane3.setViewportView(txtGhiChu);

        jLabel6.setText("Tổng số lượng:");

        jLabel5.setText("Ngày hẹn trả");

        jLabel4.setText("Ngày mượn");

        jLabel3.setText("Mã Phiếu mượn");

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

        pnlPhieuMuon.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 318, 1080, 150));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách"));

        tblPhieuMuon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã PM", "Ngày mượn", "Ngày hẹn trả", "SL mượn", "Mã Đọc giả", "Ghi chú"
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
        if (tblPhieuMuon.getColumnModel().getColumnCount() > 0) {
            tblPhieuMuon.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblPhieuMuon.getColumnModel().getColumn(3).setPreferredWidth(40);
            tblPhieuMuon.getColumnModel().getColumn(4).setPreferredWidth(40);
        }

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtSearch.setForeground(new java.awt.Color(153, 153, 153));
        txtSearch.setText("Tìm kiếm phiếu...");
        txtSearch.setBorder(null);
        txtSearch.setMinimumSize(new java.awt.Dimension(5, 20));
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSearchFocusLost(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnSearch.setBackground(new java.awt.Color(0, 204, 204));
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 204));
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/loupe.png"))); // NOI18N
        btnSearch.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnXemPMChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXemPMChiTiet.setText("XEM PHIẾU MƯỢN CHI TIẾT");
        btnXemPMChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemPMChiTietActionPerformed(evt);
            }
        });

        btnXemPhieuTra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXemPhieuTra.setText("XEM PHIẾU TRẢ");
        btnXemPhieuTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemPhieuTraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1058, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnXemPhieuTra, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXemPMChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXemPMChiTiet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXemPhieuTra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pnlPhieuMuon.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 6, 1080, 310));

        tabs.addTab("Quản lý Mượn", pnlPhieuMuon);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin mượn sách"));

        jLabel2.setText("Mã Phiếu mượn:");

        txtMaPhieuMuon_PMCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaPhieuMuon_PMCTActionPerformed(evt);
            }
        });

        btnXemPhieuMuon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXemPhieuMuon.setText("Xem phiếu mượn");
        btnXemPhieuMuon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemPhieuMuonActionPerformed(evt);
            }
        });

        jLabel12.setText("Mã Phiếu mượn chi tiết:");

        txtMaPhieuMuonChiTiet_PMCT.setEditable(false);
        txtMaPhieuMuonChiTiet_PMCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaPhieuMuonChiTiet_PMCTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXemPhieuMuon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMaPhieuMuon_PMCT)
                    .addComponent(txtMaPhieuMuonChiTiet_PMCT)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel12))
                        .addGap(0, 211, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaPhieuMuon_PMCT, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaPhieuMuonChiTiet_PMCT, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXemPhieuMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Chi Tiết Phiếu Mượn"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        txtTimKiemMaSach.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemMaSachKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiemMaSach, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(txtTimKiemMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 18, 350, 60));

        jLabel15.setText("Sách trong kho:");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 81, -1, -1));

        listSachChon.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(listSachChon);

        jPanel2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, 120, 110));

        btnAllRight.setText(">>|");
        btnAllRight.setEnabled(false);
        btnAllRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllRightActionPerformed(evt);
            }
        });
        jPanel2.add(btnAllRight, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 103, 63, -1));

        btnRight.setText(">");
        btnRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRightActionPerformed(evt);
            }
        });
        jPanel2.add(btnRight, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 132, 63, -1));

        btnLeft.setText("<");
        btnLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeftActionPerformed(evt);
            }
        });
        jPanel2.add(btnLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 161, 63, -1));

        btnAllLeft.setText("|<<");
        btnAllLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllLeftActionPerformed(evt);
            }
        });
        jPanel2.add(btnAllLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(163, 190, 65, -1));

        listSachTrongKho.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(listSachTrongKho);

        jPanel2.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 103, 140, 110));

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        jPanel2.add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));

        jLabel9.setText("Sách mượn:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, -1, -1));

        jLabel10.setText("Tổng sách:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 220, 70, 20));
        jPanel2.add(txtTongSachChon, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 120, -1));

        pnChucNang.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức Năng"));

        btnThemPMCT.setText("Thêm");
        btnThemPMCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemPMCTActionPerformed(evt);
            }
        });

        btnXoaPMCT.setText("Xóa");
        btnXoaPMCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaPMCTActionPerformed(evt);
            }
        });

        btnSuaPMCT.setText("Sửa");
        btnSuaPMCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaPMCTActionPerformed(evt);
            }
        });

        btnMoiPMCT.setText("Mới");
        btnMoiPMCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiPMCTActionPerformed(evt);
            }
        });

        btnFirstPMCT.setText("|<");
        btnFirstPMCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstPMCTActionPerformed(evt);
            }
        });

        btnNextPMCT.setText(">>");
        btnNextPMCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextPMCTActionPerformed(evt);
            }
        });

        btnPrevPMCT.setText("<<");
        btnPrevPMCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevPMCTActionPerformed(evt);
            }
        });

        btnLastPMCT.setText(">|");
        btnLastPMCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastPMCTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnChucNangLayout = new javax.swing.GroupLayout(pnChucNang);
        pnChucNang.setLayout(pnChucNangLayout);
        pnChucNangLayout.setHorizontalGroup(
            pnChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnChucNangLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(btnFirstPMCT, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThemPMCT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnSuaPMCT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXoaPMCT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnMoiPMCT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnChucNangLayout.createSequentialGroup()
                            .addComponent(btnPrevPMCT, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnNextPMCT, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLastPMCT, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        pnChucNangLayout.setVerticalGroup(
            pnChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnChucNangLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnThemPMCT)
                .addGap(18, 18, 18)
                .addComponent(btnSuaPMCT)
                .addGap(18, 18, 18)
                .addComponent(btnXoaPMCT)
                .addGap(18, 18, 18)
                .addComponent(btnMoiPMCT)
                .addGap(18, 18, 18)
                .addGroup(pnChucNangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirstPMCT)
                    .addComponent(btnNextPMCT)
                    .addComponent(btnPrevPMCT)
                    .addComponent(btnLastPMCT))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách"));

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

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlChiTietPMLayout = new javax.swing.GroupLayout(pnlChiTietPM);
        pnlChiTietPM.setLayout(pnlChiTietPMLayout);
        pnlChiTietPMLayout.setHorizontalGroup(
            pnlChiTietPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChiTietPMLayout.createSequentialGroup()
                .addGroup(pnlChiTietPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlChiTietPMLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlChiTietPMLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlChiTietPMLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(pnChucNang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlChiTietPMLayout.setVerticalGroup(
            pnlChiTietPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChiTietPMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlChiTietPMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlChiTietPMLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnChucNang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(256, 256, 256)
                .addComponent(jLabel11)
                .addContainerGap())
        );

        tabs.addTab("Chi tiết phiếu mượn", pnlChiTietPM);

        pnlTraSach.setBackground(new java.awt.Color(255, 255, 255));
        pnlTraSach.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setLayout(new java.awt.GridLayout(1, 4, 5, 5));
        pnlTraSach.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 532, 401, -1));

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Chi tiết"));

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

        jLabel23.setText("Mã Phiếu Trả");

        txtMaPhieuTra_Tra.setEditable(false);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel17)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaPhieuMuon_Tra)
                    .addComponent(txtNgayTra_Tra)
                    .addComponent(chkDaTra_Tra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(txtMaPhieuTra_Tra, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaDocGia_Tra)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txtMaDocGia_Tra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaPhieuTra_Tra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(txtMaPhieuMuon_Tra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(txtNgayTra_Tra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chkDaTra_Tra)
                            .addComponent(jLabel22))))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pnlTraSach.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 670, 210));

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

        pnlTraSach.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 16, 1040, 240));

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));

        btnNew_Tra.setText("New");
        btnNew_Tra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNew_TraActionPerformed(evt);
            }
        });

        btnUpdate_Tra.setText("Update");
        btnUpdate_Tra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate_TraActionPerformed(evt);
            }
        });

        btnHome_Tra.setText("Home");
        btnHome_Tra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHome_TraActionPerformed(evt);
            }
        });

        btnThoat_Tra.setText("Thoát");
        btnThoat_Tra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoat_TraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(btnUpdate_Tra, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNew_Tra, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(btnHome_Tra, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnThoat_Tra, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNew_Tra)
                    .addComponent(btnUpdate_Tra))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHome_Tra)
                    .addComponent(btnThoat_Tra))
                .addGap(25, 25, 25))
        );

        pnlTraSach.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 320, 370, 210));

        btnXemPhieuMuon_Tra.setBackground(new java.awt.Color(204, 255, 255));
        btnXemPhieuMuon_Tra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXemPhieuMuon_Tra.setText("XEM PHIẾU MƯỢN");
        btnXemPhieuMuon_Tra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemPhieuMuon_TraActionPerformed(evt);
            }
        });
        pnlTraSach.add(btnXemPhieuMuon_Tra, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 270, 150, 30));

        tabs.addTab("Quản Lý Trả", pnlTraSach);

        getContentPane().add(tabs, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 1090, 590));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 153, 255));
        jLabel18.setText("Quản lý mượn trả");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 230, 50));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/quanlythuvien/icon/logoLibNgang.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

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

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void txtMaPhieuMuon_PMCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaPhieuMuon_PMCTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaPhieuMuon_PMCTActionPerformed

    private void btnAllRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllRightActionPerformed
        Object[] allRight = modelSachKho.toArray();
        for (Object object : allRight) {
            modelSachChon.addElement(object);
        }
        modelSachKho.removeAllElements();
    }//GEN-LAST:event_btnAllRightActionPerformed

    private void btnRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRightActionPerformed
        chonSachMuon();
    }//GEN-LAST:event_btnRightActionPerformed

    private void btnLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeftActionPerformed
        huyChonSachMuon();
    }//GEN-LAST:event_btnLeftActionPerformed

    private void btnAllLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllLeftActionPerformed
        huyChonTatCaSachMuon();
    }//GEN-LAST:event_btnAllLeftActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        fillJlistSach();
        txtTongSachChon.setText("");
    }//GEN-LAST:event_btnResetActionPerformed

    private void txtTimKiemMaSachKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemMaSachKeyReleased
        fillJlistSach();
    }//GEN-LAST:event_txtTimKiemMaSachKeyReleased

    private void tblPhieuTraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuTraMouseClicked
        row = tblPhieuTra.getSelectedRow();
        if (row >= 0) {
            showDetailPhieuTra();
            tblPhieuMuon.setRowSelectionInterval(row, row);
            edit();
        }
    }//GEN-LAST:event_tblPhieuTraMouseClicked

    private void btnNew_TraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNew_TraActionPerformed
        clearFormTra();
    }//GEN-LAST:event_btnNew_TraActionPerformed

    private void btnUpdate_TraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate_TraActionPerformed
        updatePhieuTra();
    }//GEN-LAST:event_btnUpdate_TraActionPerformed

    private void btnHome_TraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHome_TraActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnHome_TraActionPerformed

    private void btnThoat_TraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoat_TraActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnThoat_TraActionPerformed

    private void txtSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusGained
        if (txtSearch.getText().equals("Tìm kiếm phiếu...")) {
            txtSearch.setText("");
        }
    }//GEN-LAST:event_txtSearchFocusGained

    private void txtSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSearchFocusLost
        if (txtSearch.getText().equals("")) {
            txtSearch.setText("Tìm kiếm phiếu...");
        }
    }//GEN-LAST:event_txtSearchFocusLost

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        //        if (cboLuaChon.getSelectedIndex() != 0) {
        //            fillTableSach();
        //        } else {
        //            MsgBox.alert(this, "Vui lòng chọn loại tìm kiếm");
        //        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed

    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnXemPMChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemPMChiTietActionPerformed
        if (row < 0) {
            MsgBox.alert(this, "Vui lòng chọn phiếu mượn muốn xem");
        } else {
            fillTableChiTietPhieuMuon();
            tabs.setSelectedIndex(1);
        }
    }//GEN-LAST:event_btnXemPMChiTietActionPerformed

    private void btnXemPhieuMuonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemPhieuMuonActionPerformed
        tabs.setSelectedIndex(0);
    }//GEN-LAST:event_btnXemPhieuMuonActionPerformed

    private void tabsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabsMouseClicked
        if (tabs.getSelectedIndex() == 1 && txtMaPhieuMuon_PMCT.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng chọn phiếu mượn cần xem!");
            tabs.setSelectedIndex(0);
        }
    }//GEN-LAST:event_tabsMouseClicked

    private void tblChiTietPhieuMuonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietPhieuMuonMouseClicked
        indexPMCT = tblChiTietPhieuMuon.getSelectedRow();
        if (indexPMCT >= 0) {
            soSachMuonBanDau = Integer.parseInt(tblChiTietPhieuMuon.getValueAt(indexPMCT, 3) + "");
            showDetailPhieuMuonChiTiet();
        }
    }//GEN-LAST:event_tblChiTietPhieuMuonMouseClicked

    private void btnThemPMCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemPMCTActionPerformed
        insertPMCT();
    }//GEN-LAST:event_btnThemPMCTActionPerformed

    private void btnXoaPMCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaPMCTActionPerformed
        deletePMCT();
    }//GEN-LAST:event_btnXoaPMCTActionPerformed

    private void btnSuaPMCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaPMCTActionPerformed
        updatePMCT();
    }//GEN-LAST:event_btnSuaPMCTActionPerformed

    private void txtMaPhieuMuonChiTiet_PMCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaPhieuMuonChiTiet_PMCTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaPhieuMuonChiTiet_PMCTActionPerformed

    private void btnMoiPMCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiPMCTActionPerformed
        clearFormPMCT();
    }//GEN-LAST:event_btnMoiPMCTActionPerformed

    private void tabsAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tabsAncestorAdded

    }//GEN-LAST:event_tabsAncestorAdded

    private void tabsAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tabsAncestorMoved

    }//GEN-LAST:event_tabsAncestorMoved

    private void btnXemPhieuTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemPhieuTraActionPerformed
        if (row < 0) {
            MsgBox.alert(this, "Vui lòng chọn phiếu mượn muốn xem");
        } else {
            tabs.setSelectedIndex(2);
        }
    }//GEN-LAST:event_btnXemPhieuTraActionPerformed

    private void btnXemPhieuMuon_TraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemPhieuMuon_TraActionPerformed
        if (row < 0) {
            MsgBox.alert(this, "Vui lòng chọn phiếu trả muốn xem");
        } else {
            tabs.setSelectedIndex(0);
        }
    }//GEN-LAST:event_btnXemPhieuMuon_TraActionPerformed

    private void btnFirstPMCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstPMCTActionPerformed
        firstCTPM();
    }//GEN-LAST:event_btnFirstPMCTActionPerformed

    private void btnPrevPMCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevPMCTActionPerformed
        prevCTPM();
    }//GEN-LAST:event_btnPrevPMCTActionPerformed

    private void btnNextPMCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextPMCTActionPerformed
        nextCTPM();
    }//GEN-LAST:event_btnNextPMCTActionPerformed

    private void btnLastPMCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastPMCTActionPerformed
        lastCTPM();
    }//GEN-LAST:event_btnLastPMCTActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyMuonTraJDialog dialog = new QuanLyMuonTraJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAllLeft;
    private javax.swing.JButton btnAllRight;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnFirstPMCT;
    private javax.swing.JButton btnHome_Tra;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLastPMCT;
    private javax.swing.JButton btnLeft;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnMoiPMCT;
    private javax.swing.JButton btnNew_Tra;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNextPMCT;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnPrevPMCT;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnRight;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnSuaPMCT;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemPMCT;
    private javax.swing.JButton btnThoat_Tra;
    private javax.swing.JButton btnUpdate_Tra;
    private javax.swing.JButton btnXemPMChiTiet;
    private javax.swing.JButton btnXemPhieuMuon;
    private javax.swing.JButton btnXemPhieuMuon_Tra;
    private javax.swing.JButton btnXemPhieuTra;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaPMCT;
    private javax.swing.JCheckBox chkDaTra_Tra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JList<String> listSachChon;
    private javax.swing.JList<String> listSachTrongKho;
    private javax.swing.JPanel pnChucNang;
    private javax.swing.JPanel pnlChiTietPM;
    private javax.swing.JPanel pnlPhieuMuon;
    private javax.swing.JPanel pnlTraSach;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblChiTietPhieuMuon;
    private javax.swing.JTable tblPhieuMuon;
    private javax.swing.JTable tblPhieuTra;
    private javax.swing.JTextArea txaGhiChu_Tra;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaDocGia_Tra;
    private javax.swing.JTextField txtMaNguoiDung;
    private javax.swing.JTextField txtMaPhieuMuon;
    private javax.swing.JTextField txtMaPhieuMuonChiTiet_PMCT;
    private javax.swing.JTextField txtMaPhieuMuon_PMCT;
    private javax.swing.JTextField txtMaPhieuMuon_Tra;
    private javax.swing.JTextField txtMaPhieuTra_Tra;
    private javax.swing.JTextField txtNgayHenTra;
    private javax.swing.JTextField txtNgayMuon;
    private javax.swing.JTextField txtNgayTra_Tra;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTimKiemMaSach;
    private javax.swing.JTextField txtTongSachChon;
    private javax.swing.JTextField txtTongSoLuongSachMuon;
    // End of variables declaration//GEN-END:variables
}
