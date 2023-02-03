package de.gedoplan.showcase.extension.smartrepo.deployment;

import de.gedoplan.showcase.extension.smartrepo.SmartRepo;
import org.jboss.jandex.DotName;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Stream;

public final class DotNames {

  public static final DotName SMART_REPO = DotName.createSimple(SmartRepo.class.getName());

  public static final Set<DotName> SUPPORTED_REPOSITORIES = new HashSet<>(Arrays.asList(SMART_REPO));

  public static final DotName JPA_ID = DotName.createSimple(Id.class.getName());
  public static final DotName JPA_EMBEDDED_ID = DotName.createSimple(EmbeddedId.class.getName());
  public static final DotName VERSION = DotName.createSimple(Version.class.getName());
  public static final DotName JPA_INHERITANCE = DotName.createSimple(Inheritance.class.getName());
  public static final DotName JPA_MAPPED_SUPERCLASS = DotName.createSimple(MappedSuperclass.class.getName());
  public static final DotName JPA_ENTITY = DotName.createSimple(Entity.class.getName());
  ;
  public static final DotName JPA_NAMED_QUERY = DotName.createSimple(NamedQuery.class.getName());
  public static final DotName JPA_NAMED_QUERIES = DotName.createSimple(NamedQueries.class.getName());
  public static final DotName JPA_TRANSIENT = DotName.createSimple(Transient.class.getName());
  public static final DotName VOID = DotName.createSimple(void.class.getName());
  public static final DotName LONG = DotName.createSimple(Long.class.getName());
  public static final DotName PRIMITIVE_LONG = DotName.createSimple(long.class.getName());
  public static final DotName INTEGER = DotName.createSimple(Integer.class.getName());
  public static final DotName PRIMITIVE_INTEGER = DotName.createSimple(int.class.getName());
  public static final DotName SHORT = DotName.createSimple(Short.class.getName());
  public static final DotName PRIMITIVE_SHORT = DotName.createSimple(short.class.getName());
  public static final DotName CHARACTER = DotName.createSimple(Character.class.getName());
  public static final DotName PRIMITIVE_CHAR = DotName.createSimple(char.class.getName());
  public static final DotName BYTE = DotName.createSimple(Byte.class.getName());
  public static final DotName PRIMITIVE_BYTE = DotName.createSimple(byte.class.getName());
  public static final DotName DOUBLE = DotName.createSimple(Double.class.getName());
  public static final DotName PRIMITIVE_DOUBLE = DotName.createSimple(double.class.getName());
  public static final DotName FLOAT = DotName.createSimple(Float.class.getName());
  public static final DotName PRIMITIVE_FLOAT = DotName.createSimple(float.class.getName());
  public static final DotName BOOLEAN = DotName.createSimple(Boolean.class.getName());
  public static final DotName PRIMITIVE_BOOLEAN = DotName.createSimple(boolean.class.getName());
  public static final DotName BIG_INTEGER = DotName.createSimple(BigInteger.class.getName());
  public static final DotName BIG_DECIMAL = DotName.createSimple(BigDecimal.class.getName());
  public static final DotName STRING = DotName.createSimple(String.class.getName());
  public static final DotName ITERATOR = DotName.createSimple(Iterator.class.getName());
  public static final DotName COLLECTION = DotName.createSimple(Collection.class.getName());
  public static final DotName LIST = DotName.createSimple(List.class.getName());
  public static final DotName SET = DotName.createSimple(Set.class.getName());
  public static final DotName STREAM = DotName.createSimple(Stream.class.getName());
  public static final DotName OPTIONAL = DotName.createSimple(Optional.class.getName());
  public static final DotName OBJECT = DotName.createSimple(Object.class.getName());
  public static final DotName LOCALE = DotName.createSimple(Locale.class.getName());
  public static final DotName TIMEZONE = DotName.createSimple(TimeZone.class.getName());
  public static final DotName URL = DotName.createSimple(java.net.URL.class.getName());
  public static final DotName CLASS = DotName.createSimple(Class.class.getName());
  public static final DotName UUID = DotName.createSimple(java.util.UUID.class.getName());
  public static final DotName BLOB = DotName.createSimple(java.sql.Blob.class.getName());
  public static final DotName CLOB = DotName.createSimple(java.sql.Clob.class.getName());
  public static final DotName NCLOB = DotName.createSimple(java.sql.NClob.class.getName());

  // temporal types
  public static final DotName UTIL_DATE = DotName.createSimple(java.util.Date.class.getName());
  public static final DotName CALENDAR = DotName.createSimple(Calendar.class.getName());
  // java.sql
  public static final DotName SQL_DATE = DotName.createSimple(java.sql.Date.class.getName());
  public static final DotName SQL_TIME = DotName.createSimple(java.sql.Time.class.getName());
  public static final DotName SQL_TIMESTAMP = DotName.createSimple(java.sql.Timestamp.class.getName());
  // java.time
  public static final DotName LOCAL_DATE = DotName.createSimple(LocalDate.class.getName());
  public static final DotName LOCAL_TIME = DotName.createSimple(LocalTime.class.getName());
  public static final DotName LOCAL_DATETIME = DotName.createSimple(LocalDateTime.class.getName());
  public static final DotName OFFSET_TIME = DotName.createSimple(OffsetTime.class.getName());
  public static final DotName OFFSET_DATETIME = DotName.createSimple(OffsetDateTime.class.getName());
  public static final DotName DURATION = DotName.createSimple(Duration.class.getName());
  public static final DotName INSTANT = DotName.createSimple(Instant.class.getName());
  public static final DotName ZONED_DATETIME = DotName.createSimple(ZonedDateTime.class.getName());

  // https://docs.jboss.org/hibernate/stable/orm/userguide/html_single/Hibernate_User_Guide.html#basic
  // Should be in sync with org.hibernate.type.BasicTypeRegistry
  public static final Set<DotName> HIBERNATE_PROVIDED_BASIC_TYPES = new HashSet<>(Arrays.asList(
    STRING, CLASS,
    BOOLEAN, PRIMITIVE_BOOLEAN,
    INTEGER, PRIMITIVE_INTEGER,
    LONG, PRIMITIVE_LONG,
    SHORT, PRIMITIVE_SHORT,
    BYTE, PRIMITIVE_BYTE,
    CHARACTER, PRIMITIVE_CHAR,
    DOUBLE, PRIMITIVE_DOUBLE,
    FLOAT, PRIMITIVE_FLOAT,
    BIG_INTEGER, BIG_DECIMAL,
    UTIL_DATE, CALENDAR,
    SQL_DATE, SQL_TIME, SQL_TIMESTAMP,
    LOCAL_DATE, LOCAL_TIME, LOCAL_DATETIME,
    OFFSET_TIME, OFFSET_DATETIME,
    DURATION, INSTANT,
    ZONED_DATETIME, TIMEZONE,
    LOCALE, URL, UUID,
    BLOB, CLOB, NCLOB));

  private DotNames() {
  }
}
