package de.gedoplan.showcase.persistence;

import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.showcase.model.TrainerBooking;
import de.gedoplan.showcase.model.TrainerBooking_;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TrainerBookingRepository extends SingleIdEntityRepository<Integer, TrainerBooking> {
  public Optional<TrainerBooking> findByLraId(String lraId) {
    return findSingleByProperty(TrainerBooking_.lraId, lraId);
  }
}
