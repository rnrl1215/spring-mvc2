package hello.itemservice.vaildation;

import com.sun.jdi.Field;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.FieldError;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.*;

public class MessageCodesResolverTest {
    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodeResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        for (String messageCode : messageCodes) {
            // 상세 값이 먼저 나오고 그 뒤에 범용적인 값이 나온다.
            // 상세 값이 우선순위가 더 높다.
            System.out.println("messageCode = " + messageCode);
        }
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        for (String messageCode : messageCodes) {
            System.out.println("messageCode=" + messageCode);
        }

        assertThat(messageCodes).containsExactly(
                "required.item.itemName"
                , "required.itemName"
                , "required.java.lang.String"
                , "required");
        //rejectValue 안에서 codeResolver 를 호출한다.
        //bindingResult.rejectValue("itemName", "required");
        //new FieldError("item", "itemName", null, false, messageCodes, null, null);
    }
}
