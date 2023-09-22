package grupo6.demo.repository;
import grupo6.demo.model.Category;
import grupo6.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("FROM Category c WHERE c.title = ?1")
    List<Category> getCategoryByTitle(String title);


    @Query("SELECT p FROM Product p WHERE p.vehicule_category.id = :id")
    List<Product> findByCategory(Integer id);

    @Query("SELECT p FROM Product p WHERE p.vehicule_category.title = :title")
    List<Product> findByCategoryTitle(String title);
}
