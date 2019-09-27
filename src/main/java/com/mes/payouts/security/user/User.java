package com.mes.payouts.security.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

/**
 * When annotations are on fields instead of getters, JPA will use reflection to access
 * them, even if they are private.
 *
 * This example joins two tables together into a single entity using @SecondaryTable,
 * and joins to another entity using a @OneToOne mapping because a SecondaryTable can
 * only map another table to the primary table's primary key, not any arbitrary
 * non-primary key column. The @OneToOne does not have this restriction.
 *
 * https://docs.oracle.com/javaee/7/api/javax/persistence/SecondaryTable.html
 * https://www.baeldung.com/jpa-one-to-one
 */
@Entity
@Table(name = "USERS", schema = "MES")
@SecondaryTable(name= "USER_DETAILS", schema = "MES", pkJoinColumns=@PrimaryKeyJoinColumn(name="user_name"))
public class User {
	private static final Logger log = LoggerFactory.getLogger(User.class);

	@Id
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

	@Column(name="email", table="user_details")
	private String email;

	@OneToOne
	@JoinColumn(name="department_id", referencedColumnName="dept_id")
	private Department department;

	public String getLoginName() {
		return loginName;
	}

	public String getPassword() {
		return password;
	}

	public boolean isEnabled() {
		return enabled.equals("Y");
	}

	public String getEmail() {
		return email;
	}

	public Department getDepartment() {
		return department;
	}
}
