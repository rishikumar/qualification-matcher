package qual.dao;

import qual.model.Qualification;

/**
 * Interface that provides an abstraction for the concrete DAO class
 */
public interface QualificationDao {

  /**
   * Query the DAO to retrieve the Qualification object - this stores the list of questions and valid answers
   * @return The qualification object
   */
  Qualification findQualifications();
}
