package de.gedoplan.showcase.kafka;

import java.util.Map;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * Kafka deserialiser for objects serialized as JSON.
 *
 * TODO: Is there a standard deserializer for this somewhere? If not, WTF!
 *
 * The deserializer expects the Kafka configuration property key.class or value.class to be set to the fully qualified
 * classname of the serialized key or value resp.
 *
 * @author dw
 *
 * @param <T> Type of serialized object
 */
public class JsonbDeserializer<T> implements Deserializer<T> {

  private Jsonb jsonb;
  private Class<T> clazz;

  @SuppressWarnings("unchecked")
  @Override
  public void configure(Map<String, ?> props, boolean isKey) {
    try {
      String propertyName = (isKey ? "key" : "value") + ".class";
      String clazzName = props.get(propertyName).toString();
      if (clazzName == null) {
        throw new SerializationException("Property " + propertyName + " must be set to fully qualified class name to be deserialized");
      }
      this.clazz = (Class<T>) Class.forName(clazzName);
    } catch (ClassNotFoundException e) {
      throw new SerializationException(e);
    }
    this.jsonb = JsonbBuilder.create();
  }

  @Override
  public T deserialize(String topic, byte[] bytes) {
    if (bytes == null) {
      return null;
    }

    try {
      return this.jsonb.fromJson(new String(bytes), this.clazz);
    } catch (Exception e) {
      throw new SerializationException(e);
    }
  }

  @Override
  public void close() {
  }
}