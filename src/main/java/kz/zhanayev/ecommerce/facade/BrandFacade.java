package kz.zhanayev.ecommerce.facade;

import kz.zhanayev.ecommerce.dto.BrandDTO;
import kz.zhanayev.ecommerce.models.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandFacade {
    // Преобразование из Brand в BrandDTO
    public BrandDTO toDTO(Brand brand) {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(brand.getId());
        brandDTO.setName(brand.getName());
        brandDTO.setDescription(brand.getDescription());
        return brandDTO;
    }

    // Преобразование из BrandDTO в Brand
    public Brand toEntity(BrandDTO brandDTO) {
        Brand brand = new Brand();
        brand.setId(brandDTO.getId());
        brand.setName(brandDTO.getName());
        brand.setDescription(brandDTO.getDescription());
        return brand;
    }
}
