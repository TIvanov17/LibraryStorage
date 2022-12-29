package services;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteService {

    private final static String DELETE_READER_BY_ID_SQL_QUERY   = "delete from reader where id=?";
    private final static String DELETE_BOOK_BY_ID_SQL_QUERY     = "delete from book where id=?";
    private final static String DELETE_AUTHOR_BY_ID_SQL_QUERY   = "delete from author where id=?";
    private final static String DELETE_BORROW_BY_ID_SQL_QUERY   = "delete from borrow where id=?";

    public static void deleteReader(int id){
        deleteEntity(id, DELETE_READER_BY_ID_SQL_QUERY);
    }

    public static void deleteBook(int id){
        deleteEntity(id, DELETE_BOOK_BY_ID_SQL_QUERY);
    }

    public static void deleteAuthor(int id){
        deleteEntity(id, DELETE_AUTHOR_BY_ID_SQL_QUERY);
    }

    public static void deleteBorrow(int id){
        deleteEntity(id, DELETE_BORROW_BY_ID_SQL_QUERY);
    }

    private static void deleteEntity(int id, String sql){

        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

}
