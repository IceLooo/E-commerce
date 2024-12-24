package kz.zhanayev.ecommerce.services;

import kz.zhanayev.ecommerce.dto.FeatureDTO;

import java.util.List;

public interface FeatureService {
    FeatureDTO createFeature(FeatureDTO featureDTO);
    FeatureDTO updateFeature(Long id, FeatureDTO featureDTO);
    void deleteFeature(Long id);
    List<FeatureDTO> getAllFeatures();
    FeatureDTO getFeatureById(Long id);
    List<FeatureDTO> getFeaturesByProductId(Long productId);
}
