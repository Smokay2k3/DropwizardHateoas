package model;


public class PersonListItem {

    private final Long personId;
    private final String firstName;

    public PersonListItem(Long personId, String firstName){
        this.personId = personId;
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Long getPersonId() {
        return personId;
    }
}
