package services;

import database.DBConnection;
import panels.entity.AuthorPanel;
import panels.entity.BookPanel;
import panels.entity.BorrowBookPanel;
import panels.entity.ReaderPanel;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class AddService {

    private static final String INSERT_READER_SQL_QUERY =
            "insert into reader(fname, lname, egn, address) values(?,?,?,?)";

    private static final String INSERT_BOOK_SQL_QUERY =
            "insert into book(title, p_year, pages, author_id) values(?,?,?,?)";

    private static final String INSERT_AUTHOR_SQL_QUERY =
            "insert into author(fname, lname, born_year, country) values(?,?,?,?)";

    private static final String INSERT_BORROW_SQL_QUERY =
            "insert into borrow(reader_id, book_id, loan_date, return_date, end_date) values(?,?,?,?,?)";


    public static void addReader(ReaderPanel readerPanel) {
        Connection collection = DBConnection.getConnection();
        try {
            PreparedStatement statement = collection.prepareStatement(INSERT_READER_SQL_QUERY);
            SetTextFieldService.setReaderTextFields(statement, readerPanel);
            statement.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static void addBook(BookPanel bookPanel) {
        Connection collection = DBConnection.getConnection();
        try {
            PreparedStatement statement = collection.prepareStatement(INSERT_BOOK_SQL_QUERY);
            SetTextFieldService.setBookTextFields(statement, bookPanel);
            statement.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static void addAuthor(AuthorPanel authorPanel) {
        Connection collection = DBConnection.getConnection();
        try {
            PreparedStatement statement = collection.prepareStatement(INSERT_AUTHOR_SQL_QUERY);
            SetTextFieldService.setAuthorTextFields(statement, authorPanel);
            statement.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static void addBorrow(BorrowBookPanel borrowBookPanel){
        if (!canBorrowBook(borrowBookPanel.getBookTitleComboBox(),
                borrowBookPanel.getIdBookColl())){
            return;
        }
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_BORROW_SQL_QUERY);
            SetTextFieldService.setBorrowTextFields(statement, borrowBookPanel);
            statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static boolean canBorrowBook(JComboBox<String> bookComboBox, List<String> idBookColl){
        int checkId = getSelectedID(bookComboBox, idBookColl);

        String sql = "select id from borrow where book_id = " + checkId + " and return_date = ''";
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement currentStatement = connection.prepareStatement(sql);
            ResultSet resultSet = currentStatement.executeQuery();
            if (resultSet.next()) {
                return false;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static int getSelectedID(JComboBox<String> comboBox, List<String> idCollection) {
        String selected = comboBox.getSelectedItem().toString();
        String[] splitSelected = selected.split("\\.");

        for (int i = 0; i < idCollection.size(); i++) {
            if (splitSelected[0].equals(idCollection.get(i))) {
                return Integer.parseInt(idCollection.get(i));
            }
        }
        return -1;
    }
}
