/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlythuvien.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlythuvien.entity.LoaiNguoiDung;
import quanlythuvien.utils.jdbcHelper;

/**
 *
 * @author Administrator
 */
public class LoaiNguoiDungDAO extends ThuVienDAO<LoaiNguoiDung, String>{
    final String INSERT_SQL = "INSERT INTO LoaiNguoiDung(MaLoaiNguoiDung, TenLoaiNguoiDung) Values (?,?)";
    final String UPDATE_SQL = "update LoaiNguoiDung set TenLoaiNguoiDung = ? where MaLoaiNguoiDung = ?";
    final String DELETE_SQL = "DELETE from LoaiNguoiDung where MaLoaiNguoiDung = ?";
    final String SELECT_ALL_SQL = "select * from LoaiNguoiDung";
    final String SELECT_BY_ID_SQL = "select * from LoaiNguoiDung where MaLoaiNguoiDung = ?";

    @Override
    public void insert(LoaiNguoiDung entity) {
//        jdbcHelper.update(INSERT_SQL, entity.getMaNguoiDung(), entity.getMaLoaiNguoiDung(), entity.getTenNguoiDung(), entity.getEmail(), entity.getSdt(), entity.getMatKhau());
    }

    @Override
    public void update(LoaiNguoiDung entity) {
//        jdbcHelper.update(UPDATE_SQL, entity.getMaLoaiNguoiDung(), entity.getTenNguoiDung(), entity.getEmail(), entity.getSdt(), entity.getMatKhau(), entity.getMaNguoiDung());
    }

    @Override
    public void delete(String id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<LoaiNguoiDung> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public LoaiNguoiDung selectById(String id) {
        List<LoaiNguoiDung> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<LoaiNguoiDung> selectBySql(String sql, Object... args) {
        List<LoaiNguoiDung> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                LoaiNguoiDung entity = new LoaiNguoiDung();

                entity.setMaLoaiNguoiDung(rs.getString("MaLoaiNguoiDung"));
                entity.setTenLoaiNguoiDung(rs.getString("TenLoaiNguoiDung"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
}
