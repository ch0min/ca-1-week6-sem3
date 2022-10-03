//package rest;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import dtos.PersonDTO;
//import errorhandling.EntityNotFoundException;
//import datafacades.PersonFacade;
//import utils.EMF_Creator;
//
//import javax.persistence.EntityManagerFactory;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.util.List;
//
//@Path("person")
//
//public class PersonResource {
//
//    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
//    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
//    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
//
//    @GET
//    @Path("/phone/{phone}" )
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getPersonByPhone(@PathParam("phone") int phone) {
//        PersonDTO personDTO = FACADE.getPersonByPhone(phone);
//        return Response.ok().entity(personDTO).build();
//    }
//
//    @GET
//    @Path("/hobby/{hobbyname}" )
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getPersonByHobby(@PathParam("hobbyname") String hobby) throws EntityNotFoundException {
//        List<PersonDTO> personDTO = FACADE.getAllPersonsByHobby(hobby);
//        return Response.ok().entity(personDTO).build();
//    }
//    @GET
//    @Path("/cityzip/{zip}" )
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getPersonByCityZip(@PathParam("zip") int zip) throws EntityNotFoundException {
//        List<PersonDTO> personDTO = FACADE.getPeopleInCity(zip);
//        return Response.ok().entity(personDTO).build();
//    }
//
//}
