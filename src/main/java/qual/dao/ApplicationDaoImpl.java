package qual.dao;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import qual.model.Application;

import java.util.List;

public class ApplicationDaoImpl implements ApplicationDao {

  private String filename;

  @Inject
  public ApplicationDaoImpl(@Named("application.file.path") String filename) {
    this.filename = filename;
  }

  @Override
  public List<Application> findApplications() {
    return parseJson();
  }

  private List<Application> parseJson() {
//    JsonNode root = getRootNode();
    return null;
  }



}
