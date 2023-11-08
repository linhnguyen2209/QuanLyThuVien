/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlythuvien.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlythuvien.entity.NguoiDung;
import quanlythuvien.entity.PhieuMuon;
import quanlythuvien.utils.jdbcHelper;

public class PhieuMuonDAO extends ThuVienDAO<PhieuMuon, Integer> {

    final String INSERT_SQL = "INSERT INTO PhieuMuon (NgayMuon, NgayHenTra, TongSoLuongSachMuon, MaNguoiDung, GhiChu) VALUES (?,?,?,?,?)";
    final String UPDATE_SQL = "update PhieuMuon set  NgayMuon = ? ,NgayHenTra =?, TongSoLuongSachMuon, MaNguoiDung = ?, GhiChu = ? where MaPhieuMuon = ?";
    final String DELETE_SQL = "DELETE from PhieuMuon where MaPhieuMuon = ?";
    final String SELECT_ALL_SQL = "select * from PhieuMuon";
    final String SELECT_BY_ID_SQL = "select * from PhieuMuon where MaPhieuMuon = ?";

    @Override
    public void insert(PhieuMuon entity) {
        jdbcHelper.update(INSERT_SQL, entity.getNgayMuon(), entity.getNgayHenTra(), entity.getTongSoLuongSachMuon(), entity.getMaNguoiDung(), entity.getGhiChu());
    }

    @Override
    public void update(PhieuMuon entity) {
        jdbcHelper.update(UPDATE_SQL, entity.getNgayMuon(), entity.getNgayHenTra(), entity.getTongSoLuongSachMuon(), entity.getMaNguoiDung(), entity.getGhiChu(), entity.getMaPhieuMuon());
    }

    @Override
    public void delete(Integer id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<PhieuMuon> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public PhieuMuon selectById(Integer id) {
        List<PhieuMuon> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<PhieuMuon> selectBySql(String sql, Object... args) {
        List<PhieuMuon> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                PhieuMuon entity = new PhieuMuon();
                entity.setMaPhieuMuon(rs.getInt("MaPhieuMuon"));
                entity.setNgayMuon(rs.getDate("NgayMuon"));
                entity.setNgayHenTra(rs.getDate("NgayHenTra"));
                entity.setTongSoLuongSachMuon(rs.getInt("TongSoLuongSachMuon"));
                entity.setMaNguoiDung(rs.getString("MaNguoiDung"));
                entity.setGhiChu(rs.getString("GhiChu"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
