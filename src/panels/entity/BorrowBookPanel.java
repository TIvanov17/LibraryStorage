package panels.entity;

import database.DBConnection;
import services.*;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowBookPanel extends LibraryEntityPanel {

    private JLabel readerName;
    private JComboBox<String> readersComboBox;
    private JLabel bookTitle;
    private JComboBox<String> bookTitleComboBox;
    private JLabel actualReturnDate;
    private JTextField actualReturnDateTF;

    private List<String> idBookColl = new ArrayList<>();
    private List<String> idReaderColl = new ArrayList<>();

    private final String SEARCH_CRITERIA = "Title";

    public BorrowBookPanel(){
        super();
        this.buttonPanel.setSearchByButtonCriteria(SEARCH_CRITERIA);
        this.initBorrowInputAttributes();
        this.addBookInputsToPanel();


        this.buttonPanel.getAddButton().addActionListener( e -> {
            AddService.addBorrow(this);
            this.refreshTable();
            this.clearForm();
        });


        this.buttonPanel.getDeleteButton().addActionListener( e -> {
            DeleteService.deleteBorrow(selectedTableRowId);
            this.refreshTable();
            this.clearForm();
            this.setDefaultValueForSelectedRowId();
        });

        this.buttonPanel.getEditButton().addActionListener( e -> {
            EditService.editBorrow(selectedTableRowId, this);
            this.refreshTable();
            this.clearForm();
        });

        this.buttonPanel.getSearchByButton().addActionListener( e ->{
            SearchService.searchBorrowByBookTitle(this.bookTitleComboBox, this.table);
        });

        this.buttonPanel.getRefreshTableButton().addActionListener( e -> {
            this.refreshTable();
        });

        this.buttonPanel.getClearFormButton().addActionListener( e -> {
            this.clearForm();
        });

        this.table.addMouseListener(new BorrowMouseAction());
        this.refreshTable();
    }

    public JComboBox<String> getReadersComboBox() {
        return readersComboBox;
    }

    public JComboBox<String> getBookTitleComboBox() {
        return bookTitleComboBox;
    }

    public String getReturnDateText() {
        return actualReturnDateTF.getText();
    }

    public List<String> getIdBookColl() {
        return idBookColl;
    }

    public List<String> getIdReaderColl() {
        return idReaderColl;
    }

    public void addReaderComboBox(JComboBox<String> readersCombo, List<String> idReaderColl){
        this.readersComboBox = readersCombo;
        this.idReaderColl = idReaderColl;
        this.inputPanel.add(readerName);
        this.inputPanel.add(readersCombo);
    }

    public void addBookComboBox(JComboBox<String> bookTitleCombo, List<String> idBookColl){
        this.bookTitleComboBox = bookTitleCombo;
        this.idBookColl = idBookColl;
        this.inputPanel.add(bookTitle);
        this.inputPanel.add(bookTitleCombo);
    }

    private void initBorrowInputAttributes(){
        this.readerName             = new JLabel("Reader Full Name:");
        this.readersComboBox        = new JComboBox<>();
        this.bookTitle              = new JLabel("Book Title:");
        this.bookTitleComboBox      = new JComboBox<>();
        this.actualReturnDate       = new JLabel("Actual Return Date:");
        this.actualReturnDateTF     = new JTextField();
    }

    private void addBookInputsToPanel(){
        this.inputPanel.add(actualReturnDate);
        this.inputPanel.add(actualReturnDateTF);
    }

    private void refreshTable(){
        RefreshService.refreshBorrowTable(this.table);
    }

    private void clearForm(){
        this.bookTitleComboBox.setSelectedIndex(0);
        this.readersComboBox.setSelectedIndex(0);
        this.actualReturnDateTF.setText("");
    }

    private class BorrowMouseAction implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            int row = table.getSelectedRow();
            selectedTableRowId = Integer.parseInt(table.getValueAt(row, 0).toString());

            String fkReaderID = table.getValueAt(row, 1).toString();
            readersComboBox.setSelectedIndex(getComboValue(readersComboBox, fkReaderID));

            String fkBookID = table.getValueAt(row, 2).toString();
            bookTitleComboBox.setSelectedIndex(getComboValue(bookTitleComboBox, fkBookID));
            actualReturnDateTF.setText(table.getValueAt(row, 4).toString());
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        private int getComboValue(JComboBox<String> comboBox, String id){
            for(int i = 0; i < comboBox.getItemCount(); i++){
                if(comboBox.getItemAt(i).contains(id)){
                    return i;
                }
            }
            return -1;
        }
    }
}
