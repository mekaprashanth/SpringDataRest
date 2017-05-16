/**
 * 
 */
package com.prash.sdr.model.custom.validator;

import static com.prash.sdr.model.custom.validator.ValidationResult.invalid;
import static com.prash.sdr.model.custom.validator.ValidationResult.valid;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.util.StringUtils;

import com.prash.sdr.model.Location;

/**
 * @author f2u85i8
 *
 */
public interface LocationValidation extends Function<Location, ValidationResult>	{

	static LocationValidation countryIsNotEmpty() {
		return holds(location -> !StringUtils.isEmpty(location.getCountry()), "Country should not be empty");
	}

	static LocationValidation stateIsNotEmpty() {
		return holds(location -> !StringUtils.isEmpty(location.getState()), "State should not be empty");
	}

	static LocationValidation checkCountryIfStateisTamilNadu() {
		return holds(
				location -> {
					return "Tamilnadu".equalsIgnoreCase(location.getState()) ? "India".equalsIgnoreCase(location.getCountry()) : true;
				},
				"Country should be India if State is Tamilnadu");
	}

	static LocationValidation holds(Predicate<Location> p, String message) {
		return location -> p.test(location) ? valid() : invalid(message);
	}

	static LocationValidation all(List<String> results, LocationValidation... validations) {
		return location -> {
			Arrays.stream(validations).forEach(locationValidation -> locationValidation.apply(location).getReason().ifPresent(e -> results.add(e)));
			return null;
		};
	}

	default LocationValidation and(LocationValidation other) {
		return location -> {
			final ValidationResult result = this.apply(location);
			return result.isValid() ? other.apply(location) : result;
		};
	}

}
