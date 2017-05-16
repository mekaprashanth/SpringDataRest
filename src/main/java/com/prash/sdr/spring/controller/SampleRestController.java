/**
 * 
 */
package com.prash.sdr.spring.controller;

import static com.prash.sdr.model.custom.validator.LocationValidation.checkCountryIfStateisTamilNadu;
import static com.prash.sdr.model.custom.validator.LocationValidation.countryIsNotEmpty;
import static com.prash.sdr.model.custom.validator.LocationValidation.stateIsNotEmpty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.Result;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.prash.sdr.business.validator.AdminRoleValidator;
import com.prash.sdr.business.validator.UserRoleValidator;
import com.prash.sdr.client.ClientUser;
import com.prash.sdr.client.IClientUser;
import com.prash.sdr.delegate.BusinessDelegate;
import com.prash.sdr.model.Location;
import com.prash.sdr.model.Model;
import com.prash.sdr.model.Role;
import com.prash.sdr.model.User;
import com.prash.sdr.model.custom.validator.LocationValidation;
import com.prash.sdr.repository.LocationJpaRepository;
import com.prash.sdr.service.ModelService;
import com.prash.sdr.service.ModelServiceFactory;
import com.prash.sdr.service.util.ServiceTypeEnum;

/**
 * @author f2u85i8
 *
 */

@RestController
public class SampleRestController {

	@Autowired
	BusinessDelegate delegate;

	@Autowired
	ModelServiceFactory modelServiceFactory;

	@Autowired
	LocationJpaRepository locationRepository;

	@GetMapping("/custom/{allianceCode}/locations")
	public List<Location> findAllLocations(@PathVariable("allianceCode") String allianceCode,
			@AuthenticationPrincipal Authentication authentication, 
			@IClientUser Optional<ClientUser> clientUser) {
		if (clientUser.isPresent()) {
			System.out.println("ClientUser " + clientUser.get());
		} else {
			System.out.println("ClientUser is null ");
		}
		System.out.println("ClientUser " + clientUser);
		System.out.println("authentication " + authentication);
		List<Location> locations = delegate.getAllLocations(allianceCode);
		System.out.println(locations.size());
		return locations;
	}

	@GetMapping("/custom/models")
	public List<Model> findAllModels() {
		ModelService service = modelServiceFactory.getService(ServiceTypeEnum.MODEL);
		return service.getAllModels();
	}

	@PostMapping(value = "/custom/admin/locations", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Location saveLocation(@RequestBody Location location) {
		List<String> results = new ArrayList<>();
		LocationValidation validation = LocationValidation.all(results, stateIsNotEmpty(), countryIsNotEmpty(),
				checkCountryIfStateisTamilNadu());
		validation.apply(location);
		if (results.size() > 0) {
			results.stream().forEach(System.out::println);
			throw new RuntimeException();
		}
		location = delegate.saveLocation(location);
		return location;
	}

	@RequestMapping("/api/date")
	public Date thing() {
		return new Date();
	}

	@RequestMapping("/api/user")
	public User user(@AuthenticationPrincipal Authentication authentication, 
			@IClientUser Optional<ClientUser> clientUser) {
		if (clientUser.isPresent()) {
			System.out.println("ClientUser " + clientUser.get());
		} else {
			System.out.println("ClientUser is null ");
		}
		System.out.println("authentication " + authentication);
		User user = new User();
		user.setUsername("Prashanth");
		user.setPassword("M");
		user.setEnabled(true);
		user.setRoles(Arrays.asList("Admin", "User").stream().map(p -> {
			Role r = new Role();
			r.setAuthority(p);
			return r;
		}).collect(Collectors.toList()));
		return user;
	}

	@RequestMapping("/api/admin/authuser")
	public Authentication calendar(@AuthenticationPrincipal Authentication authentication,
			@IClientUser Optional<ClientUser> clientUser) {

		if (clientUser.isPresent()) {
			System.out.println("ClientUser " + clientUser.get());
		} else {
			System.out.println("ClientUser is null ");
		}

		System.out.println("authentication " + authentication);

		User user = new User();
		user.setUsername(String.valueOf(authentication.getPrincipal()));
		user.setPassword(String.valueOf(authentication.getCredentials()));
		user.setEnabled(true);
		user.setRoles(Arrays.asList("user").stream().map(p -> {
			Role r = new Role();
			r.setAuthority(p);
			return r;
		}).collect(Collectors.toList()));

		Result result = FluentValidator.checkAll().failOver().on(user, new AdminRoleValidator())
				.on(user, new UserRoleValidator()).doValidate().result(ResultCollectors.toSimple());
		 result.getErrors().stream().forEach(System.out::println);

		return authentication;
	}

}
