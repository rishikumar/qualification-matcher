package qual.dao;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import qual.model.Qualification;

public class QualificationDaoImpl implements QualificationDao {

  private String fileName;

  @Inject
  public QualificationDaoImpl(@Named("qualification.file.path") String fileName) {
    this.fileName = fileName;
  }

  @Override
  public Qualification findQualifications() {
    return null;
  }
}
