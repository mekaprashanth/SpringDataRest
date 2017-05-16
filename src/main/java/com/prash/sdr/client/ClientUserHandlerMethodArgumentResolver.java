package com.prash.sdr.client;

import java.security.Principal;
import java.util.Optional;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author f2u85i8
 *
 */
@Component
public class ClientUserHandlerMethodArgumentResolver implements
HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterAnnotation(IClientUser.class) != null
				&& methodParameter.getParameterType().equals(Optional.class);
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		ClientUser user = null;
		if (this.supportsParameter(methodParameter)) {
			Principal principal = (Principal) webRequest.getUserPrincipal();
			Object object = ((Authentication) principal).getDetails();
			if(ClientAuthenticationDetails.class.isAssignableFrom(object.getClass()))	{
				ClientAuthenticationDetails clientDetails = (ClientAuthenticationDetails) ((Authentication) principal).getDetails();
				user = new ClientUser();
				user.setRemoteIp(clientDetails.getRemoteAddress());
				user.setUserName(clientDetails.getUserName());
			}
		}
		return Optional.ofNullable(user);
	}

}
