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

public class Bootstrap {

  private QualificationDao qualificationDao;
  private ApplicationDao applicationDao;

  @Inject
  Bootstrap(QualificationDao qualificationDao, ApplicationDao applicationDao) {
    this.qualificationDao = qualificationDao;
    this.applicationDao = applicationDao;
  }

  private void start() {
    Qualification qualification = qualificationDao.findQualifications();
    List<Application> applications = applicationDao.findApplications();

    Matcher matcher = new AllQuestionMatcher(qualification);
    List<Application> qualifiedApplications = matcher.findMatchingApplications(applications);

    print(qualifiedApplications);
    applicationDao.saveApplications(qualifiedApplications);
  }


  private void print(List<Application> applications) {
    applications.forEach(System.out::println);
  }

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

}
