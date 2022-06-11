package typeconverter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import typeconverter.conveter.IntegerToStringConverter;
import typeconverter.conveter.IpPortToStringConvert;
import typeconverter.conveter.StringToIntegerConveter;
import typeconverter.conveter.StringToIpPortConvert;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToIntegerConveter());
        registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new StringToIpPortConvert());
        registry.addConverter(new IpPortToStringConvert());
    }
}
