package ra.md4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.md4.models.Product;
import ra.md4.service.admin.product.IProductService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class HomeController {
    @Autowired
    private IProductService iProductService;
    @GetMapping
    public String home(Model model) {
        int id = 1;
        List<Product> productTable = iProductService.findByCategoryId(id);
        if (productTable.size() > 5){
            productTable = productTable.stream().limit(5).collect(Collectors.toList());
        }
        model.addAttribute("productTable", productTable);

        int id1 = 2;
        List<Product> productRoom = iProductService.findByCategoryId(id1);
        if (productRoom.size() > 5){
            productRoom = productRoom.stream().limit(5).collect(Collectors.toList());
        }
        model.addAttribute("productRoom", productRoom);


        return "layout/home";
    }


    @GetMapping("/contact")
    public String contact(){
        return "layout/contact";
    }

    @GetMapping("/wishlist")
    public String wishlist(){
        return "layout/wishlist/wishlist";
    }


}
