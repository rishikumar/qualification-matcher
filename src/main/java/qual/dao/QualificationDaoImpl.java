package qual.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import qual.model.Qualification;

import java.io.IOException;


/**
 * Implementation of QualificationDao, read and write capability for Applications
 */
public class QualificationDaoImpl implements QualificationDao {

  private String filename;

  @Inject
  public QualificationDaoImpl(@Named("qualification.file.path") String filename) {
    this.filename = filename;
  }

  /**
   * Retrieve the Qualification object from the json source file
   *
   * @return the Qualification object
   */
  @Override
  public Qualification findQualifications() {
    return parseJson();
  }


  /**
   * Parse the incoming file to build the qualification object. The JSON file format doesn't map well to my
   * Qualification object, so I'm doing the mapping manually by parsing the JSON object directly
   *
   * @return the Qualification object
   */
  private Qualification parseJson() {
    ArrayNode arrayNode = getRootArray();

    Qualification qualification = new Qualification();

    for (JsonNode node : arrayNode) {
      String questionId = node.get("Id").asText();
      String answer = node.get("Answer").asText();

      qualification.add(questionId, answer);
    }

    return qualification;
  }


  /**
   * Retrieve the root array object from the json file
   *
   * @return an ArrayNode, the root object of the Qualification json file
   */
  private ArrayNode getRootArray() {
    JsonNode root = getRootNode();

    if (!root.isArray()) {
      throw new RuntimeException("Couldn't parse the qualification file into an array");
    }

    return (ArrayNode) root;
  }


  /**
   * Read the root node from the input file
   *
   * @return the root node
   */
  private JsonNode getRootNode() {
    JsonNode root = null;

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      root = objectMapper.readValue(getClass().getClassLoader().getResource(filename), JsonNode.class);
    }
    catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }

    return root;
  }

}
