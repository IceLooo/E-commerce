package kz.zhanayev.ecommerce.services.impl;

import kz.zhanayev.ecommerce.dto.FeatureDTO;
import kz.zhanayev.ecommerce.exceptions.NotFoundException;
import kz.zhanayev.ecommerce.models.Feature;
import kz.zhanayev.ecommerce.models.Product;
import kz.zhanayev.ecommerce.repositories.FeatureRepository;
import kz.zhanayev.ecommerce.repositories.ProductRepository;
import kz.zhanayev.ecommerce.services.FeatureService;
import kz.zhanayev.ecommerce.util.mappers.FeatureMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeatureServiceImpl implements FeatureService {

    private final FeatureRepository featureRepository;
    private final ProductRepository productRepository;

    public FeatureServiceImpl(FeatureRepository featureRepository, ProductRepository productRepository) {
        this.featureRepository = featureRepository;
        this.productRepository = productRepository;
    }

    @Override
    public FeatureDTO createFeature(FeatureDTO featureDTO) {
        Product product = productRepository.findById(featureDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("Продукт не найден с идентификатором: " + featureDTO.getProductId()));
        Feature feature = FeatureMapper.toEntity(featureDTO, product);
        Feature savedFeature = featureRepository.save(feature);
        return FeatureMapper.toDTO(savedFeature);
    }

    @Override
    public List<FeatureDTO> getFeaturesByProductId(Long productId) {
        return featureRepository.findByProductId(productId)
                .stream()
                .map(FeatureMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FeatureDTO updateFeature(Long id, FeatureDTO featureDTO) {
        Feature existingFeature = featureRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Характеристика не найдена по идентификатору: " + id));

        Product product = productRepository.findById(featureDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("Продукт не найден с идентификатором: " + featureDTO.getProductId()));

        existingFeature.setName(featureDTO.getName());
        existingFeature.setValue(featureDTO.getValue());
        existingFeature.setProduct(product);

        Feature updatedFeature = featureRepository.save(existingFeature);
        return FeatureMapper.toDTO(updatedFeature);
    }

    @Override
    public void deleteFeature(Long id) {
        if (!featureRepository.existsById(id)) {
            throw new NotFoundException("Характеристика не найдена по идентификатору: " + id);
        }
        featureRepository.deleteById(id);
    }

    @Override
    public List<FeatureDTO> getAllFeatures() {
        return featureRepository.findAll()
                .stream()
                .map(FeatureMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FeatureDTO getFeatureById(Long id) {
        Feature feature = featureRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Характеристика не найдена по идентификатору: " + id));
        return FeatureMapper.toDTO(feature);
    }
}
