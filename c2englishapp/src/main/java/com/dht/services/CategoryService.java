/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.services;

import com.dht.pojo.Category;
import com.dht.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CategoryService {
    public List<Category> getCategories() throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM category");
            
            List<Category> cates = new ArrayList<>();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                cates.add(new Category(id, name));
            }
            
            return cates;
        }
    }
}
