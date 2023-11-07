package thuviensach.entity;

import java.util.Date;

public class PhieuMuon {
private int maPhieuMuon;
private Date ngayMuon, ngayHenTra;
private int tongSoLuongSachMuon;
private String maNguoiDung, ghiChu;

    public PhieuMuon() {
    }

    public PhieuMuon(int maPhieuMuon, Date ngayMuon, Date ngayHenTra, int tongSoLuongSachMuon, String maNguoiDung, String ghiChu) {
        this.maPhieuMuon = maPhieuMuon;
        this.ngayMuon = ngayMuon;
        this.ngayHenTra = ngayHenTra;
        this.tongSoLuongSachMuon = tongSoLuongSachMuon;
        this.maNguoiDung = maNguoiDung;
        this.ghiChu = ghiChu;
    }

    public int getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public Date getNgayMuon() {
        return ngayMuon;
    }

    public Date getNgayHenTra() {
        return ngayHenTra;
    }

    public int getTongSoLuongSachMuon() {
        return tongSoLuongSachMuon;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setMaPhieuMuon(int maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public void setNgayHenTra(Date ngayHenTra) {
        this.ngayHenTra = ngayHenTra;
    }

    public void setTongSoLuongSachMuon(int tongSoLuongSachMuon) {
        this.tongSoLuongSachMuon = tongSoLuongSachMuon;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

}
