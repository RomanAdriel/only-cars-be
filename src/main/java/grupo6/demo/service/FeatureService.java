package grupo6.demo.service;

import grupo6.demo.model.Feature;
import grupo6.demo.repository.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeatureService {
    @Autowired
    private FeatureRepository repository;

    public List<Feature> getAll() {
        return new ArrayList<>(repository.findAll());
    }

    public List<Feature> getAllByProductId(Integer productId) {
        return new ArrayList<>(repository.getFeaturesByProductId(productId));

    }

    public Feature getFeatureByTitle(String title) {
        return repository.getFeatureByTitle(title);
    }

    public Feature create(Feature feature) {
        return repository.save(feature);
    }
}
