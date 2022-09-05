package com.examen.webapitest.model.dto;

import com.examen.webapitest.entities.Phone;
import com.examen.webapitest.entities.User;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserDTO {
    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    private List<PhoneDTO> phones;
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the phones
     */
    public List<PhoneDTO> getPhones() {
        return phones;
    }
    /**
     * @param phones the phones to set
     */
    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }


    public User toEntity() {
        User u = new User();
        u.setEmail( this.getEmail());
        u.setName( this.getName());
        u.setPassword( this.getPassword());
        u.setPhones( this.getPhonesEntites(u) );
        return u;
    }

    private List<Phone> getPhonesEntites(User newUser) {
        List<Phone> lp = new ArrayList<>();
        if ( this.getPhones().size()>0) {
            for (PhoneDTO p : this.getPhones()){
                Phone np = new Phone();
                np.setCountryCode( p.getCountrycode());
                np.setUser(newUser);
                np.setCitycode( p.getCitycode());
                np.setNumber( p.getNumber() );
                lp.add(np);
            }
        }
        return lp;
    }
}
