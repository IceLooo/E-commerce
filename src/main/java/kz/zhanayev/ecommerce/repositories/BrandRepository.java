package kz.zhanayev.ecommerce.repositories;

import kz.zhanayev.ecommerce.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
