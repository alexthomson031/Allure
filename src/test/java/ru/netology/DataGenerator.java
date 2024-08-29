package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import static io.restassured.RestAssured.given;

@UtilityClass
public class DataGenerator {
    Faker faker = new Faker(new Locale("en"));

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @BeforeAll
    static void registerUser(RegistrationInfo registrationInfo) {
        given()
                .spec(requestSpec)
                .body(registrationInfo)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    private static RegistrationInfo generateUser(String status) {
        String login = faker.name().firstName();
        String password = faker.internet().password();
        return new RegistrationInfo(login, password, status);
    }

    public static RegistrationInfo noRegisteredPassword() {
        val registrationInfo = generateUser("active");
        registerUser(registrationInfo);
        return new RegistrationInfo(registrationInfo.getLogin(), "password", "active");
    }

    public static RegistrationInfo noRegisteredName() {
        val registrationInfo = generateUser("active");
        registerUser(registrationInfo);
        return new RegistrationInfo("Andrey", registrationInfo.getPassword(), "active");
    }

    public static RegistrationInfo registeredStatus(String status) {
        val registrationInfo = generateUser(status);
        registerUser(registrationInfo);
        return registrationInfo;
    }
}