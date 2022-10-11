package com.asyncapi.model;

import javax.validation.constraints.*;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;
import java.util.Objects;


public class AnonymousSchema6 {
    
    private @Valid String id;
    
    private @Valid String firstName;
    
    private @Valid String lastName;
    

    

    /**
     * the player id
     */
    @JsonProperty("id")@NotNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    

    /**
     * the player firstName
     */
    @JsonProperty("firstName")@NotNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    

    /**
     * the player lastName
     */
    @JsonProperty("lastName")@NotNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnonymousSchema6 anonymousSchema6 = (AnonymousSchema6) o;
        return 
            Objects.equals(this.id, anonymousSchema6.id) &&
            Objects.equals(this.firstName, anonymousSchema6.firstName) &&
            Objects.equals(this.lastName, anonymousSchema6.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "class AnonymousSchema6 {\n" +
        
                "    id: " + toIndentedString(id) + "\n" +
                "    firstName: " + toIndentedString(firstName) + "\n" +
                "    lastName: " + toIndentedString(lastName) + "\n" +
                "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
           return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}