package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import hateoas.Links;
import hateoas.OrderLinks;
import hateoas.PersonLinks;
import org.mongojack.Id;
import org.mongojack.ObjectId;

public class Order {

    @ObjectId
    @Id
    private String id;

    private String personId;
    private String description;

    @JsonIgnore
    private boolean writeLinks;

    public Order() {
        this.writeLinks = true;
    }

    public String getId() {
        return id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isWriteLinks() {
        return writeLinks;
    }

    public void setWriteLinks(boolean writeLinks) {
        this.writeLinks = writeLinks;
    }

    @JsonProperty("links")
    public Links getLinks(){
        if(writeLinks) {
            return new OrderLinks(id, personId);
        }
        return null;
    }
}
