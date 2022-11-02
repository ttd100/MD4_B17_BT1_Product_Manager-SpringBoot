package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import rikkei.academy.model.Product;
import rikkei.academy.service.product.IProductService;

import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    private IProductService productService;
    @GetMapping("/create")
    public ModelAndView showFormCreate(){
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product",new Product());
        return modelAndView;
    }
    @PostMapping("create")
    public ModelAndView CreateProduct(@ModelAttribute("product") Product product){
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product",new Product());
        modelAndView.addObject("message","Create Product successfully ");
        return modelAndView;
    }
    @GetMapping("/")
    public ModelAndView showListProduct(){
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products",productService.findAll());
        return modelAndView;
    }
    @GetMapping("edit/{id}")
    public ModelAndView showFormEdit(@PathVariable Long id){
        Optional<Product> product = productService.findById(id);
        if(product.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/product/edit");
            modelAndView.addObject("product",product.get());
            return modelAndView;
        }else {
            return new ModelAndView("/product/error.404");
        }
    }
    @PostMapping("edit")
    public ModelAndView editProduct(@ModelAttribute("product")Product product){
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("product",new Product());
        modelAndView.addObject("message","edit product successfully");
        return modelAndView;
    }
    @GetMapping("delete/{id}")
    public  ModelAndView showFormDelete(@PathVariable Long id){
        Optional<Product> product = productService.findById(id);
        if(product.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/product/delete");
            modelAndView.addObject("product",product.get());
            return modelAndView;
        }else {
            return  new ModelAndView("/product/error.404");
        }
    }
    @PostMapping("delete")
    public  String deleteProduct(@ModelAttribute("product") Product product){
        productService.remote(product.getId());
        return "redirect:/";
    }

}
