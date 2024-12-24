package kz.zhanayev.ecommerce.facade;

import kz.zhanayev.ecommerce.dto.FeatureDTO;
import kz.zhanayev.ecommerce.models.Feature;
import kz.zhanayev.ecommerce.models.Product;
import org.springframework.stereotype.Component;

@Component
public class FeatureFacade {
    // Преобразование из Feature в FeatureDTO
    public FeatureDTO toDTO(Feature feature) {
        if (feature == null) {
            return null;
        }
        FeatureDTO featureDTO = new FeatureDTO();
        featureDTO.setId(feature.getId());
        featureDTO.setName(feature.getName());
        featureDTO.setValue(feature.getValue());
        featureDTO.setProductId(feature.getProduct() != null ? feature.getProduct().getId() : null);
        return featureDTO;
    }

    // Преобразование из FeatureDTO в Feature
    public Feature toEntity(FeatureDTO featureDTO, Product product) {
        Feature feature = new Feature();
        feature.setId(featureDTO.getId());
        feature.setName(featureDTO.getName());
        feature.setValue(featureDTO.getValue());
        feature.setProduct(product);
        return feature;
    }
}
