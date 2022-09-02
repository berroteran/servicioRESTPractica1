package com.examen.webapitest.controllerswebapi;

import com.examen.webapitest.entities.User;
import com.examen.webapitest.exceptions.BussinesException;
import com.examen.webapitest.exceptions.EmailException;
import com.examen.webapitest.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping
    public List<User> findAllUsers() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable(value = "id") long id) {
        return (User) service.findById(id).orElseGet(null);
    }

    @PostMapping("/v1/singin")
    public void singup() {

    }

    @PostMapping("/v1/singup")
    @ExceptionHandler(EmailException.class)
    public User register(@Validated @RequestBody User user) throws BussinesException {
        String token = getJWTToken(user.getName());
        //User user = new User();
        //user.setUser(username);
        //user.setToken(token);
        //return user;

        return service.save(user);
    }

    @PostMapping
    public User saveUser(@Validated @RequestBody User user) {
        try {
            user = service.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/users")
    List<User> all() {
        return service.findAll();
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
