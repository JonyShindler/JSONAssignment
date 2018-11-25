package Main;

import HTTP.GetRequester;
import HTTP.PostRequester;
import JSONParser.GSONParser;
import Operations.OperationClass;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

//TODo this class should just delegate to other public method classes to tell us what to do.
public class RequestAndExecuteTasks {
private FileWriter fileWriter;

//TODO this is bad practice!!!
    public RequestAndExecuteTasks() {
        try {
            fileWriter = initialiseFileWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // TODO Wrap send request in exception and then pass that on if it happens?
    //This requests and returns the list of tasks we need.
    public JsonObject sendRequestToRetrieveListOfTasks() throws IOException {
        String taskURLS = new GetRequester().sendGetRequest("/student?id=s113867");
        fileWriter.write(taskURLS + "\n");

        JsonObject jsonObject = new GSONParser().parse(taskURLS);
        System.out.println();

        //Note this is the entire response, it has not filtered out the tasks yet.
        return jsonObject;
    }


    public void processTasks(JsonObject tasksJsonResponse) throws IOException {
        // Extract the taskURLS from the JSONArray.
        JsonArray taskURLArray = tasksJsonResponse.get("tasks").getAsJsonArray();

        // Loop through all URLS.
        for (JsonElement element : taskURLArray) {
            try {
                //TODO again maybe wrap this individual bit.
                String task = retrieveIndividualTaskDetails(element);

                //TODO then wrap the unparsing bit?
                JsonObject individualTask = new JsonParser().parse(task).getAsJsonObject();
                String taskInstruction = individualTask.get("instruction").getAsString();
                JsonArray parameters = individualTask.getAsJsonArray("parameters");
                String postUrl = individualTask.get("response URL").getAsString();

                String answerToPost = new OperationClass().doOperation(taskInstruction, parameters);
                sendTaskResponse(postUrl, answerToPost);

            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
                //TODO we need to send an error message back to the server.
            } catch (IOException e) {
                e.printStackTrace();
            }
//TODO write each JSON document and response we sent back to a file.
        }

        //This is the final task so we can do this. otherwise we could add it in the finalize method?
        fileWriter.close();

    }

    private void sendTaskResponse(String postUrl, String answerToPost) throws IOException {
        new PostRequester().sendPostRequest(postUrl, answerToPost);
        //TODO it specifically wants the response we sent back.
        fileWriter.write(answerToPost + "\n");
        System.out.println();
    }

    private String retrieveIndividualTaskDetails(JsonElement element) throws IOException {
        String taskURL = element.getAsString();
        String task = new GetRequester().sendGetRequest(taskURL);
        fileWriter.write(task + "\n");
        return task;
    }


    private static FileWriter initialiseFileWriter() throws IOException {
        String filePathWorkPc = "D:/Users/jshindle/Homework/JSONParser/outputFile.txt";
        String filePathDesktop = "C:/Users/Jony/git/Tutoring/FinalProject/JSONAssignment/outputFile.txt";
        String filePathLaptop = "/home/jony/git/JSONAssignment/outputFile.txt";
        PrintWriter pw = new PrintWriter(filePathLaptop);
        pw.close();

        return new FileWriter(filePathLaptop);
    }

}
