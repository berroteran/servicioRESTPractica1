package com.examen.webapitest.utilerias;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import static java.time.temporal.ChronoUnit.DAYS;

public class Fechas {
    final public static String MY_TIME_ZONE = "America/Santiago";
    final static String FECHA_TIME_FORMATO = "yyyy-MM-dd HH:mm:ss";
    final static String FECHA_FORMATO_UNIVERSAL = "yyyyMMdd";
    final static String FECHA_FORMATO_HUMAN = "dd/MM/yyyy";
    final static String FECHA_TIME_FORMATO_HUMAN = "dd/MM/yyyy HH:mm:ss";
    final static DateTimeFormatter fechaTimeFormato = DateTimeFormatter.ofPattern( FECHA_TIME_FORMATO );

    static DateTimeFormatter fechaFormato = DateTimeFormatter.ofPattern( "yyyy-MM-dd" );
    static DateTimeFormatter fechaFormatoHuman = DateTimeFormatter.ofPattern( "dd/MM/yyyy" );
    static DateTimeFormatter fechaSinGuion = DateTimeFormatter.ofPattern( "yyyyMMdd" );

    public static LocalDate getFechaActual () {
        LocalDate currentDate = LocalDate.now( ZoneId.of( MY_TIME_ZONE ) );
        return currentDate;
    }
    public static Date getFechaActualDate () {
        LocalDate currentDate = LocalDate.now( ZoneId.of( MY_TIME_ZONE ) );
        Date date = Date.from(currentDate.atStartOfDay( ZoneId.of( MY_TIME_ZONE ) ).toInstant());
        return date;
    }

    public static int getAnioActual () {
        return getFechaActual().getYear();
    }

    public static int getAnioActualYY () {
        return Integer.parseInt( String.valueOf( getFechaActual().getYear() ).substring( 2 ) );
    }

    public static String getMesActual () {
        return getFechaActual().getMonth().name();
    }

    public static int getMesActualInt () {
        return getFechaActual().getMonth().getValue();
    }

    public static String getMesActualStrg () {
        return String.format( "%02d", getFechaActual().getMonth().getValue() );
    }

    public static LocalDateTime getFechaHoraActual () {
        LocalDateTime currentDate = LocalDateTime.now( ZoneId.of( MY_TIME_ZONE ) );
        return currentDate;
    }

    public static DateTimeFormatter getFormatDateHuman () {
        return fechaFormatoHuman;
    }

    public static DateTimeFormatter getFormatDate () {
        return DateTimeFormatter.ofPattern( FECHA_FORMATO_UNIVERSAL );
    }
    public static DateTimeFormatter getFechaTimeFormato () {
        return fechaTimeFormato;
    }

    public static int getDiasDiff (LocalDate fechaInicial, LocalDate fechaFinal) {
        int dato = Math.toIntExact( DAYS.between( fechaInicial, fechaFinal ) );
        return dato;
    }

    public static LocalDate getFechaFrom (int year, int mes, int day) {
        return LocalDate.of( year, mes, day );
    }

    public static DateTimeFormatter getFechaSinGuionFormato () {
        return fechaSinGuion;
    }

    public static String  getFechaActualFormateada(){
        return "";
    }
    public static String  getFechaHoraActualFormateada(){
        return "";
    }
    public static String  getFechaFormateada( LocalDate fecha){
        return "";
    }
    public static String getFechaHoraFormateada (LocalDateTime fecha_hora){
        return DateTimeFormatter.ofPattern(FECHA_TIME_FORMATO, Locale.ENGLISH).format(fecha_hora);
    }

    public static String getFechaHoraFormateadaHuman (LocalDateTime locatime) {
        return DateTimeFormatter.ofPattern(FECHA_FORMATO_HUMAN, Locale.ENGLISH).format(locatime);
    }

    public static String getFechaFormateadaHuman (LocalDate fecha) {
        return DateTimeFormatter.ofPattern(FECHA_FORMATO_HUMAN, Locale.ENGLISH).format(fecha);
    }

    public static String getFechaFormatComputer (LocalDateTime fecha) {
        return DateTimeFormatter.ofPattern(FECHA_FORMATO_UNIVERSAL, Locale.ENGLISH).format(fecha);
    }
}
