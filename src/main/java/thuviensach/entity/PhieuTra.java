package thuviensach.entity;

import java.util.Date;

public class PhieuTra {
private  int maPhieuTra, maPhieuMuon;
private Date ngayTra;
private boolean trangThai;
private String maNguoiDung, ghiChu;

    public PhieuTra() {
    }

    public PhieuTra(int maPhieuTra, int maPhieuMuon, Date ngayTra, boolean trangThai, String maNguoiDung, String ghiChu) {
        this.maPhieuTra = maPhieuTra;
        this.maPhieuMuon = maPhieuMuon;
        this.ngayTra = ngayTra;
        this.trangThai = trangThai;
        this.maNguoiDung = maNguoiDung;
        this.ghiChu = ghiChu;
    }

    public int getMaPhieuTra() {
        return maPhieuTra;
    }

    public void setMaPhieuTra(int maPhieuTra) {
        this.maPhieuTra = maPhieuTra;
    }

    public int getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(int maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

   
}
