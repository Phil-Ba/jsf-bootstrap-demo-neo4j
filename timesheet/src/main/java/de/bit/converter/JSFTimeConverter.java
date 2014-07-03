package de.bit.converter;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.joda.time.LocalTime;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

@Component("timeConverter")
public class JSFTimeConverter implements Converter {

	@Resource(name = "conversionService")
	private ConversionService converter;

	@Override
	public Object getAsObject(final FacesContext context, final UIComponent component, final String value) throws ConverterException {
		if (Strings.isNullOrEmpty(value)) {
			return null;
		}
		try {
			return converter.convert(value, LocalTime.class);
		} catch (Exception e) {
			throw new ConverterException(new FacesMessage("Der von Ihnen eingegebene Wert '" + value + "' ist keine gültige Zeit!"), e);
		}
	}

	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) throws ConverterException {
		if (value == null) {
			return "";
		}
		try {
			return converter.convert(value, String.class);
		} catch (Exception e) {
			throw new ConverterException(new FacesMessage("Das Objekt '" + value.getClass().getName()
					+ "' konnte nicht in einen String umgewandelt werden!"), e);
		}
	}

}
