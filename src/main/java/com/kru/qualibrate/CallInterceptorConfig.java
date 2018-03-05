package com.kru.qualibrate;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.kru.qualibrate.eth.ApiCallInterceptor;

/**
 * Configure to Intercept all API calls for patterns "/api/v1/user", "/api/v1/project"
 * This configuration adds ApiCallInterceptor which invokes Etherium Transaction against smart contract
 *
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */
@org.springframework.context.annotation.Configuration
public class CallInterceptorConfig extends WebMvcConfigurerAdapter {
    @Bean
    public ApiCallInterceptor apiCallInterceptor() {
        return new ApiCallInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiCallInterceptor())
            .addPathPatterns("/api/v1/**", "/api/v1/**/**", "/api/v1/**/**/**")
            .excludePathPatterns("/api/v1/eth/**");
    }
}
