package de.bit.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.joda.time.base.BaseLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.bit.common.Constants;

/**
 * Validates two dates. The date enter into the input field with this validator
 * must be before the other date. Both dates must be of the {@link BaseLocal}
 * type. The other date must be passed as attribute with the name 'end' to this
 * validator.
 * 
 * @author pbayer
 */
@FacesValidator("beforeValidator")
public class BeforeValidator implements Validator {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeforeValidator.class);

	@Override
	public void validate(final FacesContext context, final UIComponent component, final Object value) throws ValidatorException {
		Object otherValue = component.getAttributes().get("otherValue");
		if (otherValue == null || otherValue instanceof UIInput == false) {
			LOGGER.error("Attribute 'otherValue' missing or invalid for beforeValidator!");
			return;
		}
		Object other = ((UIInput) otherValue).getValue();
		if (other instanceof BaseLocal && value instanceof BaseLocal) {
			if (!((BaseLocal) value).isBefore((BaseLocal) other)) {
				throw new ValidatorException(new FacesMessage("Das eingegebene Datum muss vor dem Wert " + Constants.TIME_FORMATTER.print((BaseLocal)other) + " liegen!"));
			}
		} else {
			LOGGER.error("Not both values are Partial in beforeValidator!");
		}
	}
}
