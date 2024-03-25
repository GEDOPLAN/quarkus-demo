package de.gedoplan.showcase.api;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringReader;
import java.util.Base64;
import java.util.stream.Stream;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonReader;
import jakarta.ws.rs.core.MediaType;

@QuarkusTest
public class UserInfoResourceTest {

  @ParameterizedTest
  @MethodSource("provideUserPassword")
  void testName(String user, String password) {
    int expectedStatusCode = 204;

    RequestSpecification requestSpecification = given();

    if (user != null) {
      requestSpecification.header("Authorization", getBasicAuthHeaderValue(user, password));
      expectedStatusCode = 200;
    }

    ExtractableResponse<Response> response = requestSpecification
      .accept(MediaType.TEXT_PLAIN)
      .redirects().follow(false)
      .when()
      .get("/api/user-info/name")
      .then()
      .extract();

    assertEquals(expectedStatusCode, response.statusCode());

    if (user!=null) {
      assertEquals(user, response.asString());
    }
  }

  @ParameterizedTest
  @MethodSource("provideParameters")
  void testRestricted(String user, String password, String path, int expectedStatusCode) {
    RequestSpecification requestSpecification = given();

    if (user != null)
      requestSpecification.header("Authorization", getBasicAuthHeaderValue(user, password));

    requestSpecification
      .accept(MediaType.TEXT_PLAIN)
      .redirects().follow(false)
      .when()
      .get(path)
      .then()
      .statusCode(expectedStatusCode);
  }

  private static Stream<Arguments> provideUserPassword() {
    return Stream.of(
      Arguments.of("hugo", "hugo_123"),
      Arguments.of("otto", "otto_123"),
      Arguments.of("willi", "willi_123"),
      Arguments.of(null, null)
    );
  }

  private static Stream<Arguments> provideParameters() {
    return Stream.of(
      Arguments.of("hugo", "hugo_123", "/api/user-info/restricted", 200),
      Arguments.of("hugo", "hugo_123", "/api/user-info/restricted2", 200),
      Arguments.of("otto", "otto_123", "/api/user-info/restricted", 200),
      Arguments.of("otto", "otto_123", "/api/user-info/restricted2", 403),
      Arguments.of("willi", "willi_123", "/api/user-info/restricted", 403),
      Arguments.of("willi", "willi_123", "/api/user-info/restricted2", 403),
      Arguments.of(null, null, "/api/user-info/restricted", 401),
      Arguments.of(null, null, "/api/user-info/restricted2", 401)
    );
  }

  private static String getBasicAuthHeaderValue(String user, String pwd) {
    String userAndPwd = user + ":" + pwd;
    String userAndPwdEncoded = Base64.getEncoder().encodeToString(userAndPwd.getBytes());
    String headerValue = "Basic " + userAndPwdEncoded;
    return headerValue;
  }
}
