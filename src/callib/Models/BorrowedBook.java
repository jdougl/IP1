/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package callib.Models;

import callib.Controllers.Main;
import java.sql.Date;
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
public class BorrowedBook {
    
    private DBConnector connector = DBConnector.getInstance();
    
    private static BorrowedBook borrowed = new BorrowedBook();
    
    private BorrowedBook() {}
    
    public static BorrowedBook getInstance() {
        return borrowed;
    }
    
    public ObservableList<BorrowedBookEntity> getAllBorrowedBooksList() {
        ResultSet rs = connector.executeSelectStatement("SELECT borrowed_books.id, borrowed_books.date, borrowed_books.return_date, borrowed_books.fee_applied, "
                + "borrowed_books.user_id, borrowed_books.book_id, books.title, books.category, books.author, books.isbn, books.publisher FROM borrowed_books "
                + "INNER JOIN books ON borrowed_books.book_id = books.id WHERE borrowed_books.user_id LIKE " + Main.getId());
        try {
            ObservableList<BorrowedBookEntity> result = FXCollections.observableArrayList();
            while (rs.next()) {
                result.add(new BorrowedBookEntity(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("book_id"), rs.getString("title"), rs.getString("category"), rs.getString("author"), rs.getInt("isbn"), 
                    rs.getString("publisher"), rs.getDate("date"), rs.getDate("return_date"), rs.getInt("fee_applied")));
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public BorrowedBookEntity getBorrowedBookDetails(int id) {
        try {
            ResultSet rs = connector.executeSelectStatement("SELECT borrowed_books.id, borrowed_books.date, borrowed_books.return_date, borrowed_books.fee_applied, "
                + "borrowed_books.user_id, borrowed_books.book_id, books.title, books.category, books.author, books.isbn, books.publisher FROM borrowed_books "
                + "INNER JOIN books ON borrowed_books.book_id = books.id WHERE borrowed_books.id LIKE " + id);
            if(rs.isBeforeFirst()) {
                rs.first();
                BorrowedBookEntity entity = new BorrowedBookEntity(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("book_id"), rs.getString("title"), rs.getString("category"), rs.getString("author"),
                    rs.getInt("isbn"), rs.getString("publisher"), rs.getDate("date"), rs.getDate("return_date"), rs.getInt("fee_applied"));
                return entity;
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean updateReturnDate(int id, String date) {
        return connector.executeUpdateStatement("UPDATE borrowed_books SET return_date = '" + date + "' WHERE id LIKE " + id);
    }
    
    public boolean deleteBorrowedBook(int id) {
        return connector.executeDeleteStatement("DELETE FROM borrowed_books WHERE id LIKE " + id);
    }
    
    public boolean addNewBorrowedBook(int userId, int bookId, String date, String returnDate) {
        return connector.executeInsertStatement("INSERT INTO borrowed_books (user_id, book_id, date, return_date) VALUES ("+userId+","+bookId+",'"+date+"','"+returnDate+"')");
    }
    
    public boolean isBookBorrowedAlready(int userId, int bookId) {
        ResultSet rs = connector.executeSelectStatement("SELECT id FROM borrowed_books WHERE user_id LIKE "+userId+ " AND book_id LIKE "+bookId);
        try {
            if(rs.isBeforeFirst())
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(BorrowedBook.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean updateFeeAppliedToTrue(int borrowId) {
        return connector.executeUpdateStatement("UPDATE borrowed_books SET fee_applied = 1 WHERE id LIKE " + borrowId);
    }
}
