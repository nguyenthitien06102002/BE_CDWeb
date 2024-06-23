package net.javaguides.CD_Web_backend.controller;


import lombok.AllArgsConstructor;
import net.javaguides.CD_Web_backend.dto.ChangePasswordRequest;
import net.javaguides.CD_Web_backend.dto.ForgotPasswordRequest;
import net.javaguides.CD_Web_backend.dto.LoginRequest;
import net.javaguides.CD_Web_backend.dto.UsersDto;
import net.javaguides.CD_Web_backend.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UsersService usersService;


    @PostMapping
    public ResponseEntity<UsersDto> createUser(@RequestBody UsersDto usersDto){
        String ipAddress = getClientIpAddress();
        usersDto.setIP(ipAddress);
        String hashedPassword = sha256(usersDto.getPassword());
        usersDto.setPassword(hashedPassword);
        UsersDto savedUsers = usersService.createUser(usersDto);
        return new ResponseEntity<>(savedUsers, HttpStatus.CREATED);


    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest loginRequest){
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();


        String hashedPassword = sha256(password);
        UsersDto usersDto = usersService.getUserByEmailAndPassWord(email, hashedPassword);
        if(usersDto != null){
            return ResponseEntity.ok(usersDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email or password is incorrect");
        }

    }






    //lay dia chi IP
    private String getClientIpAddress() {
        try {
            Socket socket = new Socket("google.com", 80); // Kết nối tạm thời đến một host bất kỳ
            InetAddress address = socket.getLocalAddress();
            return address.getHostAddress();
        } catch (Exception e) {
            return "Unknown";
        }
    }

    // Hàm mã hóa SHA-256
    private String sha256(String password) {
        try {
            // Tạo đối tượng MessageDigest với thuật toán SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Mã hóa mật khẩu
            byte[] encodedHash = digest.digest(password.getBytes());

            // Chuyển đổi byte array thành chuỗi hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Xử lý lỗi nếu thuật toán không tồn tại
            e.printStackTrace();
            return null;
        }
    }
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        String email = changePasswordRequest.getEmail();
        String oldPassword = changePasswordRequest.getOldPassword();
        String newPassword = changePasswordRequest.getNewPassword();

        String hashedOldPassword = sha256(oldPassword);
        String hashedNewPassword = sha256(newPassword);

        boolean isPasswordChanged = usersService.changePassword(email, hashedOldPassword, hashedNewPassword);

        if (isPasswordChanged) {
            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect");
        }
    }
    @PutMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        String email = forgotPasswordRequest.getEmail();
        String newPassword = forgotPasswordRequest.getNewPassword();

        String hashedNewPassword = sha256(newPassword);

        boolean isPasswordUpdated = usersService.updatePasswordByEmail(email, hashedNewPassword);

        if (isPasswordUpdated) {
            return ResponseEntity.ok("Password updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update password");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<UsersDto> allUsers = usersService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }
    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updateUserStatus(@PathVariable Long id, @RequestBody Map<String, Long> status) {
        boolean isUpdated = usersService.updateUserStatus(id, status.get("status"));
        if (isUpdated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update user status");
        }
    }


}
