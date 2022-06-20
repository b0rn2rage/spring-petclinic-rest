package tinkoff.edu.api;

import org.junit.jupiter.api.BeforeAll;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import java.sql.*;

import static io.restassured.RestAssured.*;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;

public class CrashControllerTests {

	private static Connection connection;

	@BeforeAll
	public static void setUpErrorLogging() {
		enableLoggingOfRequestAndResponseIfValidationFails();
	}

	@BeforeAll
	public static void connect() throws SQLException {
		connection = DriverManager.getConnection("jdbc:postgresql://localhost/petclinic", "petclinic", "petclinic");
	}

	@Test
	public void shouldGetExceptionStringWhenPopulatedDB() {
		given().contentType("application/json").when().get("http://localhost:8080/oups").then().statusCode(200);
	}

}
