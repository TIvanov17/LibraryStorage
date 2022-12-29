package panels.entity;

import services.*;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class BookPanel extends LibraryEntityPanel {

    private JLabel bookTitle;
    private JTextField bookTitleTF;
    private JLabel year;
    private JTextField yearTF;
    private JLabel bookPages;
    private JTextField bookPagesTF;
    private JLabel authorName;

    private JComboBox<String> authorsComboBoxRef;
    private List<String> collectionOfAuthorsIdRef;

    private JComboBox<String> bookTitleCombo;
    private List<String> idBookColl;

    private final String SEARCH_CRITERIA = "Title";

    public BookPanel(){

        super();
        this.buttonPanel.setSearchByButtonCriteria(SEARCH_CRITERIA);
        this.initBookInputAttributes();
        this.addBookInputsToPanel();

        this.buttonPanel.getAddButton().addActionListener( e -> {
            AddService.addBook(this);
            this.refreshTable();
            this.clearForm();
        });

        this.buttonPanel.getDeleteButton().addActionListener( e -> {
            DeleteService.deleteBook(selectedTableRowId);
            this.refreshTable();
            this.clearForm();
            this.setDefaultValueForSelectedRowId();
        });

        this.buttonPanel.getEditButton().addActionListener( e -> {
            EditService.editBook(selectedTableRowId, this);
            this.refreshTable();
            this.clearForm();
        });

        this.buttonPanel.getSearchByButton().addActionListener( e -> {
            SearchService.searchBookByTitle(this.bookTitleTF.getText(), table);
        });

        this.buttonPanel.getRefreshTableButton().addActionListener( e ->
                this.refreshTable()
        );

        this.buttonPanel.getClearFormButton().addActionListener( e ->
                this.clearForm()
        );

        this.table.addMouseListener(new BookMouseAction());
        this.refreshTable();
    }

    public void addComboBox(JComboBox<String> comboBox,
                            List<String> collectionOfAuthorsId){

        this.authorsComboBoxRef = comboBox;
        this.collectionOfAuthorsIdRef = collectionOfAuthorsId;
        this.inputPanel.add(authorsComboBoxRef);
    }

    public String getBookTitleText() {
        return bookTitleTF.getText();
    }

    public String getYearText() {
        return yearTF.getText();
    }

    public String getBookPagesText() {
        return bookPagesTF.getText();
    }

    public JComboBox<String> getAuthorsComboBoxRef() {
        return authorsComboBoxRef;
    }

    public List<String> getCollectionOfAuthorsIdRef() {
        return collectionOfAuthorsIdRef;
    }

    public JComboBox<String> getBookTitleCombo() {
        return this.bookTitleCombo;
    }

    public List<String> getIdBookColl() {
        return this.idBookColl;
    }

    private void refreshTable(){
        RefreshService.refreshBookTable(this.table);
        RefreshService.refreshBookComboBox(this.bookTitleCombo, this.idBookColl);
    }

    private void initBookInputAttributes(){
        this.bookTitle      = new JLabel("Title:");
        this.bookTitleTF    = new JTextField();
        this.year           = new JLabel("Year:");
        this.yearTF         = new JTextField();
        this.bookPages      = new JLabel("Pages:");
        this.bookPagesTF    = new JTextField();
        this.authorName     = new JLabel("Author:");
        this.bookTitleCombo = new JComboBox<>();
        this.idBookColl     = new ArrayList<>();
    }

    private void addBookInputsToPanel(){
        this.inputPanel.add(bookTitle);
        this.inputPanel.add(bookTitleTF);
        this.inputPanel.add(year);
        this.inputPanel.add(yearTF);
        this.inputPanel.add(bookPages);
        this.inputPanel.add(bookPagesTF);
        this.inputPanel.add(authorName);
    }

    private void clearForm(){
        this.bookTitleTF.setText("");
        this.yearTF.setText("");
        this.bookPagesTF.setText("");
        this.authorsComboBoxRef.setSelectedIndex(0);
    }

    private class BookMouseAction implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int row = table.getSelectedRow();
            selectedTableRowId = Integer.parseInt(table.getValueAt(row, 0).toString());
            bookTitleTF.setText(table.getValueAt(row, 1).toString());
            yearTF.setText(table.getValueAt(row, 2).toString());
            bookPagesTF.setText(table.getValueAt(row, 3).toString());

            String fkAuthorID = table.getValueAt(row, 4).toString();
            authorsComboBoxRef.setSelectedIndex(getComboValue(authorsComboBoxRef, fkAuthorID));
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
