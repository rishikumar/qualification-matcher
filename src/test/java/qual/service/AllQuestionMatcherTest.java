package qual.service;

import org.junit.Assert;
import org.junit.Test;
import qual.model.Application;
import qual.model.Qualification;

import java.util.ArrayList;
import java.util.List;

/**
 * Validate matching scenarios for the AllQuestionMatcher
 */
public class AllQuestionMatcherTest {

  /**
   * Basic test that makes sure that we can find successful matches and filter out ones that don't match
   */
  @Test
  public void testBasicMatchAndFilterScenario() {

    //
    // Setup test data
    //

    Qualification qualification = getQualification();

    List<Application> applications = new ArrayList<>();

    // this one will be found
    Application a1 = new Application("Name1");
    a1.addResponse("Q1", "A1");
    a1.addResponse("Q2", "A2");
    applications.add(a1);

    // this one will not
    Application a2 = new Application("Name2");
    a2.addResponse("Q1", "A1");
    a2.addResponse("Q2", "A4");
    applications.add(a2);

    // this one will be found
    Application a3 = new Application("Name3");
    a3.addResponse("Q1", "A1");
    a3.addResponse("Q3", "A1");
    applications.add(a3);

    //
    // Invoke Matcher
    //

    Matcher matcher = new AllQuestionMatcher(qualification);
    List<Application> qualifiedApplicants = matcher.findMatchingApplications(applications);

    //
    // Validate Assertions
    //

    // confirm that two are found.
    Assert.assertNotNull(qualifiedApplicants);
    Assert.assertTrue(qualifiedApplicants.size() == 2);

    // check their names to make sure we got the right ones
    Assert.assertTrue(qualifiedApplicants.get(0).getName().equals("Name1"));
    Assert.assertTrue(qualifiedApplicants.get(1).getName().equals("Name3"));
  }

  /**
   * Test an application that has a question that we weren't expecting. Making the assumption that this should
   * disqualify the application. (Its most likely a bug in the code that generated the JSON application)
   */

  @Test
  public void testApplicantWithUnknownResponse() {

    //
    // Setup test data
    //

    Qualification qualification = getQualification();

    List<Application> applications = new ArrayList<>();

    Application a1 = new Application("Name1");
    a1.addResponse("Q1", "A1");
    a1.addResponse("UNKNOWN", "A2"); // unknown question
    applications.add(a1);


    //
    // Invoke Matcher
    //

    Matcher matcher = new AllQuestionMatcher(qualification);
    List<Application> qualifiedApplicants = matcher.findMatchingApplications(applications);

    //
    // Validate Assertions
    //
    Assert.assertNotNull(qualifiedApplicants);
    Assert.assertTrue(qualifiedApplicants.size() == 0);
  }


  /**
   * Test that if the application has no responses, it should disqualify the application.
   */
  @Test
  public void testApplicantWithNoResponses() {

    //
    // Setup test data
    //

    Qualification qualification = getQualification();

    List<Application> applications = new ArrayList<>();

    Application a1 = new Application("Name1");
    applications.add(a1);

    //
    // Invoke Matcher
    //

    Matcher matcher = new AllQuestionMatcher(qualification);
    List<Application> qualifiedApplicants = matcher.findMatchingApplications(applications);

    //
    // Validate Assertions
    //
    Assert.assertNotNull(qualifiedApplicants);

    // user should still be found since we are just ignoring the question
    Assert.assertTrue(qualifiedApplicants.size() == 0);

  }


  /**
   * Utility method that builds a qualification object that we can test against
   * @return the test qualification object
   */
  private Qualification getQualification() {
    Qualification qualification = new Qualification();

    qualification.add("Q1", "A1");
    qualification.add("Q1", "A2");
    qualification.add("Q1", "A3");
    qualification.add("Q2", "A1");
    qualification.add("Q2", "A2");
    qualification.add("Q3", "A1");

    return qualification;
  }

}
