package rest;

import dtos.PersonDTO;
import entities.Person;
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
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static PersonDTO p1, p2;

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
//        p1 = new PersonDTO
//        p2 = null;
        try {
            em.getTransaction().begin();
//            em.createNamedQuery("RenameMe.deleteAllRows").executeUpdate();
//            em.persist(r1);
//            em.persist(r2);
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
                .get("/person/phone/" + p1.getPhones().toArray()[0]).as(PersonDTO.class);

        assertThat(personDTO, equalTo(p1));
    }

    @Test
    void getAllPersonsByHobby(){
        List<PersonDTO> personDTOList =
                given().contentType("application/json").when()
                .get("/person/hobby/"+p1.getHobbies().toArray()[0])//find hobbyid ?? tostring
                .then().extract().body().jsonPath()
                .getList("", PersonDTO.class);

        assertThat(personDTOList, containsInAnyOrder(p1));
    }

    @Test
    void getAmountWithHobby(){
        List<PersonDTO> personDTOList =
                given().contentType("application/json").when()
                        .get("/person/hobby/"+p1.getHobbies().toArray()[0])//find hobbyid ?? tostring
                        .then().extract().body().jsonPath()
                        .getList("", PersonDTO.class);
//        assertThat(personDTOList.size(), 1);
    }

    @Test
    void getPeopleInCity(){
        List<PersonDTO> personDTOList =
                given().contentType("application/json").when()
                        .get("/person/cityzip/"+p1.getAddress().getZip().getId())
                        .then().extract().body().jsonPath()
                        .getList("", PersonDTO.class);

        assertThat(personDTOList, containsInAnyOrder(p1));
    }


    @Test
    void createPerson(){

    }
    @Test
    void deletePerson() {

    }

}