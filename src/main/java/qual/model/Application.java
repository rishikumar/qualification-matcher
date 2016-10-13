package qual.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Application {

  private String name;
  private List<Response> responses;

  @JsonProperty("Name")
  public String getName() {
    return name;
  }

  @JsonProperty("Questions")
  public List<Response> getResponses() {
    return responses;
  }

  @Override
  public String toString() {
    return "Application{" +
        "name='" + name + '\'' +
        ", responses=" + responses +
        '}';
  }
}
