package cn.jessexiong.rpcfx.demo.provider;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:config.properties")
@Data
@Configuration
public class ConfigProperty {

    @Value("${app.group}")
    public String group;

    @Value("${app.version}")
    public String version;
}
