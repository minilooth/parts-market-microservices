package by.minilooth.vehicleservice.config;

import by.minilooth.vehicleservice.common.consts.ErrorConsts;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;

@Configuration
public class ErrorConfig {

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {

            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions option) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, option);
                errorAttributes.put(ErrorConsts.TIMESTAMP_KEY, LocalDateTime.now());
                return errorAttributes;
            }

        };
    }

}
