package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase{
    @BeforeMethod
    private void ensurePreconditions(){
        if ( app.contact().list().size() == 0){
            app.goTo().groupPage();
            if (app.group().all().size() == 0 || ! app.group().isThereAGroupName("test2")) {
                app.group().create(new GroupData().withName("test2"));
            }
            app.contact().create(new ContactData().withFirstname("Anna").withMiddlename("Aleksandrovna").withLastname("Masitseva")
                    .withAddress("St.Peterburg").withMobile("+79009009090").withEmail("email@domain.com").withGroup("test2"));
        }
        app.goTo().homePage();

    }
    @Test
    public void testContactModification() throws InterruptedException {
        List<ContactData> before = app.contact().list();
        int index = before.size()-1;
        app.contact().selectEditContact(index);
        ContactData contact = new ContactData().withId(before.get(index).getId())
                .withFirstname("Anna").withMiddlename("Aleksandrovna").withLastname("Masitseva").withAddress("St.Peterburg")
                .withMobile("+79009009090").withEmail("email@domain.com");
        app.contact().modify(contact);
        app.goTo().homePage();
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size());
        before.remove(index);
        before.add(contact);
        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(),g2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
