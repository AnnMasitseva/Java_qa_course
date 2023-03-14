package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactDelitionTests extends TestBase{
    @BeforeMethod
    private void ensurePreconditions(){
        if ( app.contact().list().size() == 0){
            app.goTo().groupPage();
            if ((app.group().list().size() == 0) || ! app.group().isThereAGroupName("test2")){
                app.group().create(new GroupData("test2", null, null));
            }
            app.contact().create(new ContactData("Anna", "Aleksandrovna", "Masitseva", "St.Peterburg", "+79009009090", "email@domain.com", "test2"));
        }
        app.goTo().homePage();
    }
    @Test
    public void testContactDelition() throws InterruptedException {
        List<ContactData> before = app.contact().list();
        int index = before.size()-1;
        app.contact().delete(index);
        app.closeAlert();
        app.goTo().homePage();
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size()-1);
        before.remove(index);
        Assert.assertEquals(before, after);
    }


}
