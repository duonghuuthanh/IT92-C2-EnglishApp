/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dht.c2englishapp;

import com.dht.pojo.Category;
import com.dht.pojo.Choice;
import com.dht.pojo.Question;
import com.dht.services.CategoryService;
import com.dht.services.QuestionService;
import com.dht.utils.Utils;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
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
    private static final QuestionService s = new QuestionService();
    @FXML
    private ComboBox<Category> cbCategories;
    @FXML
    private TableView<Question> tbQuestions;
    @FXML
    private TextField txtKeyword;
    @FXML
    private TextField txtContent;
    @FXML
    private TextField txtA;
    @FXML
    private TextField txtB;
    @FXML
    private TextField txtC;
    @FXML
    private TextField txtD;
    @FXML
    private RadioButton rdoA;
    @FXML
    private RadioButton rdoB;
    @FXML
    private RadioButton rdoC;
    @FXML
    private RadioButton rdoD;
    
//    private TextField[] t = new TextField[] { txtA,  }
    
    
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
        try {
            this.tbQuestions.setItems(FXCollections.observableList(s.getQuestions(kw)));
        } catch (SQLException ex) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadColumns() {
        TableColumn col1 = new TableColumn("Id");
        col1.setCellValueFactory(new PropertyValueFactory("id"));
        col1.setPrefWidth(250);
        
        TableColumn col2 = new TableColumn("Question Content");
        col2.setCellValueFactory(new PropertyValueFactory("content"));
        col2.setPrefWidth(250);
        
        TableColumn col3 = new TableColumn();
        col3.setCellFactory((p) -> {
            Button btn = new Button("Delete");
            
            btn.setOnAction((evt) -> {
                TableCell c = (TableCell)((Button)evt.getSource()).getParent();
                Question q = (Question) c.getTableRow().getItem();
                
                
                try {
                    if (s.deleteQuestion(q.getId()) == true) {
                        Utils.showBox("Delete successful!", Alert.AlertType.INFORMATION).show();
                        this.loadData(null);
                    } else {
                        Utils.showBox("Delete failed!", Alert.AlertType.ERROR).show();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            TableCell cell = new TableCell();
            cell.setGraphic(btn);
            return cell;
        });
        
        this.tbQuestions.getColumns().addAll(col1, col2, col3);
    }
    
    public void addQuestion(ActionEvent evt) throws SQLException {
        TextField[] t = new TextField[] { txtA, txtB, txtC, txtD };
        RadioButton[] r = new RadioButton[] { rdoA, rdoB, rdoC, rdoD };
        
        Question q = new Question(UUID.randomUUID().toString(), txtContent.getText(), 
                cbCategories.getSelectionModel().getSelectedItem().getId());
        
        List<Choice> choices = new ArrayList<>();
        for (int i = 0; i < t.length; i++) {
            choices.add(new Choice(UUID.randomUUID().toString(), t[i].getText(), 
                    r[i].isSelected(), q.getId()));
        }
        
        if (s.addQuestion(q, choices) == true) {
            Utils.showBox("Add successful!", Alert.AlertType.INFORMATION).show();
             this.loadData(null);
        } else {
            Utils.showBox("Add failed!", Alert.AlertType.WARNING).show();
        }
    }
}
