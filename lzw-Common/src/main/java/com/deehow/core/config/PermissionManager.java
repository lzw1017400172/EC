package com.deehow.core.config;

import java.util.Collection;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PermissionManager extends ModularRealmAuthorizer{
	protected final Logger log = LoggerFactory.getLogger(PermissionManager.class);

	@Override
	public void checkPermission(PrincipalCollection principals, String permission) throws AuthorizationException {
		// TODO Auto-generated method stub
		try {
			super.checkPermission(principals, permission);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("没有权限： [" + permission + "]");
		}
	}

	@Override
	public void checkPermission(PrincipalCollection principals, Permission permission) throws AuthorizationException {
		// TODO Auto-generated method stub
		try {
			super.checkPermission(principals, permission);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("没有权限： [" + permission + "]");
		}
	}

	@Override
	public void checkPermissions(PrincipalCollection principals, String... permissions) throws AuthorizationException {
		// TODO Auto-generated method stub
		try {
			super.checkPermissions(principals, permissions);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("没有权限： [" + permissions + "]");
		}
	}

	@Override
	public void checkPermissions(PrincipalCollection principals, Collection<Permission> permissions)
			throws AuthorizationException {
		// TODO Auto-generated method stub
		try {
			super.checkPermissions(principals, permissions);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("没有权限： [" + permissions + "]");
		}
	}

}
