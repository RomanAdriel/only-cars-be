package grupo6.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(unique = true)
    private String name;
    private String description;
    private String car_brand;
    private double price;
    @ManyToOne
    private Category vehicule_category;
    private int passengers;
    private boolean air_conditioning;
    private boolean manual;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<Image> images = new ArrayList<>();

    @ManyToMany(cascade = {
            CascadeType.REMOVE,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "product_feature",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private List<Feature> addedFeatures;

}
