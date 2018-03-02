/**
 * 
 */
package com.kru.qualibrate.eth;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
public interface EthLogRepository extends JpaRepository<EthLogRecord, Long> {
	Page<EthLogRecord> findAll(Pageable pageable);

}