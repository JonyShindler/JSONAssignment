package Main;

import HTTP.GetRequester;
import HTTP.PostRequester;
import JSONParser.BasicParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestAndExecuteTasks {
private FileWriter fileWriter;

//TODO this is bad practice!!!
    public RequestAndExecuteTasks() {
        try {
            fileWriter = initiliaseFileWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // TODO Wrap this in try catch?
    //This requests and returns the list of tasks we need.
    public JsonObject sendRequestToRetrieveListOfTasks() throws IOException {
        String taskURLS = new GetRequester().sendGetRequest("/student?id=s113867");
        fileWriter.write(taskURLS + "\n");

        //TODO parse it here.
        JsonObject jsonObject = new BasicParser().parse(taskURLS);
        System.out.println();

        return jsonObject;
    }


    public void processTasks(JsonObject tasksJsonResponse) throws IOException {
        // Extract the tasks from the string.
        JsonArray tasksArray = tasksJsonResponse.get("tasks").getAsJsonArray();

        for (JsonElement element : tasksArray) {
            try {
                String taskURL = element.getAsString();
                // System.out.println(taskURL);
                String task = new GetRequester().sendGetRequest(taskURL);
                fileWriter.write(task + "\n");
                //parse each one.

                JsonObject taskJson = new JsonParser().parse(task).getAsJsonObject();
                // extract the URL and the task.
                String taskInstruction = taskJson.get("instruction").getAsString();
                // System.out.println(taskInstruction);

                JsonArray parameters = taskJson.getAsJsonArray("parameters");
                // System.out.println(parameters.toString());

                String postUrl = taskJson.get("response URL").getAsString();
                // System.out.println(postUrl);

                //Figure out what operation to take.

                String answerToPost;
                int parameter1 = Integer.parseInt(parameters.get(0).getAsString());
                int parameter2 = Integer.parseInt(parameters.get(1).getAsString());

                switch (taskInstruction)
                {
                    case "add":
                    {
                        //extract the parameters.
                        int answer = parameter1 + parameter2;
                        answerToPost = String.valueOf(answer);
                        break;
                    }
                    case "multiply":
                    {
                        int answer = parameter1 * parameter2;
                        answerToPost = String.valueOf(answer);
                        break;
                    }
                    case "concat":
                        answerToPost = String.valueOf(parameter1) + String.valueOf(parameter2);
                        break;
                    default:
                        throw new IllegalStateException("Not a valid operation \n");
                }

                new PostRequester().sendPostRequest(postUrl, answerToPost);
                //TODO it specifically wants the response we sent back.
                fileWriter.write(answerToPost + "\n");
                System.out.println();

            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
                //TODO we need to send an error message back to the server.
            } catch (IOException e) {
                e.printStackTrace();
            }
//TODO write each JSON document and response we sent back to a file.
        }
        fileWriter.close();

    }


    private static FileWriter initiliaseFileWriter() throws IOException {
        //        String filePath = "D:/Users/jshindle/Homework/JSONParser/outputFile.txt";
        String filePath = "C:/Users/Jony/git/Tutoring/FinalProject/JSONAssignment/outputFile.txt";
        PrintWriter pw = new PrintWriter(filePath);
        pw.close();

        return new FileWriter(filePath);
    }

}
