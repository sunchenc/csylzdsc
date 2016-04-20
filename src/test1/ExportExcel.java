/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test1;

/**
 *
 * @author ck
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportExcel {

    JTable table;
    FileOutputStream fos;
    JFileChooser jfc = new JFileChooser();

    public ExportExcel(JTable table) {
        this.table = table;
        jfc.addChoosableFileFilter(new FileFilter() {
            public boolean accept(File file) {
                return (file.getName().indexOf("xls") != -1);
            }

            public String getDescription() {
                return "Excel";
            }
        });

        jfc.showSaveDialog(null);
        File file = jfc.getSelectedFile();
        try {
            this.fos = new FileOutputStream(file + ".xls");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void export() {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet hs = wb.createSheet();
        TableModel tm = table.getModel();
        int row = tm.getRowCount();
        int cloumn = tm.getColumnCount();
        for (int i = 0; i < row + 1; i++) {
            HSSFRow hr = hs.createRow(i);
            for (int j = 0; j < cloumn; j++) {
                if (i == 0) {
                    String value = tm.getColumnName(j);
                    int len = value.length();
                    HSSFRichTextString srts = new HSSFRichTextString(value);
                    HSSFCell hc = hr.createCell((short) j);
                    hc.setCellValue(srts);
                } else {
                    System.out.println("vlue  " + tm.getValueAt(i - 1, j));
                    if (tm.getValueAt(i - 1, j) != null) {
                        String value = tm.getValueAt(i - 1, j).toString();
                        HSSFRichTextString srts = new HSSFRichTextString(value);
                        HSSFCell hc = hr.createCell((short) j);
                        if (value.equals("") || value == null) {
                            hc.setCellValue(new HSSFRichTextString(""));
                        } else {
                            hc.setCellValue(srts);
                        }
                    }
                }
            }
        }

        try {
            wb.write(fos);
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
