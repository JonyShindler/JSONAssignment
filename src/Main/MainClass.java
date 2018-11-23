package Main;


import HTTP.GetRequester;
import HTTP.PostRequester;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

public class MainClass {


    public static void main(String[] args) throws IOException {
        RequestAndExecuteTasks requestAndExecuteTasks = new RequestAndExecuteTasks();

        JsonObject tasksJsonResponse = requestAndExecuteTasks.sendRequestToRetrieveListOfTasks();
        requestAndExecuteTasks.processTasks(tasksJsonResponse);

    }


}
