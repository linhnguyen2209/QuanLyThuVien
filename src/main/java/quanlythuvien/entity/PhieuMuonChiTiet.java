package quanlythuvien.entity;

/**
 *
 * @author thuon
 */
public class PhieuMuonChiTiet {

    private int maChiTietPhieuMuon, maPhieuMuon, maSach, slSachMuonMoiLoai;

    public PhieuMuonChiTiet() {
    }

    public PhieuMuonChiTiet(int maChiTietPhieuMuon, int maPhieuMuon, int maSach, int slSachMuonMoiLoai) {
        this.maChiTietPhieuMuon = maChiTietPhieuMuon;
        this.maPhieuMuon = maPhieuMuon;
        this.maSach = maSach;
        this.slSachMuonMoiLoai = slSachMuonMoiLoai;
    }

    public int getMaChiTietPhieuMuon() {
        return maChiTietPhieuMuon;
    }

    public void setMaChiTietPhieuMuon(int maChiTietPhieuMuon) {
        this.maChiTietPhieuMuon = maChiTietPhieuMuon;
    }

    public int getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(int maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getSlSachMuonMoiLoai() {
        return slSachMuonMoiLoai;
    }

    public void setSlSachMuonMoiLoai(int slSachMuonMoiLoai) {
        this.slSachMuonMoiLoai = slSachMuonMoiLoai;
    }

}
