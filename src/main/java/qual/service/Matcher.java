package qual.service;

import qual.model.Application;
import qual.model.Qualification;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Matcher abstraction that allows concrete implementers to specify how they choose to satisfy the qualifications
 */
public abstract class Matcher {

  protected Qualification qualification;

  public Matcher(Qualification qualification) {
    this.qualification = qualification;
  }

  /**
   * Go through the application list and check each one to see if it meets the qualifications (as defined by the
   * implementing class). For those that qualify, it creates a List of application objects to return
   *
   * @param applications input applications before filtering
   * @return the list of applications that meet the qualifications
   */
  public List<Application> findMatchingApplications(List<Application> applications) {
    return applications.stream().filter(this::meetsQualifications).collect(Collectors.toList());
  }

  /**
   * Abstract predicate that checks if the application meets the qualifications as laid out by the implementing class
   *
   * @param application the application to check
   * @return true if qualifications are met, false otherwise
   */
  abstract boolean meetsQualifications(Application application);

}
