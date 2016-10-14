package qual.exec;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import qual.dao.ApplicationDao;
import qual.dao.QualificationDao;
import qual.model.Application;
import qual.model.Qualification;
import qual.service.AllQuestionMatcher;
import qual.service.Matcher;

import java.util.List;

/**
 * Main executable class for the application
 */
public class Bootstrap {

  private QualificationDao qualificationDao;
  private ApplicationDao applicationDao;

  @Inject
  Bootstrap(QualificationDao qualificationDao, ApplicationDao applicationDao) {
    this.qualificationDao = qualificationDao;
    this.applicationDao = applicationDao;
  }

  /**
   * Main class
   * @param args command line arguments, none are required to run this application
   */
  public static void main(String... args) {
    Injector injector = Guice.createInjector(new AppModule());
    Bootstrap application = injector.getInstance(Bootstrap.class);

    try {
      application.start();
    }
    catch (Throwable t) {
      t.printStackTrace();
    }
  }

  /**
   * Main application launcher
   */
  private void start() {
    // Lookup data
    Qualification qualification = qualificationDao.findQualifications();
    List<Application> applications = applicationDao.findApplications();

    // Execute the matching algorithm
    // Only one Matcher has been implemented: AllQuestionMatcher
    Matcher matcher = new AllQuestionMatcher(qualification);
    List<Application> qualifiedApplications = matcher.findMatchingApplications(applications);

    // print to standard out and save qualified applications to disk
    qualifiedApplications.forEach(System.out::println);
    applicationDao.saveApplications(qualifiedApplications);
  }

}
