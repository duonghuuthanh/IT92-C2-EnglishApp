/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.tester01;

import com.dht.pojo.Category;
import com.dht.services.CategoryService;
import com.dht.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Admin
 */
public class CategoryTestSuite {
    private static Connection conn;
    private static CategoryService s;
    
    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        s = new CategoryService();
    }
    
    @AfterAll
    public static void afterAll() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CategoryTestSuite.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Test
    public void testCategoryNameNotNull() {
        try {
            List<Category> cates = s.getCategories();
            
            cates.forEach(c -> Assertions.assertNotNull(c.getName()));
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testCategoryNameUnique() throws SQLException {
        List<Category> cates = s.getCategories();
        
        List<String> names = cates.stream().flatMap(c -> Stream.of(c.getName())).collect(Collectors.toList());
        Set<String> others = new HashSet<>(names);
        
        Assertions.assertEquals(names.size(), others.size());
    }
}
