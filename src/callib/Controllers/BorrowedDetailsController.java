/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package callib.Controllers;

import callib.Models.BorrowedBook;
import callib.Models.BorrowedBookEntity;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class BorrowedDetailsController implements Initializable {

    
    private Stage stage;
    private int borrowkId;
    private BorrowedBook borrowed = BorrowedBook.getInstance();
    
    
    @FXML
    private Label label;
    @FXML
    private Label title;
    @FXML
    private Label category;
    @FXML
    private Label author;
    @FXML
    private Label isbn;
    @FXML
    private Label publisher;
    @FXML
    private Label date;
    @FXML
    private Label return_date;
    
    @FXML
    private void close(ActionEvent event) {
        stage = (Stage) label.getScene().getWindow();
        stage.close();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }
    /**
     * 
     * @param data
     * @return void
     */
    public void initData(int id) {
        this.borrowkId = id;
        this.displayData();
    }
    
    public void displayData() {
        System.out.println("controller " + this.borrowkId);
        BorrowedBookEntity bookDetails = borrowed.getBorrowedBookDetails(this.borrowkId);
        title.setText(bookDetails.getTitle());
        category.setText(bookDetails.getCategory());
        author.setText(bookDetails.getAuthor());
        isbn.setText(Integer.toString(bookDetails.getIsbn()));
        publisher.setText(bookDetails.getPublisher());
        date.setText(bookDetails.getDate().toString());
        return_date.setText(bookDetails.getReturn_date().toString());
    }
    
}
