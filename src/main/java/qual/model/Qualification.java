package qual.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Qualification {

  private Map<String, Set<String>> questionMap = new HashMap<>();

  public void addQuestionAndAnswer(String question, String answer) {
    Set<String> answers = findExistingAnswers(question);
    answers.add(answer);
  }

  public Set<String> getAnswersForQuestion(String question) {
    return questionMap.get(question);
  }


  private Set<String> findExistingAnswers(String question) {
    Set<String> answers = questionMap.get(question);

    if (answers != null) {
      // we have a an object created to hold the answers
      return answers;
    }

    // no object found - let's create a new one and add it to the map before returning it
    answers = new HashSet<>();
    questionMap.put(question, answers);
    return answers;
  }

  // TODO: Builder Pattern to load Qualification? Would be nice to make them immutable

}
