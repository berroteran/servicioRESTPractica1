package com.examen.webapitest.services;

import com.examen.webapitest.entities.User;
import com.examen.webapitest.exceptions.BussinesException;
import com.examen.webapitest.exceptions.EmailException;
import com.examen.webapitest.repositories.UserRepository;
import com.examen.webapitest.security.JwtTokenProvider;
import com.examen.webapitest.utilerias.Fechas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    @Autowired private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<Object> findById(long id) {
        return Optional.of(userRepository.findById(id));
    }

    @ExceptionHandler(EmailException.class) public User save(User user) throws BussinesException {
        //try {
        //validate correo.
        if (!validateEmail(user)) {
            throw new EmailException("El correo indicado no es válido");
        }
        user = userRepository.save(user);
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


    public String signin(final User user) {
        String token = "";
        User userFinded = userRepository.findByEmail(user.getEmail());

        if (null != userFinded ) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
                token = createToken(userFinded.getEmail() );
                userFinded.setLastLoginDate(Fechas.getFechaHoraActual() );
                userFinded.setToken( token );
                userFinded.setActive(true);
                userRepository.save(userFinded );
            } catch (AuthenticationException e){
                throw new BussinesException(e.getMessage());
            }
        }
        return token;
    }

    public String createToken(String email) {
        return tokenProvider.createToken(email);
    }
}
