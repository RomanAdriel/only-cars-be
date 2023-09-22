package grupo6.demo.repository;

import grupo6.demo.model.UserFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Integer> {

    @Query("FROM UserFavorite uf WHERE uf.userEmail = ?1")
    List<UserFavorite> getFavoritesByEmail(String email);

}
