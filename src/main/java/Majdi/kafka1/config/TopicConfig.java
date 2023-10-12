package Majdi.kafka1.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {



    @Bean
    public NewTopic employeeTopic() {
        return TopicBuilder.name("employee").build();
    }

    @Bean
    public NewTopic adminTopic() {
        return TopicBuilder.name("admin").build();
    }

}
