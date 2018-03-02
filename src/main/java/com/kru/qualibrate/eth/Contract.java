/**
 * 
 */
package com.kru.qualibrate.eth;

import java.math.BigInteger;

import lombok.Data;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@Data
public class Contract {
	private BigInteger nonce;
	private BigInteger gasPrice;
	private BigInteger gasLimit;
	private BigInteger value;
	private String abiByteCode;
}
