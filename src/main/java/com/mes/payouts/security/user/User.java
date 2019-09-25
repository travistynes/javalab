package com.mes.payouts.security.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

/**
 * When annotations are on fields instead of getters, JPA will use reflection to access
 * them, even if they are private.
 */
@Entity
@Table(name = "USERS", schema = "MES")
public class User {
	private static final Logger log = LoggerFactory.getLogger(User.class);

	@Id
	@Column(name="user_id")
	private long userId;

	@Column(name="login_name")
	private String loginName;

	@Column(name="password")
	private String password;

	@Column(name="password_hash_type")
	private int passwordHashType;

	@Column(name="hierarchy_node")
	private Long hierarchyNode;

	@Column(name="enabled")
	private String enabled;

	public String getLoginName() {
		return loginName;
	}

	public String getPassword() {
		return password;
	}

	public boolean isEnabled() {
		return enabled.equals("Y");
	}
}
