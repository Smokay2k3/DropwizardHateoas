package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import hateoas.Links;
import hateoas.OrderLinks;
import hateoas.PersonLinks;
import org.mongojack.Id;
import org.mongojack.ObjectId;

public class Person {

    @ObjectId
    @Id
    private String id;

    private String firstName;

    private String lastName;

    @JsonIgnore
    private boolean writeLinks;

    public Person(){
        this.writeLinks = true;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isWriteLinks() {
        return writeLinks;
    }

    public void setWriteLinks(boolean writeLinks) {
        this.writeLinks = writeLinks;
    }

    @JsonProperty(value="links")
    public Links getLinks(){
        if(writeLinks) {
            return new PersonLinks(id);
        }
        return null;
    }
}

