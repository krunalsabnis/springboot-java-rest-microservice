/**
 * 
 */
package com.kru.qualibrate.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 * Data Access Layer for Project
 */
public interface ProjectRepository extends JpaRepository<ProjectRecord, Long> {
	Page<ProjectRecord> findAll(Pageable pageable);
}
