package de.gedoplan.showcase.persistence;

import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.showcase.model.Trainer;

import java.time.LocalDate;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TrainerRepository extends SingleIdEntityRepository<String, Trainer> {
  /**
   * Find available rooms.
   * @param course course id
   * @param begin begin date
   * @param end end data
   * @return available rooms (may be empty)
   */
  public List<Trainer> findAvailable(String course, LocalDate begin, LocalDate end) {

    return this.entityManager
      .createQuery("""
        select t 
        from Trainer t
        join t.courses c
        where c=:course
          and not exists ( select tb
                           from TrainerBooking tb
                           where tb.trainer=t
                             and :end>=tb.begin
                             and :begin<=tb.end
                         )
        """,
      Trainer.class)
      .setParameter("course", course)
      .setParameter("begin", begin)
      .setParameter("end", end)
      .getResultList();
  }
}
