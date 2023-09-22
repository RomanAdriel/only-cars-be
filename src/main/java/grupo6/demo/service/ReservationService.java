package grupo6.demo.service;

import grupo6.demo.DTO.ReservationDTO;
import grupo6.demo.model.Product;
import grupo6.demo.model.Reservation;
import grupo6.demo.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService{
    @Autowired
    private ReservationRepository repository;
    @Autowired
    private ProductService productService;


    public ReservationDTO create(Reservation reservation, Integer productId) throws Exception {
        ReservationDTO reservationDTO;
        Product product = productService.findById(productId);
        reservation.setProduct(product);
        if(reservation.getUserEmail() == null||
                reservation.getUserEmail().isEmpty() ||
                reservation.getUserFirstName() == null ||
                reservation.getUserFirstName().isEmpty()) {
            throw new Exception("Falta alguno de los campos.");
        }
        if(isDateRangeAvailableForReservation(reservation)){
            Reservation reservationEnBD = repository.save(reservation);
            reservationDTO = classToDTO(reservationEnBD);
            return reservationDTO;
        }
        else{
            throw new Exception("La fecha solicitada no se encuentra disponible.");
        }
    }


    public void delete(Integer id) throws Exception {
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
        else{
            throw new Exception("No se encontro reserva para eliminar con el id proporcionado.");
        }
    }

    public Reservation update(Reservation t) throws Exception {
        return null;
    }

    public ReservationDTO getById(Integer id) throws Exception {
        Optional<Reservation> reservation = repository.findById(id);
        ReservationDTO reservationDTO;
        if (reservation.isEmpty()){
            throw new Exception("No se encontro reserva con el ID proporcionado.");
        }
        reservationDTO = classToDTO(reservation.get());
        return reservationDTO;
    }

    public List<ReservationDTO> getAll() {
        List<Reservation> reservas = repository.findAll();
        List<ReservationDTO> reservasDTO = new ArrayList<>();
        for(Reservation reserva: reservas){
            reservasDTO.add(classToDTO(reserva));
        }
        return reservasDTO;
    }

    public List<ReservationDTO> getAllByProductId(Integer productId) {
        List<Reservation> reservas = repository.findReservationByProductId(productId);
        List<ReservationDTO> reservasDTO = new ArrayList<>();
        for(Reservation reserva: reservas){
            reservasDTO.add(classToDTO(reserva));
        }
        return reservasDTO;
    }

    public boolean isDateRangeAvailableForReservation(Reservation reservation) throws Exception {
        List<ReservationDTO> reservations = getAllByProductId(reservation.getProduct().getId());
        for (ReservationDTO productReservation : reservations) {
            if (!reservation.getEndDate().isBefore(productReservation.getStartDate()) &&
                    !reservation.getStartDate().isAfter(productReservation.getEndDate())) {
                    return false;
            }
        }
        return true;
    }

    public List<ReservationDTO> getAllByEmail(String email) {
        List<Reservation> reservas = repository.findReservationByEmail(email);
        List<ReservationDTO> reservasDTO = new ArrayList<>();
        for(Reservation reserva: reservas){
            reservasDTO.add(classToDTO(reserva));
        }
        return reservasDTO;
    }

    public List<Reservation> getAllByEmailNoDTO(String email) {
        List<Reservation> reservas = repository.findReservationByEmailNoDTO(email);
        return reservas;
    }

    public ReservationDTO classToDTO(Reservation reservation){
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setUserEmail(reservation.getUserEmail());
        reservationDTO.setEndDate(reservation.getEndDate());
        reservationDTO.setStartDate(reservation.getStartDate());
        reservationDTO.setProduct_id(reservation.getProduct().getId());
        return reservationDTO;
    }

    public boolean isDateRangeAvailableForReservationByDates(LocalDate startDate, LocalDate endDate, Integer productId) throws Exception {
        List<ReservationDTO> reservations = getAllByProductId(productId);
        for (ReservationDTO productReservation : reservations) {
            if (!endDate.isBefore(productReservation.getStartDate()) &&
                    !startDate.isAfter(productReservation.getEndDate())) {
                return false;
            }
        }
        return true;
    }

}
