package de.gedoplan.showcase.persistence;

import java.time.LocalDate;
import java.util.List;

import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.showcase.model.Room;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoomRepository extends SingleIdEntityRepository<String, Room> {
  /**
   * Find available rooms.
   * @param location location
   * @param minNoOfSeats minimal number of seats
   * @param begin begin date
   * @param end end data
   * @return available rooms (may be empty)
   */
  public List<Room> findAvailable(String location, int minNoOfSeats, LocalDate begin, LocalDate end) {

    return this.entityManager
      .createQuery("""
        select r 
        from Room r
        where r.location=:location
          and r.noOfSeats>=:minNoOfSeats
          and not exists ( select rb
                           from RoomBooking rb
                           where rb.room=r
                             and :end>=rb.begin
                             and :begin<=rb.end
                         )
        """,
      Room.class)
      .setParameter("location", location)
      .setParameter("minNoOfSeats", minNoOfSeats)
      .setParameter("begin", begin)
      .setParameter("end", end)
      .getResultList();
  }
}
