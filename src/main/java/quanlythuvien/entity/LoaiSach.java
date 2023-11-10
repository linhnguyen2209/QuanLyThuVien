/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlythuvien.entity;

/**
 *
 * @author Dino
 */
public class LoaiSach {
    private  String MaLoaiSach, TenLoaiSach;

    public LoaiSach() {
    }

    public LoaiSach(String MaLoaiSach, String TenLoaiSach) {
        this.MaLoaiSach = MaLoaiSach;
        this.TenLoaiSach = TenLoaiSach;
    }

    public String getMaLoaiSach() {
        return MaLoaiSach;
    }

    public void setMaLoaiSach(String MaLoaiSach) {
        this.MaLoaiSach = MaLoaiSach;
    }

    public String getTenLoaiSach() {
        return TenLoaiSach;
    }

    public void setTenLoaiSach(String TenLoaiSach) {
        this.TenLoaiSach = TenLoaiSach;
    }

    @Override
    public String toString() {
       return  this.MaLoaiSach;
                
    }
    
    
    
}
