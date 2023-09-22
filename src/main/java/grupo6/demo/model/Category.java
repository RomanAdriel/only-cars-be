package grupo6.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(unique = true)
    private String title;
    private String description;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image = new Image();

}
