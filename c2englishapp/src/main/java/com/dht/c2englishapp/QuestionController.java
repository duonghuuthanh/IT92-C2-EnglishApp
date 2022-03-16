/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.c2englishapp;

import com.dht.pojo.Category;
import com.dht.pojo.Question;
import com.dht.services.CategoryService;
import com.dht.services.QuestionService;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class QuestionController implements Initializable {
    @FXML
    private ComboBox<Category> cbCategories;
    @FXML
    private TableView<Question> tbQuestions;
    @FXML
    private TextField txtKeyword;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CategoryService s = new CategoryService();
        try {
            this.cbCategories.setItems(FXCollections.observableList(s.getCategories()));
        } catch (SQLException ex) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.loadColumns();
        this.loadData(null);
        
        this.txtKeyword.textProperty().addListener((evt) -> {
            this.loadData(this.txtKeyword.getText());
        });
    }   
    
    private void loadData(String kw) {
        QuestionService s = new QuestionService();
        try {
            this.tbQuestions.setItems(FXCollections.observableList(s.getQuestions(kw)));
        } catch (SQLException ex) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadColumns() {
        TableColumn col1 = new TableColumn("Id");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setPrefWidth(300);
        
        TableColumn col2 = new TableColumn("Question Content");
        col2.setCellValueFactory(new PropertyValueFactory("content"));
        col2.setPrefWidth(300);
        
        this.tbQuestions.getColumns().addAll(col1, col2);
    }
}
