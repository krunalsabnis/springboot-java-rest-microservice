/**
 * 
 */
package com.kru.qualibrate;

import org.springframework.context.annotation.Bean;

import com.kru.qualibrate.commons.NonNullCopyBeanUtils;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@org.springframework.context.annotation.Configuration
public class Configuration {
	
	@SuppressWarnings("rawtypes")
	@Bean
	NonNullCopyBeanUtils beanUtils() {
		return new NonNullCopyBeanUtils();
	}

}
