package quanlythuvien.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlythuvien.entity.PhieuTra;
import quanlythuvien.utils.jdbcHelper;

/**
 *
 * @author thuon
 */
public class PhieuTraDAO extends ThuVienDAO<PhieuTra, Integer>{
    final String INSERT_SQL = "INSERT INTO PhieuTra (MaPhieuMuon, NgayTra, TrangThai, MaNguoiDung, GhiChu) VALUES (?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE PhieuTra set MaPhieuMuon = ?, NgayTra = ?, TrangThai = ?, MaNguoiDung = ?, GhiChu = ? where MaPhieuTra = ?";
    final String DELETE_SQL = "DELETE FROM PhieuTra where MaPhieuTra = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM PhieuTra";
    final String SELECT_BY_ID_SQL = "SELECT * FROM PhieuTra where MaPhieuTra = ?";
    final String SELECT_BY_IDPM_SQL = "SELECT * FROM PhieuTra where MaPhieuMuon = ?";

    @Override
    public void insert(PhieuTra entity) {
        jdbcHelper.update(INSERT_SQL, entity.getMaPhieuTra(), entity.getMaPhieuMuon(), entity.getNgayTra(), entity.isTrangThai(), entity.getMaNguoiDung(), entity.getGhiChu());
    }

    @Override
    public void update(PhieuTra entity) {
        jdbcHelper.update(UPDATE_SQL,entity.getMaPhieuMuon(), entity.getNgayTra(), entity.isTrangThai(), entity.getMaNguoiDung(), entity.getGhiChu(), entity.getMaPhieuTra());
    }

    @Override
    public void delete(Integer id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<PhieuTra> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public PhieuTra selectById(Integer id) {
        List<PhieuTra> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<PhieuTra> selectBySql(String sql, Object... args) {
        List<PhieuTra> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                PhieuTra entity = new PhieuTra();
                entity.setMaPhieuTra(rs.getInt("MaPhieuTra"));
                entity.setMaPhieuMuon(rs.getInt("MaPhieuMuon"));
                entity.setNgayTra(rs.getDate("NgayTra"));
                entity.setTrangThai(rs.getBoolean("TrangThai"));
                entity.setMaNguoiDung(rs.getString("MaNguoiDung"));
                entity.setGhiChu(rs.getString("GhiChu"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public PhieuTra selectByIdPM(Integer id) {
        List<PhieuTra> list = selectBySql(SELECT_BY_IDPM_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
