pragma solidity ^0.4.20;


contract ApiContract20 {

  mapping (bytes32 => uint8) public apiHits;
  
  bytes32[] public apiList;

  function ApiContract20(bytes32[] apiEndPoint) public {
    apiList = apiEndPoint;
  }

  function totalHitsFor(bytes32 api) view public returns (uint8) {
    require(validApi(api));
    return apiHits[api];
  }

  function countHitForApi(bytes32 api) public {
    require(validApi(api));
    apiHits[api] += 1;
  }

  function validApi(bytes32 api) view public returns (bool) {
    if (apiList[0] == api || apiList[1] == api) {
        return true;
    } else 
      return false;
  }
}
