package springstudy.itemservice.web.basic;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springstudy.itemservice.domain.item.Item;
import springstudy.itemservice.domain.item.ItemRepository;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // final 변수를 자동 생성자 DI
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                      @RequestParam Integer price,
                      @RequestParam Integer quantity,
                      Model model) {
        Item item = new Item(itemName, price, quantity);
        itemRepository.save(item);
        model.addAttribute(item);
        return "basic/item";
    }

    // @ModelAttribute에 이름 설정할 경우 자동으로 model에 추가함 (생략 가능 -> 클래스명의 첫글자를 소문자로 변경하여 자동 추가)
    // @ModelAttribute 생략 가능
//    @PostMapping("/add")
    public String addItemV2(Item item) {
        itemRepository.save(item);
//        model.addAttribute(item); //자동 추가, 생략 가능
        return "redirect:/basic/items/" + item.getId();
    }

    // RedirectAttribute에서 return 값을 사용되지 않는 값은 쿼리 파라미터로 사용됨 (?status=true)
    @PostMapping("/add")
    public String addItemV3(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute(item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
//        return "redirect:/basic/items/" + itemId;
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

}
