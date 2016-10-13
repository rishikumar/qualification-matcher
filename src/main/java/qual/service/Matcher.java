package qual.service;

import qual.model.Application;
import qual.model.Qualification;
import qual.model.Response;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Matcher {

  private Qualification qualification;

  public Matcher(Qualification qualification) {
    this.qualification = qualification;
  }

  public List<Application> findMatchingApplications(List<Application> applications) {
    return applications.stream().filter(this::meetsQualifications).collect(Collectors.toList());
  }

  private boolean meetsQualifications(Application application) {
    return application.getResponses().stream().allMatch(this::isMatchingOnQualifications);
  }

  private boolean isMatchingOnQualifications(Response response) {
    Set<String> qualificationAnswers = qualification.getAnswersForQuestion(response.getQuestionId());
    return qualificationAnswers.contains(response.getAnswer());
  }




}
