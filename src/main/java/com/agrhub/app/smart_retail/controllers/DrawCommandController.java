package com.agrhub.app.smart_retail.controllers;

import com.agrhub.app.smart_retail.models.DrawCommandEntity;
import com.agrhub.app.smart_retail.repositories.DrawCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DrawCommandController extends BaseController {

    @Autowired
    DrawCommandService service;

    @CrossOrigin
    @RequestMapping(path = "/deviceTypes/{id}/drawCommands", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DrawCommandEntity> gets(@PathVariable(name = "id", required = true) String id) {
        return service.listEntitiesWithDeviceType(Long.valueOf(id));
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/drawCommand/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DrawCommandEntity get(@PathVariable(name = "id", required = true) String id) {
        return service.readEntity(Long.valueOf(id));
    }

    @CrossOrigin
    @RequestMapping(path = "/drawCommand", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DrawCommandEntity create(@RequestBody(required = true) DrawCommandEntity task) {
        Long id = service.createEntity(task);
        task.setId(id);

        return task;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE, path = "/drawCommand/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(name = "id", required = true) String id) {
        service.deleteEntity(Long.valueOf(id));
    }

    @CrossOrigin
    @RequestMapping(path = "/drawCommand", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody(required = true) DrawCommandEntity task) {
        service.updateEntity(task);
        return;
    }
}
