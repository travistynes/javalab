package com.mes.payouts.security.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByLoginName(String loginName);
	public User save(User user);
	public void delete(User user);
}
