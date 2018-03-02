/**
 * 
 */
package com.kru.qualibrate.eth;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@Data
@AllArgsConstructor
public class EthDTO {
	private long id;

	private String transaction;

	private String response;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date timestamp = new Date();

    public EthDTO(String transaction, String response) {
    	this.transaction = transaction;
    	this.response = response;
    	this.timestamp =  new Date();
    	
    }
}
