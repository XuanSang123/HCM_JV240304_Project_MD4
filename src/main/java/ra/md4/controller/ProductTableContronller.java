package ra.md4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.md4.models.Product;
import ra.md4.service.admin.product.IProductService;

import java.util.List;

@Controller
@RequestMapping("/product-table")
public class ProductTableContronller {

    @Autowired
    private IProductService iProductService;

    @GetMapping
    public String productTable(Model model){
        int id = 1;
        List<Product> products = iProductService.findByCategoryId(id);
        model.addAttribute("products", products);
        return "layout/producttable/table";
    }


}
