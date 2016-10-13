package qual.exec;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import qual.dao.ApplicationDao;
import qual.dao.QualificationDao;
import qual.model.Application;
import qual.model.Qualification;
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

  void start() {
    Qualification qualification = qualificationDao.findQualifications();
    List<Application> applications = applicationDao.findApplications();

    Matcher matcher = new Matcher(qualification);
    List<Application> qualifiedApplications = matcher.findMatchingApplications(applications);

    print(qualifiedApplications);
    // TODO: Save output to a JSON file (to the build folder)
  }


  private void print(List<Application> applications) {
    System.out.println(applications);
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
