package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping(value = "/items/new")
    public String createForm(Model model) {
        model.addAttribute("bookForm", new BookForm()); // th:object = "${form}" 에 엮이게 된다.
        return "/items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(@Valid BookForm form, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("에러 발생" + result.getAllErrors());
            return "/items/createItemForm";
        }
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping(value = "/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "/items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm bookForm = new BookForm();
        bookForm.setId(item.getId());
        bookForm.setName(bookForm.getName());
        bookForm.setPrice(bookForm.getPrice());
        bookForm.setStockQuantity(bookForm.getStockQuantity());
        bookForm.setIsbn(bookForm.getIsbn());

        model.addAttribute("bookForm", bookForm);
        return "items/updateItemForm";
    }

    //Comment @PathVariable Annotation which indicates that a method parameter should be bound to a URI template variable
    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId,@ModelAttribute("bookForm") BookForm bookForm, BindingResult result) {
        if (result.hasErrors()) {
            return "items/updateItemForm";
        }
        //Comment 훨씬 깔끔하다.
        itemService.updateItem(itemId, bookForm.getName(), bookForm.getPrice(), bookForm.getStockQuantity());
        return "redirect:/items";
    }
}
