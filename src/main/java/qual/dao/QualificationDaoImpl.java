package qual.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import qual.model.Qualification;

import java.io.IOException;

public class QualificationDaoImpl implements QualificationDao {

  private String filename;

  @Inject
  public QualificationDaoImpl(@Named("qualification.file.path") String filename) {
    this.filename = filename;
  }

  @Override
  public Qualification findQualifications() {
    return parseJson();
  }

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

  private ArrayNode getRootArray() {
    JsonNode root = getRootNode();

    if (!root.isArray()) {
      throw new RuntimeException("Couldn't parse the qualification file into an array");
    }

    return (ArrayNode) root;
  }

  private JsonNode getRootNode() {
    JsonNode root = null;

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      root = objectMapper.readValue(getClass().getClassLoader().getResource(filename), JsonNode.class);
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    return root;
  }

}
