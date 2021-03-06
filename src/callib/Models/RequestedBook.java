/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package callib.Models;

import callib.Controllers.Main;
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
public class RequestedBook {
    
    private DBConnector connector = DBConnector.getInstance();
    
    private static RequestedBook requested = new RequestedBook();
    
    private RequestedBook() {}
    
    public static RequestedBook getInstance() {
        return requested;
    }
    
    public ObservableList<RequestedBookEntity> getAllRequestedBooksList() {
        ResultSet rs = connector.executeSelectStatement("SELECT requested_books.id, requested_books.date, requested_books.user_id, requested_books.book_id, "
                + "books.title, books.category, books.author, books.isbn, books.publisher FROM requested_books INNER JOIN "
                + "books ON requested_books.book_id = books.id WHERE requested_books.user_id LIKE " + Main.getId());
        try {
            ObservableList<RequestedBookEntity> result = FXCollections.observableArrayList();
            while (rs.next()) {
                result.add(new RequestedBookEntity(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("book_id"), rs.getString("title"), rs.getString("category"), rs.getString("author"), 
                        rs.getInt("isbn"), rs.getString("publisher"), rs.getDate("date")));
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean addNewRequestedBook(int userId, int bookId, String date) {
        return connector.executeInsertStatement("INSERT INTO requested_books (user_id, book_id, date) VALUES ("+userId+","+bookId+",'"+date+"')");
    }
    
    public boolean isBookRequestedAlready(int userId, int bookId) {
        ResultSet rs = connector.executeSelectStatement("SELECT id FROM requested_books WHERE user_id LIKE "+userId+ " AND book_id LIKE "+bookId);
        try {
            if(rs.isBeforeFirst())
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(BorrowedBook.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public RequestedBookEntity getRequestedBookDetails(int id) {
        try {
            ResultSet rs = connector.executeSelectStatement("SELECT requested_books.id, requested_books.date, requested_books.user_id, requested_books.book_id, "
                + "books.title, books.category, books.author, books.isbn, books.publisher FROM requested_books INNER JOIN "
                + "books ON requested_books.book_id = books.id WHERE requested_books.id LIKE " + id);
            if(rs.isBeforeFirst()) {
                rs.next();
                return new RequestedBookEntity(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("book_id"), rs.getString("title"), rs.getString("category"), rs.getString("author"),
                    rs.getInt("isbn"), rs.getString("publisher"), rs.getDate("date"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RequestedBook.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean deleteBookRequest(int id) {
        return connector.executeDeleteStatement("DELETE FROM requested_books WHERE id LIKE " + id);
    }
}