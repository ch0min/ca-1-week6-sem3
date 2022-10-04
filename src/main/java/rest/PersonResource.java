package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datafacades.IPersonFacade;
import dtofacades.PersonDTOFacade;
import dtos.PersonDTO;
import errorhandling.EntityNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("person")
public class PersonResource {

    private static final IPersonFacade<PersonDTO> FACADE =  PersonDTOFacade.getFacade();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPersons() {
        return Response.ok().entity(GSON.toJson(FACADE.getAllPersons())).build();
    }

    @GET
    @Path("/phone/{phone}" )
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonByPhone(@PathParam("phone") int phone) throws EntityNotFoundException {
        List<PersonDTO> personDTO = FACADE.getPersonByPhone(phone);
        return Response.ok().entity(personDTO).build();
    }

    @GET
    @Path("/hobby/{hobbyname}" )
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonByHobby(@PathParam("hobbyname") String hobby) throws EntityNotFoundException {
        List<PersonDTO> personDTO = FACADE.getAllPersonsByHobby(hobby);
        return Response.ok().entity(personDTO).build();
    }
    @GET
    @Path("/cityzip/{zip}" )
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonsByCityZip(@PathParam("zip") int zip) throws EntityNotFoundException {
        List<PersonDTO> personDTO = FACADE.getPersonsByCityZip(zip);
        return Response.ok().entity(personDTO).build();
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Response deletePerson(@PathParam("id") int id) throws EntityNotFoundException {
        System.out.println(id);
        PersonDTO deleted = FACADE.deletePerson(id);
        return Response.ok().entity(GSON.toJson(deleted)).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createPerson(String content) {
        PersonDTO pdto = GSON.fromJson(content, PersonDTO.class);
        PersonDTO newPdto = FACADE.createPerson(pdto);
        return Response.ok().entity(GSON.toJson(newPdto)).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Response updatePerson(@PathParam("id") int id, String personInput) throws EntityNotFoundException {
        PersonDTO personDTO = GSON.fromJson(personInput, PersonDTO.class);
        personDTO.setId(id);
        PersonDTO returned = FACADE.updatePerson(personDTO);
        return Response.ok().entity(GSON.toJson(returned)).build();
    }

}
