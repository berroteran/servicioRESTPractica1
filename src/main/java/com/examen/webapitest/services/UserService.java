package com.examen.webapitest.services;

import com.examen.webapitest.entities.User;
import com.examen.webapitest.exceptions.BussinesException;
import com.examen.webapitest.exceptions.EmailException;
import com.examen.webapitest.model.MensajeError;
import com.examen.webapitest.model.dto.LoginDto;
import com.examen.webapitest.model.dto.UserDTO;
import com.examen.webapitest.repositories.RolRepository;
import com.examen.webapitest.repositories.UserRepository;
import com.examen.webapitest.security.JwtTokenProvider;
import com.examen.webapitest.utilerias.Fechas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    @Autowired private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;
    private RolRepository roleRepository;
    private PasswordEncoder passwordEncoder;


    public UserService(	RolRepository roleRepository,
                         PasswordEncoder passwordEncoder,
                         JwtTokenProvider tokenProvider,
                         AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<Object> findById(long id) {
        return Optional.of(userRepository.findById(id));
    }

    @ExceptionHandler(EmailException.class)
    public Response createUser(UserDTO user) throws BussinesException {
        if (!validateEmail(user)) {
            return Response.noContent().status(Response.Status.BAD_REQUEST).entity(new MensajeError("Correo Electrónico no pude estar vacio")).build();
        }
        if ( user.getPhones().isEmpty() ){
            return Response.status(Response.Status.BAD_REQUEST).entity(new MensajeError("Para crear un usuario se require al menos un telefono.")).build();
        }
        if ( user.getName().isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(new MensajeError("Nombre no puede estar vacio")).build();
        }
        try {
            userRepository.save(user.toEntity());
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()).build();
        }
    }

    private boolean validateEmail(UserDTO user) throws BussinesException {
        if (null == user)
            throw new BussinesException("Usuario no puede estar vacio.");
        return user.getEmail().matches("(^[0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[cl]");
    }


    public String signin(final LoginDto user) {
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
                e.printStackTrace();
                throw new BussinesException(e.getMessage());
            }
        }else{
            throw new BussinesException("Usuario no encontrado.");
        }
        return token;
    }

    public String createToken(String email) {
        return tokenProvider.createToken(email);
    }
}
