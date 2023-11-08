package quanlythuvien.utils;

import quanlythuvien.entity.NguoiDung;

/**
 *
 * @author Linh
 */
public class Auth {

    // Đối tượng chứa thông tin ng dùng sau khi đăng nhập
    public static NguoiDung user = null;
    public static NguoiDung userXacThuc = null;// khi người dùng xác thực quên mk thì lưu vào đây
    public static NguoiDung nguoiDungDangKy = null;// khi người dùng nhấn đăng ký thì lưu tạm vào đây

    
    // Xóa thông tin của ng sử dụng khi có yêu cầu đăng xuất   
    public static void clear() {
        Auth.user = null;
        Auth.userXacThuc = null;
    }

    // Kiểm tra xem đăng nhập hay chưa và trả về đn hay chưa. 
    public static boolean isLogin() {
        return Auth.user != null;
    }

    // Kiểm tra xem có phải là quản lý hay không
    public static boolean isManager() {
        return Auth.isLogin() && user.getMaLoaiNguoiDung().equals("LND001");
    }

    // Kiểm tra xem có phải là thủ thư hay không
    public static boolean isLibrarian() {
        return Auth.isLogin() && user.getMaLoaiNguoiDung().equals("LND002");
    }
}
