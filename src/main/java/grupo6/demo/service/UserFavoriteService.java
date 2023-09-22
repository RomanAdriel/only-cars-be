package grupo6.demo.service;

import grupo6.demo.model.UserFavorite;
import grupo6.demo.repository.UserFavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserFavoriteService {

    @Autowired
    private UserFavoriteRepository repository;


    public List<UserFavorite> getFavoritesByUserEmail(String email) {

        return new ArrayList<>(repository.getFavoritesByEmail(email));

    }

    public UserFavorite addFavorite(UserFavorite userFavorite) {

        return repository.save(userFavorite);
    }

    public void deleteFavorite(Integer favoriteId) throws Exception {

        UserFavorite favorite = repository.findById(favoriteId).orElse(null);

        if (favorite != null) {
            repository.deleteById(favoriteId);
        } else {
            throw new Exception("No existe ese favorito para el usuario");
        }

    }
}
