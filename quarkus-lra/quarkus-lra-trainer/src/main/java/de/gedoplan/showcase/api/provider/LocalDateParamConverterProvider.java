package de.gedoplan.showcase.api.provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import jakarta.ws.rs.ext.Provider;

@Provider
public class LocalDateParamConverterProvider implements ParamConverterProvider {

	@SuppressWarnings("unchecked")
	public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
		if (rawType == LocalDate.class) {
			return (ParamConverter<T>) new LocalDateParamConverter();
		}

		return null;
	}
}

class LocalDateParamConverter implements ParamConverter<LocalDate> {

	@Override
	public LocalDate fromString(String value) {
		return value != null ? LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE) : null;
	}

	@Override
	public String toString(LocalDate value) {
		return value != null ? value.format(DateTimeFormatter.ISO_LOCAL_DATE) : null;
	}
}
