package Main;


import com.google.gson.JsonObject;

import java.io.IOException;

public class MainClass {


    public static void main(String[] args) throws IOException {
        RequestAndExecuteTasks requestAndExecuteTasks = new RequestAndExecuteTasks();

        JsonObject tasksJsonResponse = requestAndExecuteTasks.sendRequestToRetrieveListOfTasks();
        requestAndExecuteTasks.processTasks(tasksJsonResponse);

    }


}
