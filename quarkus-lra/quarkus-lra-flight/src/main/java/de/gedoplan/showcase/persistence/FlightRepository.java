package de.gedoplan.showcase.persistence;

import java.util.List;

import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.showcase.model.Flight;
import de.gedoplan.showcase.model.Flight_;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FlightRepository extends SingleIdEntityRepository<Character, Flight> {
  public List<Flight> findByLraId(String lraId) {
    return findMultiByProperty(Flight_.lraId, lraId);
  }
}
