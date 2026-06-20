package com.towhid.spring_mvc.day11.validation.exception;

// Thrown when a resource is not found
// Results in 404 Not Found response
public class ResourceNotFoundException
        extends RuntimeException{

    private final String resourceName;
    // what resource was not found
    // "Product", "Student", "Order"

    private final String fieldName;
    // which field was searched
    // "id", "email", "name"

    private final Object fieldValue;
    // what value was searched for
    // 5, "john@email.com"


    public ResourceNotFoundException(
            String resourceName,
            String fieldName,
            Object fieldValue) {
        super(String.format(
                "%s not found with %s: %s",
                resourceName,
                fieldName,
                fieldValue));
        // "Product not found with id: '5'"

        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }
    public String getFieldName() {
        return fieldName;
    }
    public Object getFieldValue() {
        return fieldValue;
    }
}
