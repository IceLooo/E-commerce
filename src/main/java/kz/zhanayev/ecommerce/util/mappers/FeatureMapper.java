package kz.zhanayev.ecommerce.util.mappers;

import kz.zhanayev.ecommerce.dto.FeatureDTO;
import kz.zhanayev.ecommerce.models.Feature;
import kz.zhanayev.ecommerce.models.Product;

public class FeatureMapper {
    public static FeatureDTO toDTO(Feature feature) {
        FeatureDTO featureDTO = new FeatureDTO();
        featureDTO.setId(feature.getId());
        featureDTO.setName(feature.getName());
        featureDTO.setValue(feature.getValue());
        featureDTO.setProductId(feature.getProduct().getId());
        return featureDTO;
    }

    public static Feature toEntity(FeatureDTO featureDTO, Product product) {
        Feature feature = new Feature();
        feature.setId(featureDTO.getId());
        feature.setName(featureDTO.getName());
        feature.setValue(featureDTO.getValue());
        feature.setProduct(product);
        return feature;
    }
}
