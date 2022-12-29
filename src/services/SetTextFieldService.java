package services;

import panels.entity.AuthorPanel;
import panels.entity.BookPanel;
import panels.entity.BorrowBookPanel;
import panels.entity.ReaderPanel;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SetTextFieldService {

    public static void setAuthorTextFields(PreparedStatement statement,
                                           AuthorPanel authorPanel) throws SQLException{
        statement.setString(1, authorPanel.getAuthorFirstNameText());
        statement.setString(2, authorPanel.getAuthorLastNameText());
        statement.setInt(3, Integer.parseInt(authorPanel.getAuthorBornYearText()));
        statement.setString(4, authorPanel.getAuthorBornCountryText());
    }

    public static void setReaderTextFields(PreparedStatement statement,
                                           ReaderPanel readerPanel) throws SQLException {

        statement.setString(1, readerPanel.getFirstNameText());
        statement.setString(2, readerPanel.getLastNameText());
        statement.setString(3, readerPanel.getEgnText());
        statement.setString(4, readerPanel.getAddressText());
    }

    public static void setBookTextFields(PreparedStatement statement,
                                         BookPanel bookPanel) throws SQLException {

        statement.setString(1, bookPanel.getBookTitleText());
        statement.setString(2, bookPanel.getYearText());
        statement.setString(3, bookPanel.getBookPagesText());
        statement.setInt(4, getSelectedID(bookPanel.getAuthorsComboBoxRef(),
                                        bookPanel.getCollectionOfAuthorsIdRef())
        );
    }

    public static void setBorrowTextFields(PreparedStatement statement,
                                           BorrowBookPanel borrowBookPanel) throws SQLException{

        LocalDate currentDate = LocalDate.now();
        LocalDate mustReturnToDate = currentDate.plusWeeks(1);

        statement.setInt(1, getSelectedID(borrowBookPanel.getReadersComboBox(),
                borrowBookPanel.getIdReaderColl()
        ));

        statement.setInt(2, getSelectedID(borrowBookPanel.getBookTitleComboBox(),
                borrowBookPanel.getIdBookColl())
        );

        statement.setString(3, currentDate.toString());
        statement.setString(4, borrowBookPanel.getReturnDateText());
        statement.setString(5, mustReturnToDate.toString());

    }

    private static int getSelectedID(JComboBox<String> comboBox,
                                     List<String> idCollection) {

        if(comboBox.getSelectedItem() == null){
            return -1;
        }

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
