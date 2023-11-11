package quanlythuvien.dao;

/**
 *
 * @author thuon
 */
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlythuvien.entity.PhieuMuonChiTiet;
import quanlythuvien.utils.jdbcHelper;

public class PhieuMuonChiTietDAO extends ThuVienDAO<PhieuMuonChiTiet, Integer> {

    final String INSERT_SQL = "INSERT INTO ChiTietPhieuMuon (MaPhieuMuon, MaSach, SoLuongSachMuonMoiLoai) VALUES (?, ?, ?),";
    final String UPDATE_SQL = "UPDATE ChiTietPhieuMuon SET MaPhieuMuon = ? ,MaSach = ?, SoLuongSachMuonMoiLoai = ?, WHERE MaChiTietPhieuMuon = ?";
    final String DELETE_SQL = "DELETE FROM ChiTietPhieuMuon WHERE MaChiTietPhieuMuon = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM ChiTietPhieuMuon";
    final String SELECT_BY_ID_SQL = "SELECT * FROM ChiTietPhieuMuon WHERE MaChiTietPhieuMuon = ?";

    @Override
    public void insert(PhieuMuonChiTiet entity) {
        jdbcHelper.update(INSERT_SQL, entity.getMaPhieuMuon(), entity.getMaSach(), entity.getSlSachMuonMoiLoai());
    }

    @Override
    public void update(PhieuMuonChiTiet entity) {
        jdbcHelper.update(UPDATE_SQL, entity.getMaPhieuMuon(), entity.getMaSach(), entity.getSlSachMuonMoiLoai(), entity.getMaChiTietPhieuMuon());
    }

    @Override
    public void delete(Integer id) {
        jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<PhieuMuonChiTiet> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public PhieuMuonChiTiet selectById(Integer id) {
        List<PhieuMuonChiTiet> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<PhieuMuonChiTiet> selectBySql(String sql, Object... args) {
        List<PhieuMuonChiTiet> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while (rs.next()) {
                PhieuMuonChiTiet entity = new PhieuMuonChiTiet();
                entity.setMaChiTietPhieuMuon(rs.getInt("MaChiTietPhieuMuon"));
                entity.setMaPhieuMuon(rs.getInt("MaPhieuMuon"));
                entity.setMaSach(rs.getInt("MaSach"));
                entity.setSlSachMuonMoiLoai(rs.getInt("SoLuongSachMuonMoiLoai"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

}
