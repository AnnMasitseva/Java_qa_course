package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactDelitionTests extends TestBase{
    @Test
    public void testContactDelition() throws InterruptedException {
        if (! app.getContactHelper().isThereAContact()){
            app.getNavigationHelper().gotoGroupPage();
            if (! app.getGroupHelper().isThereAGroup() || ! app.getGroupHelper().isThereAGroupName()){
                app.getGroupHelper().createGroup(new GroupData("test2", null, null));
            }
            app.getContactHelper().createContact(new ContactData("Anna", "Aleksandrovna", "Masitseva", "St.Peterburg", "+79009009090", "email@domain.com", "test2"));
        }
        app.getNavigationHelper().goToHomePage();
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size()-1);
        app.getContactHelper().deleteContact();
        app.closeAlert();
        app.getNavigationHelper().goToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size()-1);
        before.remove(before.size()-1);
        Assert.assertEquals(before, after);
        app.getSessionHelper().logoutPage();
    }
}
