package com.examen.webapitest.entities;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "Users")
@EntityListeners(AuditingEntityListener.class)
public class User extends EntityAuditory  implements Serializable {

    private static final long serialVersionUID = -89067143496586633L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @ColumnDefault("random_uuid()")
    @Type(type = "uuid-char")
    private UUID id;

    private String name;
    private String email;
    private String password;

    private LocalDateTime lastLoginDate;
    private Boolean active;

    private String token;


    @OneToMany(mappedBy = "user", targetEntity = Phone.class, fetch = FetchType.LAZY)
    private List<Phone> phones;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns
        = @JoinColumn(name = "user_id",
        referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id",
            referencedColumnName = "id"))
    private List<RoleEntity> roles;



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phoneList) {
        this.phones = phoneList;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean activo) {
        this.active = activo;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(final LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public User orElseThrow(final Object o) {
        return null;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(final List<RoleEntity> roles) {
        this.roles = roles;
    }
}
