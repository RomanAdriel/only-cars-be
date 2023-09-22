package grupo6.demo.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    @Override
    public String toString() {
        return "ReservationDTO{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", userEmail='" + userEmail + '\'' +
                ", product_id=" + product_id +
                '}';
    }

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String userEmail;
    private Integer product_id;


}
