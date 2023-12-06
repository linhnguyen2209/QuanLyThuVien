package quanlythuvien.utils;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 */
public class ValidationForm {

    public static boolean isDate(JTextField field, Component parent, String tenTruong) {
        int year, month, day;
        try {
            String[] dob = field.getText().split("-");
            System.out.println(Arrays.toString(dob));
            if (field.getText().equals("")) {
                MsgBox.alert(parent, "Vui lòng nhập " + tenTruong + "!");
                return false;
            } else if (dob.length < 3 || dob.length > 3) {
                MsgBox.alert(parent, "Vui lòng nhập đúng định dạng yyyy-MM-dd!");
                return false;
            } else {
                try {
                    year = Integer.parseInt(dob[0]);
                    if (year <= 0) {
                        MsgBox.alert(parent, "Vui lòng nhập năm lớn hơn 0!!");
                        return false;
                    }
                } catch (Exception e) {
                    MsgBox.alert(parent, "Vui lòng nhập năm bằng số!");
                    return false;
                }
                try {
                    month = Integer.parseInt(dob[1]);
                    if (month <= 0 || month > 12) {
                        MsgBox.alert(parent, "Vui lòng nhập tháng trong đoạn [1, 12]!");
                        return false;
                    }
                } catch (Exception e) {
                    MsgBox.alert(parent, "Vui lòng nhập tháng bằng số!");
                    return false;

                }
                try {
                    day = Integer.parseInt(dob[2]);
                } catch (Exception e) {
                    MsgBox.alert(parent, "Vui lòng nhập ngày bằng số!");
                    return false;

                }
                int d = 0;

                if (month > 0 && month <= 12) {
                    switch (month) {
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
                        default:
                            d = 31;
                            break;
                    }
                }
                if (day <= 0 || day > d) {
                    MsgBox.alert(parent, "Vui lòng nhập ngày trong đoạn[1," + d + "]");
                    return false;
                }
            }
        } catch (Exception e) {
            MsgBox.alert(parent, "Lỗi ngày-tháng-năm!");
            return false;
        }
        return true;
    }

    public static boolean isTen(Component parents, JTextField tf, String tenTruong) {
        final String reTen = "^[\\p{L}\\p{M}\\s]+$"; // tên có dấu.
        if (tf.getText().equals("")) {
            MsgBox.alert(parents, "Vui lòng điền " + tenTruong + "!");
            tf.requestFocus();
            return false;
        }
        if (!tf.getText().matches(reTen)) {
            MsgBox.alert(parents, tenTruong + " không hợp lệ!");
            tf.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isMa(Component parents, JTextField tf, String tenTruong) {
        final String reMa = "^[a-zA-Z0-9_-]{2,30}$";
        if (tf.getText().equals("")) {
            MsgBox.alert(parents, "Vui lòng điền " + tenTruong + "!");
            tf.requestFocus();
            return false;
        }
        if (!tf.getText().matches(reMa)) {
            MsgBox.alert(parents, tenTruong + " không hợp lệ(ví dụ: abc123)!");
            tf.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isSDT(Component parents, JTextField tf, String tenTruong) {
        final String reSoDienThoai = "^0[0-9]{9}$";
        if (tf.getText().equals("")) {
            MsgBox.alert(parents, "Vui lòng điền " + tenTruong + "!");
            tf.requestFocus();
            return false;
        }
        if (!tf.getText().matches(reSoDienThoai)) {
            MsgBox.alert(parents, tenTruong + " không hợp lệ(ví dụ: 0123456789)!");
            tf.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isEmail(Component parents, JTextField tf, String tenTruong) {
        final String reEmail = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";
        if (tf.getText().equals("")) {
            MsgBox.alert(parents, "Vui lòng điền " + tenTruong + "!");
            tf.requestFocus();
            return false;
        }
        if (!tf.getText().matches(reEmail)) {
            MsgBox.alert(parents, tenTruong + " không hợp lệ(ví dụ: example@gmail.com)!");
            tf.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isSo(Component parents, JTextField tf, String tenTruong) {
        if (tf.getText().equals("")) {
            MsgBox.alert(parents, "Vui lòng điền " + tenTruong + "!");
            tf.requestFocus();
            return false;
        }
        try {
            int so = Integer.parseInt(tf.getText());
            if (so <= 0) {
                MsgBox.alert(parents, tenTruong + " phải là số nguyên dương!");
                tf.requestFocus();
                return false;
            }
        } catch (Exception e) {
            MsgBox.alert(parents, tenTruong + " chỉ được nhập số!");
            tf.requestFocus();
            return false;
        }
        return true;
    }

}
