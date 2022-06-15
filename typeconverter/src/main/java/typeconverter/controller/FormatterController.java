package typeconverter.controller;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;

@Controller
public class FormatterController {

    @GetMapping("/formatter/edit")
    public String formmaterForm(Model model) {
        Form form = new Form();
        form.setNumber(10000);
        form.setLocalDateTime(LocalDateTime.now());
        model.addAttribute("form", form);
        return "formatter-form";
    }

    @GetMapping("/formatter/edit")
    public String formatteEdit(@ModelAttribute Form form) {
        // Model.addAttribute 생략 가능 왜 @ModelAttrbute 에서 알아서 해줌
        return "formatter-view";
    }

    @Data
    static class Form {
        @NumberFormat(pattern  = "###,###")
        private Integer number;

        @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
        private LocalDateTime localDateTime;
    }
}
