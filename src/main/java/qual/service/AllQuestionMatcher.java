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
    return application.getResponses().size() > 0 &&
        application.getResponses().stream().allMatch(this::isMatchingOnQualifications);
  }

  private boolean isMatchingOnQualifications(Response response) {
    Set<String> qualificationAnswers = qualification.getAnswersForQuestion(response.getQuestionId());

    if (qualificationAnswers == null) {
      // Can happen if an applicant has answered a question that we don't have in our qualifications.
      // I'm making the assumption here that this is an error condition that will reject the Applicant
      return false;
    }

    return qualificationAnswers.contains(response.getAnswer());
  }

}
