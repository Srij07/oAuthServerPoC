package oauth.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import oauth.server.model.User;

public interface UserDetailRepository extends JpaRepository<User,Integer> {


    Optional<User> findByUsername(String name);
}
