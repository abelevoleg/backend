package com.geekbrains.internship.warehouse.utils;

import com.geekbrains.internship.warehouse.entities.Category;
import com.geekbrains.internship.warehouse.entities.Product;
import com.geekbrains.internship.warehouse.repositories.specifications.ProductSpecifications;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

@Getter
public class ProductFilter {
    private Specification<Product> spec;
    private StringBuilder filterDefinition;

    public ProductFilter(Map<String, String> map, List<Category> categories) {
        this.spec = Specification.where(null);
        this.filterDefinition = new StringBuilder();

        if (map.containsKey("title") && !map.get("title").isEmpty()) {
            String title = map.get("title");
            spec = spec.and(ProductSpecifications.titleLike(title));
            filterDefinition.append("&title=").append(title);
        }
        if (categories != null && !categories.isEmpty()) {
            Specification specCategories = null;
            for (Category c : categories) {
                if (specCategories == null) {
                    specCategories = ProductSpecifications.categoryIs(c);
                } else {
                    specCategories = specCategories.or(ProductSpecifications.categoryIs(c));
                }
            }
            spec = spec.and(specCategories);
        }
    }
}
