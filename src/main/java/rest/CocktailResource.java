package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtoFacades.CocktailDTOFacade;
import dtoFacades.ProfileDTOFacade;
import dtos.CocktailDTO;
import dtos.ProfileDTO;
import errorhandling.EntityNotFoundException;
import facades.IFacade;
import utils.EMF_Creator;
import facades.FacadeExample;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

//Todo Remove or change relevant parts before ACTUAL use
@Path("cocktail")
public class CocktailResource {

    private static final IFacade<CocktailDTO> FACADE =  CocktailDTOFacade.getFacade();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Path("demo")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }


    @Path("data")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getExampleData() throws IOException {
        return CocktailDTOFacade.getExampleData();
    }


    //TODO: Change these

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAll() {
        return Response.ok().entity(GSON.toJson(FACADE.getAll())).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getById(@PathParam("id") int id) throws EntityNotFoundException {
        CocktailDTO cdto = FACADE.getById(id);
        return Response.ok().entity(GSON.toJson(cdto)).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(String content) {
        CocktailDTO cdto = GSON.fromJson(content, CocktailDTO.class);
        CocktailDTO newCdto = FACADE.create(cdto);
        return Response.ok().entity(GSON.toJson(newCdto)).build();
    }

    @PUT
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") int id, String content) throws EntityNotFoundException {
        CocktailDTO cdto = GSON.fromJson(content, CocktailDTO.class);
        cdto.setId(id);
        CocktailDTO updated = FACADE.update(cdto);
        return Response.ok().entity(GSON.toJson(updated)).build();
    }

    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") int id) throws EntityNotFoundException {
        CocktailDTO deleted = FACADE.delete(id);
        return Response.ok().entity(GSON.toJson(deleted)).build();
    }

}
