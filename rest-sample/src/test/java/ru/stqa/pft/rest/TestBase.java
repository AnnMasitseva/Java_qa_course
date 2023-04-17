package ru.stqa.pft.rest;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.concurrent.Executor;

public class TestBase {
    @BeforeClass
    public void init(){
        RestAssured.authentication = RestAssured.basic(" b31e382ca8445202e66b03aaf31508a3", "");
    }
    public boolean isIssueOpen(int issueId) throws IOException {

        String json = RestAssured.get("https://bugify.stqa.ru/api/issues"+issueId+".json").asString();
        JsonElement parsed = new JsonParser().parse(json);
        String issueState = parsed.getAsJsonObject().get("issues")
                .getAsJsonArray().get(0)
                .getAsJsonObject().get("state_name")
                .getAsString();

        if (issueState.equals("Open")) {
            return true;
        } else {
            return false;
        }
    }

    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }
}
