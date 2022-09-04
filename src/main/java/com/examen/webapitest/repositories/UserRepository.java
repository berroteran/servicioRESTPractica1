package com.examen.webapitest.repositories;

import com.examen.webapitest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u  where u.email = :pEmail ")
    User findByEmail(String pEmail);

}
