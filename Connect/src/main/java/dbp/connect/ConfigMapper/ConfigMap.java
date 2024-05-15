package dbp.connect.ConfigMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
public class ConfigMap {
    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }
}
