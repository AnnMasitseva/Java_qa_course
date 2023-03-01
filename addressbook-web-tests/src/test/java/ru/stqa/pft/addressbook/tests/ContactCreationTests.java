package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreationTests() throws Exception {
    app.getContactHelper().initNewContact();
    app.getContactHelper().fillContactForm(new ContactData("Anna", "Aleksandrovna", "Masitseva", "St.Peterburg", "+79009009090", "test2"), true);
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().goToHomePage();
    app.getSessionHelper().logoutPage();
  }
}


