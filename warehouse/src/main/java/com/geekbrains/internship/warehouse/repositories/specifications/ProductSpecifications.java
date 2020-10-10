package com.geekbrains.internship.warehouse.repositories.specifications;

import com.geekbrains.internship.warehouse.entities.Category;
import com.geekbrains.internship.warehouse.entities.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {
    public static Specification<Product> titleLike(String title) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", title));
    }

    public static Specification<Product> categoryIs(Category category) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> {
//            Join join = root.join("categories");
//            return criteriaBuilder.equal(join.get("id"), category.getId());
             return criteriaBuilder.isMember(category, root.get("categories"));
        };
    }
}
