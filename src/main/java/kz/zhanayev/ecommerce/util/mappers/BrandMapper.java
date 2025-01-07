package kz.zhanayev.ecommerce.util.mappers;

import kz.zhanayev.ecommerce.dto.BrandDTO;
import kz.zhanayev.ecommerce.models.Brand;

public class BrandMapper {
    public static BrandDTO toDTO(Brand brand) {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(brand.getId());
        brandDTO.setName(brand.getName());
        brandDTO.setDescription(brand.getDescription());
        return brandDTO;
    }

    public static Brand toEntity(BrandDTO brandDTO) {
        Brand brand = new Brand();
        brand.setId(brandDTO.getId());
        brand.setName(brandDTO.getName());
        brand.setDescription(brandDTO.getDescription());
        return brand;
    }
}
