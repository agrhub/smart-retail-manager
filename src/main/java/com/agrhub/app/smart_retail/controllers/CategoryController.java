package com.agrhub.app.smart_retail.controllers;

import com.agrhub.app.smart_retail.models.CategoryEntity;
import com.agrhub.app.smart_retail.repositories.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController extends BaseController {

    @Autowired
    CategoryService service;

    @CrossOrigin
    @RequestMapping(path = "/categories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CategoryEntity> gets() {
        logger.info("CategoryController: gets");
        return service.listEntities(null);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryEntity get(@PathVariable(name = "id", required = true) String id) {
        return service.readEntity(Long.valueOf(id));
    }

    @CrossOrigin
    @RequestMapping(path = "/category", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CategoryEntity create(@RequestBody(required = true) CategoryEntity task) {
        logger.info("TaskEntity: " + task);

        Long id = service.createEntity(task);
        task.setId(id);

        return task;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE, path = "/category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(name = "id", required = true) String id) {
        service.deleteEntity(Long.valueOf(id));
    }

    @CrossOrigin
    @RequestMapping(path = "/category", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody(required = true) CategoryEntity task) {
        logger.info("TaskEntity: " + task);

        service.updateEntity(task);

        return;
    }

}
