/**
 * 
 */
package com.kru.qualibrate.eth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import lombok.extern.slf4j.Slf4j;

/**
 * Etherium Network client service using Web3J
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 * 
 */
@Slf4j
@Service
public class Web3JService {

	private Web3j web3j;

	private final String ethNetUrl;

	public Web3JService(Web3j web3j,
			@Value("${eth.url:localhost:8545}") String ethNetUrl) {
		this.ethNetUrl = ethNetUrl;
		this.web3j = web3j;
		//web3j.build(new HttpService(ethNetUrl));

	}
	
	public String getClientVersion() throws IOException {
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
        log.info("connected to : {}", ethNetUrl);
        return web3ClientVersion.getWeb3ClientVersion();
    }
	
}
