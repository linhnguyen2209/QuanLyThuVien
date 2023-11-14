/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlythuvien.utils;

import java.awt.Component;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 *
 * @author Linh
 */
public class ExportFile {

    public static void exportToExcel(Component parent, JTable table, String sheetName) {

        // Tạo một Workbook
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        // Lấy model của table
        TableModel model = table.getModel();

        // Tạo hàng tiêu đề
        Row rows = sheet.createRow(3); // tạo 1 dòng ở vị trí index 3 trong excel ( index bắt đầu từ 0), vì trừa lại 3 dòng để viết Tiêu đề bảng
        for (int i = 0; i < model.getColumnCount(); i++) {
            Cell cell = rows.createCell(i);
            cell.setCellValue(model.getColumnName(i));
        }

        // Tạo các hàng dữ liệu
        for (int i = 0; i < model.getRowCount(); i++) {
            Row row = sheet.createRow(i + 4);
            for (int j = 0; j < model.getColumnCount(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(model.getValueAt(i, j) + "");
            }
        }
        
        String selectedFilePath;
        // Tạo một đối tượng FileChooser
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            // Lấy đường dẫn được chọn
            selectedFilePath = fileChooser.getSelectedFile().getPath();
            // Xuất dữ liệu ra file Excel
            try (FileOutputStream out = new FileOutputStream(selectedFilePath+".xlsx")) {
                workbook.write(out);
                MsgBox.alert(parent, "Xuất file thành công!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Thêm phần xử lý tùy chọn sau khi lấy đường dẫn
        }

    }
}
