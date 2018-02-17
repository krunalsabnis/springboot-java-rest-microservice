/**
 * 
 */
package com.kru.qualibrate.config;



import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 * Configuration class for customizing Swagger Page Docket
 * 
 * reference : https://github.com/springfox/springfox/issues/755
 */


@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    
	@Component
    @Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
    public static class PageableParameterBuilder implements ParameterBuilderPlugin {

        @Override
        public boolean supports(DocumentationType delimiter) {
            return true;
        }

        @Override
        public void apply(ParameterContext parameterContext) {
            MethodParameter param = parameterContext.methodParameter();
            if (param.getParameterType().equals(Pageable.class)) {
                List<Parameter> parameters = new ArrayList<>();
                parameters.add(parameterContext.parameterBuilder()
                        .parameterType("query").name("page").modelRef(new ModelRef("int"))
                        .description("Results page you want to retrieve (0..N)").build());
                parameters.add(parameterContext.parameterBuilder()
                        .parameterType("query").name("size").modelRef(new ModelRef("int"))
                        .description("Number of records per page.").build());
                parameters.add(parameterContext.parameterBuilder()
                        .parameterType("query").name("sort").modelRef(new ModelRef("string")).allowMultiple(true)
                        .description("Sorting criteria in the format: property(,asc|desc)."
                                + "Default sort order is ascending. Multiple sort criteria are supported.").build());
                parameterContext.getOperationContext().operationBuilder().parameters(parameters);
            }
        }
    }
}