package com.towhid.spring_mvc.day12.swagger.exception;

public class ResourceNotFoundException
        extends RuntimeException {

    public ResourceNotFoundException(
            String resourceName,
            String fieldName,
            Object fieldValue) {
        super(String.format(
                "%s not found with %s: '%s'",
                resourceName, fieldName, fieldValue));
    }
}