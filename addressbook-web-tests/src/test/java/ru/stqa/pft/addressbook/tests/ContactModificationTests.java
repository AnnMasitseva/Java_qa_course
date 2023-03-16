package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase{
    @BeforeMethod
    private void ensurePreconditions(){
        if ( app.contact().all().size() == 0){
            app.goTo().groupPage();
            if (app.group().all().size() == 0 || ! app.group().isThereAGroupName("test2")) {
                app.group().create(new GroupData().withName("test2"));
            }
            app.contact().create(new ContactData().withFirstname("Anna").withMiddlename("Aleksandrovna").withLastname("Masitseva")
                    .withAddress("St.Peterburg").withMobilePhone("+79009009090").withEmail("email@domain.com").withGroup("test2"));
        }
        app.goTo().homePage();

    }
    @Test
    public void testContactModification() throws InterruptedException {
        Contacts before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId())
                .withFirstname("Anna").withMiddlename("Aleksandrovna").withLastname("Masitseva").withAddress("St.Peterburg")
                .withMobilePhone("+79009009090").withEmail("email@domain.com");
        app.contact().modify(contact);
        app.goTo().homePage();
        assertEquals(app.contact().counter(), before.size());
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
