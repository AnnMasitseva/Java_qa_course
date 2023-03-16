package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreationTests() throws Exception {
    app.goTo().groupPage();
    if (app.group().all().size() == 0 || ! app.group().isThereAGroupName("test2"))  {
        app.group().create(new GroupData().withName("test2"));
    }
        app.goTo().homePage();
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstname("Anna").withMiddlename("Aleksandrovna").withLastname("Masitseva").withAddress("St.Peterburg")
            .withMobilePhone("+79009009090").withEmail("email@domain.com").withGroup("test2");
    app.contact().create(contact);
    app.goTo().homePage();
    assertThat(app.contact().counter(), equalTo(before.size()+1));
    Contacts after = app.contact().all();

    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }

  @Test
  public void testContactBadCreationTests() throws Exception {
    app.goTo().groupPage();
    if (app.group().all().size() == 0 || ! app.group().isThereAGroupName("test2"))  {
      app.group().create(new GroupData().withName("test2"));
    }
    app.goTo().homePage();
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstname("Anna'").withGroup("test2");
    app.contact().create(contact);
    app.goTo().homePage();
    assertThat(app.contact().counter(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }
}


