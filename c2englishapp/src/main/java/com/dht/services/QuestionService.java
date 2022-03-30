/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.services;

import com.dht.pojo.Choice;
import com.dht.pojo.Question;
import com.dht.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class QuestionService {
   public List<Question> getQuestions(String kw) throws SQLException {
       try (Connection conn = JdbcUtils.getConn()) {
           PreparedStatement stm = conn.prepareStatement("SELECT * FROM question WHERE content like concat('%', ?, '%')");
           
           if (kw == null)
               kw = "";
           stm.setString(1, kw);
           
           ResultSet rs = stm.executeQuery();
           
           List<Question> questions = new ArrayList<>();
           
           while (rs.next()) {
               String id = rs.getString("id");
               String content = rs.getString("content");
               int cateId = rs.getInt("category_id");
               questions.add(new Question(id, content, cateId));
           }
           
           return questions;
       }
   }
   
   public boolean deleteQuestion(String qId) throws SQLException {
       try (Connection conn = JdbcUtils.getConn()) {
           PreparedStatement stm = conn.prepareStatement("DELETE FROM question WHERE id=?");
           stm.setString(1, qId);
           
           return stm.executeUpdate() > 0;
       }
   }
   
   public Question getQuestionById(String qId) throws SQLException {
       try (Connection conn = JdbcUtils.getConn()) {
           PreparedStatement stm = conn.prepareStatement("SELECT * FROM question WHERE id=?");
           stm.setString(1, qId);
           
           
           ResultSet rs = stm.executeQuery();
           
           Question q = null;
           if (rs.next()) {
               q = new Question();
               q.setId(rs.getString("id"));
               q.setContent(rs.getString("content"));
               q.setCategoryId(rs.getInt("category_id"));
           }
           
           return q;
       }
   }
   
   public boolean addQuestion(Question q, List<Choice> choices) throws SQLException {
       if (choices.size() == 4) {
           String q1 = "INSERT INTO question(id, content, category_id) VALUES(?, ?, ?)";
           String q2 = "INSERT INTO choice(id, content, is_correct, question_id) VALUES(?, ?, ?, ?)";
           
            try (Connection conn = JdbcUtils.getConn()) {
                conn.setAutoCommit(false);
                PreparedStatement stm1 = conn.prepareStatement(q1);
                stm1.setString(1, q.getId());
                stm1.setString(2, q.getContent());
                stm1.setInt(3, q.getCategoryId());
                
                if (stm1.executeUpdate() > 0) {
                    for (Choice c: choices) {
                        PreparedStatement stm2 = conn.prepareStatement(q2);
                        stm2.setString(1, c.getId());
                        stm2.setString(2, c.getContent());
                        stm2.setBoolean(3, c.isCorrect());
                        stm2.setString(4, q.getId());
                        
                        stm2.executeUpdate();
                    }
                }
                
                conn.commit();
            }
           
           return true;
       }
       
       return false;
   }
}
