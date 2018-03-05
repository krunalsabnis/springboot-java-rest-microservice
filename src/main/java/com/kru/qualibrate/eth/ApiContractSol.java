package com.kru.qualibrate.eth;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.3.0. from soliditi ABI and BIN file
 */
@SuppressWarnings("rawtypes")
public class ApiContractSol extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b6040516102fa3803806102fa833981016040528080519091019050600181805161003d929160200190610044565b50506100ae565b828054828255906000526020600020908101928215610081579160200282015b828111156100815782518255602090920191600190910190610064565b5061008d929150610091565b5090565b6100ab91905b8082111561008d5760008155600101610097565b90565b61023d806100bd6000396000f30060606040526004361061006b5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166297f2c3811461007057806346f0eaac146100985780634bc87e33146100b057806351e17963146100dc57806368bd04be146100f2575b600080fd5b341561007b57600080fd5b61008660043561011c565b60405190815260200160405180910390f35b34156100a357600080fd5b6100ae60043561013b565b005b34156100bb57600080fd5b6100c6600435610173565b60405160ff909116815260200160405180910390f35b34156100e757600080fd5b6100c66004356101a2565b34156100fd57600080fd5b6101086004356101b7565b604051901515815260200160405180910390f35b600180548290811061012a57fe5b600091825260209091200154905081565b610144816101b7565b151561014f57600080fd5b6000908152602081905260409020805460ff8082166001011660ff19909116179055565b600061017e826101b7565b151561018957600080fd5b5060008181526020819052604090205460ff165b919050565b60006020819052908152604090205460ff1681565b600180546000918391839081106101ca57fe5b60009182526020909120015414806101fc575060018054839190819081106101ee57fe5b600091825260209091200154145b156102095750600161019d565b50600061019d5600a165627a7a72305820fb5881a0750bf10ac64c27d99ab49500ac18dec0994be1a343e3b2b4aab2a22f0029";

    protected ApiContractSol(String contractAddress, Web3j web3j,
        Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ApiContractSol(String contractAddress, Web3j web3j,
        TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
            super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<byte[]> apiList(BigInteger param0) {
        Function function = new Function("apiList",
               Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)),
               Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<TransactionReceipt> countHitForApi(byte[] api) {
        Function function = new Function("countHitForApi",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(api)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> totalHitsFor(byte[] api) {
        Function function = new Function("totalHitsFor",
            Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(api)),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> apiHits(byte[] param0) {
        Function function = new Function("apiHits",
            Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> validApi(byte[] api) {
        Function function = new Function("validApi",
            Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(api)),
            Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public static RemoteCall<ApiContractSol> deploy(Web3j web3j,
           Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, List<byte[]> apiEndPoint) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                org.web3j.abi.Utils.typeMap(apiEndPoint, org.web3j.abi.datatypes.generated.Bytes32.class))));
        return deployRemoteCall(ApiContractSol.class, web3j,
                credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<ApiContractSol> deploy(Web3j web3j,
           TransactionManager transactionManager, BigInteger gasPrice,
           BigInteger gasLimit, List<byte[]> apiEndPoint) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j
            .abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.Utils.typeMap(apiEndPoint, org.web3j.abi.datatypes.generated.Bytes32.class))));
        return deployRemoteCall(ApiContractSol.class, web3j,
            transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static ApiContractSol load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ApiContractSol(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static ApiContractSol load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ApiContractSol(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
