package kz.zhanayev.ecommerce.services.impl;

import kz.zhanayev.ecommerce.dto.BrandDTO;
import kz.zhanayev.ecommerce.exceptions.NotFoundException;
import kz.zhanayev.ecommerce.models.Brand;
import kz.zhanayev.ecommerce.repositories.BrandRepository;
import kz.zhanayev.ecommerce.services.BrandService;
import kz.zhanayev.ecommerce.util.mappers.BrandMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public BrandDTO createBrand(BrandDTO brandDTO) {
        Brand brand = BrandMapper.toEntity(brandDTO);
        Brand savedBrand = brandRepository.save(brand);
        return BrandMapper.toDTO(savedBrand);
    }

    @Override
    public BrandDTO updateBrand(Long id, BrandDTO brandDTO) {
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Бренд не найден по идентификатору: " + id));
        existingBrand.setName(brandDTO.getName());
        existingBrand.setDescription(brandDTO.getDescription());
        Brand updatedBrand = brandRepository.save(existingBrand);
        return BrandMapper.toDTO(updatedBrand);
    }

    @Override
    public void deleteBrand(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new NotFoundException("Бренд не найден по идентификатору: " + id);
        }
        brandRepository.deleteById(id);
    }

    @Override
    public List<BrandDTO> getAllBrands() {
        return brandRepository.findAll()
                .stream()
                .map(BrandMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BrandDTO getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Бренд не найден по идентификатору: " + id));
        return BrandMapper.toDTO(brand);
    }
}
