package com.examen.webapitest.controllerswebapi;

import com.examen.webapitest.entities.User;
import com.examen.webapitest.exceptions.BussinesException;
import com.examen.webapitest.exceptions.EmailException;
import com.examen.webapitest.model.MensajeError;
import com.examen.webapitest.model.dto.LoginDto;
import com.examen.webapitest.model.dto.UserDTO;
import com.examen.webapitest.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/v1/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public User findUserById(@PathVariable(value = "id") long id) {
        return (User) userService.findById(id).orElseGet(null);
    }

    /**
     * Metodo para iniciar sesion
     */
    @PostMapping( path = "/v1/signin",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<?> signin(@RequestBody @Valid LoginDto login) {
        String token = "";
        try {
            token = this.userService.signin(login);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new MensajeError(e.getMessage()) , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Metodo crear nuevo usuario
     *
     * @param user
     * @return
     * @throws BussinesException
     */
    @PostMapping(path = "/v1/signup",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(EmailException.class)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Response createUser(@Validated @RequestBody UserDTO user) throws BussinesException {
        try {
             return userService.createUser(user);
        }catch (Exception e){
            return Response.noContent().status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error" + e.getMessage()).build();
        }
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping(path = "/v1/users", consumes = MediaType.APPLICATION_JSON_VALUE,    produces = MediaType.APPLICATION_JSON_VALUE)
    List<User> all() {
        return userService.findAll();
    }
    // end::get-aggregate-root[]


    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts.builder().setId("softtekJWT").setSubject(username).claim("authorities", grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())).setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 600000)).signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

        return "Bearer " + token;
    }


}
