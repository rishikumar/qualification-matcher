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

/**
 * Implementation of ApplicationDao, read and write capability for Applications
 */
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

  /**
   * Retrieves Applications from the provided input path. Each doocument maps to one Application object
   *
   * @return List of Applications
   */
  @Override
  public List<Application> findApplications() {
    return parseJson();
  }


  /**
   * Provides the capability to save applications to the output directory. This is used to save applications meet
   * the qualifications for consideration.
   *
   * @param applications the list of applications to save
   */
  @Override
  public void saveApplications(List<Application> applications) {
    applications.forEach(this::writeApplication);
  }

  /**
   * Setup the input paths by finding them on the classpath
   *
   * @param inputPath  the input file path as defined in app.properties
   * @param outputPath the output file path as defined in app.properties
   */
  private void registerPaths(String inputPath, String outputPath) {
    ClassLoader cl = getClass().getClassLoader();
    this.inputPath = cl.getResource(inputPath).getPath();
    this.outputPath = cl.getResource(outputPath).getPath();
  }

  /**
   * Takes a list of files from the input directory and translates them into a list
   *
   * @return List of application objects
   */

  private List<Application> parseJson() {
    List<File> fileList = getInputFileList();
    return fileList.stream().map(this::parseApplicationFrom).collect(Collectors.toList());
  }

  /**
   * Find and return all the files in the input path
   *
   * @return List of input applicant files to process
   */
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

  /**
   * Parses an Application object from the provided file
   *
   * @param f the file ot parse
   * @return the application object
   */
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

  /**
   * Persist an application to the output path. The file name is taking from the applicant's name
   *
   * @param application the application to save
   */

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
