/**
 * 
 */
package com.kru.qualibrate.eth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Custom Intercepter which invokes a transaction on etharium netwok against deployed
 * smart contract.
 *
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
public class ApiCallInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private ContractService contractService;
	@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
    		Object handler, Exception exception)
    throws Exception {
		contractService.transact(request.getMethod() + ":" + request.getRequestURI());
    }
}
