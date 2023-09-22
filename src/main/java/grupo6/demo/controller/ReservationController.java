package grupo6.demo.controller;

import grupo6.demo.DTO.ReservationDTO;
import grupo6.demo.model.Reservation;
import grupo6.demo.service.ReservationService;
import grupo6.demo.service.SendGridServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservationController {
    @Autowired
    private ReservationService service;

    @Autowired
    private SendGridServiceImpl sendGridService;

    @PostMapping("productos/{productId}")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody Reservation reservation,
                                                            @PathVariable Integer productId) {
        ResponseEntity response;
        try {
            ReservationDTO reservationDTO = service.create(reservation, productId);
            sendGridService.sendEmail(reservation, reservation.getUserEmail(), reservation.getUserFirstName());
            response = ResponseEntity.ok(reservationDTO);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body( e.getMessage());
        }
        return response;
    }


    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @GetMapping("productos/{productId}")
    public ResponseEntity<List<Reservation>> getAllByProductId(@PathVariable Integer productId) {
        ResponseEntity response;
        try{
            List<ReservationDTO> reservas = service.getAllByProductId(productId);
            response = ResponseEntity.status(HttpStatus.OK).body(reservas);
        } catch (Exception e){
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        ResponseEntity response;
        try{
            ReservationDTO reservationDTO = service.getById(id);
            response = ResponseEntity.status(HttpStatus.OK).body(reservationDTO);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Integer id){
        ResponseEntity response;
        try{
            service.delete(id);
            response = ResponseEntity.status(HttpStatus.OK).body("Se elimino la reserva de manera exitosa.");
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return response;
    }
    @GetMapping("usuario/{email}")
    public ResponseEntity getAllByEmail(@PathVariable String email){
        ResponseEntity response;
        try{
            List<ReservationDTO> listReservationDTO = service.getAllByEmail(email);
            response = ResponseEntity.status(HttpStatus.OK).body(listReservationDTO);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return response;
    }

    @GetMapping("usuarios/{email}")
    public ResponseEntity<?> getAllByEmailNoDTO(@PathVariable String email){
        ResponseEntity<?> response;
        try{
            List<Reservation> listReservation = service.getAllByEmailNoDTO(email);
            response = ResponseEntity.status(HttpStatus.OK).body(listReservation);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return response;
    }
}
