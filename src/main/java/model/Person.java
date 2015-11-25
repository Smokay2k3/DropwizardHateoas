package model;

import hateoas.Links;
import hateoas.PersonLinks;
import org.hibernate.validator.constraints.NotEmpty;

public class Person {
    private final Long personId;

    @NotEmpty
    private final String firstName;
    @NotEmpty
    private final String lastName;

    private final ContactInfo contactInfo;

    private Links links;

    public Person() {
        personId = null;
        firstName = null;
        lastName = null;
        contactInfo = null;
    }

    public Person(Long personId, String firstName, String lastName, ContactInfo contactInfo) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactInfo = contactInfo;

        links = new PersonLinks(personId);
    }

    public Long getPersonId() {
        return personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public Links getLinks() {
        return links;
    }

    public static class Builder{
        private Long personId;
        private String firstName;
        private String lastName;
        private ContactInfo contactInfo;

        public Builder withPersonId(Long personId){
            this.personId = personId;
            return this;
        }

        public Builder withFirstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public Builder withContactInfo(ContactInfo contactInfo){
            this.contactInfo = contactInfo;
            return this;
        }

        public Person build(){
            return new Person(personId, firstName, lastName, contactInfo);
        }
    }
}

