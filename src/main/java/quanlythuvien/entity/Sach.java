package quanlythuvien.entity;

import java.util.Date;

public class Sach {
private int maSach;
private String tieuDe, nhaXuatBan, tacGia;
private int soTrang, soLuongSach;
private double giaTien;
private Date ngayNhapKho;
private String viTriSach, maLoaiSach;

    public Sach() {
    }

    public Sach(int maSach, String tieuDe, String nhaXuatBan, String tacGia, int soTrang, int soLuongSach, double giaTien, Date ngayNhapKho, String viTriSach, String maLoaiSach) {
        this.maSach = maSach;
        this.tieuDe = tieuDe;
        this.nhaXuatBan = nhaXuatBan;
        this.tacGia = tacGia;
        this.soTrang = soTrang;
        this.soLuongSach = soLuongSach;
        this.giaTien = giaTien;
        this.ngayNhapKho = ngayNhapKho;
        this.viTriSach = viTriSach;
        this.maLoaiSach = maLoaiSach;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNhaXuatBan() {
        return nhaXuatBan;
    }

    public void setNhaXuatBan(String nhaXuatBan) {
        this.nhaXuatBan = nhaXuatBan;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }

    public int getSoLuongSach() {
        return soLuongSach;
    }

    public void setSoLuongSach(int soLuongSach) {
        this.soLuongSach = soLuongSach;
    }

    public double getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(double giaTien) {
        this.giaTien = giaTien;
    }

    public Date getNgayNhapKho() {
        return ngayNhapKho;
    }

    public void setNgayNhapKho(Date ngayNhapKho) {
        this.ngayNhapKho = ngayNhapKho;
    }

    public String getViTriSach() {
        return viTriSach;
    }

    public void setViTriSach(String viTriSach) {
        this.viTriSach = viTriSach;
    }

    public String getMaLoaiSach() {
        return maLoaiSach;
    }

    public void setMaLoaiSach(String maLoaiSach) {
        this.maLoaiSach = maLoaiSach;
    }

    @Override
    public String toString() {
        return maSach+"";
    }
}
