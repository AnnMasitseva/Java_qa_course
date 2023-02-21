package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase{

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
      click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactData) {
        type(By.name("firstname"),contactData.firstname());
        type(By.name("middlename"),contactData.middlename());
        type(By.name("lastname"),contactData.lastname() );
        type(By.name("address"),contactData.address());
        type(By.name("mobile"), contactData.mobile());
    }

    public void initNewContact() {
      click(By.linkText("add new"));
    }
}
