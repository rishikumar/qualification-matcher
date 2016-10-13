package qual.dao;

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

  private String inputPath;
  private String outputPath;
  private ObjectMapper objectMapper;

  @Inject
  public ApplicationDaoImpl(@Named("application.path.input") String inputPath,
                            @Named("application.path.output") String outputPath) {

    registerPaths(inputPath, outputPath);
    objectMapper = new ObjectMapper();
  }

  private void registerPaths(String inputPath, String outputPath) {
    ClassLoader cl = getClass().getClassLoader();
    this.inputPath = cl.getResource(inputPath).getPath();
    this.outputPath = cl.getResource(outputPath).getPath();
  }

  @Override
  public List<Application> findApplications() {
    return parseJson();
  }

  private List<Application> parseJson() {
    List<File> fileList = getInputFileList();
    return fileList.stream().map(this::parseApplicationFrom).collect(Collectors.toList());
  }

  private List<File> getInputFileList() {
    try {
      File folder = new File(inputPath);

      File[] files = folder.listFiles();
      if (files == null || files.length == 0) {
        throw new RuntimeException("Could not locate applicant files");
      }

      return Arrays.stream(files).filter(File::isFile).collect(Collectors.toList());
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


  @Override
  public void saveApplications(List<Application> applications) {
    applications.forEach(this::writeApplication);
  }

  private void writeApplication(Application application) {
    String filename = outputPath + "/" + application.getName() + ".json";

    try {
      objectMapper.writeValue(new File(filename), application);
    }
    catch (IOException e) {
      e.printStackTrace();
      System.out.println("Could not write application=[" + application + "] to file=[" + filename + "]");
    }
  }

}
