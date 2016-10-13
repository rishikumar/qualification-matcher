package qual.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import qual.model.Application;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationDaoImpl implements ApplicationDao {

  private String path;
  private ObjectMapper objectMapper;

  @Inject
  public ApplicationDaoImpl(@Named("application.path") String path) {
    this.path = path;
    objectMapper = new ObjectMapper();
  }

  @Override
  public List<Application> findApplications() {
    return parseJson();
  }

  private List<Application> parseJson() {
    List<File> fileList = getFileList();
    return fileList.stream().map(this::parseApplicationFrom).collect(Collectors.toList());
  }

  private List<File> getFileList() {
    try {
      File folder = new File(getClass().getClassLoader().getResource(path).getPath());

      File[] files = folder.listFiles();
      if (files == null || files.length == 0) {
        throw new RuntimeException("Could not locate applicant files");
      }

      return Arrays.asList(files);
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

  private Application parseApplicationFrom(File f) {
    Application application = null;

    try {
      application = objectMapper.readValue(f, Application.class);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }

    return application;
  }

}
