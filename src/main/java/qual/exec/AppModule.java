package qual.exec;

import com.google.inject.AbstractModule;
import qual.dao.QualificationDao;
import qual.dao.QualificationDaoImpl;

public class AppModule extends AbstractModule {

  @Override
  protected void configure() {

    bind(QualificationDao.class).to(QualificationDaoImpl.class);

  }
}
