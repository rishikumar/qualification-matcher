package qual.exec;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import qual.dao.ApplicationDao;
import qual.dao.ApplicationDaoImpl;
import qual.dao.QualificationDao;
import qual.dao.QualificationDaoImpl;

import java.io.IOException;
import java.util.Properties;


/**
 * Guice module that's used for the dependency inject and property file loading into Guice's dependency graph
 */
public class AppModule extends AbstractModule {


  /**
   * Configuration setup function for Guice
   */
  @Override
  protected void configure() {
    loadProperties();

    // Bind interfaces to implementations
    bind(QualificationDao.class).to(QualificationDaoImpl.class);
    bind(ApplicationDao.class).to(ApplicationDaoImpl.class);
  }

  /**
   * Load the application properties file and bind them into guice
   */
  private void loadProperties() {
    Properties properties = new Properties();

    try {
      properties.load(getClass().getClassLoader().getResourceAsStream("app.properties"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    Names.bindProperties(binder(), properties);
  }

}
