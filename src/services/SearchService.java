package services;

import database.DBConnection;
import model.LibraryTableModel;
import utils.Constants;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchService {

    private static final String SEARCH_READER_BY_EGN_SQL_QUERY =
            "select * from reader where egn=?";

    private static final String SEARCH_BOOK_BY_TITLE_SQL_QUERY = "select b.id, b.title, p_year, " +
            "b.pages, CONCAT(a.fname, ' ', a.lname) as author " +
            "from book b join author a on b.author_id = a.id where title=?";

    private static final String SELECT_AUTHOR_BY_SQL_QUERY =
            "select * from author where lname=?";

    private static final String SELECT_BORROW_BY_BOOK_TITLE =
            "select bb.id, CONCAT(r.fname, ' ', r.lname) as reader, b.title, " +
                    "loan_date, return_date, end_date" +
                    " from borrow bb join reader r on bb.reader_id = r.id join book b" +
                    " on bb.book_id = b.id where title=?";

    private static final String REFERENCE_SQL_QUERY = "SELECT CONCAT(R.FNAME, ' ', R.LNAME) as READER, B.TITLE," +
            "CONCAT(A.FNAME, ' ', A.LNAME) as AUTHOR, BR.LOAN_DATE, BR.RETURN_DATE, BR.END_DATE" +
            " FROM BORROW BR JOIN READER R ON BR.READER_ID = R.ID" +
            " JOIN BOOK B ON BR.BOOK_ID = B.ID JOIN AUTHOR A ON B.AUTHOR_ID = A.ID ";

    public static void searchReaderByEgn(String egn, JTable table){
        searchProcess(egn, table, SEARCH_READER_BY_EGN_SQL_QUERY);
    }

    public static void searchBookByTitle(String title, JTable table){
        searchProcess(title, table, SEARCH_BOOK_BY_TITLE_SQL_QUERY);
    }

    public static void searchAuthorByLastName(String lastName, JTable table){
        searchProcess(lastName, table, SELECT_AUTHOR_BY_SQL_QUERY);
    }

    public static void searchBorrowByBookTitle(JComboBox<String> bookTitleInfoCombo, JTable table){
        searchProcess(getBookTitle(bookTitleInfoCombo), table, SELECT_BORROW_BY_BOOK_TITLE);
    }

    public static void searchByBookTitleOnly(String bookTitle, JTable table){
        searchByOneCriteria(bookTitle, table, "WHERE B.TITLE = ?");
    }

    public static void searchByReaderNameOnly(String readerName, JTable table){
        searchByOneCriteria(readerName, table, "WHERE CONCAT(R.FNAME, ' ', R.LNAME) = ?");
    }

    public static void searchByReaderNameAndBookTitle(String readerName, String bookTitle, JTable table){

        String sql = REFERENCE_SQL_QUERY + "WHERE CONCAT(R.FNAME, ' ', R.LNAME) = ? AND B.TITLE = ?";

        Connection connection = DBConnection.getConnection();
        try {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, readerName);
            statement.setString(2, bookTitle);

            ResultSet result = statement.executeQuery();
            table.setModel(new LibraryTableModel(result));
        } catch (Exception throwable) {
            throwable.printStackTrace();
        }
    }


    private static void searchByOneCriteria(String criteriaTextField, JTable table, String criteria){
        String sql = REFERENCE_SQL_QUERY + criteria;

        Connection connection = DBConnection.getConnection();
        try {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, criteriaTextField);

            ResultSet result = statement.executeQuery();
            table.setModel(new LibraryTableModel(result));
        } catch (Exception throwable) {
            throwable.printStackTrace();
        }
    }

    private static void searchProcess(String textField, JTable table, String sql){
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, textField);
            ResultSet result = statement.executeQuery();
            table.setModel(new LibraryTableModel(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getBookTitle(JComboBox<String> bookTitleInfoCombo){

        StringBuilder title = new StringBuilder();

        if(bookTitleInfoCombo.getSelectedItem() == null){
            return "";
        }

        String[] comboSplitColl = bookTitleInfoCombo.getSelectedItem().toString().split(" ");
        int startIndex = 1;
        String currentWord = comboSplitColl[startIndex];

        while(!currentWord.equals(Constants.BOOK_COMBO_BOX_SEPARATOR_FROM_AUTHOR)){
            title.append(currentWord);
            title.append(" ");
            currentWord = comboSplitColl[++startIndex];
        }
        title.deleteCharAt(title.length() - 1);
        return title.toString();
    }

}
