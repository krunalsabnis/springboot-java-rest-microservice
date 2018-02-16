/**
 * 
 */
package com.kru.qualibrate.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 * Data Access Layer for User
 */
public interface UserRepository extends JpaRepository<UserRecord, Long> {
	Page<UserRecord> findAll(Pageable pageable);

}
