package grupo6.demo.repository;

import grupo6.demo.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Integer> {
    @Query(value = "select f.* from feature f left join product_feature pf on f.id = pf.feature_id where pf" +
            ".product_id = ?1", nativeQuery = true)
    List<Feature> getFeaturesByProductId(Integer productId);

    @Query("FROM Feature f WHERE f.title = ?1")
    Feature getFeatureByTitle(String title);

}
