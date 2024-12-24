package kz.zhanayev.ecommerce.repositories;

import kz.zhanayev.ecommerce.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
