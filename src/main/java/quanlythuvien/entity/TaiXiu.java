/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package quanlythuvien.entity;

/**
 *
 * @author Linh
 */
public class TaiXiu {

    private double tienCuaBan;
    private int so1, so2, so3;
    private double tienCuoc,tienNhaCai;

    public TaiXiu() {
    }

    public TaiXiu(double tienCuaBan, int so1, int so2, int so3, double tienCuoc, double tienNhaCai) {
        this.tienCuaBan = tienCuaBan;
        this.so1 = so1;
        this.so2 = so2;
        this.so3 = so3;
        this.tienCuoc = tienCuoc;
        this.tienNhaCai = tienNhaCai;
    }

    public double getTienCuaBan() {
        return tienCuaBan;
    }

    public void setTienCuaBan(double tienCuaBan) {
        this.tienCuaBan = tienCuaBan;
    }

    public int getSo1() {
        return so1;
    }

    public void setSo1(int so1) {
        this.so1 = so1;
    }

    public int getSo2() {
        return so2;
    }

    public void setSo2(int so2) {
        this.so2 = so2;
    }

    public int getSo3() {
        return so3;
    }

    public void setSo3(int so3) {
        this.so3 = so3;
    }

    public double getTienCuoc() {
        return tienCuoc;
    }

    public void setTienCuoc(double tienCuoc) {
        this.tienCuoc = tienCuoc;
    }

    public double getTienNhaCai() {
        return tienNhaCai;
    }

    public void setTienNhaCai(double tienNhaCai) {
        this.tienNhaCai = tienNhaCai;
    }
    
    public int tinhTong(int so1, int so2, int so3){
        return so1+so2+so3;
    }
    
    public double tinhTienThang (double tienCuoc){
        return (tienCuoc - tienCuoc*(5.0/100)); // Nhà cái ăn 5% , 5.0 là chuyển từ in sang double chứ k là chia sẽ ra 0
    }
  
}
