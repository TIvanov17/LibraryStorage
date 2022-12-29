package frame;

import panels.entity.AuthorPanel;
import panels.entity.BookPanel;
import panels.entity.BorrowBookPanel;
import panels.entity.ReaderPanel;
import panels.reference.ReferencePanel;
import utils.Constants;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridLayout;

public class MainFrame extends JFrame {

    private JTabbedPane tab;
    private ReaderPanel readerPanel;
    private BookPanel bookPanel;
    private AuthorPanel authorPanel;
    private BorrowBookPanel borrowBookPanel;
    private ReferencePanel referencePanel;

    public MainFrame() {

        this.setSize(new Dimension(Constants.MAIN_FRAME_DIMENSION_WIDTH, Constants.MAIN_FRAME_DIMENSION_HEIGHT));
        this.setLayout(new GridLayout(Constants.MAIN_FRAME_GRID_ROWS, Constants.MAIN_FRAME_GRID_COLS));

        this.tab = new JTabbedPane();

        this.readerPanel = new ReaderPanel();
        this.bookPanel = new BookPanel();
        this.authorPanel = new AuthorPanel();
        this.borrowBookPanel = new BorrowBookPanel();
        this.referencePanel = new ReferencePanel();

        this.borrowBookPanel.addReaderComboBox(readerPanel.getReadersComboBox(), readerPanel.getIdReaderColl());
        this.borrowBookPanel.addBookComboBox(bookPanel.getBookTitleCombo(), bookPanel.getIdBookColl());
        this.bookPanel.addComboBox(authorPanel.getAuthorsComboBox(), authorPanel.getCollectionOfAuthorsId());

        this.tab.add("Readers", readerPanel);
        this.tab.add("Books", bookPanel);
        this.tab.add("Author", authorPanel);

        this.tab.add("Borrow", borrowBookPanel);
        this.tab.add("Reference", referencePanel);

        this.add(tab);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}