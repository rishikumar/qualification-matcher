package qual.dao;

import qual.model.Application;

import java.util.List;

/**
 * Interface that provides an abstraction for the concrete DAO class
 */

public interface ApplicationDao {


  /**
   * Find a list of Applicants from the backing data store
   *
   * @return List of Applications
   */
  List<Application> findApplications();

  /**
   * Persist the list of applications
   *
   * @param applications the list of applications to save
   */

  void saveApplications(List<Application> applications);
}
