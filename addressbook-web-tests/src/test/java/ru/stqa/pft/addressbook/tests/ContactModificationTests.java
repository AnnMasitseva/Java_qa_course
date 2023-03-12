package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactModificationTests extends TestBase{
    @Test
    public void testContactModification() throws InterruptedException {
        if (! app.getContactHelper().isThereAContact()){
            app.getNavigationHelper().gotoGroupPage();
            if (! app.getGroupHelper().isThereAGroup() || ! app.getGroupHelper().isThereAElement("//span[contains(text(), 'test2')]")) {
                app.getGroupHelper().createGroup(new GroupData("test2", null, null));
            }
            app.getContactHelper().createContact(new ContactData("Anna", "Aleksandrovna", "Masitseva", "St.Peterburg", "+79009009090", "email@domain.com", "test2"));
        }
        app.getNavigationHelper().goToHomePage();
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size()-1);
        ContactData contact = new ContactData(before.get(before.size()-1).getId(),"Anna", "Aleksandrovna", "Masitseva", "St.Peterburg", "+79009009090", "email@domain.com", null);
        app.getContactHelper().modificationContact(contact);
        app.getNavigationHelper().goToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());
        before.remove(before.size()-1);
        before.add(contact);
        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(),g2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
