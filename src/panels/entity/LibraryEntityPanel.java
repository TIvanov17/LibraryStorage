package panels.entity;

import panels.ButtonPanel;
import utils.Constants;

import javax.swing.*;
import java.awt.*;

public class LibraryEntityPanel extends JPanel{

    protected int selectedTableRowId;

    protected final JPanel inputPanel;
    protected final ButtonPanel buttonPanel;
    protected final JPanel tablePanel;
    protected final JTable table;
    protected final JScrollPane tableScroll;

    public LibraryEntityPanel(){

        this.inputPanel  = new JPanel();
        this.buttonPanel = new ButtonPanel();
        this.tablePanel  = new JPanel();
        this.table       = new JTable();
        this.tableScroll = new JScrollPane(table);

        this.inputPanel.setLayout(new GridLayout(Constants.GRID_LAYOUT_INPUT_PANEL_ROWS,
                                    Constants.GRID_LAYOUT_INPUT_PANEL_COLS));

        this.add(inputPanel);
        this.add(buttonPanel);
        this.add(tablePanel);

        this.setLayout(new GridLayout(Constants.GRID_LAYOUT_MAIN_PANEL_ROWS,
                                     Constants.GRID_LAYOUT_MAIN_PANEL_COLS));

        this.setDefaultValueForSelectedRowId();

        this.tableScroll.setPreferredSize(
                new Dimension(Constants.TABLE_WIDTH, Constants.TABLE_HEIGHT)
        );

        this.tablePanel.add(tableScroll);
    }

    public void setDefaultValueForSelectedRowId(){
        this.selectedTableRowId = Constants.INIT_SELECTED_TABLE_ROW_ID;;
    }

}
