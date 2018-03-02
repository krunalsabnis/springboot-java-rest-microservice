/**
 * 
 */
package com.kru.qualibrate.eth;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@Component
public class EthConverter implements Converter<EthLogRecord, EthDTO> {
	@Override
	public EthDTO convert(EthLogRecord source) {
		return new EthDTO(source.getId(),
				source.getTransaction(), source.getResponse(), source.getTimestamp());
	
	}
}
