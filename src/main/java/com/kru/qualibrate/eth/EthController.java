package com.kru.qualibrate.eth;

import java.io.IOException;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

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
    private ContractService web3Service;

    @Autowired
    private EthLogService ethLogService;

    @ApiOperation(value = "Get Client Details", notes = "Get Eth Client Details")
    @RequestMapping(method = RequestMethod.GET, value = "/client")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getEthClientDetails() {
        try {
            return ResponseEntity.ok(web3Service.getClientVersion());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity<String>("eth client initialization error", HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Get Block Details", notes = "Get Eth block Details")
    @RequestMapping(method = RequestMethod.GET, value = "/block")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<BigInteger> getBlockByNumber() {
        try {
            return ResponseEntity.ok(web3Service.getBlockNumber());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value = "Get Client Details", notes = "Get Contract Adress balance")
    @RequestMapping(method = RequestMethod.GET, value = "/balance")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getBalance() {
        return ResponseEntity.ok(web3Service.getBalance());
    }

    @ApiOperation(value = "Get Client Details", notes = "Get Eth Client Details")
    @RequestMapping(method = RequestMethod.GET, value = "/transact")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TransactionReceipt> transact() throws Exception {
        return ResponseEntity.ok(web3Service.transact("/api/v1/user"));
    }

    @ApiOperation(value = "Get Client Details", notes = "Get Eth Client Details")
    @RequestMapping(method = RequestMethod.GET, value = "/getCount")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getC() {
        try {
            return ResponseEntity.ok(web3Service.getCount("/api/v1/user").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value = "Deploy contract", notes = "deploy solidity contract")
    @RequestMapping(method = RequestMethod.POST, value = "/deploy")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> deploy(@RequestBody Contract cont) {
        return ResponseEntity.ok(web3Service.deployContract(cont));
    }

    @ApiOperation(value = "Eth logs", notes = "get logs")
    @RequestMapping(method = RequestMethod.GET, value = "/logs")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<EthDTO>> getLogs(@PageableDefault(value = 50) Pageable pageable) {
        return ResponseEntity.ok(ethLogService.getLogs(pageable));
    }
}
