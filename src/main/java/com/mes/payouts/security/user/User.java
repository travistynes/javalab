package com.mes.payouts.security.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "USERS", schema = "MES")
public class User {
	private static final Logger log = LoggerFactory.getLogger(User.class);

	@Id
	@Column(name="user_id")
	private Long userID;

	@Column(name="login_name")
	private String loginName;

	@Column(name="password_enc")
	private String passwordEncoded;

	@Column(name="password_hash_type")
	private int passwordHashType;

	@Column(name="hierarchy_node")
	private Long hierarchyNode;

	@Column(name="enabled")
	private String enabled;

	public String getLoginName() {
		return loginName;
	}

	public String getPasswordEncoded() {
		return passwordEncoded;
	}

	public boolean isEnabled() {
		return enabled.equals("Y");
	}
}
