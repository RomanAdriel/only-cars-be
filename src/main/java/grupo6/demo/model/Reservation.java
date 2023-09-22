package grupo6.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String userEmail;
    private LocalDate startDate;
    private LocalDate endDate;
    private String userFirstName;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", userFirstName='" + userFirstName + '\'' +
                ", product=" + product +
                '}';
    }
}
