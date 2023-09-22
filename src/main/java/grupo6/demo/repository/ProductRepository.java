package grupo6.demo.repository;

import grupo6.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("FROM Product p WHERE p.name = ?1")
    List<Product> getProductByName(String name);

    @Query("SELECT p FROM Product p WHERE p.name = :name OR p.description = :description")
    List<Product> findByNameOrDescription(String name, String description);

    @Query("SELECT p FROM Product p WHERE p.vehicule_category.title = :category")
    List<Product> findByCategoryName(String category);

    @Query("SELECT p, f FROM Product p LEFT JOIN " +
            "p.addedFeatures f WHERE p.vehicule_category.title = :category OR " +
            "f.title = :feature")
    List<Product> findByCategoryOrFeature(String category, String feature);

}
