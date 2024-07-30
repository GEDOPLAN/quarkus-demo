package de.gedoplan.showcase.api.adapter;

import de.gedoplan.showcase.entity.Publisher;
import jakarta.json.bind.adapter.JsonbAdapter;

public class PublisherIdOnlyJsonbAdapter implements JsonbAdapter<Publisher, Integer> {

  @Override
  public Publisher adaptFromJson(Integer id) {
    throw new UnsupportedOperationException("Unimplemented method 'adaptFromJson'");
  }

  @Override
  public Integer adaptToJson(Publisher publisher) {
    return publisher == null ? null : publisher.getId();
  }


}
