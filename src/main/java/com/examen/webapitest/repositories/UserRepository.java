package com.examen.webapitest.repositories;

import com.examen.webapitest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
