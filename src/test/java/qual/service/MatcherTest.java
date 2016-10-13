package qual.service;

import org.junit.Assert;
import org.junit.Test;
import qual.model.Application;
import qual.model.Qualification;

import java.util.ArrayList;
import java.util.List;

public class MatcherTest {

  @Test
  public void testBasicMatchScenario() {

    Qualification qualification = getQualification();

    List<Application> applications = new ArrayList<>();

    // this one will be found
    Application a1 = new Application.Builder()
        .setName("name1")
        .addQuestionAndResponse("Q1", "A1")
        .addQuestionAndResponse("Q2", "A2")
        .build();
    applications.add(a1);

    // this one will not
    Application a2 = new Application.Builder()
        .setName("name1")
        .addQuestionAndResponse("Q1", "A1")
        .addQuestionAndResponse("Q2", "A4")
        .build();
    applications.add(a2);

    // this one will be found
    Application a3 = new Application.Builder()
        .setName("name1")
        .addQuestionAndResponse("Q1", "A1")
        .addQuestionAndResponse("Q3", "A1")
        .build();
    applications.add(a3);

    Matcher matcher = new Matcher(qualification);
    List<Application> qualifiedApplicants = matcher.findMatchingApplications(applications);

    Assert.assertNotNull(qualifiedApplicants);
    Assert.assertTrue(qualifiedApplicants.size() == 2);
  }

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

  // TODO: what happens if a user answered a question that isn't in the qualifications? Do we ignore it? Add a test for this
  // TODO: user doesn't have any questions and answers - they are all blank
}
