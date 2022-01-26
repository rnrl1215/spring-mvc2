package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    //@PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                           Model model) {
        // Binding Result 를 사용하면 erros 역할을 대신해준다.
        // 모델에 안담아도 자동으로 넘어간다.
        Map<String, String> errors = new HashMap<>();

        // 검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            //errors.put("itemName", "상품이름은 필수 입니다.");
            bindingResult.addError(new FieldError("item", "itemName", "상품이름은 필수입니다."));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            //errors.put("price", "가격은 1,000 에서 ~ 1,000,000 까지 허용합니다.");
            bindingResult.addError(new FieldError("item", "price", "가격은 1,000 에서 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
           // errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."));
        }
        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 100000) {
                //errors.put("globalError", "가격 * 수량의 합은 10,000원 이상 이어야 합니다. 현재값 = " + resultPrice);
                bindingResult.addError(new ObjectError("item", "수량은 최대 9,999 까지 허용합니다."));
            }
        }
        // 검증 실패시 다시 입력 폼으로
       // if (!errors.isEmpty()) {
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            //model.addAttribute("errors", errors);
            return "validation/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                             Model model) {

        // Binding Result 를 사용하면 erros 역할을 대신해준다.
        // 모델에 안담아도 자동으로 넘어간다.
        Map<String, String> errors = new HashMap<>();

        // 검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            //errors.put("itemName", "상품이름은 필수 입니다.");
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null,  "상품이름은 필수입니다."));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            //errors.put("price", "가격은 1,000 에서 ~ 1,000,000 까지 허용합니다.");
            //bindingResult.addError(new FieldError("item", "price", "가격은 1,000 에서 ~ 1,000,000 까지 허용합니다."));
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000 에서 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            // errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
            //bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."));
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null,  "수량은 최대 9,999 까지 허용합니다."));
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 100000) {
                //errors.put("globalError", "가격 * 수량의 합은 10,000원 이상 이어야 합니다. 현재값 = " + resultPrice);
                bindingResult.addError(new ObjectError("item", "수량은 최대 9,999 까지 허용합니다."));
            }
        }

        // 검증 실패시 다시 입력 폼으로
        // if (!errors.isEmpty()) {
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            //model.addAttribute("errors", errors);
            return "validation/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }


    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                             Model model) {

        // Binding Result 를 사용하면 erros 역할을 대신해준다.
        // 모델에 안담아도 자동으로 넘어간다.
        Map<String, String> errors = new HashMap<>();

        // 검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            //errors.put("itemName", "상품이름은 필수 입니다.");
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null,  null));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            //errors.put("price", "가격은 1,000 에서 ~ 1,000,000 까지 허용합니다.");
            //bindingResult.addError(new FieldError("item", "price", "가격은 1,000 에서 ~ 1,000,000 까지 허용합니다."));
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false,  new String[]{"range.item.price"}, new Object[]{1000,1000000}, null));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            // errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
            //bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."));
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999},  null));
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 100000) {
                //errors.put("globalError", "가격 * 수량의 합은 10,000원 이상 이어야 합니다. 현재값 = " + resultPrice);
                bindingResult.addError(new ObjectError("item",  new String[]{"totalPriceMin"},new Object[]{10000, resultPrice},null));
            }
        }

        // 검증 실패시 다시 입력 폼으로
        // if (!errors.isEmpty()) {
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            //model.addAttribute("errors", errors);
            return "validation/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                            Model model) {

        // Binding Result 를 사용하면 erros 역할을 대신해준다.
        // 모델에 안담아도 자동으로 넘어간다.
        Map<String, String> errors = new HashMap<>();

        // Item 뒤에 BindingResult가 오는 것을 유의해서 보자
        // 다음과 같이 BindingResult는 타겟을 미리 알 수 있다.
        log.info("objectName = {}", bindingResult.getObjectName());
        log.info("target = {}", bindingResult.getTarget());

        // 검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            //bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null,  null));
            bindingResult.rejectValue("itemName", "required");
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            //errors.put("price", "가격은 1,000 에서 ~ 1,000,000 까지 허용합니다.");
            //bindingResult.addError(new FieldError("item", "price", "가격은 1,000 에서 ~ 1,000,000 까지 허용합니다."));
            //bindingResult.addError(new FieldError("item", "price", item.getPrice(), false,  new String[]{"range.item.price"}, new Object[]{1000,1000000}, null));
            bindingResult.rejectValue("price", "range", new Object[]{1000,1000000}, null);
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            // errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
            //bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."));
            //bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999},  null));
            bindingResult.rejectValue("quantity", "max", new Object[]{9999},  null);
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 100000) {
                //errors.put("globalError", "가격 * 수량의 합은 10,000원 이상 이어야 합니다. 현재값 = " + resultPrice);
                //bindingResult.addError(new ObjectError("item",  new String[]{"totalPriceMin"},new Object[]{10000, resultPrice},null));
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice},null);
            }
        }

        // 검증 실패시 다시 입력 폼으로
        // if (!errors.isEmpty()) {
        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            //model.addAttribute("errors", errors);
            return "validation/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}
