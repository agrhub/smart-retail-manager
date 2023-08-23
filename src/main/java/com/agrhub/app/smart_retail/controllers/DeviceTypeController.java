package com.agrhub.app.smart_retail.controllers;

import com.agrhub.app.smart_retail.models.DeviceTypeEntity;
import com.agrhub.app.smart_retail.repositories.DeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DeviceTypeController extends BaseController {

    @Autowired
    DeviceTypeService service;

    @CrossOrigin
    @RequestMapping(path = "/deviceTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DeviceTypeEntity> gets() {
        return service.listEntities(null);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/deviceType/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeviceTypeEntity get(@PathVariable(name = "id", required = true) String id) {
        return service.readEntity(Long.valueOf(id));
    }

    @CrossOrigin
    @RequestMapping(path = "/deviceType", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DeviceTypeEntity create(@RequestBody(required = true) DeviceTypeEntity task) {
        Long id = service.createEntity(task);
        task.setId(id);

        return task;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE, path = "/deviceType/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(name = "id", required = true) String id) {
        service.deleteEntity(Long.valueOf(id));
    }

    @CrossOrigin
    @RequestMapping(path = "/deviceType", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody(required = true) DeviceTypeEntity task) {
        service.updateEntity(task);
        return;
    }
}
