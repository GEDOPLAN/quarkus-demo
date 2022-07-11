package de.gedoplan.showcase.extension.smartrepo;

import java.util.List;
import java.util.Optional;

public interface SmartRepo<E,I> {

  void persist(E item);
  List<E> findAll();

  Optional<E> findById(I id);
}
