package nl.thehpi.resource;

import nl.thehpi.entity.TestEntity;
import nl.thehpi.repository.TestRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Stateless
@Path("test")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class TestResource {

  @Inject TestRepository documentRepository;

  @Inject
  @ConfigProperty(name = "DB_DRIVER")
  String dbDriver;

  @Inject
  @ConfigProperty(name = "DB_SERVER")
  String dbServer;

  @Inject
  @ConfigProperty(name = "DB_USER")
  String dbUser;

  @Inject
  @ConfigProperty(name = "DB_URL")
  String jdbcUrl;

  @GET
  public String get() {

    return "Driver: " + dbDriver + "\n" +
            "Server: " + dbServer + "\n" +
            "JDBC Url: " + jdbcUrl + "\n" +
            "User: " + dbUser + "\n";
  }

  @GET
  @Path("tests")
  public Collection<TestEntity> getDocuments() {
    return documentRepository.getTests();
  }

  @POST
  @Path("tests/{name}")
  public TestEntity create(@PathParam("name") String name) {
    return documentRepository.createTest(name);
  }

  @Path("tests/{id}")
  @GET
  public Response getTest(@PathParam("id") String id) {
    return documentRepository
        .getTest(id)
        .map(t -> Response.ok(t).build())
        .orElse(
            Response.status(Response.Status.NOT_FOUND)
                .entity(Json.createObjectBuilder().add("message", "Test not found: " + id).build())
                .build());
  }
}
