package qual.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO to manage a question and answer pair submitted by the applicant
 */
public class Response {
  private String questionId;
  private String answer;

  public Response() {
    // default constructor required for JSON Data binding
  }

  public Response(String questionId, String answer) {
    this.questionId = questionId;
    this.answer = answer;
  }

  @JsonProperty("Id")
  public String getQuestionId() {
    return questionId;
  }

  @JsonProperty("Answer")
  public String getAnswer() {
    return answer;
  }

  @Override
  public String toString() {
    return "Response{" +
        "questionId='" + questionId + '\'' +
        ", answer='" + answer + '\'' +
        '}';
  }
}
