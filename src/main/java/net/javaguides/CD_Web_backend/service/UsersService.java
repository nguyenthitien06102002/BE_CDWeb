package net.javaguides.CD_Web_backend.service;

import net.javaguides.CD_Web_backend.dto.UsersDto;
import net.javaguides.CD_Web_backend.entity.Users;

import java.util.List;

public interface UsersService {
    UsersDto createUser(UsersDto usersDto);
    UsersDto getUserByEmailAndPassWord(String email, String password);
    boolean changePassword(String email, String oldPassword, String newPassword);

    boolean updatePasswordByEmail(String email, String hashedNewPassword);

    List<UsersDto> getAllUsers();
    boolean updateUserStatus(Long id, long newStatus);

}
