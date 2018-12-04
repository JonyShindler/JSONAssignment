package Operations;

import LexerParser.JToken;
import com.google.gson.JsonArray;

import java.util.List;

public class OperationClass {

    //TODO this should be its own class method
    public String doOperation(String taskInstruction, List<JToken> parameters) {
        int parameter1 = Integer.parseInt(parameters.get(0).getAsString());
        int parameter2 = Integer.parseInt(parameters.get(1).getAsString());

        String answerToPost;
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
                //TODO find out if we should be able to process subtract or not as it kinda seems genuine?
                throw new IllegalStateException("Not a valid operation \n");
        }
        return answerToPost;
    }

}
