package de.gedoplan.showcase.kafka;

import java.util.Map;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

/**
 * Kafka serialiser for objects serialized as JSON.
 *
 * TODO: Is there a standard serializer for this somewhere? If not, WTF!
 *
 * @author dw
 *
 * @param <T> Type of serialized object
 */
public class JsonbSerializer<T> implements Serializer<T> {
  private Jsonb jsonb;

  @Override
  public void configure(Map<String, ?> props, boolean isKey) {
    this.jsonb = JsonbBuilder.create();
  }

  @Override
  public byte[] serialize(String topic, T data) {
    if (data == null) {
      return null;
    }

    try {
      return this.jsonb.toJson(data).getBytes();
    } catch (Exception e) {
      throw new SerializationException(e);
    }
  }

  @Override
  public void close() {
  }

}