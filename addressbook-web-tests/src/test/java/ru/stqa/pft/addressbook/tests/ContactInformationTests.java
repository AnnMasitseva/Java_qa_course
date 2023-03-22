package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactInformationTests extends TestBase{
    @BeforeMethod
    private void ensurePreconditions(){
        if ( app.contact().all().size() == 0){
            app.goTo().groupPage();
            if (app.group().all().size() == 0 || ! app.group().isThereAGroupName("test2")){
                app.group().create(new GroupData().withName("test2"));
            }
            app.contact().create(new ContactData().withFirstname("Anna").withMiddlename("Aleksandrovna").withLastname("Masitseva-Tikhonova")
                    .withAddress("St.Petersburg, str. Professora Popova, 37SH, apt 307")
                    .withMobilePhone("+79009009090").withWorkPhone("88128008080").withEmail("email@domain.com")
                    .withEmail2("email2@domain.com").withGroup("test2"));
        }
        app.goTo().homePage();
    }
    @Test
    public void testContactInfo(){
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
        assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
        assertThat(contact.getLastname(),equalTo(contactInfoFromEditForm.getLastname()));
        assertThat(contact.getFirstname(),equalTo(contactInfoFromEditForm.getFirstname()));
    }

    private String mergeEmails(ContactData contact) {
        return   Arrays.asList(contact.getEmail(),contact.getEmail2(),contact.getEmail3())
                .stream().filter((s) -> ! s.equals(""))
                .map(ContactInformationTests::cleanedPhones)
                .collect(Collectors.joining("\n"));
    }

    private String mergePhones(ContactData contact) {
      return   Arrays.asList(contact.getHomePhone(),contact.getMobilePhone(),contact.getWorkPhone())
                .stream().filter((s) -> ! s.equals(""))
               .map(ContactInformationTests::cleanedEmails)
               .collect(Collectors.joining("\n"));
    }

    public static String cleanedPhones(String phone){
        return phone.replaceAll("\\s", "").replaceAll("[-()]","");
    }
    public static String cleanedEmails(String email){ return email.replaceAll("\\s", "");}

}
