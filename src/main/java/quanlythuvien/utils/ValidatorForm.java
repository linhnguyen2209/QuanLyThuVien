/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlythuvien.utils;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 */
public class ValidatorForm {

    public static boolean isDate(JTextField field, StringBuilder sb, String errorMessage) {
        boolean test = true;
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd");
        int year, month = 1, day = 1;
        try {
            String[] dob = field.getText().split("-");
            System.out.println(Arrays.toString(dob));
            if (field.getText().equals("")) {
                sb.append("Vui lòng nhập năm-tháng-ngày!\n");
            } else if (dob.length < 3 || dob.length > 3) {
                sb.append("Vui lòng nhập đúng định dạng yyyy-MM-dd\n");
            } else {
                StringBuilder strDate = new StringBuilder();
                try {
                    year = Integer.parseInt(dob[0]);
                    if (year <= 0) {
                        sb.append("Vui lòng nhập năm lớn hơn 0!\n");
                    }
                    strDate.append(String.valueOf(year));
                    strDate.append("-");
                } catch (Exception e) {
                    sb.append("Vui lòng nhập năm bằng số").append("\n");
                    test = false;
                }
                try {
                    month = Integer.parseInt(dob[1]);
                    if (month <= 0 || month > 12) {
                        sb.append("Vui lòng nhập tháng trong đoạn [1, 12]!\n");
                    }
                    strDate.append(String.valueOf(month));
                    strDate.append("-");
                } catch (Exception e) {
                    sb.append("Vui lòng nhập tháng bằng số").append("\n");
                    test = false;
                }
                try {
                    day = Integer.parseInt(dob[2]);
                    strDate.append(String.valueOf(day));
                } catch (Exception e) {
                    sb.append("Vui lòng nhập ngày bằng số").append("\n");
                    test = false;
                }
                try {
                    int d = 0;

                    if (month > 0 && month <= 12) {
                        switch (month) {
                            case 1:
                            case 3:
                            case 5:
                            case 7:
                            case 8:
                            case 10:
                            case 12:
                                d = 31;
                                break;
                            case 4:
                            case 6:
                            case 9:
                            case 11:
                                d = 30;
                                break;
                            case 2:
                                year = Integer.parseInt(dob[0]);
                                if (year > 0) {
                                    boolean flag = ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
                                    if (flag) {
                                        d = 29;
                                    } else {
                                        d = 28;
                                    }
                                }
                                break;
                        }
                    }else{
                        d = 31;
                    }
                    if (day <= 0 || day > d) {
                        sb.append("Vui lòng nhập ngày trong đoạn[1," + d + "]");
                    }
                    strDate.append(String.valueOf(day));
                } catch (Exception e) {
                    sb.append("Vui lòng nhập tháng bằng số").append("\n");
                    test = false;
                }

                Date date = sdf.parse(strDate.toString());
                test = true;
            }
        } catch (Exception e) {
            sb.append(errorMessage).append("\n");
            test = false;
        }
        return test;
    }

}
