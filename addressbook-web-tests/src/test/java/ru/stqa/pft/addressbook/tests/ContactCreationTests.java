package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {
  @Test
  public void testContactCreationTests() throws Exception {
    app.goTo().groupPage();
    if (! app.group().isThereAGroup() || ! app.group().isThereAGroupName("test2"))  {
        app.group().create(new GroupData("test2", null, null));
    }
        app.goTo().homePage();
    List<ContactData> before = app.contact().list();
    ContactData contact = new ContactData("Anna", "Aleksandrovna", "Masitseva", "St.Peterburg", "+79009009090", "email@domain.com", "test2");
    app.contact().create(contact);
    app.goTo().homePage();
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size()+1);
    contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(),g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }
}


