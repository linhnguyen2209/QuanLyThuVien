/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlythuvien.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlythuvien.utils.jdbcHelper;

/**
 *
 * @author Administrator
 */
public class ThongKeDAO {
     //class trung gian không có entity
    private List<Object[]> getListOfArray(String sql,String[] cols, Object...args){
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = jdbcHelper.query(sql, args);
            while(rs.next()){
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Object[]> getMuonTraTheoLoai(Integer nam, Integer thangBD, Integer thangKT, String tinhTrang, String maDocGia){
        String sql = "{CALL sp_muonTraTheoLoai(?,?,?,?,?)}";
        String[] cols = {"MaPhieuMuon", "MaNguoiDung", "TenNguoiDung", "NgayMuon", "NgayHenTra", "NgayTra", "SoNgayMuonQuaHan", "TienPhat", "TinhTrangTraSach"};
        return getListOfArray(sql, cols, nam, thangBD, thangKT, "%"+tinhTrang+"%", "%"+maDocGia+"%");
    }
    public List<Object[]> getMuonTraTheoCacNam(){
        String sql = "{CALL sp_muonTraALLYears}";
        String[] cols = {"MaPhieuMuon", "MaNguoiDung", "TenNguoiDung", "NgayMuon", "NgayHenTra", "NgayTra", "SoNgayMuonQuaHan", "TienPhat", "TinhTrangTraSach"};
        return getListOfArray(sql, cols);
    }
}
