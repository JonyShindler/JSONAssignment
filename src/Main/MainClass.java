package Main;


import LexerParser.JToken;

import java.io.IOException;

public class MainClass {

    public static void main(String[] args) throws IOException {
        RequestAndExecuteTasks requestAndExecuteTasks = new RequestAndExecuteTasks();

        JToken tasksJsonResponse = requestAndExecuteTasks.sendRequestToRetrieveListOfTasks();
        requestAndExecuteTasks.processTasks(tasksJsonResponse);

    }

}
