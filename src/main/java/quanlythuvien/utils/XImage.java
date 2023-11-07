/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlythuvien.utils;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Linh
 */
public class XImage {
    public static Image getAppIcon(){
        URL url = XImage.class.getResource("/quanlythuvien/icon/logo.png");
        return new ImageIcon(url).getImage();       
    }
    public static boolean save(File src){
        File dst = new File("src\\main\\resources\\com\\mycompany\\logos", src.getName());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdir(); // Tạo thư mục nếu chưa tồn tại
        }
        try {
            Path from = Paths.get(src.toURI());
            Path to = Paths.get(dst.toURI());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            return true; // Trả về true nếu sao lưu thành công
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }
    
    public static ImageIcon read(String fileName){
        File path = new File("src\\main\\resources\\com\\mycompany\\logos", fileName);
        if (path.exists()) {
            return new ImageIcon(path.getAbsolutePath());
        } else {
            System.out.println("File not found: " + fileName);
            return null; // Trả về null nếu tệp không tồn tại
        }
    }
}
