/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.tester01;

import com.dht.pojo.Choice;
import com.dht.pojo.Question;
import com.dht.services.QuestionService;
import com.dht.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Admin
 */
public class QuestionTestSuite {
    private static Connection conn;
    private static QuestionService s;
    
    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        s = new QuestionService();
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
    public void testSearchSuccessful() throws SQLException {
        String kw = "you";
        List<Question> questions = s.getQuestions(kw);
        
        for (Question q: questions)
            Assertions.assertTrue(q.getContent().toLowerCase().contains(kw.toLowerCase()));
    }
    
    @Test
    public void testSearchInvalid() throws SQLException {
        String kw = "youuuuuuuuuuuuuuuuuuuuuuuuuuuu";
        List<Question> questions = s.getQuestions(kw);
        
        Assertions.assertEquals(questions.size(), 0);
    }
    
    @Test
    public void testSearchUnscure() throws SQLException {
        String kw = "1 OR 1=1";
        List<Question> questions = s.getQuestions(kw);
        
        Assertions.assertEquals(questions.size(), 0);
    }
    
    @Test
    public void deleteFail() throws SQLException {
        String id = "499999670eb24-3985-40f9-bab6-a86de52a5c34";
        Assertions.assertFalse(s.deleteQuestion(id));
    }
    
    @Test
    public void deleteSuccess() throws SQLException {
        String id = "4670eb24-3985-40f9-bab6-a86de52a5c34";
        Assertions.assertTrue(s.deleteQuestion(id));
        Assertions.assertNull(s.getQuestionById(id));
    }
    
    @Test
    public void testAddSuccess() throws SQLException {
        Question q = new Question(UUID.randomUUID().toString(), "A", 1);
        
        List<Choice> choices = new ArrayList<>();
        choices.add(new Choice(UUID.randomUUID().toString(), "1", true, q.getId()));
        choices.add(new Choice(UUID.randomUUID().toString(), "2", false, q.getId()));
        choices.add(new Choice(UUID.randomUUID().toString(), "3", false, q.getId()));
        choices.add(new Choice(UUID.randomUUID().toString(), "4", false, q.getId()));
        
        Assertions.assertTrue(s.addQuestion(q, choices));
        
        Question ques = s.getQuestionById(q.getId());
        Assertions.assertEquals(q.getContent(), ques.getContent());
        Assertions.assertEquals(q.getCategoryId(), ques.getCategoryId());
    }
}
