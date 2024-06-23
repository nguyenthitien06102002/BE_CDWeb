package net.javaguides.CD_Web_backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.CD_Web_backend.dto.UsersDto;
import net.javaguides.CD_Web_backend.entity.Users;
import net.javaguides.CD_Web_backend.mapper.UsersMapper;
import net.javaguides.CD_Web_backend.repository.UsersRepository;
import net.javaguides.CD_Web_backend.service.UsersService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UsersServiceIpml implements UsersService{
    private UsersRepository usersRepository;

    @Override
    public UsersDto createUser(UsersDto usersDto) {
        Users users = UsersMapper.mapToUsers(usersDto);
        Users saveUsers = usersRepository.save(users);
        return UsersMapper.mapToUsersDto(saveUsers);
    }

    @Override
    public UsersDto getUserByEmailAndPassWord(String email, String password) {
        Users users = usersRepository.findByEmailAndPassword(email, password);
        if (users == null) {
            return null;
        }
        return UsersMapper.mapToUsersDto(users);
    }
    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        Users users = usersRepository.findByEmailAndPassword(email, oldPassword);
        if (users != null) {
            users.setPassword(newPassword);
            usersRepository.save(users);
            return true;
        }
        return false;
    }
    public boolean updatePasswordByEmail(String email, String hashedNewPassword) {
        // Implement the actual database update logic
        Users user = usersRepository.findByEmail(email);
        if (user != null) {
            user.setPassword(hashedNewPassword);
            usersRepository.save(user);
            return true;
        }
        return false;
    }
    @Override
    public List<UsersDto> getAllUsers() {
        List<Users> usersList = usersRepository.findAll();
        List<UsersDto> usersDtoList = new ArrayList<>();
        for (Users user : usersList) {
            usersDtoList.add(UsersMapper.mapToUsersDto(user));
        }
        return usersDtoList;
    }

    @Override
    public boolean updateUserStatus(Long id, long newStatus) {
        Users user = usersRepository.findById(id).orElse(null);
        if (user != null) {
            user.setStatus(newStatus);
            usersRepository.save(user);
            return true;
        }
        return false;
    }


}
