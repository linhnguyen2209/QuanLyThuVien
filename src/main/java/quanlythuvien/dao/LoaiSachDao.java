
package quanlythuvien.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlythuvien.entity.LoaiSach;
import quanlythuvien.utils.jdbcHelper;

/**
 *
 * @author Dino
 * Create and Edit By Dino
 */
public class LoaiSachDao extends ThuVienDAO<LoaiSach, String>{

    final String INSERT_SQL = "INSERT INTO LoaiSach (MaLoaiSach, TenLoaiSach) values (?,?)";
    final String UPDATE_SQL = "update LoaiSach set TenLoaiSach = ? Where MaLoaiSach = ?";
    final String DELETE_SQL = "Delete from LoaiSach where MaLoaiSach = ?";
    final String SELECT_ALL = "select * from LoaiSach";
    final String SELECT_BY_ID_SQL = "select * from LoaiSach where MaLoaiSach = ?";
    @Override
    public void insert(LoaiSach entity) {
        jdbcHelper.update(INSERT_SQL, entity.getMaLoaiSach(), entity.getTenLoaiSach());
    }

    @Override
    public void update(LoaiSach entity) {
       jdbcHelper.update(UPDATE_SQL, entity.getTenLoaiSach(), entity.getMaLoaiSach());
    }

    @Override
    public void delete(String id) {
       jdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<LoaiSach> selectAll() {
       return selectBySql(SELECT_ALL);
    }

    @Override
    public LoaiSach selectById(String id) {
       List<LoaiSach> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<LoaiSach> selectBySql(String sql, Object... args) {
       List<LoaiSach> list = new ArrayList<>();
        try {
            ResultSet rs = jdbcHelper.query(sql, args);
            while(rs.next()){
                LoaiSach entity = new LoaiSach();
                entity.setMaLoaiSach(rs.getString("MaLoaiSach"));
                entity.setTenLoaiSach(rs.getString("TenLoaiSach"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
}
