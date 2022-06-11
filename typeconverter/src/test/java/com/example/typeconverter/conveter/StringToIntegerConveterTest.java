package com.example.typeconverter.conveter;

import com.example.typeconverter.type.IpPort;
import net.bytebuddy.description.field.FieldDescription;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConveterTest {

    @Test
    void StringToInteger() {
        StringToIntegerConveter conveter = new StringToIntegerConveter();
        Integer result = conveter.convert("10");
        Assertions.assertThat(result).isEqualTo(10);
    }

    @Test
    void IntegerToString() {
        IntegerToStringConverter conveter = new IntegerToStringConverter();
        String result = conveter.convert(10);
        Assertions.assertThat(result).isEqualTo("10");
    }

    @Test
    void stringToIpPort() {
        StringToIpPortConvert convert = new StringToIpPortConvert();
        String source = "127.0.0.1:8080";
        IpPort result = convert.convert(source);
        // equalshashcode 때문에 비교가 가능함.
        Assertions.assertThat(result).isEqualTo(new IpPort("127.0.0.1",8080));
    }

    @Test
    void ipPortToString() {
        IpPortToStringConvert convert = new IpPortToStringConvert();
        IpPort ipPort = new IpPort("127.0.0.1", 8080);
        String result = convert.convert(ipPort);
        Assertions.assertThat(result).isEqualTo("127.0.0.1:8080");
    }
}