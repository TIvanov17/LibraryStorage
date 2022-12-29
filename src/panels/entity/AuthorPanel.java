package panels.entity;

import services.*;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class AuthorPanel extends LibraryEntityPanel{

    private JLabel authorFirstName;
    private JTextField authorFirstNameTF;
    private JLabel authorLastName;
    private JTextField authorLastNameTF;
    private JLabel authorBornYear;
    private JTextField authorBornYearTF;
    private JLabel authorBornCountry;
    private JTextField authorBornCountryTF;

    private JComboBox<String> authorsComboBox;
    private List<String> collectionOfAuthorsId;

    private final String SEARCH_CRITERIA = "Last Name";

    public AuthorPanel(){
        super();
        this.buttonPanel.setSearchByButtonCriteria(SEARCH_CRITERIA);
        this.initAuthorInputAttributes();
        this.addAuthorInputsToPanel();

        this.buttonPanel.getAddButton().addActionListener( e -> {
            AddService.addAuthor(this);
            this.refreshTable();
            this.clearReaderForm();
        });

        this.buttonPanel.getDeleteButton().addActionListener( e -> {
            DeleteService.deleteAuthor(selectedTableRowId);
            this.refreshTable();
            this.clearReaderForm();
            this.setDefaultValueForSelectedRowId();
        });

        this.buttonPanel.getEditButton().addActionListener( e -> {
            EditService.editAuthor(selectedTableRowId, this);
            this.refreshTable();
            this.clearReaderForm();
        });

        this.buttonPanel.getSearchByButton().addActionListener( e -> {
            SearchService.searchAuthorByLastName(this.getAuthorLastNameText(), table);
        });

        this.buttonPanel.getRefreshTableButton().addActionListener( e ->
                this.refreshTable()
        );

        this.buttonPanel.getClearFormButton().addActionListener( e ->
                this.clearReaderForm()
        );


        this.table.addMouseListener(new AuthorMouseAction());
        this.refreshTable();
    }

    public JComboBox<String> getAuthorsComboBox(){
        return this.authorsComboBox;
    }

    public List<String> getCollectionOfAuthorsId(){
        return this.collectionOfAuthorsId;
    }

    public String getAuthorFirstNameText() {
        return authorFirstNameTF.getText();
    }

    public String getAuthorLastNameText() {
        return authorLastNameTF.getText();
    }

    public String getAuthorBornYearText() {
        return authorBornYearTF.getText();
    }

    public String getAuthorBornCountryText() {
        return authorBornCountryTF.getText();
    }

    private void initAuthorInputAttributes(){
        this.authorFirstName        = new JLabel("First Name:");
        this.authorFirstNameTF      = new JTextField();
        this.authorLastName         = new JLabel("Last Name:");
        this.authorLastNameTF       = new JTextField();
        this.authorBornYear         = new JLabel("Year Of Birth:");
        this.authorBornYearTF       = new JTextField();
        this.authorBornCountry      = new JLabel("Country:");
        this.authorBornCountryTF    = new JTextField();
        this.authorsComboBox        = new JComboBox<>();
        this.collectionOfAuthorsId  = new ArrayList<>();
    }

    private void addAuthorInputsToPanel(){
        this.inputPanel.add(authorFirstName);
        this.inputPanel.add(authorFirstNameTF);
        this.inputPanel.add(authorLastName);
        this.inputPanel.add(authorLastNameTF);
        this.inputPanel.add(authorBornYear);
        this.inputPanel.add(authorBornYearTF);
        this.inputPanel.add(authorBornCountry);
        this.inputPanel.add(authorBornCountryTF);
    }

    private void clearReaderForm(){
        this.authorFirstNameTF.setText("");
        this.authorLastNameTF.setText("");
        this.authorBornYearTF.setText("");
        this.authorBornCountryTF.setText("");
    }

    private void refreshTable(){
        RefreshService.refreshAuthorTable(this.table);
        RefreshService.refreshAuthorComboBox(this.authorsComboBox, this.collectionOfAuthorsId);
    }

    private class AuthorMouseAction implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int row = table.getSelectedRow();

            selectedTableRowId = Integer.parseInt(table.getValueAt(row, 0).toString());

            authorFirstNameTF.setText(table.getValueAt(row, 1).toString());
            authorLastNameTF.setText(table.getValueAt(row, 2).toString());
            authorBornYearTF.setText(table.getValueAt(row, 3).toString());
            authorBornCountryTF.setText(table.getValueAt(row, 4).toString());
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
}
