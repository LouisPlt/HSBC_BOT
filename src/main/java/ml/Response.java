package ml;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.APIConnector;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by louis on 27/01/17.
 */
public class Response {
    private String askedQuestion;
    private String retrievedResponse;
    private Boolean isCorrect;
    private LocalDateTime date;

    public Response(String askedQuestion, String retrievedResponse) {
        this.askedQuestion = askedQuestion;
        this.retrievedResponse = retrievedResponse;
        this.date = LocalDateTime.now();
    }

    public void save() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(this);
        APIConnector.createUserQuestion(jsonInString);

    }

    public String getAskedQuestion() {
        return askedQuestion;
    }

    public void setAskedQuestion(String askedQuestion) {
        this.askedQuestion = askedQuestion;
    }

    public String getRetrievedResponse() {
        return retrievedResponse;
    }

    public void setRetrievedResponse(String retrievedResponse) {
        this.retrievedResponse = retrievedResponse;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
