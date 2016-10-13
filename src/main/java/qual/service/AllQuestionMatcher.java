package qual.service;

import qual.model.Application;
import qual.model.Qualification;
import qual.model.Response;

import java.util.Set;

public class AllQuestionMatcher extends Matcher {

  public AllQuestionMatcher(Qualification qualification) {
    super(qualification);
  }

  boolean meetsQualifications(Application application) {
    return application.getResponses().stream().allMatch(this::isMatchingOnQualifications);
  }

  private boolean isMatchingOnQualifications(Response response) {
    Set<String> qualificationAnswers = qualification.getAnswersForQuestion(response.getQuestionId());
    return qualificationAnswers.contains(response.getAnswer());
  }

}
