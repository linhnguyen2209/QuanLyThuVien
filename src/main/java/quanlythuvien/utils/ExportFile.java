package quanlythuvien.utils;

import java.awt.Component;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 *
 * @author Linh
 */
public class ExportFile {

    public static void exportToExcel(Component parent, JTable table, String sheetName) {

        // Tạo một Workbook cho file excel
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName); // đại diện cho 1 trang trong workbook

        // Lấy model của table
        TableModel model = table.getModel();
        
        // Tạo hàng tiêu đề(tên cột)
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
            try (FileOutputStream out = new FileOutputStream(selectedFilePath + ".xlsx")) {
                workbook.write(out);
                MsgBox.alert(parent, "Xuất file thành công!");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Thêm phần xử lý tùy chọn sau khi lấy đường dẫn
        }
    }

    public static void exportToWord(Component parent, String[] listTitel, String[] listData, String titleMain, JTable tbl) {
        try (XWPFDocument document = new XWPFDocument();) {

            XWPFParagraph paragraph = document.createParagraph();// Đại diện cho 1 đoạn văn bản trong file word
//            paragraph.setAlignment(ParagraphAlignment.CENTER);// căn giữa
            XWPFRun run = paragraph.createRun(); // XWPFRun đại diện cho 1 PHẦN VĂN BẢN trong đoạn văn bản (paragraph)
            XWPFRun run1; // đại diện cho phần văn bản (value)
            run.setText(titleMain);
            // định dạng cho titleMain
            run.setFontSize(15);
            run.setFontFamily("Times New Roman");
            run.setBold(true);
            run.setColor("0000FF"); // Màu văn bản: xanh lam
            paragraph.setSpacingAfter(200); // khoảng cách dưới của đoạn văn bản

            for (int i = 0; i < listTitel.length; i++) {
                paragraph = document.createParagraph(); // tạo đoạn văn bản mới để lưu phần thông tin phiếu

                // Tạo run bên trái bên phải ở giữa là tab chúng nằm cùng 1 dòng
                //bên trái
                run = paragraph.createRun(); // run tiêu đề
                run.setText(listTitel[i] + ":  ");
                run.setBold(true);
                run1 = paragraph.createRun();// run value
                run1.setText(listData[i]);
                // bên phải
                if (i < listTitel.length - 1) { // đề phòng i ở cuối sẽ gây ra lỗi(vì phiếu mượn sẽ có 1 dòng CUỐI chỉ có phần bên trái)
                    run1.addTab(); // thêm tab ở giữa
                    run1.addTab(); // thêm tab ở giữa nữa để khoảng cách 2 phần rộng ra cho đẹp
                    run = paragraph.createRun(); // run tiêu đè
                    i+=1; // để tăng index là tiêu đề kế sau tiêu đề thứ i
                    run.setText(listTitel[i] + ":  ");
                    run.setBold(true);
                    run1 = paragraph.createRun();// run value
                    run1.setText(listData[i]);
                }
            }
            // Đặt khoảng trắng dưới của đoạn văn bản
            paragraph.setSpacingAfter(200); // khoảng cách dưới, 200 là 200%chiều cao của văn bản hay là bằng gấp đôi chiều cao của 1 dòng văn bản

            paragraph = document.createParagraph();
            run = paragraph.createRun();
            run.setText("Phiếu mượn chi tiết:");
            run.setBold(true);
            run.setItalic(true);
            run.setColor("0000FF");

            TableModel tblModel = tbl.getModel();
            // Tạo một bảng mới với số dòng và số cột
            XWPFTable table = document.createTable(tblModel.getRowCount() + 1, tblModel.getColumnCount()); // +1 là dòng tên cột

            // Tạo hàng tên cột
            XWPFTableRow tableRow = table.getRow(0); // lấy ra hàng đầu tiên trong bảng
            for (int i = 0; i < tblModel.getColumnCount(); i++) {
                XWPFTableCell cell = tableRow.getCell(i);
                cell.setText(tblModel.getColumnName(i));
                // Đặt định dạng văn bản cho ô
                paragraph = cell.getParagraphs().get(0); // lấy phần tử đầu tiên từ danh sách các đoạn văn bản trong ô đó
                paragraph.setAlignment(ParagraphAlignment.CENTER);// căn giữa
                run = paragraph.createRun();
                run.setBold(true);
                cell.setWidth("4000");// độ rộng của ô
                cell.setColor("F0E68C");
            }

            // Lặp qua từng dòng của bảng
            for (int row = 0; row < tblModel.getRowCount(); row++) {
                tableRow = table.getRow(row+1); // cộng 1 vì hàng đầu tiên là tên cột
                // Lặp qua từng ô của dòng
                for (int col = 0; col < tblModel.getColumnCount(); col++) {
                    XWPFTableCell cell = tableRow.getCell(col);
                    // Đặt nội dung của ô
                    cell.setText(tblModel.getValueAt(row, col) + "");

                    // Đặt định dạng văn bản cho ô
                    paragraph = cell.getParagraphs().get(0);
                    paragraph.setAlignment(ParagraphAlignment.CENTER);// căn giữa
                }
            }

            paragraph = document.createParagraph();
            run = paragraph.createRun();
            run.setText("Xin cảm ơn!");
            run.setBold(true);
            run.setItalic(true);
            run.setColor("0000FF");

            String selectedFilePath;
            // Tạo một đối tượng FileChooser
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(parent);
            if (result == JFileChooser.APPROVE_OPTION) {
                // Lấy đường dẫn được chọn
                selectedFilePath = fileChooser.getSelectedFile().getPath();
                // Xuất dữ liệu ra file
                try (FileOutputStream out = new FileOutputStream(selectedFilePath + ".docx")) {
                    document.write(out);
                    MsgBox.alert(parent, "Xuất file thành công!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
