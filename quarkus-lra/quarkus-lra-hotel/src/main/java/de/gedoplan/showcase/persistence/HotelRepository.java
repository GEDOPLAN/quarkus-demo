package de.gedoplan.showcase.persistence;

import java.util.List;

import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.showcase.model.Hotel;
import de.gedoplan.showcase.model.Hotel_;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HotelRepository extends SingleIdEntityRepository<Character, Hotel> {
  public List<Hotel> findByLraId(String lraId) {
    return findMultiByProperty(Hotel_.lraId, lraId);
  }
}
