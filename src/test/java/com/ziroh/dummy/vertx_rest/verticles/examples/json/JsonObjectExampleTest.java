package com.ziroh.dummy.vertx_rest.verticles.examples.json;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonObjectExampleTest {
  @Test
  void jsonObjectCanBeMapped() {
    JsonObject jsonObject = new JsonObject();
    jsonObject
      .put("id", 1)
      .put("name", "Pupu")
      .put("vertx", true);
    String encoded = jsonObject.encode();
    assertEquals("{\"id\":1,\"name\":\"Pupu\",\"vertx\":true}", encoded);
    // Alt. way of doing the above test
    JsonObject decodedJsonObject = new JsonObject(encoded);
    assertEquals(jsonObject, decodedJsonObject);
  }

  @Test
  void jsonObjectCanBeCreatedFromMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("id", 1);
    map.put("name", "Pupu");
    map.put("vertx", true);
    JsonObject jsonObject = new JsonObject(map);
    assertEquals(map, jsonObject.getMap());
    assertEquals(1, jsonObject.getInteger("id"));
    assertEquals("Pupu", jsonObject.getString("name"));
    assertEquals(true, jsonObject.getBoolean("vertx"));
  }

  @Test
  void jsonArrayCanBeMapped() {
    JsonArray jsonArray = new JsonArray()
      .add(new JsonObject().put("id", 1))
      .add(new JsonObject().put("id", 2))
      .add(new JsonObject().put("id", 3))
      .add("RandomDataTypeCanBeUsedInJsonArray");
    assertEquals("[{\"id\":1},{\"id\":2},{\"id\":3},\"RandomDataTypeCanBeUsedInJsonArray\"]", jsonArray.encode());
  }

  @Test
  void canMapJavaObjects() {
    Person person = new Person(1, "Pupu", true);
    JsonObject pupu = JsonObject.mapFrom(person);
    assertEquals(person.getId(), pupu.getInteger("id"));
    assertEquals(person.getName(), pupu.getString("name"));
    assertEquals(person.getVertxUse(), pupu.getBoolean("vertxUse"));

    Person person1 = pupu.mapTo(Person.class);
    assertEquals(person.getId(), person1.getId());
    assertEquals(person.getName(), person1.getName());
    assertEquals(person.getVertxUse(), person1.getVertxUse());
  }
}
