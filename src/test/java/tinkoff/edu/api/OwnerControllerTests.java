package tinkoff.edu.api;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;

public class OwnerControllerTests {
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
	public void shouldGetOwnersWhenPopulatedDB () {
		given()
			.contentType("application/json")
		.when()
			.get("http://localhost:8080/owners")
		.then()
			.statusCode(200)
			.body(
				JsonSchemaValidator.
					matchesJsonSchema(new File(
						"src/test/java/tinkoff/edu/data/ownerControllerSchema"))
			);
	}

	@Test
	public void should404WhenIncorrectResource () {
		given()
			.contentType("application/json")
		.when()
			.get("http://localhost:8080/ownerssss")
		.then()
			.statusCode(404);
	}

	@Test
	public void shouldAddNewPetWhenIdIsNotExist () throws SQLException {
		given()
			.contentType("application/json")
			.body("\n" +
				"  {\n" +
				"    \"id\": 10,\n" +
				"    \"firstName\": \"Pet\",\n" +
				"    \"lastName\": \"Mc\",\n" +
				"    \"address\": \"2387 S. Fair Way\",\n" +
				"    \"city\": \"Madison\",\n" +
				"    \"telephone\": \"6085552765\",\n" +
				"    \"pets\": [\n" +
				"      {\n" +
				"        \"id\": 6,\n" +
				"        \"name\": \"George\",\n" +
				"        \"birthDate\": \"2000-01-20\",\n" +
				"        \"visits\": [],\n" +
				"        \"new\": false\n" +
				"      }\n" +
				"    ],\n" +
				"    \"new\": false\n" +
				"  }")
		.when()
			.post("http://localhost:8080/owners")
		.then()
			.statusCode(201);
		PreparedStatement preparedSql = connection.prepareStatement(
			"DELETE FROM owners WHERE id = ?"
		);
		preparedSql.setInt(1, 10);
		preparedSql.executeUpdate();
	}
}
