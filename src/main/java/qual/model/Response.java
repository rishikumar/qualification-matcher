package qual.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
  private String questionId;
  private String answer;

  @JsonProperty("Id")
  public String getQuestionId() {
    return questionId;
  }

  @JsonProperty("Answer")
  public String getAnswer() {
    return answer;
  }
}
