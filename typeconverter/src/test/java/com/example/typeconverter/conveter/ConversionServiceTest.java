package com.example.typeconverter.conveter;

import typeconverter.conveter.IntegerToStringConverter;
import typeconverter.conveter.IpPortToStringConvert;
import typeconverter.conveter.StringToIntegerConveter;
import typeconverter.conveter.StringToIpPortConvert;
import typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

public class ConversionServiceTest {
    @Test
    void conversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIntegerConveter());
        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new StringToIpPortConvert());
        conversionService.addConverter(new IpPortToStringConvert());

        // convert
        Assertions.assertThat(conversionService.convert("10", Integer.class)).isEqualTo(10);
        Assertions.assertThat(conversionService.convert(10, String.class)).isEqualTo("10");
        IpPort ipPort = conversionService.convert("127.0.0.1:8080", IpPort.class);
        Assertions.assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8080));

        String ipPortString = conversionService.convert(new IpPort("127.0.0.1", 8080), String.class);
        Assertions.assertThat(ipPortString).isEqualTo("127.0.0.1:8080");

    }
}
