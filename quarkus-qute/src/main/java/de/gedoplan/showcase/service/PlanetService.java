package de.gedoplan.showcase.service;

import de.gedoplan.showcase.model.Planet;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class to manage the in-memory list of planets.
 */
@ApplicationScoped
public class PlanetService {

    private List<Planet> planets = new ArrayList<>();

    /**
     * Initialize the list with default planets from our solar system.
     */
    @PostConstruct
    public void init() {
        addPlanet(new Planet("Merkur", 4879, 57.9, false, 0,
            "Merkur ist der kleinste und sonnennächste Planet im Sonnensystem."));
        addPlanet(new Planet("Venus", 12104, 108.2, false, 0,
            "Venus ist der zweite Planet von der Sonne und der nächste planetare Nachbar der Erde."));
        addPlanet(new Planet("Erde", 12756, 149.6, false, 1,
            "Erde ist unser Heimatplanet und der einzige Ort, den wir bisher kennen, der bewohnt wird."));
        addPlanet(new Planet("Mars", 6792, 228, false, 2,
            "Mars ist der vierte Planet von der Sonne und bekannt als der rote Planet."));
        addPlanet(new Planet("Jupiter", 142984, 778.5, true, 97,
            "Jupiter ist der fünfte Planet von der Sonne und der größte im Sonnensystem."));
        addPlanet(new Planet("Saturn", 120536, 1432, true, 274,
            "Saturn ist der sechste Planet von der Sonne und der zweitgrößte im Sonnensystem, nach Jupiter."));
        addPlanet(new Planet("Uranus", 51118, 2867, true, 28,
            "Uranus ist der siebente Planet von der Sonne und hat den drittgrößten Durchmesser im Sonnensystem."));
        addPlanet(new Planet("Neptun", 49528, 4515, true, 16,
            "Neptun ist der achte und am weitesten entfernte bekannte Planet im Sonnensystem."));
    }

    /**
     * Get all planets.
     *
     * @return List of all planets
     */
    public List<Planet> getAllPlanets() {
        return planets;
    }

    /**
     * Get a planet by name.
     *
     * @param name Name of the planet
     * @return Optional containing the planet if found, empty otherwise
     */
    public Optional<Planet> getPlanetByName(String name) {
        return planets.stream()
                .filter(p -> p.name().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Add a new planet.
     *
     * @param planet Planet to add
     * @return true if added successfully, false if a planet with the same name already exists
     */
    public boolean addPlanet(Planet planet) {
        // Check if a planet with the same name already exists
        if (planets.stream().anyMatch(p -> p.name().equalsIgnoreCase(planet.name()))) {
            return false;
        }

        planets.add(planet);
        return true;
    }

    /**
     * Update an existing planet.
     *
     * @param name Name of the planet to update
     * @param updatedPlanet Updated planet data
     * @return true if updated successfully, false if the planet was not found
     */
    public boolean updatePlanet(String name, Planet updatedPlanet) {
        for (int i = 0; i < planets.size(); i++) {
            if (planets.get(i).name().equalsIgnoreCase(name)) {
                // Update the planet but keep the original name
                planets.set(i, updatedPlanet.withName(name));
                return true;
            }
        }
        return false;
    }

    /**
     * Delete a planet by name.
     *
     * @param name Name of the planet to delete
     * @return true if deleted successfully, false if the planet was not found
     */
    public boolean deletePlanet(String name) {
        return planets.removeIf(p -> p.name().equalsIgnoreCase(name));
    }

    /**
     * Search for planets by name (case-insensitive, partial match).
     *
     * @param searchTerm Search term
     * @return List of planets matching the search term
     */
    public List<Planet> searchPlanets(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllPlanets();
        }

        String term = searchTerm.toLowerCase();
        return planets.stream()
                .filter(p -> p.name().toLowerCase().contains(term))
                .collect(Collectors.toList());
    }
}