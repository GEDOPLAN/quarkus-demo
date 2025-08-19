package de.gedoplan.showcase.web;

import de.gedoplan.showcase.model.Planet;
import de.gedoplan.showcase.service.PlanetService;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateEnum;
import io.quarkus.qute.TemplateExtension;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Web resource for managing planets.
 */
@Path("/planets/ui")
public class PlanetResource {

    @Inject
    PlanetService planetService;

    // ##
    // Type-safe with native method definition
    // ##

    @CheckedTemplate
    static class Templates {
      public static native TemplateInstance planets(List<Planet> planets);
      public static native TemplateInstance planet(Planet planet);
      public static native TemplateInstance error(Integer code, String title);
    }

    /**
     * Render the planets list page using Qute template.
     *
     * @param search Optional search term to filter planets
     * @return TemplateInstance for the planets list page
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getPlanetsPage(@QueryParam("search") String search) {
      List<Planet> planetList = search != null && !search.trim().isEmpty()
          ? planetService.searchPlanets(search)
          : planetService.getAllPlanets();
      return Templates.planets(planetList);
    }

    /**
     * Render the planet details page using Qute template.
     *
     * @param name Name of the planet
     * @return TemplateInstance for the planet details page
     */
    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getPlanetPage(@PathParam("name") String name) {
      Optional<Planet> planetOpt = planetService.getPlanetByName(name);
      if (planetOpt.isPresent()) {
        return Templates.planet(planetOpt.get());
      } else {
        return Templates.error(404, "Planet '" + name + "' nicht gefunden");
      }
    }

    // ##
    // Type-safe with record definition
    // ##

    // Access enum constants in template as Action:NEW
    @TemplateEnum
    public enum Action {NEW, EDIT}

    public record PlanetForm(Planet planet, Action action) implements TemplateInstance {}

    /**
     * Render the planet form page for adding a new planet.
     *
     * @return TemplateInstance for the planet form page
     */
    @GET
    @Path("/new")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getNewPlanetForm() {
        return new PlanetForm(new Planet(), Action.NEW);
    }

    /**
     * Render the planet form page for editing an existing planet.
     *
     * @param name Name of the planet to edit
     * @return TemplateInstance for the planet form page
     */
    @GET
    @Path("/{name}/edit")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getEditPlanetForm(@PathParam("name") String name) {
        Optional<Planet> planetOpt = planetService.getPlanetByName(name);
        if (planetOpt.isPresent()) {
          return new PlanetForm(planetOpt.get(), Action.EDIT);
        } else {
          return Templates.error(404, "Planet '" + name + "' nicht gefunden");
        }
    }

    @TemplateExtension
    static double distanceFromSunAU(Planet planet) {
      return planet.distanceFromSunMillionKm() / 149.597_870_700;
    }

    // ##
    // Actions with redirects
    // ##

    /**
     * Handle form submission for creating a new planet.
     *
     * @param formData Form data containing planet information
     * @return Response redirecting to the appropriate page
     */
    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createPlanetFromForm(MultivaluedMap<String, String> formData) {
        Planet planet = new Planet(formData.getFirst("name"),
            Double.parseDouble(formData.getFirst("diameterKm")),
            Double.parseDouble(formData.getFirst("distanceFromSunMillionKm")),
            formData.getFirst("hasRings") != null,
            Integer.parseInt(formData.getFirst("numberOfMoons")),
            formData.getFirst("description"));
        boolean added = planetService.addPlanet(planet);
        if (added) {
            return Response.seeOther(URI.create("/planets/ui/" + planet.name())).build();
        } else {
            // If planet already exists, return to the form with an error
            return Response.seeOther(URI.create("/planets/ui/new?error=exists")).build();
        }
    }

    /**
     * Handle form submission for updating an existing planet.
     *
     * @param name Name of the planet to update
     * @param formData Form data containing updated planet information
     * @return Response redirecting to the appropriate page
     */
    @POST
    @Path("/{name}/edit")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updatePlanetFromForm(@PathParam("name") String name, MultivaluedMap<String, String> formData) {
        Optional<Planet> existingPlanet = planetService.getPlanetByName(name);
        if (!existingPlanet.isPresent()) {
            // Redirect to planet page (which will show the approriate error)
            Response.seeOther(URI.create("/planets/ui/" + name)).build();
        }

        Planet planet = new Planet(formData.getFirst("name"),
            Double.parseDouble(formData.getFirst("diameterKm")),
            Double.parseDouble(formData.getFirst("distanceFromSunMillionKm")),
            formData.getFirst("hasRings") != null,
            Integer.parseInt(formData.getFirst("numberOfMoons")),
            formData.getFirst("description"));

        planetService.updatePlanet(name, planet);
        return Response.seeOther(URI.create("/planets/ui/" + name)).build();
    }

    /**
     * Handle form submission for deleting a planet.
     *
     * @param name Name of the planet to delete
     * @return Response redirecting to the planets list
     */
    @POST
    @Path("/{name}/delete")
    public Response deletePlanetFromForm(@PathParam("name") String name) {
        planetService.deletePlanet(name);
        return Response.seeOther(URI.create("/planets/ui")).build();
    }
}