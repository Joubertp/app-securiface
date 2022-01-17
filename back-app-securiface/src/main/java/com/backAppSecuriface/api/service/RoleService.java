package com.backAppSecuriface.api.service;

import java.util.Collection;
import java.util.stream.Stream;

import com.backAppSecuriface.api.model.Role;

public interface RoleService {
	
	Role findByRoleName(String roleName);
	
	Collection<Role> getAllRoles();
	
	Stream<Role> getAllRolesStream();
}
