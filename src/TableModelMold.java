import java.awt.Dimension;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TableModelMold {
  /**
   * Create a Table model with a generated Columns title
   * Then populate the result set to the given table
   * @param table
   * @param result
   * @param col
   * @param colName
   */
  public static void setTableModel (JTable table, List<String []> result, int col, String[] colName){

    DefaultTableModel model = (DefaultTableModel) table.getModel();
    table.setPreferredSize(new Dimension(500, 500));

    for (int idx = 0; idx < colName.length; idx ++) {
      model.addColumn(colName[idx]);
    }

    for (int idx = 0 ; idx < result.size(); idx ++){
      Object [] rowData = new Object[col];

      for (int colIdx = 0; colIdx < col; colIdx++){
        rowData [colIdx] = result.get(idx)[colIdx]; 
      }
      model.addRow(rowData);
    }
    table.setModel(model); 
  }
}
