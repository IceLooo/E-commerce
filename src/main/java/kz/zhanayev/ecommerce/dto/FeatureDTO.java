package kz.zhanayev.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeatureDTO {
    private Long id;
    private String name;
    private String value;
    private Long productId;
}
