package com.examen.webapitest.repositories;

import com.examen.webapitest.entities.RoleEntity;
import com.examen.webapitest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RolRepository extends JpaRepository<RoleEntity, Long> {

}
