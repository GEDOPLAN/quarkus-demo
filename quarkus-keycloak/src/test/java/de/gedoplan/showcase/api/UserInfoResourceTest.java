package de.gedoplan.showcase.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class UserInfoResourceTest {

  @Inject
  Logger logger;

  @Inject
  @ConfigProperty(name = "quarkus.oidc.auth-server-url")
  String keycloakUrl;

  @Inject
  @ConfigProperty(name = "quarkus.oidc.client-id")
  String keycloakClientId;

  @Inject
  @ConfigProperty(name = "quarkus.oidc.credentials.secret")
  String keycloakClientPassword;

  @ParameterizedTest
  @MethodSource("provideUserPassword")
  void testName(String user, String password) {
    int expectedStatusCode = 204;

    RequestSpecification requestSpecification = given();

    if (user != null) {
      requestSpecification.header("Authorization", "Bearer " + retrieveAccessToken(user, password));
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
      requestSpecification.header("Authorization", "Bearer " + retrieveAccessToken(user, password));

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
      Arguments.of(null, null)
    );
  }

  private static Stream<Arguments> provideParameters() {
    return Stream.of(
      Arguments.of("hugo", "hugo_123", "/api/user-info/restricted", 200),
      Arguments.of("hugo", "hugo_123", "/api/user-info/restricted2", 200),
      Arguments.of("otto", "otto_123", "/api/user-info/restricted", 200),
      Arguments.of("otto", "otto_123", "/api/user-info/restricted2", 403),
      Arguments.of(null, null, "/api/user-info/restricted", 401),
      Arguments.of(null, null, "/api/user-info/restricted2", 401)
    );
  }

  private String retrieveAccessToken(String user, String password) {

    return given()
      .with()
      .auth().preemptive().basic(keycloakClientId, keycloakClientPassword)
      .contentType("application/x-www-form-urlencoded; charset=utf-8")
      .formParam("grant_type", "password")
      .formParam("username", user)
      .formParam("password", password)
      .when()
      .post(keycloakUrl + "/protocol/openid-connect/token")
      .then()
      .statusCode(200)
      .extract()
      .as(JSONObject.class)
      .get("access_token")
      .toString();
  }
}
