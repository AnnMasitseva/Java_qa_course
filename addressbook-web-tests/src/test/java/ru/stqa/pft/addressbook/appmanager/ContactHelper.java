package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
      click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"),contactData.getFirstname());
        type(By.name("middlename"),contactData.getMiddlename());
        type(By.name("lastname"),contactData.getLastname());
        type(By.name("address"),contactData.getAddress());
        type(By.name("mobile"), contactData.getMobile());
        type(By.name("email"),contactData.getEmail());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        }else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initNewContact() {
      click(By.linkText("add new"));
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void deleteContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void submitContactModification() {
        click(By.xpath("//div[@id='content']/form/input[22]"));
    }

    public void editContact(int index) {
        wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
    }

    public void createContact(ContactData contact) {
       initNewContact();
       fillContactForm(contact, true);
       submitContactCreation();
    }

    public void modificationContact(ContactData contact){
       fillContactForm(contact, false);
       submitContactModification();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getContactCounter() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            String firstname = element.findElement(By.xpath("//*[@id=\"maintable\"]/tbody/tr[2]/td[3]")).getText();
            String lastname = element.findElement(By.xpath("//*[@id=\"maintable\"]/tbody/tr[2]/td[2]")).getText();
            ContactData contact = new ContactData( id, firstname , null, lastname, null, null, null, null);
            contacts.add(contact);
        }
        return contacts;
    }
}
