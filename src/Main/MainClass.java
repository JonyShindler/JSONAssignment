package Main;


import HTTP.GetRequester;
import HTTP.PostRequester;

import java.io.IOException;

public class MainClass {

    public static void main(String[] args) throws IOException {

        String allTasks = new GetRequester().sendGetRequest("student?id=s113867");
        String task1 = new GetRequester().sendGetRequest("task/5447");

        String sendFirstResponse = new PostRequester().sendPostRequest("answer/5447");


    }
}
