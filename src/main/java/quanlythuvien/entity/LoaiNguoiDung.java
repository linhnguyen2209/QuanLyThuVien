/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlythuvien.entity;

/**
 *
 * @author Administrator
 */
public class LoaiNguoiDung {
    private String maLoaiNguoiDung, TenLoaiNguoiDung;

    public LoaiNguoiDung() {
    }

    public LoaiNguoiDung(String maLoaiNguoiDung, String TenLoaiNguoiDung) {
        this.maLoaiNguoiDung = maLoaiNguoiDung;
        this.TenLoaiNguoiDung = TenLoaiNguoiDung;
    }

    @Override
    public String toString() {
        return this.TenLoaiNguoiDung;
    }

    public String getMaLoaiNguoiDung() {
        return maLoaiNguoiDung;
    }

    public void setMaLoaiNguoiDung(String maLoaiNguoiDung) {
        this.maLoaiNguoiDung = maLoaiNguoiDung;
    }

    public String getTenLoaiNguoiDung() {
        return TenLoaiNguoiDung;
    }

    public void setTenLoaiNguoiDung(String TenLoaiNguoiDung) {
        this.TenLoaiNguoiDung = TenLoaiNguoiDung;
    }
    
}
