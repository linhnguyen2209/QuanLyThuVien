package quanlythuvien.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlythuvien.entity.PhieuMuon;
import quanlythuvien.utils.jdbcHelper;

public class PhieuMuonDAO extends ThuVienDAO<PhieuMuon, Integer> {

    final String INSERT_SQL = "INSERT INTO PhieuMuon (NgayMuon, NgayHenTra, TongSoLuongSachMuon, MaNguoiDung, GhiChu) VALUES (?,?,?,?,?)";
    final String UPDATE_SQL = "update PhieuMuon set  NgayMuon = ? ,NgayHenTra = ?, TongSoLuongSachMuon = ?, MaNguoiDung = ?, GhiChu = ? where MaPhieuMuon = ?";
    final String DELETE_SQL = "DELETE from PhieuMuon where MaPhieuMuon = ?";
    final String SELECT_ALL_SQL = "select * from PhieuMuon";
    final String SELECT_BY_ID_SQL = "select * from PhieuMuon where MaPhieuMuon = ?";
    final String SELECT_BY_NAM = "select Distinct(Year(NgayMuon)) as Nam from PhieuMuon ORDER BY Nam DESC ";
    final String SELECT_BY_IDND_SQL = "select * from PhieuMuon where MaNguoiDung = ?";
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
    
    public List<PhieuMuon> selectByIDND( String ID) {
        return selectBySql(SELECT_BY_IDND_SQL,ID );
    }
    
    public List<Integer> selectYear(){
        List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(SELECT_BY_NAM);
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
