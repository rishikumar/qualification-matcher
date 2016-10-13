package qual.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Qualification {

  private Map<String, Set<String>> questionMap;

  public Qualification() {
     questionMap = new HashMap<>();
  }

  public void add(String questionId, String answer) {
    Set<String> answers = findOrCreateWith(questionId);
    answers.add(answer);
  }

  public Set<String> getAnswersForQuestion(String questionId) {
    return questionMap.get(questionId);
  }

  private Set<String> findOrCreateWith(String questionId) {
    Set<String> answers = questionMap.get(questionId);

    if (answers != null) {
      // we have a an object created to hold the answers
      return answers;
    }

    // no object found - let's create a new one and add it to the map before returning it
    answers = new HashSet<>();
    questionMap.put(questionId, answers);
    return answers;
  }

}
