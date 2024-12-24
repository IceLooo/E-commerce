package kz.zhanayev.ecommerce.services;

import kz.zhanayev.ecommerce.dto.BrandDTO;

import java.util.List;

public interface BrandService {
    BrandDTO createBrand(BrandDTO brandDTO);
    BrandDTO updateBrand(Long id, BrandDTO brandDTO);
    void deleteBrand(Long id);
    List<BrandDTO> getAllBrands();
    BrandDTO getBrandById(Long id);
}
