/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlythuvien.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlythuvien.entity.Sach;
import quanlythuvien.utils.jdbcHelper;

/**
 *
 * @author Dino
 * Create and edit by Dino
 */
public class SachDAO extends ThuVienDAO<Sach, Integer>{
    final String INSERT_SQL = "INSERT INTO Sach (TieuDe, NhaXuatBan, TacGia, SoTrang, SoLuongSach, GiaTien, NgayNhapKho, ViTriDatSach, MaLoaiSach) VALUES (?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL ="UPDATE Sach SET TieuDe = ?, NhaXuatBan = ?, TacGia = ?, SoTrang = ?, SoLuongSach = ?, GiaTien = ?, NgayNhapKho = ?, ViTriDatSach = ?, MaLoaiSach = ? WHERE MaSach = ?";
    final String DELETE_SQL ="delete from Sach where MaSach = ?";
    final String SELECT_ALL_SQL = "select * from Sach";
    final String SELECT_BY_ID_SQL  ="select * from Sach where MaSach = ?";
    
    final String Select_By_TieuDE ="select * from Sach where TieuDe like ? "; 
    final String Select_By_TacGia ="select * from Sach where TacGia like ?";
    final String Select_By_ID_LoaiSach =" select * from Sach Where MaLoaiSach like ? ";
    final String Select_By_NhaXB ="select * from Sach Where NhaXuatBan like?"; 

    @Override
    public void insert(Sach entity) {
        jdbcHelper.update(INSERT_SQL, entity.getTieuDe() ,entity.getNhaXuatBan(), entity.getTacGia(), entity.getSoTrang(), entity.getSoLuongSach(), entity.getGiaTien(), entity.getNgayNhapKho(), entity.getViTriSach(), entity.getMaLoaiSach());
    }

    @Override
    public void update(Sach entity) {
         jdbcHelper.update(UPDATE_SQL, entity.getTieuDe() ,entity.getNhaXuatBan(), entity.getTacGia(), entity.getSoTrang(), entity.getSoLuongSach(), entity.getGiaTien(), entity.getNgayNhapKho(), entity.getViTriSach(), entity.getMaLoaiSach(), entity.getMaSach());
    }

    @Override
    public void delete(Integer id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<Sach> selectAll() {
       return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Sach selectById(Integer id) {
        List <Sach> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Sach> selectBySql(String sql, Object... args) {
        List<Sach> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()){
                Sach entity = new Sach();
                entity.setMaSach(rs.getInt("MaSach"));
                entity.setTieuDe(rs.getString("TieuDe"));
                entity.setNhaXuatBan(rs.getString("NhaXuatBan"));
                entity.setTacGia(rs.getString("TacGia"));
                entity.setSoTrang(rs.getInt("SoTrang"));
                entity.setSoLuongSach(rs.getInt("SoLuongSach"));
                entity.setGiaTien(rs.getDouble("GiaTien"));
                entity.setNgayNhapKho(rs.getDate("NgayNhapKho"));
                entity.setViTriSach(rs.getString("ViTriDatSach"));
                entity.setMaLoaiSach(rs.getString("MaLoaiSach"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
   
    public List<Sach> selectByTacGia(String name){
        return selectBySql(Select_By_TacGia, "%"+name +"%");
    }
    public List<Sach> selectByTieuDe(String name){
        return selectBySql(Select_By_TieuDE, "%"+name +"%");
    }
     public List<Sach> selectByMaLoaiSach(String ID){
        return selectBySql(Select_By_ID_LoaiSach, "%"+ID +"%");
    }
    public List<Sach> selectByNhaXB(String name){
        return selectBySql(Select_By_NhaXB, "%"+name +"%");
    }
    
}
