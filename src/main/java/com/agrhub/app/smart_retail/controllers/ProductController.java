package com.agrhub.app.smart_retail.controllers;

import com.agrhub.app.smart_retail.entity.PostProductDevice;
import com.agrhub.app.smart_retail.models.DeviceEntity;
import com.agrhub.app.smart_retail.models.ProductEntity;
import com.agrhub.app.smart_retail.repositories.DeviceService;
import com.agrhub.app.smart_retail.repositories.ProductService;
import com.agrhub.app.smart_retail.utils.URLUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductController extends BaseController {

    @Autowired
    ProductService service;
    @Autowired
    DeviceService deviceService;

    @CrossOrigin
    @RequestMapping(path = "/products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductEntity> gets() {
        return service.listEntities(null);
    }

    @CrossOrigin
    @RequestMapping(path = "/products/promotion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductEntity> getPromotion() {
        return service.getPromotion();
    }

    @CrossOrigin
    @RequestMapping(path = "/products", params = "q", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductEntity> getWithParam(@RequestParam("q") String query) {
        return service.listEntities(null);
    }

    @CrossOrigin
    @RequestMapping(path = "/category/{id}/products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductEntity> getProductOfCategory(@PathVariable(name = "id", required = true) String id) {
        logger.info("ProductController: gets");
        return service.productOfCategory(Long.valueOf(id));
    }

    @CrossOrigin
    @RequestMapping(path = "/device/{id}/product", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductEntity getProductByDevice(@PathVariable(name = "id", required = true) String id) {
        DeviceEntity entity = deviceService.getDeviceByMacAddress(id);
        if (entity != null) {
            ProductEntity productEntity = service.readEntity(entity.getProductId());
            if (productEntity != null) {
                return productEntity;
            } else {
                productEntity = new ProductEntity();
                productEntity.setName("Product not found");
                return productEntity;
            }
        } else {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setName("Device not found");
            return productEntity;
        }
//        return null;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductEntity get(@PathVariable(name = "id", required = true) String id) {
        return service.readEntity(Long.valueOf(id));
    }

    @CrossOrigin
    @RequestMapping(path = "/product", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductEntity create(@RequestBody(required = true) ProductEntity task) {
        logger.info("TaskEntity: " + task);

        Long id = service.createEntity(task);
        task.setId(id);

        return task;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE, path = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(name = "id", required = true) String id) {
        service.deleteEntity(Long.valueOf(id));
    }

    @CrossOrigin
    @RequestMapping(path = "/product", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody(required = true) ProductEntity task) {
        logger.info("TaskEntity: " + task);

        service.updateEntity(task);

        List<DeviceEntity> devices = deviceService.getDeviceWithProduct(task.getId());
        if (devices != null && devices.size() > 0) {
            // Iterate through the results
            String url = "https://farmapi.agrhub.com/gateway/push";

            Gson gson = new Gson();

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-type", "application/x-www-form-urlencoded");

            String params;
            okhttp3.MediaType postType = okhttp3.MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

            PostProductDevice postData;
            for (DeviceEntity device : devices) {
                try {
                    postData = new PostProductDevice(device.getMacAddress(), task);

                    params = "data=" + URLEncoder.encode(gson.toJson(postData), "UTF-8");

                    URLUtil.POST(url, params, postType, headers, URLUtil.DEFAULT_TIMEOUT);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        return;
    }

}
