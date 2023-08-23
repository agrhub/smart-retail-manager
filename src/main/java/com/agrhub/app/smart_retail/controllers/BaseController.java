package com.agrhub.app.smart_retail.controllers;

import com.agrhub.app.smart_retail.models.ErrorResponseModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.awt.*;
import java.io.File;


public class BaseController {

    protected final Logger logger = LogManager.getLogger(BaseController.class);

    @ExceptionHandler({ OurApplicationException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            OurApplicationException ex, WebRequest request) {
        return new ResponseEntity<Object>(new ErrorResponseModel(ex.getStatus().toString(), ex.getMessage()), new HttpHeaders(), ex.getStatus());
    }

    protected void initFontConfig() {
        String fontConfig = System.getProperty("java.home")
                + File.separator + "lib"
                + File.separator + "fontconfig.Prodimage.properties";
        if (new File(fontConfig).exists()) {
            System.setProperty("sun.awt.fontconfig", fontConfig);
        }
    }

    protected void registerFont(String fontPath) throws Exception {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)));
    }
}
