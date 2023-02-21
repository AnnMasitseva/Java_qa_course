package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;

public class GroupDelitionTests extends TestBase {

  @Test
  public void testGroupDelitionTests() throws Exception {
    app.getNavigationHelper().gotoGroupPage();
    app.getGroupHelper().selectGroup();
    app.getGroupHelper().deleteSelectedGroups();
    app.getGroupHelper().returnGroupPage();
  }

}

