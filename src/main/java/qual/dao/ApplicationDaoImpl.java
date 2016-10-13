package qual.dao;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import qual.model.Application;

import java.util.List;

public class ApplicationDaoImpl implements ApplicationDao {

  private String fileName;

  @Inject
  public ApplicationDaoImpl(@Named("application.file.path") String fileName) {
    this.fileName = fileName;
  }

  @Override
  public List<Application> findApplications() {
    return null;
  }
}
