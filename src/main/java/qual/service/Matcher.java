package qual.service;

import com.google.inject.Inject;
import qual.dao.QualificationDao;
import qual.model.Application;
import qual.model.Qualification;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Matcher {

  private Qualification qualification;

  @Inject
  public Matcher(QualificationDao qualificationDao) {
    this.qualification = null;
  }


  public Matcher(Qualification qualification) {
    this.qualification = qualification;
  }

  public List<Application> findMatchingApplications(List<Application> applications) {
    return applications.stream()
        .filter(this::meetsQualifications)
        .collect(Collectors.toList());
  }

  private boolean meetsQualifications(Application application) {
    // TODO: make more functional?
    // TODO: externalize the matching criteria into an injected Matching Strategy
    for (String question : application.getResponses().keySet()) {
      Set<String> qualificationAnswers = qualification.getAnswersForQuestion(question);
      String applicantAnswer = application.getResponses().get(question);

      if (!isMatchingOnQualifications(qualificationAnswers, applicantAnswer)) {
        // If any aren't matching, no point in continuing to check. This application is shown the door.
        return false;
      }
    }

    return true;
  }

  private boolean isMatchingOnQualifications(Set<String> qualificationAnswers, String applicantAnswer) {
    return qualificationAnswers.contains(applicantAnswer);
  }




}
