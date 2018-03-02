/**
 * 
 */
package com.kru.qualibrate.eth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kru.qualibrate.user.UserDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/eth/")
@Api(description = "Etherium Contract Administration", tags = "Smart Contracts", consumes = "application/json")
public class EthController {

	@Autowired
	private Web3JService web3;
	
	@ApiOperation(value = "Get Client Details", notes = "Get Eth Client Details")
	@RequestMapping(method = RequestMethod.GET, value = "/client")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getEthClientDetails() {
		try {
			return ResponseEntity.ok(web3.getClientVersion());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return new ResponseEntity<String>("eth client initialization error", HttpStatus.NOT_FOUND);
	}
}
