package grupo6.demo.repository;

import grupo6.demo.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    @Query("FROM Reservation r WHERE r.product.id = ?1")
    List<Reservation> findReservationByProductId(Integer id);

    @Query("FROM Reservation r WHERE r.userEmail = ?1")
    List<Reservation> findReservationByEmail(String email);

    @Query(value = "SELECT r.* FROM reservation r WHERE r.user_email = ?1 order by r.start_date desc", nativeQuery =
            true)
    List<Reservation> findReservationByEmailNoDTO(String email);
}
