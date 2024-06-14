package Customer.Registration.Customer.Data.save.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class CustomRestTemplate {

    @Bean
    public RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Replace default converters with MappingJackson2HttpMessageConverter
        restTemplate.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));

        return restTemplate;
    }


}
