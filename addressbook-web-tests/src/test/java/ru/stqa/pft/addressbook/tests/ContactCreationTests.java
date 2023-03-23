package ru.stqa.pft.addressbook.tests;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContactsFromXml() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader((new File("src/test/resources/contacts.xml"))))) {
            String xml = "";
            String line = reader.readLine();
            while (line != null) {
                xml += line;
                line = reader.readLine();
            }
            XStream xstream = new XStream();
            xstream.processAnnotations(ContactData.class);
            xstream.allowTypes(new Class[]{ContactData.class});
            List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
            return contacts.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
        }
    }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
      try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
          String json = "";
          String line = reader.readLine();
          while (line != null) {
              json += line;
              line = reader.readLine();
          }
          Gson gson = new Gson();
          List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {}.getType());
          return contacts.stream().map((с) -> new Object[]{с}).collect(Collectors.toList()).iterator();
      }
  }

    @BeforeMethod
    private void ensurePreconditions(){
        app.goTo().groupPage();
        if (app.group().all().size() == 0 ){
            app.group().create(new GroupData().withName("test2"));
        }
    }

  @Test(dataProvider = "validContactsFromJson")
  public void testContactCreationTests(ContactData contact) {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    //File photo = new File("src/test/resources/cat.png");
    app.contact().create(contact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size()+1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }

  @Test(enabled = false)
  public void testContactBadCreationTests(){
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
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }
}


