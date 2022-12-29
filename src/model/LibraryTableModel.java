package model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


public class LibraryTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private ResultSet result;
    private int rowCount;
    private int columnCount;
    private List<Object> data = new ArrayList<>();

    public LibraryTableModel(ResultSet rs) throws Exception
    {
        this.setResultSet(rs);
    }

    public void setResultSet(ResultSet resultSet) throws Exception
    {
        this.result = resultSet;
        ResultSetMetaData metaData=resultSet.getMetaData();
        columnCount = metaData.getColumnCount();

        while(resultSet.next()){

            Object[] row = new Object[columnCount];

            for(int j = 0; j < columnCount;j++){
                row[j] = resultSet.getObject(j + 1);
            }

            data.add(row);
            rowCount++;
        }
    }

    public int getColumnCount(){
        return columnCount;
    }

    public int getRowCount(){
        return rowCount;
    }

    public Object getValueAt(int rowIndex, int columnIndex){
        Object[] row = (Object[]) data.get(rowIndex);
        return row[columnIndex];
    }

    public String getColumnName(int columnIndex){
        try{
            ResultSetMetaData metaData = result.getMetaData();
            return metaData.getColumnName(columnIndex + 1);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}