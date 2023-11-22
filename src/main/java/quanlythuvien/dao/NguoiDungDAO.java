/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlythuvien.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlythuvien.entity.NguoiDung;
import quanlythuvien.utils.jdbcHelper;

public class NguoiDungDAO extends ThuVienDAO<NguoiDung, String> {

    final String INSERT_SQL = "INSERT INTO NguoiDung (MaNguoiDung, MaLoaiNguoiDung, TenNguoiDung, Email, SoDienThoai, MatKhau) VALUES (?,?,?,?,?,?)";
    final String UPDATE_SQL = "update NguoiDung set MaLoaiNguoiDung = ?, TenNguoiDung = ?, Email = ?, SoDienThoai = ?, MatKhau = ? where MaNguoiDung = ?";
    final String DELETE_SQL = "DELETE from NguoiDung where MaNguoiDung = ?";
    final String SELECT_ALL_SQL = "select * from NguoiDung";
    final String SELECT_BY_ID_SQL = "select * from NguoiDung where MaNguoiDung = ?";
    final String SELECT_BY_MA_OF_USER = "select * from NguoiDung where MaNguoiDung like ?";
    final String SELECT_BY_NAME = "select * from NguoiDung where TenNguoiDung like ?";

    @Override
    public void insert(NguoiDung entity) {
        jdbcHelper.update(INSERT_SQL, entity.getMaNguoiDung(), entity.getMaLoaiNguoiDung(), entity.getTenNguoiDung(), entity.getEmail(), entity.getSdt(), entity.getMatKhau());
    }

    @Override
    public void update(NguoiDung entity) {
        jdbcHelper.update(UPDATE_SQL, entity.getMaLoaiNguoiDung(), entity.getTenNguoiDung(), entity.getEmail(), entity.getSdt(), entity.getMatKhau(), entity.getMaNguoiDung());
    }

    @Override
    public void delete(String id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<NguoiDung> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NguoiDung selectById(String id) {
        List<NguoiDung> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NguoiDung> selectBySql(String sql, Object... args) {
        List<NguoiDung> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                NguoiDung entity = new NguoiDung();

                entity.setMaNguoiDung(rs.getString("MaNguoiDung"));
                entity.setMaLoaiNguoiDung(rs.getString("MaLoaiNguoiDung"));
                entity.setTenNguoiDung(rs.getString("TenNguoiDung"));
                entity.setEmail(rs.getString("Email"));
                entity.setSdt(rs.getString("SoDienThoai"));
                entity.setMatKhau(rs.getString("MatKhau"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<NguoiDung> selectByMaOfUser(String nameOfUser){
        return selectBySql(SELECT_BY_MA_OF_USER, "%"+nameOfUser +"%");
    }
    public List<NguoiDung> selectByTenNguoiDung(String name){
        return selectBySql(SELECT_BY_NAME, "%"+name +"%");
    }

}
