package com.examen.webapitest.controllerswebapi;

import com.examen.webapitest.entities.User;
import com.examen.webapitest.exceptions.BussinesException;
import com.examen.webapitest.exceptions.EmailException;
import com.examen.webapitest.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public List<User> findAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/v1/{id}")
    public User findUserById(@PathVariable(value = "id") long id) {
        return (User) userService.findById(id).orElseGet(null);
    }

    /**
     * Metodo para iniciar sesion
     */
    @PostMapping( path = "/v1/signin",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE )
    public Response signin(User user) {
        String token = "";
        try {
            token = this.userService.signin(user);
            return Response.status(Response.Status.CREATED).entity(token).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).build();
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
    public User createUser(@Validated @RequestBody User user) throws BussinesException {
        String token = getJWTToken(user.getName());
        //User user = new User();
        //user.setUser(username);
        //user.setToken(token);
        //return user;

        return userService.save(user);
    }

    @PostMapping
    public User saveUser(@Validated @RequestBody User user) {
        try {
            user = userService.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
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
