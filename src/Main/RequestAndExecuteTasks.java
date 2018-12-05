package Main;

import HTTP.GetRequester;
import HTTP.PostRequester;
import LexerParser.JObject;
import LexerParser.JToken;
import LexerParser.Parser2;
import Operations.OperationClass;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
    public JToken sendRequestToRetrieveListOfTasks() throws IOException {
        String taskURLS = new GetRequester().sendGetRequest("/student?id=s195206");
        fileWriter.write(taskURLS + "\n");

        JToken jsonObject = new Parser2().parse(taskURLS);
        System.out.println();

        //Note this is the entire response, it has not filtered out the tasks yet.
        return jsonObject;
    }


    public void processTasks(JToken tasksJsonResponse) throws IOException {
        // Extract the taskURLS from the JSONArray.
        List<JToken> taskURLArray = tasksJsonResponse.getAsObject().get("tasks").getAsArray().getTokens();

        // Loop through all URLS.
        for (JToken element : taskURLArray) {
            try {
                //Element is the URL
                String task = retrieveIndividualTaskDetails(element);

                //TODO then wrap the unparsing bit?
                //TODO this bit throws the exception.
                JObject individualTask = new Parser2().parse(task).getAsObject();
                String taskInstruction = individualTask.get("instruction").getAsString().getString();
                List<JToken> parameters = individualTask.get("parameters").getAsArray().getTokens();
                String postUrl = individualTask.get("response URL").getAsString().getString();

                String answerToPost = new OperationClass().doOperation(taskInstruction, parameters);
                sendTaskResponse(postUrl, answerToPost);

            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                sendTaskResponse(element.getAsString().getString(), "Not valid JSON");
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

    private String retrieveIndividualTaskDetails(JToken element) throws IOException {
        String taskURL = element.getAsString().getString();
        String task = new GetRequester().sendGetRequest(taskURL);
        fileWriter.write(task + "\n");
        return task;
    }


    private static FileWriter initialiseFileWriter() throws IOException {
        String filePathWorkPc = "D:/Users/jshindle/Homework/JSONParser/outputFile.txt";
        String filePathDesktop = "C:/Users/Jony/git/Tutoring/FinalProject/JSONAssignment/outputFile.txt";
        String filePathLaptop = "/home/jony/git/JSONAssignment/outputFile.txt";
        PrintWriter pw = new PrintWriter(filePathDesktop );
        pw.close();

        return new FileWriter(filePathDesktop);
    }

}
