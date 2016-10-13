package qual.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Application {

  private String name;
  private List<Response> responses;

  public Application() {
    // default constructor required for JSON Data binding
  }

  public Application(String name) {
    this.name = name;
    responses = new ArrayList<>();
  }

  @JsonProperty("Name")
  public String getName() {
    return name;
  }

  @JsonProperty("Questions")
  public List<Response> getResponses() {
    return responses;
  }

  @JsonIgnore
  public void addResponse(String questionId, String answer) {
    responses.add(new Response(questionId, answer));
  }

  @Override
  public String toString() {
    return "Application{" +
        "name='" + name + '\'' +
        ", responses=" + responses +
        '}';
  }
}
