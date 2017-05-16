/**
 * 
 */
package com.prash.sdr.business.validator;

import org.springframework.util.CollectionUtils;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;
import com.prash.sdr.model.User;

/**
 * @author f2u85i8
 *
 */
public class UserRoleValidator extends ValidatorHandler<User> {

	@Override
	public boolean validate(ValidatorContext context, User user) {
		boolean retVal = true;
		if(user == null || CollectionUtils.isEmpty(user.getRoles()))	{
			 context.addErrorMsg(String.format("User/Roles Cannot be null"));
			 retVal = false;
		}else if(!user.getRoles().stream().anyMatch(p->p.getAuthority().equalsIgnoreCase("User")))	{
			context.addErrorMsg(String.format("Atleast one Role should be User"));
			retVal = false;
		}
		return retVal;
	}

}
