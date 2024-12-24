package kz.zhanayev.ecommerce.repositories;

import kz.zhanayev.ecommerce.models.Role;
import kz.zhanayev.ecommerce.models.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
