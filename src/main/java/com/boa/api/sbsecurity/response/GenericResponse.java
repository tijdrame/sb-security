package com.boa.api.sbsecurity.response;

import java.time.Instant;

//import org.apache.commons.lang3.RandomStringUtils;

import lombok.Data;

@Data
public class GenericResponse {
    protected String code;
    protected String description;
    protected Instant dateResponse;
    protected String responseReference;

    public GenericResponse() {
        //this.responseReference = RandomStringUtils.randomAlphanumeric(20).toUpperCase();
    }
}
