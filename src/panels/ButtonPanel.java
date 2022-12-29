package panels;

import javax.swing.*;
import java.awt.FlowLayout;

public class ButtonPanel extends JPanel {

    private JButton addButton;
    private JButton deleteButton;
    private JButton editButton;
    private JButton searchByButton;
    private JButton refreshTableButton;
    private JButton clearFormButton;

    public ButtonPanel(){
        this.setLayout(new FlowLayout());
        this.initAllButtons();

        this.add(this.addButton);
        this.add(this.deleteButton);
        this.add(this.editButton);
        this.add(this.searchByButton);
        this.add(this.refreshTableButton);
        this.add(this.clearFormButton);
    }

    private void initAllButtons(){
        this.addButton          = new JButton("Add");
        this.deleteButton       = new JButton("Delete");
        this.editButton         = new JButton("Edit");
        this.searchByButton     = new JButton("Search by");
        this.refreshTableButton = new JButton("Refresh");
        this.clearFormButton    = new JButton("Clear");
    }

    public JButton getAddButton(){
        return this.addButton;
    }

    public JButton getDeleteButton(){
        return this.deleteButton;
    }

    public JButton getEditButton(){
        return this.editButton;
    }

    public JButton getRefreshTableButton(){
        return this.refreshTableButton;
    }

    public JButton getClearFormButton(){
        return this.clearFormButton;
    }

    public void setSearchByButtonCriteria(String criteria){
        this.searchByButton.setText(this.searchByButton.getText() + " " + criteria);
    }

    public JButton getSearchByButton(){
        return this.searchByButton;
    }
}
