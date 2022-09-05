package com.examen.webapitest.model;

public class MensajeError {
    private String mensaje;


    public MensajeError(String msj){
        this.mensaje = msj;
    }

    public MensajeError() {

    }


    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(final String mensaje) {
        this.mensaje = mensaje;
    }
}
