package services;

import database.DBConnection;
import panels.entity.AuthorPanel;
import panels.entity.BookPanel;
import panels.entity.BorrowBookPanel;
import panels.entity.ReaderPanel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditService {

    private static final String UPDATE_READER_SQL_QUERY =
            "update reader set fname=?, lname=?, egn=?, address=? where id=?";

    private static final String UPDATE_BOOK_SQL_QUERY =
            "update book set title=?, p_year=?, pages=?, author_id=? where id=?";

    private static final String UPDATE_AUTHOR_SQL_QUERY =
            "update author set fname=?, lname=?, born_year=?, country=? where id=?";

    private static final String UPDATE_BORROW_SQL_QUERY =
            "update borrow set reader_id=?, book_id=?, loan_date=?, return_date=?, end_date=? where id=?";


    public static void editReader(int id, ReaderPanel readerPanel){
        Connection collection = DBConnection.getConnection();
        try {
            PreparedStatement statement = collection.prepareStatement(UPDATE_READER_SQL_QUERY);
            SetTextFieldService.setReaderTextFields(statement, readerPanel);
            statement.setInt(5, id);
            statement.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static void editBook(int id, BookPanel bookPanel){
        Connection collection = DBConnection.getConnection();
        try {
            PreparedStatement statement = collection.prepareStatement(UPDATE_BOOK_SQL_QUERY);
            SetTextFieldService.setBookTextFields(statement, bookPanel);
            statement.setInt(5, id);
            statement.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static void editAuthor(int id, AuthorPanel authorPanel){
        Connection collection = DBConnection.getConnection();
        try {
            PreparedStatement statement = collection.prepareStatement(UPDATE_AUTHOR_SQL_QUERY);
            SetTextFieldService.setAuthorTextFields(statement, authorPanel);
            statement.setInt(5, id);
            statement.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static void editBorrow(int id, BorrowBookPanel borrowBookPanel){
        Connection collection = DBConnection.getConnection();
        try {
            PreparedStatement statement = collection.prepareStatement(UPDATE_BORROW_SQL_QUERY);
            SetTextFieldService.setBorrowTextFields(statement, borrowBookPanel);

            statement.setInt(6, id);
            statement.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
