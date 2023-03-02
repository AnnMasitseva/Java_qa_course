package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDelitionTests extends TestBase{
    @Test
    public void testContactDelition() throws InterruptedException {
        app.getNavigationHelper().goToHomePage();
        if (! app.getContactHelper().isThereAContact()){
            app.getContactHelper().createContact(new ContactData("Anna", "Aleksandrovna", "Masitseva", "St.Peterburg", "+79009009090", "test2"), true);
        }
        app.getNavigationHelper().goToHomePage();
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteContact();
        app.closeAlert();
        app.getSessionHelper().logoutPage();
    }
}
