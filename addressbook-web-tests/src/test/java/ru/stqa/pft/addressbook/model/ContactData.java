package ru.stqa.pft.addressbook.model;

public class ContactData {

    private final String firstname;
    private final String middlename;
    private final String lastname;
    private final String address;

    private final String mobile;
    private final String group;



    public ContactData(String firstname, String middlename, String lastname, String address, String mobile, String group) {
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.address = address;
        this.mobile = mobile;
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getFirstname() {
        return firstname;
    }
}
