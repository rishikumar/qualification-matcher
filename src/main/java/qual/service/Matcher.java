package qual.service;

import qual.model.Application;
import qual.model.Qualification;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Matcher {

  protected Qualification qualification;

  public Matcher(Qualification qualification) {
    this.qualification = qualification;
  }

  public List<Application> findMatchingApplications(List<Application> applications) {
    return applications.stream().filter(this::meetsQualifications).collect(Collectors.toList());
  }

  abstract boolean meetsQualifications(Application application);

}
