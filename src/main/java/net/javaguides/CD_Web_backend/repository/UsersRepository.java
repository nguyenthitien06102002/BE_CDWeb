package net.javaguides.CD_Web_backend.repository;

import net.javaguides.CD_Web_backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByEmailAndPassword(String email, String password);

    Users findByEmail(String email);


}
