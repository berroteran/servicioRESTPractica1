package com.examen.webapitest.model.dto;

import javax.validation.constraints.NotEmpty;

public class PhoneDTO {
    @NotEmpty
    private String number;
    @NotEmpty
    private String citycode;
    @NotEmpty
    private String countrycode;
    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }
    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }
    /**
     * @return the citycode
     */
    public String getCitycode() {
        return citycode;
    }
    /**
     * @param citycode the citycode to set
     */
    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }
    /**
     * @return the countrycode
     */
    public String getCountrycode() {
        return countrycode;
    }
    /**
     * @param countrycode the countrycode to set
     */
    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }


}
