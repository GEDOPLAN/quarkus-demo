package de.gedoplan.showcase.persistence;

import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.showcase.model.RoomBooking;
import de.gedoplan.showcase.model.RoomBooking_;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoomBookingRepository extends SingleIdEntityRepository<Integer, RoomBooking> {
  public Optional<RoomBooking> findByLraId(String lraId) {
    return findSingleByProperty(RoomBooking_.lraId, lraId);
  }
}
