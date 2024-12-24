package kz.zhanayev.ecommerce.repositories;

import kz.zhanayev.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
