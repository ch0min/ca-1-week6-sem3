package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import entities.Address;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;

import io.restassured.parsing.Parser;

import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


//@Disabled
public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person p1;
    private static PersonDTO p2;
    private static PersonDTO pdto;
    private static Phone ph1, ph2;
    private static Hobby h1,h2;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;


    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;

    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        ph1 = new Phone(29842712, "HOME");
        ph2 = new Phone(12345678, "WORK");

        h1 = new Hobby("3D-udskrivning", "https://en.wikipedia.org/wiki/3D_printing", "Generel", "Indendørs");
        h2 = new Hobby("Akrobatik", "https://en.wikipedia.org/wiki/Acrobatics", "Generel", "Indendørs");

        p1 = new Person("test@test.dk","oscar","tuff",new Address("vejen 1", 2900));
        p1.getHobbies().add(h1);
        p1.getHobbies().add(h2);
        p1.getPhones().add(ph1);
        p2 = null;
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAllRows").executeUpdate();
            em.createNamedQuery("Address.deleteAllRows").executeUpdate();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.createNamedQuery("Hobby.deleteAllRows").executeUpdate();
            em.persist(h1);
            em.persist(h2);
            em.persist(p1);

            pdto = new PersonDTO(p1);
//            em.persist(p2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/xxx").then().statusCode(200);
    }

    @Test
    void getPersonByPhone() {
        PersonDTO personDTO = given().contentType("application/json").when()
                .get("/person/phone/" + p1.getPhones().stream().findFirst().get().getPhoneNumber()).as(PersonDTO.class);

        assertThat(personDTO, equalTo(p1));
    }

    @Test
    void getAllPersonsByHobby(){
        List<PersonDTO> personDTOList =
                given().contentType("application/json").when()
                        .get("/person/hobby/"+p1.getHobbies().stream().findFirst().get().getName())
                        .then().extract().body().jsonPath()
                        .getList("", PersonDTO.class);

        assertThat(personDTOList, containsInAnyOrder(p1));
    }

    @Test
    void getAmountWithHobby(){
        List<PersonDTO> personDTOList =
                given().contentType("application/json").when()
                        .get("/person/hobby/"+p1.getHobbies().size())
                        .then().extract().body().jsonPath()
                        .getList("", PersonDTO.class);

        assertThat(personDTOList, hasSize(1));
    }

//    @Test
//    void getPersonsByCityZip(){
//        List<PersonDTO> personDTOList =
//                given().contentType("application/json").when()
//                        .get("/person/cityzip/"+p1.getAddress().getZip())
//                        .then().extract().body().jsonPath()
//                        .getList("", PersonDTO.class);
//
//        assertThat(personDTOList, containsInAnyOrder(p1));
//    }

    @Test
    void getPersonsByCityZip(){
        List<PersonDTO> personDTOList =
                given().contentType("application/json").when()
                        .get("/person/cityzip/" + pdto.getAddress().getZip())
                        .then().extract().body().jsonPath()
                        .getList("", PersonDTO.class);

        assertThat(personDTOList, containsInAnyOrder(pdto));
    }

//    @Test
//    public void getPersonsByCityZipTest() {
//        given()
//                .contentType(ContentType.JSON)
//                .get("/person/cityzip/{zip}", p1.getAddress().getZip())
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("id", equalTo(p1.getId()))
//                .body("email", equalTo(p1.getEmail()))
//                .body("forName", equalTo(p1.getFirstName()))
//                .body("address", equalTo(p1.getAddress()));
//
//    }


    @Test
    void createPerson(){
        Person newPerson = new Person("testtesttest@test.dk", "New", "Test");
        PersonDTO pdto = new PersonDTO(newPerson);
        String requestBody = GSON.toJson(pdto);

        given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/person")
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue())
                .body("email", equalTo("testtesttest@test.dk"))
                .body("firstName", equalTo("New"))
                .body("lastName", equalTo("Test"));
    }




    @Test
    void deletePerson() {
        int myId = 1; //change this to p1.getId()

        given()
                .log().everything()
                .contentType(ContentType.JSON)
                .pathParam("id", myId)
                .expect().statusCode(200)
                .when()
                .delete("person" + "/{id}");

    }

}