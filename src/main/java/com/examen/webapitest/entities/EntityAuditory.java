package com.examen.webapitest.entities;

import com.examen.webapitest.utilerias.Fechas;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "fecha_creacion", "fecha_modificacion" }, allowGetters = true)
public class EntityAuditory {

    static DateTimeFormatter fechaTimeFormato = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );
    static DateTimeFormatter fechaFormato = DateTimeFormatter.ofPattern( "yyyy-MM-dd" );

    @Column(name = "creation_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime creationDate;

    @Column (name = "mod_date", nullable = true)
    @LastModifiedDate
    private LocalDateTime updateDate;

    private String createdby;
    private String modBy;

    ///////

    public String getCreatedby() {
        return createdby;
    }
    public void setCreatedby(String creado_por) {
        this.createdby = creado_por;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime fechaActual) {
        this.creationDate = fechaActual;
    }

    public String getModBy() {
        return modBy;
    }
    public void setModBy(String modificado_por) {
        this.modBy = modificado_por;
    }

    public String getDataPersistent () {
        return getCreationDate().format( fechaTimeFormato ) + " por: " + getCreatedby();
    }


    @PrePersist
    protected void onCreate () {
        creationDate = Fechas.getFechaHoraActual();
        updateDate = Fechas.getFechaHoraActual();
        createdby = (getCurrentAuditor() == null ? "anonimous" : getCurrentAuditor().getEmail() );
    }

    @PreUpdate
    protected void onUpdate () {
        updateDate = Fechas.getFechaHoraActual();
        modBy = (getCurrentAuditor() == null ? "anonimous" : getCurrentAuditor().getEmail() );
    }

    public User getCurrentAuditor () {
        /**
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        } else {
            if (authentication.getPrincipal().getClass().getTypeName().equals( "java.lang.String" )) {
                return null;
            } else return ((User) authentication.getPrincipal());
        }**/

         return null;
    }

    public String getFechaCreacionFormatedHuman () {
        return Fechas.getFechaFormateadaHuman( this.creationDate.toLocalDate() );
    }
    public String getFechaHoraCreacionFormatedHuman () {
        return Fechas.getFechaHoraFormateadaHuman(creationDate);
    }


}
