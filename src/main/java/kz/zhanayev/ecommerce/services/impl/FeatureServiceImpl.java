package kz.zhanayev.ecommerce.services.impl;

import kz.zhanayev.ecommerce.dto.FeatureDTO;
import kz.zhanayev.ecommerce.exceptions.FeatureNotFoundException;
import kz.zhanayev.ecommerce.facade.FeatureFacade;
import kz.zhanayev.ecommerce.models.Feature;
import kz.zhanayev.ecommerce.models.Product;
import kz.zhanayev.ecommerce.repositories.FeatureRepository;
import kz.zhanayev.ecommerce.repositories.ProductRepository;
import kz.zhanayev.ecommerce.services.FeatureService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeatureServiceImpl implements FeatureService {

    private final FeatureRepository featureRepository;
    private final ProductRepository productRepository;
    private final FeatureFacade featureFacade;

    public FeatureServiceImpl(FeatureRepository featureRepository, ProductRepository productRepository, FeatureFacade featureFacade) {
        this.featureRepository = featureRepository;
        this.productRepository = productRepository;
        this.featureFacade = featureFacade;
    }

    @Override
    public FeatureDTO createFeature(FeatureDTO featureDTO) {
        Product product = productRepository.findById(featureDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + featureDTO.getProductId()));
        Feature feature = featureFacade.toEntity(featureDTO, product);
        Feature savedFeature = featureRepository.save(feature);
        return featureFacade.toDTO(savedFeature);
    }

    @Override
    public List<FeatureDTO> getFeaturesByProductId(Long productId) {
        return featureRepository.findByProductId(productId)
                .stream()
                .map(featureFacade::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FeatureDTO updateFeature(Long id, FeatureDTO featureDTO) {
        Feature existingFeature = featureRepository.findById(id)
                .orElseThrow(() -> new FeatureNotFoundException("Feature not found with ID: " + id));

        Product product = productRepository.findById(featureDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + featureDTO.getProductId()));

        existingFeature.setName(featureDTO.getName());
        existingFeature.setValue(featureDTO.getValue());
        existingFeature.setProduct(product);

        Feature updatedFeature = featureRepository.save(existingFeature);
        return featureFacade.toDTO(updatedFeature);
    }

    @Override
    public void deleteFeature(Long id) {
        if (!featureRepository.existsById(id)) {
            throw new FeatureNotFoundException("Feature not found with ID: " + id);
        }
        featureRepository.deleteById(id);
    }


    @Override
    public List<FeatureDTO> getAllFeatures() {
        return featureRepository.findAll()
                .stream()
                .map(featureFacade::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FeatureDTO getFeatureById(Long id) {
        Feature feature = featureRepository.findById(id)
                .orElseThrow(() -> new FeatureNotFoundException("Feature not found with ID: " + id));
        return featureFacade.toDTO(feature);
    }

}
