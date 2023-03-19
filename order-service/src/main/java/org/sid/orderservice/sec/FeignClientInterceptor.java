package org.sid.orderservice.sec;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    public static String getBearerTokenHeader(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null || attributes.getRequest() == null || attributes.getRequest().getHeader("Authorization") == null) {
            return null;
        }
        return attributes.getRequest().getHeader("Authorization");
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String bearerTokenHeader = getBearerTokenHeader();
        if (bearerTokenHeader != null) {
            requestTemplate.header(AUTHORIZATION_HEADER, bearerTokenHeader);
        }
    }
}
