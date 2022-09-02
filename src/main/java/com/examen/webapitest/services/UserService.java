package com.examen.webapitest.services;

import com.examen.webapitest.entities.User;
import com.examen.webapitest.exceptions.BussinesException;
import com.examen.webapitest.exceptions.EmailException;
import com.examen.webapitest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    @Autowired private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<Object> findById(long id) {
        return Optional.of(repository.findById(id));
    }

    @ExceptionHandler(EmailException.class) public User save(User user) throws BussinesException {
        //try {
        //validate correo.
        if (!validateEmail(user)) {
            throw new EmailException("El correo indicado no es válido");
        }
        user = repository.save(user);
        //atch (Exception e){
        //  throw
        //
        return user;
    }

    private boolean validateEmail(User user) throws BussinesException {
        if (null == user)
            throw new BussinesException("No se ha especifado un correo electrónico");
        return user.getEmail().matches("");
    }


}
