package qual.dao;

import qual.model.Application;

import java.util.List;

public interface ApplicationDao {
  List<Application> findApplications();

  void saveApplications(List<Application> applications);
}
