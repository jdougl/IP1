/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package callib.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Admin
 */
public class Book {
    
    private DBConnector connector = DBConnector.getInstance();
    
    private static Book book = new Book();
    
    private Book() {}
    
    public static Book getInstance() {
        return book;
    }
    
    public ObservableList<BookEntity> getBookList() {
        ResultSet rs = connector.executeSelectStatement("SELECT * FROM books");
        try {
            ObservableList<BookEntity> result = FXCollections.observableArrayList();
            while (rs.next()) {
                result.add(new BookEntity(rs.getString("title"), rs.getString("category"), rs.getString("author"), rs.getInt("isbn"), rs.getString("publisher"),
                        rs.getString("pub_date"), rs.getInt("pages"), rs.getInt("quantity")));
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;     
    }
}
