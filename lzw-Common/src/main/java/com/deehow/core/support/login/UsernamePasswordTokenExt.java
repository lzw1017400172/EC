package com.deehow.core.support.login;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.deehow.core.Constants;

public class UsernamePasswordTokenExt extends UsernamePasswordToken{

	/**
	 * 
	 */
	private static final long serialVersionUID = 760902443194970106L;

	private Long tenantId;
	
    private String username;

    private char[] password;

    private boolean rememberMe = false;

    private String host;
    
    /**
     * 登录类型.用来免密码登录
     */
    private String loginType;

    public UsernamePasswordTokenExt() {
    }

    /**
     * 免密码登录
     * @param username
     * @param password
     * @param tenantId
     */
    public UsernamePasswordTokenExt(final String username, final Long tenantId) {
        this(username, null,tenantId, false, null,Constants.LOGINTYPE_NOPASSWD);
    }
    
    public UsernamePasswordTokenExt(final String username, final char[] password,final Long tenantId) {
        this(username, password,tenantId, false, null,Constants.LOGINTYPE_PASSWORD);
    }

    public UsernamePasswordTokenExt(final String username, final String password,final Long tenantId) {
        this(username, password != null ? password.toCharArray() : null,tenantId, false, null,Constants.LOGINTYPE_PASSWORD);
    }

    public UsernamePasswordTokenExt(final String username, final char[] password,final Long tenantId, final String host) {
        this(username, password,tenantId, false, host,Constants.LOGINTYPE_PASSWORD);
    }

    public UsernamePasswordTokenExt(final String username, final String password,final Long tenantId, final String host) {
        this(username, password != null ? password.toCharArray() : null,tenantId, false, host,Constants.LOGINTYPE_PASSWORD);
    }

    public UsernamePasswordTokenExt(final String username, final char[] password,final Long tenantId, final boolean rememberMe) {
        this(username, password,tenantId, rememberMe, null,Constants.LOGINTYPE_PASSWORD);
    }

    public UsernamePasswordTokenExt(final String username, final String password,final Long tenantId, final boolean rememberMe) {
        this(username, password != null ? password.toCharArray() : null,tenantId, rememberMe, null,Constants.LOGINTYPE_PASSWORD);
    }

    public UsernamePasswordTokenExt(final String username, final char[] password,final Long tenantId,
                                 final boolean rememberMe, final String host, final String loginType) {

        this.username = username;
        this.password = password;
        this.tenantId = tenantId;
        this.rememberMe = rememberMe;
        this.host = host;
        this.loginType = loginType;
    }


    public UsernamePasswordTokenExt(final String username, final String password,final Long tenantId,
                                 final boolean rememberMe, final String host) {
        this(username, password != null ? password.toCharArray() : null,tenantId, rememberMe, host,Constants.LOGINTYPE_PASSWORD);
    }

    
    
    public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public Object getPrincipal() {
        return getUsername();
    }

    public Object getCredentials() {
        return getPassword();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public void clear() {
        this.username = null;
        this.tenantId = null;
        this.host = null;
        this.rememberMe = false;

        if (this.password != null) {
            for (int i = 0; i < password.length; i++) {
                this.password[i] = 0x00;
            }
            this.password = null;
        }

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(" - ");
        sb.append(username);
        sb.append(",tenantId=").append(tenantId);
        sb.append(", rememberMe=").append(rememberMe);
        sb.append(", loginType=").append(loginType);
        if (host != null) {
            sb.append(" (").append(host).append(")");
        }
        return sb.toString();
    }
}
