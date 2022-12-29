package panels.entity;

import services.*;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class ReaderPanel extends LibraryEntityPanel {

    private JLabel firstName;
    private JTextField firstNameTF;
    private JLabel lastName;
    private JTextField lastNameTF;
    private JLabel egn;
    private JTextField egnTF;
    private JLabel address;
    private JTextField addressTF;

    private JComboBox<String> readersComboBox;
    private List<String> idReaderColl;

    private final String SEARCH_CRITERIA = "EGN";

    public ReaderPanel(){

        super();
        this.buttonPanel.setSearchByButtonCriteria(SEARCH_CRITERIA);

        this.initReaderInputAttributes();
        this.addReaderInputsToPanel();

        this.buttonPanel.getAddButton().addActionListener( e -> {
            AddService.addReader(this);
            this.refreshTable();
            this.clearReaderForm();
        });

        this.buttonPanel.getDeleteButton().addActionListener( e -> {
            DeleteService.deleteReader(selectedTableRowId);
            this.refreshTable();
            this.clearReaderForm();
            this.setDefaultValueForSelectedRowId();
        });

        this.buttonPanel.getEditButton().addActionListener( e -> {
            EditService.editReader(selectedTableRowId, this);
            this.refreshTable();
            this.clearReaderForm();
        });

        this.buttonPanel.getSearchByButton().addActionListener( e -> {
            SearchService.searchReaderByEgn(this.getEgnText(), table);
        });

        this.buttonPanel.getRefreshTableButton().addActionListener( e ->
                this.refreshTable()
        );

        this.buttonPanel.getClearFormButton().addActionListener( e ->
                this.clearReaderForm()
        );

        this.table.addMouseListener(new ReaderMouseAction());
        this.refreshTable();
    }

    public String getFirstNameText() {
        return firstNameTF.getText();
    }

    public String getLastNameText() {
        return lastNameTF.getText();
    }

    public String getEgnText() {
        return egnTF.getText();
    }

    public String getAddressText() {
        return addressTF.getText();
    }

    public JComboBox<String> getReadersComboBox() {
        return readersComboBox;
    }

    public List<String> getIdReaderColl() {
        return idReaderColl;
    }

    private void refreshTable(){
        RefreshService.refreshReaderTable(this.table);
        RefreshService.refreshReaderComboBox(this.readersComboBox, this.idReaderColl);
    }

    private void initReaderInputAttributes(){
        this.firstName          = new JLabel("First Name:");
        this.firstNameTF        = new JTextField();
        this.lastName           = new JLabel("Last Name:");
        this.lastNameTF         = new JTextField();
        this.egn                = new JLabel("EGN:");
        this.egnTF              = new JTextField();
        this.address            = new JLabel("Address:");
        this.addressTF          = new JTextField();
        this.readersComboBox    = new JComboBox<>();
        this.idReaderColl       = new ArrayList<>();
    }

    private void addReaderInputsToPanel(){
        this.inputPanel.add(firstName);
        this.inputPanel.add(firstNameTF);
        this.inputPanel.add(lastName);
        this.inputPanel.add(lastNameTF);
        this.inputPanel.add(egn);
        this.inputPanel.add(egnTF);
        this.inputPanel.add(address);
        this.inputPanel.add(addressTF);
    }

    private void clearReaderForm(){
        this.firstNameTF.setText("");
        this.lastNameTF.setText("");
        this.egnTF.setText("");
        this.addressTF.setText("");
    }


    private class ReaderMouseAction implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            int row = table.getSelectedRow();

            selectedTableRowId = Integer.parseInt(table.getValueAt(row, 0).toString());

            firstNameTF.setText(table.getValueAt(row, 1).toString());
            lastNameTF.setText(table.getValueAt(row, 2).toString());
            egnTF.setText(table.getValueAt(row, 3).toString());
            addressTF.setText(table.getValueAt(row, 4).toString());
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}
