package services;

import database.DBConnection;
import model.LibraryTableModel;
import utils.Constants;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RefreshService {

    private static final String SELECT_ALL_READERS_SQL_QUERY = "select * from reader";

    private static final String SELECT_ALL_BOOKS_SQL_QUERY =
            "select b.id, b.title, p_year, " +
                    "b.pages, CONCAT(a.fname, ' ', a.lname) as author " +
                    "from book b join author a on b.author_id = a.id";

    private static final String SELECT_ALL_AUTHORS_SQL_QUERY = "select * from author";

    private static final String SELECT_ALL_BORROWS_SQL_QUERY =
            "select bb.id, CONCAT(r.fname, ' ', r.lname) as reader, b.title, " +
                    "loan_date, return_date, end_date" +
                    " from borrow bb join reader r on bb.reader_id = r.id join book b" +
                    " on bb.book_id = b.id";

    private static final String REFRESH_BOOK_COMBO_BOX_SQL_QUERY =
            "select b.id, b.title, CONCAT(a.fname, ' ', a.lname) " +
                    "from book b join author a on b.author_id = a.id";

    private static final String REFRESH_AUTHOR_COMBO_BOX_SQL_QUERY = "select id, fname, lname from author";

    private static final String REFRESH_READER_COMBO_BOX_SQL_QUERY = "select id, fname, lname from reader";


    public static void refreshReaderTable(JTable table) {
        refreshTableProcess(SELECT_ALL_READERS_SQL_QUERY, table);
    }

    public static void refreshBookTable(JTable table) {
        refreshTableProcess(SELECT_ALL_BOOKS_SQL_QUERY, table);
    }

    public static void refreshAuthorTable(JTable table) {
        refreshTableProcess(SELECT_ALL_AUTHORS_SQL_QUERY, table);
    }

    public static void refreshBorrowTable(JTable table) {
        refreshTableProcess(SELECT_ALL_BORROWS_SQL_QUERY, table);
    }

    public static void refreshBookComboBox(JComboBox<String> comboBox, List<String> idColl){
        clearComboBox(comboBox, idColl);

        StringBuilder stringBuilder = new StringBuilder();
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(REFRESH_BOOK_COMBO_BOX_SQL_QUERY);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                stringBuilder.append(result.getObject(1).toString())
                        .append(". ")
                        .append(result.getObject(2).toString())
                        .append(" ")
                        .append(Constants.BOOK_COMBO_BOX_SEPARATOR_FROM_AUTHOR)
                        .append(" ")
                        .append(result.getObject(3).toString());

                idColl.add(result.getObject(1).toString());
                comboBox.addItem(stringBuilder.toString());
                stringBuilder.setLength(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void refreshReaderComboBox(JComboBox<String> comboBox, List<String> idColl){
        refreshComboBoxProcess(comboBox, idColl, REFRESH_READER_COMBO_BOX_SQL_QUERY);
    }

    public static void refreshAuthorComboBox(JComboBox<String> comboBox, List<String> idColl){
        refreshComboBoxProcess(comboBox, idColl, REFRESH_AUTHOR_COMBO_BOX_SQL_QUERY);
    }

    private static void refreshTableProcess(String sql, JTable table) {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            table.setModel(new LibraryTableModel(resultSet));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void refreshComboBoxProcess(JComboBox<String> comboBox,
                                               List<String> idColl,
                                               String sql){
        clearComboBox(comboBox, idColl);
        StringBuilder stringBuilder = new StringBuilder();
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                stringBuilder.append(result.getObject(1).toString())
                        .append(". ")
                        .append(result.getObject(2).toString())
                        .append(" ")
                        .append(result.getObject(3).toString());

                idColl.add(result.getObject(1).toString());
                comboBox.addItem(stringBuilder.toString());
                stringBuilder.setLength(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void clearComboBox(JComboBox<String> comboBox, List<String> idColl){
        comboBox.removeAllItems();
        idColl.clear();
    }

}
