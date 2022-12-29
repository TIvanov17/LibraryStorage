package panels.reference;

import services.SearchService;
import utils.Constants;

import javax.swing.*;
import java.awt.*;

public class ReferencePanel extends JPanel{

    private JPanel referenceInputPanel;
    private JPanel referenceButtonPanel;
    private JPanel referenceTablePanel;

    private JTable referenceTable;
    private JScrollPane referenceTableScroll;

    private JLabel referenceReaderName;
    private JTextField referenceReaderNameTF;
    private JLabel referenceBookTitle;
    private JTextField referenceBookTitleTF;

    private JButton searchReferenceButton;

    public ReferencePanel(){
        this.initPanels();
        this.initReferenceInputAttributes();
        this.addReferenceInputsToPanel();

        this.searchReferenceButton = new JButton("Search");
        this.searchReferenceButton.setLayout(new FlowLayout());
        this.referenceButtonPanel.add(searchReferenceButton);

        this.searchReferenceButton.addActionListener( e -> {

            String bookTitle = referenceBookTitleTF.getText();
            String readerName = referenceReaderNameTF.getText();

            if(!bookTitle.isEmpty() && !readerName.isEmpty()){
                SearchService.searchByReaderNameAndBookTitle(readerName, bookTitle, referenceTable);
            }
            if(bookTitle.isEmpty() && !readerName.isEmpty()){
                SearchService.searchByReaderNameOnly(readerName, referenceTable);
            }
            if(!bookTitle.isEmpty() && readerName.isEmpty()){
                SearchService.searchByBookTitleOnly(bookTitle, referenceTable);
            }
            this.clearForm();
        });

        this.referenceInputPanel.setLayout(new GridLayout(Constants.GRID_LAYOUT_INPUT_PANEL_ROWS,
                Constants.GRID_LAYOUT_INPUT_PANEL_COLS));

        this.add(referenceInputPanel);
        this.add(referenceButtonPanel);
        this.add(referenceTablePanel);

        this.setLayout(new GridLayout(Constants.GRID_LAYOUT_MAIN_PANEL_ROWS,
                Constants.GRID_LAYOUT_MAIN_PANEL_COLS));

        this.referenceTableScroll.setPreferredSize(
                new Dimension(Constants.TABLE_WIDTH, Constants.TABLE_HEIGHT)
        );

        this.referenceTablePanel.add(referenceTableScroll);
    }

    private void clearForm(){
        this.referenceBookTitleTF.setText("");
        this.referenceReaderNameTF.setText("");
    }

    private void initPanels(){
        this.referenceInputPanel = new JPanel();
        this.referenceButtonPanel = new JPanel();
        this.referenceTablePanel = new JPanel();
        this.referenceTable = new JTable();
        this.referenceTableScroll = new JScrollPane(referenceTable);
    }

    private void initReferenceInputAttributes(){
        referenceReaderName = new JLabel("Reader Full Name:");
        referenceReaderNameTF = new JTextField();
        referenceBookTitle = new JLabel("Book Title:");
        referenceBookTitleTF = new JTextField();
    }

    private void addReferenceInputsToPanel(){
        this.referenceInputPanel.setLayout(new GridLayout(3,1));
        this.referenceInputPanel.add(referenceReaderName);
        this.referenceInputPanel.add(referenceReaderNameTF);
        this.referenceInputPanel.add(referenceBookTitle);
        this.referenceInputPanel.add(referenceBookTitleTF);

    }
}
