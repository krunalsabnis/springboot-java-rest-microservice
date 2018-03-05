package com.kru.qualibrate;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.kru.qualibrate.eth.ApiCallInterceptor;
import com.kru.qualibrate.eth.ContractService;

/**
 * Configure to Intercept all API calls for patterns "/api/v1/user", "/api/v1/project"
 * This configuration adds ApiCallInterceptor which invokes Etherium Transaction against smart contract
 *
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@Profile({ "test" })
@org.springframework.context.annotation.Configuration
public class TestCallInterceptorConfig extends WebMvcConfigurerAdapter {

    @Bean
    ContractService contractService() {
        return Mockito.mock(ContractService.class);
    }

    @Bean
    ApiCallInterceptor apiCallInterceptor() {
        return Mockito.mock(ApiCallInterceptor.class);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiCallInterceptor()).addPathPatterns("/api/v1/user", "/api/v1/project");
    }
}
