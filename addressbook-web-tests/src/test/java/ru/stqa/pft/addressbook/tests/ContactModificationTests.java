package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{
    @Test
    public void testContactModification() throws InterruptedException {
        app.getNavigationHelper().goToHomePage();
        if (! app.getContactHelper().isThereAContact()){
            app.getContactHelper().createContact(new ContactData("Anna", "Aleksandrovna", "Masitseva", "St.Peterburg", "+79009009090", "test2"));
        }
        app.getNavigationHelper().goToHomePage();
        app.getContactHelper().selectContact();
        app.getContactHelper().modificationContact(new ContactData("Anna", "Aleksandrovna", "Masitseva", "St.Peterburg", "+79009009090", null));
        app.getNavigationHelper().goToHomePage();
    }
}
