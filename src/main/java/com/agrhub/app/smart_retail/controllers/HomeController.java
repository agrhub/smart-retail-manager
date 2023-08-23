package com.agrhub.app.smart_retail.controllers;

import com.agrhub.app.smart_retail.models.CategoryEntity;
import com.agrhub.app.smart_retail.models.ProductEntity;
import com.agrhub.app.smart_retail.repositories.CategoryService;
import com.agrhub.app.smart_retail.repositories.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeController extends BaseController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @CrossOrigin
    @RequestMapping(path = "/home", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryEntity> gets() {
        CategoryEntity categoryEntity;
        List<ProductEntity> productList;

        List<CategoryEntity> categoryList = categoryService.listEntities(null);
        for (int i = 0; i < categoryList.size(); i++) {
            categoryEntity = categoryList.get(i);
            productList = productService.productOfCategory(categoryEntity.getId());
            categoryEntity.setProducts(productList);
        }
        return categoryList;
    }

}
