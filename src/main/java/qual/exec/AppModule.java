package qual.exec;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import qual.dao.ApplicationDao;
import qual.dao.ApplicationDaoImpl;
import qual.dao.QualificationDao;
import qual.dao.QualificationDaoImpl;

import java.io.IOException;
import java.util.Properties;


public class AppModule extends AbstractModule {

  @Override
  protected void configure() {
    loadProperties();

    // Bind interfaces to implementations
    bind(QualificationDao.class).to(QualificationDaoImpl.class);
    bind(ApplicationDao.class).to(ApplicationDaoImpl.class);
  }

  private void loadProperties() {
    Properties properties = new Properties();

    try {
      properties.load(getClass().getClassLoader().getResourceAsStream("app.properties"));
    }
    catch (IOException e) {
      System.out.println(e.toString());
    }

    Names.bindProperties(binder(), properties);
  }

}
