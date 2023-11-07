/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlythuvien.dao;

import java.util.List;

/**
 *
 * @author Dino
 */
public abstract class ThuVienDAO<EntityType, KeyType> {
    public abstract void insert(EntityType entity); //abstract là hàm chỉ cso khai báo
    public abstract void update(EntityType entity);
    public abstract void delete(KeyType id);   
    public abstract List<EntityType> selectAll();
    public abstract EntityType selectById(KeyType id);
    public abstract List<EntityType> selectBySql(String sql, Object... args);
}
