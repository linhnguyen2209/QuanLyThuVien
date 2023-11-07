package thuviensach.entity;

/**
 *
 * @author thuon
 */
public class NguoiDung {
    private String maNguoiDung, maLoaiNguoiDung, tenNguoiDung, email, sdt, matKhau;

    public NguoiDung() {
    }

    public NguoiDung(String maNguoiDung, String maLoaiNguoiDung, String tenNguoiDung, String email, String sdt, String matKhau) {
        this.maNguoiDung = maNguoiDung;
        this.maLoaiNguoiDung = maLoaiNguoiDung;
        this.tenNguoiDung = tenNguoiDung;
        this.email = email;
        this.sdt = sdt;
        this.matKhau = matKhau;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public String getMaLoaiNguoiDung() {
        return maLoaiNguoiDung;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public String getEmail() {
        return email;
    }

    public String getSdt() {
        return sdt;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public void setMaLoaiNguoiDung(String maLoaiNguoiDung) {
        this.maLoaiNguoiDung = maLoaiNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
    
    
}
