package qual.service;

import qual.model.Application;
import qual.model.Qualification;
import qual.model.Response;

import java.util.Set;

/**
 * Matcher implementation that checks that all questions in an application meet the qualifications
 */

public class AllQuestionMatcher extends Matcher {

  public AllQuestionMatcher(Qualification qualification) {
    super(qualification);
  }

  /**
   * Predicate that checks that the given application has responses that meet the qualifications
   * @param application the application to check
   * @return true if the application has all valid responses (e.g. matching the qualifications), false otherwise
   */
  boolean meetsQualifications(Application application) {
    return application.getResponses().size() > 0 &&
        application.getResponses().stream().allMatch(this::isMatchingOnQualifications);
  }

  /**
   * Checks if an individual question's response is found in the qualifications object
   * @param response response object to check
   * @return true if the question and response are found in the qualifications object. false otherwise
   */
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
