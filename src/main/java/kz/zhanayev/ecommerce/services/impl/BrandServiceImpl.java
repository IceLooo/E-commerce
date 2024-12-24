package kz.zhanayev.ecommerce.services.impl;

import kz.zhanayev.ecommerce.dto.BrandDTO;
import kz.zhanayev.ecommerce.exceptions.BrandNotFoundException;
import kz.zhanayev.ecommerce.facade.BrandFacade;
import kz.zhanayev.ecommerce.models.Brand;
import kz.zhanayev.ecommerce.repositories.BrandRepository;
import kz.zhanayev.ecommerce.services.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandFacade brandFacade;

    public BrandServiceImpl(BrandRepository brandRepository, BrandFacade brandFacade) {
        this.brandRepository = brandRepository;
        this.brandFacade = brandFacade;
    }


    @Override
    public BrandDTO createBrand(BrandDTO brandDTO) {
        Brand brand = brandFacade.toEntity(brandDTO);
        Brand savedBrand = brandRepository.save(brand);
        return brandFacade.toDTO(savedBrand);
    }

    @Override
    public BrandDTO updateBrand(Long id, BrandDTO brandDTO) {
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new BrandNotFoundException("Brand not found with id: " + id));
        existingBrand.setName(brandDTO.getName());
        existingBrand.setDescription(brandDTO.getDescription());
        Brand updatedBrand = brandRepository.save(existingBrand);
        return brandFacade.toDTO(updatedBrand);
    }

    @Override
    public void deleteBrand(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new BrandNotFoundException("Brand not found with id: " + id);
        }
        brandRepository.deleteById(id);
    }

    @Override
    public List<BrandDTO> getAllBrands() {
        return brandRepository.findAll()
                .stream()
                .map(brandFacade::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BrandDTO getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new BrandNotFoundException("Brand not found with id: " + id));
        return brandFacade.toDTO(brand);
    }
}
