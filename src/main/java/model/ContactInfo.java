package model;

public class ContactInfo {

    private final String address;
    private final String email;

    public ContactInfo() {
        address = null;
        email = null;
    }

    public ContactInfo(String address, String email) {
        this.address = address;
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }
}
