package de.gedoplan.showcase.kafka;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * Kafka deserialiser for serialized objects.
 *
 * TODO: Is there a standard deserializer for this somewhere? If not, WTF!
 *
 * @author dw
 */
public class SerializableDeserializer implements Deserializer<Serializable> {

  @Override
  public void configure(Map<String, ?> props, boolean isKey) {
  }

  @Override
  public Serializable deserialize(String topic, byte[] bytes) {
    if (bytes == null) {
      return null;
    }

    try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); ObjectInputStream ois = new ObjectInputStream(bis)) {
      return (Serializable) ois.readObject();
    } catch (Exception e) {
      throw new SerializationException(e);
    }
  }

  @Override
  public void close() {
  }
}