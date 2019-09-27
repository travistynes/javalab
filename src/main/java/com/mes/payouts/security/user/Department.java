package com.mes.payouts.security.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "DEPARTMENT", schema = "MES")
public class Department {
	private static final Logger log = LoggerFactory.getLogger(Department.class);

	@Id
	@Column(name="dept_id")
	private String departmentId;

	@Column(name="name")
	private String name;

	@OneToOne(mappedBy="department")
	private User user;

	public String getName() {
		return name;
	}
}
