package com.kru.qualibrate.eth;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import com.kru.qualibrate.commons.ResourceToFile;
import com.kru.qualibrate.exceptions.EtheriumServiceException;

import lombok.extern.slf4j.Slf4j;

/**
 * Etherium Network client service using Web3J
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@Slf4j
@Service
public class ContractService {

    private final Web3j web3j;

    private final String ethNetUrl;

    private static final String CONTRACT_ADD = "0xe9792191E510a5C71B2d24F8251fE2a5D4e298e0";

    private static final String WALLET_FILE = "UTC--2018-03-02T11-30-48.905Z--ccc980274fb2c043fd1e637f7fa4dcc9ec5d772e";

    private static final String WALLET_PASSWORD = "ether@2018";

    private final ApiContract_sol_ApiContract contract;

    private Credentials credentials;

    private EthLogService ethLogService;

    public ContractService(@Value("${eth.url:localhost:8545}") String ethNetURL,
        EthLogService ethLogService) throws Exception {
        this.ethNetUrl = ethNetURL;
        this.web3j = Web3j.build(new HttpService(ethNetUrl));
        this.ethLogService = ethLogService;
        File file;
        try {
        	file = ResourceToFile.getResourceAsFile(WALLET_FILE);
            this.credentials = WalletUtils.loadCredentials(WALLET_PASSWORD, file);
            this.contract = ApiContract_sol_ApiContract.load(CONTRACT_ADD, web3j,
                credentials, BigInteger.valueOf((long) 1), BigInteger.valueOf((long) 21000));
        } catch (IOException | CipherException e) {
            log.error("error initializing eth services {}", e.getMessage());
            throw e;
        }
    }

    public String getClientVersion() throws IOException {
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
        return web3ClientVersion.getWeb3ClientVersion();
    }

    @SuppressWarnings("unchecked")
    public List<String> getBalance() {
        Request<String, EthGetBalance> req = null;
        try {
            req = (Request<String, EthGetBalance>)
                web3j.ethGetBalance(CONTRACT_ADD, new DefaultBlockParameterNumber(getBlockNumber()));
            log.info("connected to : {}", req.getParams());
        } catch (IOException e) {
            log.error("error connecting eth network {}", e.getMessage());
            throw new EtheriumServiceException(e.getMessage());
        }
        return req.getParams();
    }

    /**
     * Accepts API end point against which contract transaction is to be recorder in Etherium network
     *
     * @param apiEP API endpint, eg. /api/v1/user, /api/v1/project
     * @return TransactionReceipt
     * @throws Exception
     */
    public TransactionReceipt transact(String apiEP) {
        TransactionReceipt receipt = null;
        try {
            log.debug("gas price {}", web3j.ethGasPrice().send().getGasPrice());
            receipt = contract.countHitForApi(Numeric.hexStringToByteArray(asciiToHex(apiEP))).send();
        } catch (Exception e) {
            ethLogService.save(new EthDTO(apiEP, e.getMessage()));
            log.error("error while creating transaction {}", e.getMessage());
            //throw new EtheriumServiceException(e.getMessage());
        }
        return receipt;
    }

    private static String asciiToHex(String asciiValue) {
        char[] chars = asciiValue.toCharArray();
        StringBuffer hex = new StringBuffer();
        IntStream.range(0, chars.length - 1).forEach(x -> {hex.append(Integer.toHexString((int) chars[x]));});
        return hex.toString() + String.join("", Collections.nCopies(32 - (hex.length() / 2), "00"));
    }

    public RemoteCall<BigInteger> getCount(String apiEP) throws IOException {
        String uInt256 = web3j.web3Sha3(apiEP).send().getResult();
        return contract.totalHitsFor(uInt256.getBytes());
    }

    public BigInteger getBlockNumber() throws IOException {
        return web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf("latest"),
             true).send().getBlock().getGasLimit();
    }


    public String deployContract(Contract cont) {
        String contractAddress = null;
        RawTransaction rawTransaction = RawTransaction.createContractTransaction(
            cont.getNonce(), cont.getGasPrice(), cont.getGasLimit(), cont.getValue(),
            cont.getAbiByteCode());
        String transactionHash;
        EthGetTransactionReceipt transactionReceipt = null;
        try {
            transactionHash = web3j.web3Sha3(rawTransaction.getData()).send().getResult();
            transactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).send();
        } catch (IOException e) {
            log.error("error deploying contract {}", e.getMessage());
            throw new EtheriumServiceException(e.getMessage());
        }

        contractAddress = transactionReceipt.getTransactionReceipt().get().getContractAddress();
        log.info("contractAddress {}", contractAddress);
        return contractAddress;
    }
}
