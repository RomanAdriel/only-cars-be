package grupo6.demo.controller;

import grupo6.demo.model.UserFavorite;
import grupo6.demo.service.UserFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoritos")
public class UserFavoriteController {

    @Autowired
    private UserFavoriteService service;

    @PostMapping
    public ResponseEntity<?> addFavorite(@RequestBody UserFavorite favorite) {

        ResponseEntity<?> response;
        try {
            UserFavorite newFavorite = service.addFavorite(favorite);
            response = ResponseEntity.status(HttpStatus.CREATED).body(newFavorite);
        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return response;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserFavorite>> getByEmail(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getFavoritesByUserEmail(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {

        ResponseEntity<?> response;

        try {
            service.deleteFavorite(id);
            response = ResponseEntity.ok().body("Favorito eliminado");
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
        }

        return response;
    }
}
