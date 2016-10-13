package qual.model;

import java.util.HashMap;
import java.util.Map;

public class Application {
  private String name;

  // Key: questionId, Value: Response
  private Map<String, String> responses;


  private Application(String name, Map<String, String> responses) {
    this.name = name;
    this.responses = responses;
  }

  public String getName() {
    return name;
  }

  public Map<String, String> getResponses() {
    return responses;
  }

  public static class Builder {
    private String name;
    private Map<String, String> responses;

    public Builder setName(final String name) {
      this.name = name;
      return this;
    }

    public Builder addQuestionAndResponse(String question, String response) {
      if (responses == null) {
        responses = new HashMap<>();
      }

      this.responses.put(question, response);

      return this;
    }

    public Application build() {
      return new Application(name, responses);
    }

  }

}
