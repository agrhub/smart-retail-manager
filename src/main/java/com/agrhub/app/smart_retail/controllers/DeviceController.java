package com.agrhub.app.smart_retail.controllers;

import com.agrhub.app.smart_retail.models.DeviceEntity;
import com.agrhub.app.smart_retail.models.DeviceTypeEntity;
import com.agrhub.app.smart_retail.models.DrawCommandEntity;
import com.agrhub.app.smart_retail.models.ProductEntity;
import com.agrhub.app.smart_retail.repositories.DeviceService;
import com.agrhub.app.smart_retail.repositories.DeviceTypeService;
import com.agrhub.app.smart_retail.repositories.DrawCommandService;
import com.agrhub.app.smart_retail.repositories.ProductService;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DeviceController extends BaseController {

    @Autowired
    DeviceService service;
    @Autowired
    DeviceTypeService deviceTypeService;
    @Autowired
    DrawCommandService drawCommandService;
    @Autowired
    ProductService productService;

    @CrossOrigin
    @RequestMapping(path = "/devices", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DeviceEntity> gets() {
        return service.listEntities(null);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/device/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeviceEntity get(@PathVariable(name = "id", required = true) String id) {
        return service.readEntity(Long.valueOf(id));
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/device/{id}/imageJson", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductEntity getImageJson(@PathVariable(name = "id", required = true) String id) {
        DeviceEntity entity = service.getDeviceByMacAddress(id);
        if (entity != null) {
            logger.info(entity.getName());
            return  productService.readEntity(entity.getProductId());
        }
        return null;
    }

//    @CrossOrigin
//    @RequestMapping(method = RequestMethod.GET, path = "/device/{id}/image", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public byte[] getImage(@PathVariable(name = "id", required = true) String id) {
//        DeviceEntity device = service.getDeviceByMacAddress(id);
//        if (device != null) {
//            DeviceTypeEntity type = deviceTypeService.readEntity(device.getType());
//            if (device.getProductId() < 0) {
//                int realWidth = device.getLCDWidth(type.getWidth());
//                int realHeight = device.getLCDHeight(type.getHeight());
//
//                int idx = 0;
//                byte[] returnData = new byte[realWidth * realHeight * (type.getRedMode() == 0 ? 1 : 2)];
//
//                if (DeviceEntity.DRAW_MODE_LEFT_RIGHT_BOTTOM_TOP.equals(device.getDrawMode())) {
//                    for (int j = realHeight - 1; j >= 0; j--) {
//                        for (int i = 0; i < realWidth; i++) {
//                            if (i == 1 || i == 3) {
//                                returnData[idx++] = (byte)Integer.parseInt("00000000", 2);
//                            } else {
//                                returnData[idx++] = (byte) Integer.parseInt("11111111", 2);
//                            }
//                        }
//                    }
//                } else {
//                    for (int i = 0; i < realWidth; i++) {
//                        for (int j = 0; j < realHeight; j++) {
//                            if (j == 1 || j == 3) {
//                                returnData[idx++] = (byte)Integer.parseInt("00000000", 2);
//                            } else {
//                                returnData[idx++] = (byte) Integer.parseInt("11111111", 2);
//                            }
//                        }
//                    }
//                }
//
//                if (type.getRedMode() != 0) {
//                    if (DeviceEntity.DRAW_MODE_LEFT_RIGHT_BOTTOM_TOP.equals(device.getDrawMode())) {
//                        for (int j = realHeight - 1; j >= 0; j--) {
//                            for (int i = 0; i < realWidth; i++) {
//                                if (i == 2 || i == 3) {
//                                    returnData[idx++] = (byte)Integer.parseInt("00000000", 2);
//                                } else {
//                                    returnData[idx++] = (byte) Integer.parseInt("11111111", 2);
//                                }
//                            }
//                        }
//                    } else {
//                        for (int i = 0; i < realWidth; i++) {
//                            for (int j = 0; j < realHeight; j++) {
//                                if (j == 2 || j == 3) {
//                                    returnData[idx++] = (byte)Integer.parseInt("00000000", 2);
//                                } else {
//                                    returnData[idx++] = (byte) Integer.parseInt("11111111", 2);
//                                }
//                            }
//                        }
//                    }
//                }
//                return returnData;
//            } else {
//                ProductEntity productEntity = productService.readEntity(device.getProductId());
//                if (type != null && productEntity != null) {
//                    try {
//                        int idx = 0;
//
//                        int realWidth = device.getLCDWidth(type.getWidth());
//                        int realHeight = device.getLCDHeight(type.getHeight());
//
//                        byte[] returnData = new byte[realWidth * realHeight * (type.getRedMode() == 0 ? 1 : 2)];
//
//                        BufferedImage image = buildImage(device, productEntity);
//                        byte[][] data = convertToLCDImageByte(image, realWidth, realHeight, type.getRedMode(), 0, device.getDrawMode());
//
//                        // copy layer 1 data
//                        returnData = this.copyData(data, returnData, idx, realWidth, realHeight, device.getDrawMode());
//
//                        if (type.getRedMode() != 0) {
//                            // copy layer 2 data
//                            data = convertToLCDImageByte(image, realWidth, realHeight, type.getRedMode(), 1, device.getDrawMode());
//                            returnData = this.copyData(data, returnData, idx, realWidth, realHeight, device.getDrawMode());
//                        }
//
//                        return returnData;
//                    } catch (Exception ex) {
//                        logger.error(ex.getMessage(), ex);
//                    }
//                }
//            }
//        }
//        return null;
//    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/device/{id}/image", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getImage(@PathVariable(name = "id", required = true) String id) {
        DeviceEntity entity = service.getDeviceByMacAddress(id);
        if (entity != null) {
            DeviceTypeEntity type = deviceTypeService.readEntity(entity.getType());
            if (entity.getProductId() < 0) {
                int drawMode = Long.parseLong("5651124426113024") == type.getId() ? 1 : 0;
                int realWidth = drawMode == 1 ? type.getWidth() / 8 : type.getWidth();
                int realHeight = drawMode == 1 ? type.getHeight() : type.getHeight() / 8;
                int idx = 0;

                byte[] returnData = new byte[realWidth * realHeight * (type.getRedMode() == 0 ? 1 : 2)];
                if (drawMode == 1) {
                    for (int j = realHeight - 1; j >= 0; j--) {
                        for (int i = 0; i < realWidth; i++) {
                            returnData[idx++] = (byte)(( i % (realWidth / 4)) % 2);
                        }
                    }
                } else {
                    for (int i = 0; i < realWidth; i++) {
                        for (int j = 0; j < realHeight; j++) {
                            returnData[idx++] = (byte)(( j % (realHeight / 4)) % 2);
                        }
                    }
                }

                if (type.getRedMode() != 0) {
                    if (drawMode == 1) {
                        for (int j = realHeight - 1; j >= 0; j--) {
                            for (int i = 0; i < realWidth; i++) {
                                returnData[idx++] = (byte)( i % (realWidth / 2));
                            }
                        }
                    } else {
                        for (int i = 0; i < realWidth; i++) {
                            for (int j = 0; j < realHeight; j++) {
                                returnData[idx++] = (byte)( j % (realHeight / 2));
                            }
                        }
                    }
                }

                return returnData;
            } else {
                ProductEntity productEntity = productService.readEntity(entity.getProductId());
                if (type != null && productEntity != null) {
                    try {
                        int drawMode = Long.parseLong("5651124426113024") == type.getId() ? 1 : 0;
                        BufferedImage image = buildImage(entity, productEntity);
                        byte[][] data = convertToLCDImageByte(image, type.getRedMode(), 0, drawMode);

                        int realWidth = drawMode == 1 ? image.getWidth() / 8 : image.getWidth();
                        int realHeight = drawMode == 1 ? image.getHeight() : image.getHeight() / 8;
                        int idx = 0;

                        byte[] returnData = new byte[realWidth * realHeight * (type.getRedMode() == 0 ? 1 : 2)];
                        if (drawMode == 1) {
                            for (int j = realHeight - 1; j >= 0; j--) {
                                for (int i = 0; i < realWidth; i++) {
                                    returnData[idx++] = data[i][j];
                                }
                            }
                        } else {
                            for (int i = 0; i < realWidth; i++) {
                                for (int j = 0; j < realHeight; j++) {
                                    returnData[idx++] = data[i][j];
                                }
                            }
                        }

                        if (type.getRedMode() != 0) {
                            data = convertToLCDImageByte(image, 1, 1, drawMode);
                            if (drawMode == 1) {
                                for (int j = realHeight - 1; j >= 0; j--) {
                                    for (int i = 0; i < realWidth; i++) {
                                        returnData[idx++] = data[i][j];
                                    }
                                }
                            } else {
                                for (int i = 0; i < realWidth; i++) {
                                    for (int j = 0; j < realHeight; j++) {
                                        returnData[idx++] = data[i][j];
                                    }
                                }
                            }
                        }

                        return returnData;
                    } catch (Exception ex) {
                        logger.error(ex.getMessage(), ex);
                    }
                }
            }
        }
        return null;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/device/{id}/imageFile", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageFile(@PathVariable(name = "id", required = true) String id) {
        DeviceEntity entity = service.getDeviceByMacAddress(id);
        if (entity != null) {
            logger.info(entity.getName());
            ProductEntity productEntity = productService.readEntity(entity.getProductId());
            if (productEntity != null) {
                try {
                    BufferedImage image = buildImage(entity, productEntity);

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ImageIO.write(image, "jpg", bos);

                    return bos.toByteArray();
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
        }
        try {
            File input = new File("WEB-INF/no-image.jpg");
            BufferedImage image = ImageIO.read(input);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", bos);

            return bos.toByteArray();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/device/{id}/imageHex", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getImageHex(@PathVariable(name = "id", required = true) String id) {
        DeviceEntity device = service.getDeviceByMacAddress(id);
        if (device != null) {
            DeviceTypeEntity type = deviceTypeService.readEntity(device.getType());
            if (device.getProductId() < 0) {
                try {
                    StringBuilder sb = new StringBuilder();

                    //BufferedImage image = buildImage(device, productEntity, sb);
                    BufferedImage image = ImageIO.read(new File("WEB-INF/tmp1.jpg"));

                    int realWidth = image.getWidth();
                    int realHeight = image.getHeight();
                    sb.append("RedMode: ").append(type.getRedMode());
                    sb.append("\nLayer: ").append(type.getRedMode() < 0 ? 1 : 0);
                    byte[][] data = convertToLCDImageByte(image, realWidth, realHeight, type.getRedMode(), type.getRedMode() < 0 ? 1 : 0, device.getDrawMode(), sb);

                    if (DeviceEntity.DRAW_MODE_LEFT_RIGHT_BOTTOM_TOP.equals(device.getDrawMode())) {
                        for (int j = realHeight - 1; j >= 0; j--) {
                            for (int i = 0; i < realWidth; i++) {
                                sb.append("0X").append(String.format("%02X", (data[i][j] & 0xFF))).append(",");
                            }
                        }
                    } else {
                        for (int i = 0; i < realWidth; i++) {
                            for (int j = 0; j < realHeight; j++) {
                                sb.append("0X").append(String.format("%02X", (data[i][j] & 0xFF))).append(",");
                            }
                        }
                    }

                    if (type.getRedMode() != 0) {
                        sb.append("\n\nRedMode: ").append(type.getRedMode());
                        sb.append("\nLayer: ").append(type.getRedMode() < 0 ? 0 : 1);
                        data = convertToLCDImageByte(image, realWidth, realHeight, type.getRedMode(), type.getRedMode() < 0 ? 0 : 1, device.getDrawMode(), sb);
                        if (DeviceEntity.DRAW_MODE_LEFT_RIGHT_BOTTOM_TOP.equals(device.getDrawMode())) {
                            for (int j = realHeight - 1; j >= 0; j--) {
                                for (int i = 0; i < realWidth; i++) {
                                    sb.append("0X").append(String.format("%02X", (data[i][j] & 0xFF))).append(",");
                                }
                            }
                        } else {
                            for (int i = 0; i < realWidth; i++) {
                                for (int j = 0; j < realHeight; j++) {
                                    sb.append("0X").append(String.format("%02X", (data[i][j] & 0xFF))).append(",");
                                }
                            }
                        }
                    }

                    // Readable version
                    if (sb.length() > 0) {
                        sb.setLength(sb.length() - 1);
                    }

                    return sb.toString();
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    return sw.toString(); // stack trace as a string
                }
            } else {
                ProductEntity productEntity = productService.readEntity(device.getProductId());
                if (type != null && productEntity != null) {
                    try {
                        StringBuilder sb = new StringBuilder();

                        int realWidth = device.getLCDWidth(type.getWidth());
                        int realHeight = device.getLCDHeight(type.getHeight());

                        //BufferedImage image = buildImage(device, productEntity, sb);
                        BufferedImage image = buildImage(device, productEntity);
                        byte[][] data = convertToLCDImageByte(image, realWidth, realHeight, type.getRedMode(), type.getRedMode() < 0 ? 1 : 0, device.getDrawMode());

                        if (DeviceEntity.DRAW_MODE_LEFT_RIGHT_BOTTOM_TOP.equals(device.getDrawMode())) {
                            for (int j = realHeight - 1; j >= 0; j--) {
                                for (int i = 0; i < realWidth; i++) {
                                    sb.append("0X").append(String.format("%02X", (data[i][j] & 0xFF))).append(",");
                                }
                            }
                        } else {
                            for (int i = 0; i < realWidth; i++) {
                                for (int j = 0; j < realHeight; j++) {
                                    sb.append("0X").append(String.format("%02X", (data[i][j] & 0xFF))).append(",");
                                }
                            }
                        }

                        if (type.getRedMode() != 0) {
                            data = convertToLCDImageByte(image, realWidth, realHeight, type.getRedMode(), type.getRedMode() < 0 ? 0 : 1, device.getDrawMode());
                            if (DeviceEntity.DRAW_MODE_LEFT_RIGHT_BOTTOM_TOP.equals(device.getDrawMode())) {
                                for (int j = realHeight - 1; j >= 0; j--) {
                                    for (int i = 0; i < realWidth; i++) {
                                        sb.append("0X").append(String.format("%02X", (data[i][j] & 0xFF))).append(",");
                                    }
                                }
                            } else {
                                for (int i = 0; i < realWidth; i++) {
                                    for (int j = 0; j < realHeight; j++) {
                                        sb.append("0X").append(String.format("%02X", (data[i][j] & 0xFF))).append(",");
                                    }
                                }
                            }
                        }

                        // Readable version
                        if (sb.length() > 0) {
                            sb.setLength(sb.length() - 1);
                        }

                        return sb.toString();
                    } catch (Exception ex) {
                        logger.error(ex.getMessage(), ex);
                        StringWriter sw = new StringWriter();
                        PrintWriter pw = new PrintWriter(sw);
                        ex.printStackTrace(pw);
                        return sw.toString(); // stack trace as a string
                    }
                }
            }
        }
        return null;
    }

    @CrossOrigin
    @RequestMapping(path = "/device", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DeviceEntity create(@RequestBody(required = true) DeviceEntity task) {
        logger.info("TaskEntity: " + task);

        Long id = service.createEntity(task);
        task.setId(id);

        return task;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE, path = "/device/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(name = "id", required = true) String id) {
        service.deleteEntity(Long.valueOf(id));
    }

    @CrossOrigin
    @RequestMapping(path = "/device", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody(required = true) DeviceEntity task) {
        logger.info("TaskEntity: " + task);

        service.updateEntity(task);

        return;
    }

    private BufferedImage buildImage(DeviceEntity device, ProductEntity product) throws Exception {
        return this.buildImage(device, product, null);
    }

    private BufferedImage buildImage(DeviceEntity device, ProductEntity product, StringBuilder builder) throws Exception {
        initFontConfig();
        registerFont("WEB-INF/OpenSans-Light.ttf");
        registerFont("WEB-INF/OpenSans-Regular.ttf");
        registerFont("WEB-INF/OpenSans-Bold.ttf");

        DeviceTypeEntity type = deviceTypeService.readEntity(device.getType());
        if (type == null) {
            return null;
        }

        final BufferedImage image = new BufferedImage(type.getWidth(), type.getHeight(), BufferedImage.TYPE_INT_RGB);
        final Graphics2D g2d = image.createGraphics();
        g2d.setPaint(Color.white);
        g2d.fillRect(0, 0, type.getWidth(), type.getHeight());

        List<DrawCommandEntity> commands = drawCommandService.listEntitiesWithDeviceType(type.getId());
        if (commands != null && commands.size() > 0) {
            for (DrawCommandEntity command : commands) {
                if (builder != null) {
                    builder.append("\nCommand type: ").append(command.getType());
                }
                if ("text".equals(command.getType())) {
                    drawText(g2d, product, command);
                } else if ("line".equals(command.getType())) {
                    drawLine(g2d, command);
                } else if ("qr_code".equals(command.getType())) {
                    drawQRCode(g2d, product, command);
                } else if ("image".equals(command.getType())) {
                    drawImage(g2d, command);
                } else if ("child_products".equals(command.getType())) {
                    drawChildProducts(g2d, product, command, builder);
                }
            }
        }

        g2d.dispose();

        return image;
    }

    private void drawText(Graphics2D g, ProductEntity product, DrawCommandEntity command) {
        int width = command.getX2() - command.getX1();
        int height = command.getY2() - command.getY1();
        if ("black".equals(command.getBackgroundColor())) {
            g.setPaint(Color.black);
            g.fillRect(command.getX1(), command.getY1(), width, height);
        } else if ("red".equals(command.getBackgroundColor())) {
            g.setPaint(Color.red);
            g.fillRect(command.getX1(), command.getY1(), width, height);
        }

        String val = command.getValFromProduct(product);

        Font font = new Font(command.getFontName(), command.isFontBold() ? Font.BOLD : Font.PLAIN, command.getFontSize());

        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);

        int displayWidth = command.getX2() - command.getX1();
        if (metrics.stringWidth(val) > displayWidth) {
            Font minFont = new Font(command.getFontName(), command.isFontBold() ? Font.BOLD : Font.PLAIN, command.getMinFontSize());
            // Get the MinFontMetrics
            FontMetrics minMetrics = g.getFontMetrics(minFont);

            if (minMetrics.stringWidth(val) > displayWidth) {
                // truncate string
                font = minFont;
                metrics = g.getFontMetrics(font);

                while (val.length() > 4) {
                    val = val.substring(0, val.length() - 4) + "...";
                    if (minMetrics.stringWidth(val) <= displayWidth) {
                        break;
                    }
                }
            } else {
                // calculator font size
                int fontSize = (int) Math.floor((displayWidth - minMetrics.stringWidth(val))
                        * (command.getFontSize() - command.getMinFontSize())
                        / (metrics.stringWidth(val) - minMetrics.stringWidth(val))) + command.getMinFontSize();

                font = new Font(command.getFontName(), command.isFontBold() ? Font.BOLD : Font.PLAIN, fontSize);
                metrics = g.getFontMetrics(font);
            }
        }
        g.setFont(font);

        // Determine the X coordinate for the text
        int xVal = command.getX1() + (width - metrics.stringWidth(val)) / 2;
        if (command.getAlign() < 0) {
            xVal = command.getX1();
        } else if (command.getAlign() > 0) {
            xVal = command.getX1() + width - metrics.stringWidth(val);
        }
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int yVal = command.getY1() + ((height - metrics.getHeight()) / 2) + metrics.getAscent();
        if (command.getVerticalAlign() < 0) {
            yVal = command.getY2();
        } else if (command.getVerticalAlign() > 0) {
            yVal = command.getY1() + metrics.getHeight() - (metrics.getAscent() / 2);
        }

        g.setPaint(command.getColorObj());
        g.drawString(val, xVal, yVal);
    }

    private void drawLine(Graphics2D g, DrawCommandEntity command) {
        g.setPaint(command.getColorObj());
        g.setStroke(new BasicStroke(command.getStroke()));
        g.drawLine(command.getX1(), command.getY1(), command.getX2(), command.getY2());
    }

    private void drawQRCode(Graphics2D g, ProductEntity product, DrawCommandEntity command) throws Exception {
        String val = command.getValFromProduct(product);

        // Create new configuration that specifies the error correction
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(val, BarcodeFormat.QR_CODE, command.getX2() - command.getX1(), command.getY2() - command.getY1(), hints);
        // Build QRCode image
        int widthOfQRCodeImage = bitMatrix.getWidth();
        int heightOfQRCodeImage = bitMatrix.getHeight();
        BufferedImage qrCode = new BufferedImage(widthOfQRCodeImage, heightOfQRCodeImage, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < widthOfQRCodeImage; i++) {
            for (int j = 0; j < heightOfQRCodeImage; j++) {
                qrCode.setRGB(i, j, bitMatrix.get(i, j) ? command.getColorObj().getRGB() : command.getBackgroundColorObj().getRGB());
            }
        }
        g.drawImage(qrCode, command.getX1(), command.getY1(), null);
    }

    private void drawImage(Graphics2D g, DrawCommandEntity command) throws Exception {
        String val = command.getVal();

        BufferedImage img = ImageIO.read(new File(val));

        int drawWidth = command.getX2() - command.getX1();
        int drawHeight = command.getY2() - command.getY1();
        if (img.getWidth() > drawWidth || img.getHeight() > drawHeight) {
            // scale image
            img = this.scaleImage(img, drawWidth, drawHeight);
        }

        g.drawImage(img, command.getX1(), command.getY1(), null);
    }

    private BufferedImage scaleImage(BufferedImage img, int width, int height) {
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();
        if (imgWidth*height < imgHeight*width) {
            width = imgWidth*height/imgHeight;
        } else {
            height = imgHeight*width/imgWidth;
        }
        BufferedImage newImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.clearRect(0, 0, width, height);
            g.drawImage(img, 0, 0, width, height, null);
        } finally {
            g.dispose();
        }
        return newImage;
    }

    private void drawChildProducts(Graphics2D g, ProductEntity product, DrawCommandEntity command) throws Exception {
        this.drawChildProducts(g, product, command, null);
    }

    private void drawChildProducts(Graphics2D g, ProductEntity product, DrawCommandEntity command, StringBuilder builder) throws Exception {
        int width = command.getX2() - command.getX1();
        int height = command.getY2() - command.getY1();

        if ("black".equals(command.getBackgroundColor())) {
            g.setPaint(Color.black);
            g.fillRect(command.getX1(), command.getY1(), width, height);
        } else if ("red".equals(command.getBackgroundColor())) {
            g.setPaint(Color.red);
            g.fillRect(command.getX1(), command.getY1(), width, height);
        }

        Gson gson = new Gson();
        int sizeOfChildItem = product.getChildProductsObj() == null ? 0 : product.getChildProductsObj().size();
        if (builder != null) {
            builder.append("\nProduct: ").append(gson.toJson(product));
        }
        if (builder != null) {
            builder.append("\nChild item size: ").append(sizeOfChildItem);
        }
        if (sizeOfChildItem > 0) {
            int itemHeight = Math.round(height / sizeOfChildItem);
            if (builder != null) {
                builder.append("\nItem row height: ").append(itemHeight);
            }
            List<DrawCommandEntity> itemCommands = drawCommandService.listEntitiesWithDeviceType(command.getId());
            if (builder != null) {
                builder.append("\nChild commands: ").append(gson.toJson(itemCommands));
            }

            // loop child product
            ProductEntity childProduct;
            DrawCommandEntity cmd;
            int textItemCount;
            for (int i = 0; i < sizeOfChildItem; i++) {
                textItemCount = 0;
                childProduct = product.getChildProductsObj().get(i);
                for (DrawCommandEntity commandEntity : itemCommands) {
                    cmd = new DrawCommandEntity(commandEntity);
                    if ("text".equals(cmd.getType())) {
                        cmd.setX1(command.getX1() + cmd.getX1());
                        cmd.setY1(command.getY1() + (i * itemHeight) + (textItemCount * Math.round(itemHeight / 2)) + cmd.getY1());
                        cmd.setX2(command.getX2() - cmd.getX2());
                        cmd.setY2(command.getY1() + (i * itemHeight) + (textItemCount * Math.round(itemHeight / 2)) + Math.round(itemHeight / 2) - cmd.getY2());
                        if (builder != null) {
                            builder.append("\nCommand: ").append(gson.toJson(cmd));
                        }
                        drawText(g, childProduct, cmd);

                        textItemCount++;
                    } else if ("line".equals(cmd.getType()) && i < (sizeOfChildItem - 1)) {
                        cmd.setX1(command.getX1() + cmd.getX1());
                        cmd.setY1(command.getY1() + (i * itemHeight) + itemHeight);
                        cmd.setX2(command.getX2() - cmd.getX2());
                        cmd.setY2(command.getY1() + (i * itemHeight) + itemHeight);
                        if (builder != null) {
                            builder.append("\nCommand: ").append(gson.toJson(cmd));
                        }
                        drawLine(g, cmd);
                    }
                }
            }
        }
    }

    /**
     * Convert image to LCD image data
     * @param image src image
     * @param mode 0: White/Black; 1: White/Black/Red
     * @param layerMode 0: Black layer; 1: Red layer
     * @param drawMode 0: Bottom to top, left to right; 1: Left to right, bottom to top
     * @return
     */
    private byte[][] convertToLCDImageByte(BufferedImage image, int width, int height, int mode, int layerMode, String drawMode) {
        return this.convertToLCDImageByte(image, width, height, mode, layerMode, drawMode, null);
    }

    private byte[][] convertToLCDImageByte(BufferedImage image, int width, int height, int mode, int layerMode, String drawMode, StringBuilder builder) {
        byte[][] data = new byte[width][height];
        int tmpX, tmpY, r, g, b, point, gray;
        boolean isRed;
        StringBuilder bitBuilder;
        if (DeviceEntity.DRAW_MODE_LEFT_RIGHT_BOTTOM_TOP.equals(drawMode)) {
            for (int j = height - 1; j >= 0; j--) {
                for (int i = 0; i < width; i++) {
                    bitBuilder = new StringBuilder();
                    for (int k = 0; k < 8; k++) {
                        isRed = false;
                        try {
                            tmpX = i * 8 + k;
                            point = image.getRGB(tmpX, j);
    
                            r = (point >> 16) & 0xff;
                            g = (point >> 8) & 0xff;
                            b = (point & 0xff);
    
                            isRed = r > 127 && g < 127 && b < 127;
    
                            gray = (r + g + b) / 3;
                        } catch (Exception e) {
                            gray = 255;
                        }
                        this.appendVal(bitBuilder, mode, layerMode, gray, isRed);
                        if (builder != null) {
                            builder.append("\nDevice mode: ").append(mode).append(" | Layer: ").append(layerMode).append(" | Gray: ").append(gray).append(" | Red: ").append(isRed).append(bitBuilder.toString());
                        }
                    }
                    data[i][j] = (byte)Integer.parseInt(bitBuilder.toString(), 2);
                }
            }
        } else {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bitBuilder = new StringBuilder();
                    for (int k = 0; k <= 8; k++) {
                        isRed = false;
                        try {
                            tmpY = j * 8 + k;
                            point = image.getRGB(i, tmpY);
    
                            r = (point >> 16) & 0xff;
                            g = (point >> 8) & 0xff;
                            b = (point & 0xff);
    
                            isRed = r > 127 && g < 127 && b < 127;
    
                            gray = (r + g + b) / 3;
                        } catch (Exception e) {
                            gray = 255;
                        }
                        this.appendVal(bitBuilder, mode, layerMode, gray, isRed);
                        if (builder != null) {
                            builder.append("\nDevice mode: ").append(mode).append(" | Layer: ").append(layerMode).append(" | Gray: ").append(gray).append(" | Red: ").append(isRed).append(bitBuilder.toString());
                        }
                    }
                    data[i][j] = (byte)Integer.parseInt(bitBuilder.toString(), 2);
                }
            }
        }
        return data;
    }

    private byte[] copyData(byte[][] src, byte[] desc, int fromIdx, int width, int height, String mode) {
        int idx = fromIdx;
        if (DeviceEntity.DRAW_MODE_LEFT_RIGHT_BOTTOM_TOP.equals(mode)) {
            for (int j = height - 1; j >= 0; j--) {
                for (int i = 0; i < width; i++) {
                    desc[idx++] = src[i][j];
                }
            }
        } else {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    desc[idx++] = src[i][j];
                }
            }
        }
        return desc;
    }

    /**
     * Append bit to byte
     * @param builder byte builder string
     * @param screenColorMode 0: White/Black; 1: White/Black/Red
     * @param layerMode 0: Black layer; 1: Red layer
     * @param grayVal gray value
     * @param redVal is red
     * @return
     */
    private void appendVal(StringBuilder builder, int screenColorMode, int layerMode, int grayVal, boolean redVal) {
        // White/Black/Red mode
        if (screenColorMode != 0) {
            // Red layer
            if (layerMode != 0) {
                if (redVal) {
                    builder.append("1");
                } else {
                    builder.append("0");
                }
            } else {
                if (grayVal > 127) {
                    builder.append("0");
                } else {
                    builder.append("1");
                }
            }
        } else {
            if (grayVal > 127) {
                builder.append("1");
            } else {
                builder.append("0");
            }
        }
    }

    /**
     * Convert image to LCD image data
     * @param image src image
     * @param mode 0: White/Black; 1: White/Black/Red
     * @param color 0: Black layer; 1: Red layer
     * @param drawMode 0: Bottom to top, left to right; 1: Left to right, bottom to top
     * @return
     */
    private byte[][] convertToLCDImageByte(BufferedImage image, int mode, int color, int drawMode) {
        int realWidth = drawMode == 1 ? image.getWidth() / 8 : image.getWidth();
        int realHeight = drawMode == 1 ? image.getHeight() : image.getHeight() / 8;

        byte[][] data = new byte[realWidth][realHeight];
        int tmpX, tmpY, r, g, b, point, gray;
        boolean isRed, needNot;
        StringBuilder bitBuilder;
        if (drawMode == 1) {
            for (int j = realHeight - 1; j >= 0; j--) {
                for (int i = 0; i < realWidth; i++) {
                    needNot = false;
                    bitBuilder = new StringBuilder();
                    for (int k = 0; k < 8; k++) {
                        isRed = false;
                        try {
                            tmpX = i * 8 + k;
                            point = image.getRGB(tmpX, j);

                            r = (point >> 16) & 0xff;
                            g = (point >> 8) & 0xff;
                            b = (point & 0xff);

                            isRed = r > 127 && g < 127 && b < 127;

                            gray = (r + g + b) / 3;
                        } catch (Exception e) {
                            gray = 255;
                        }
                        // White/Black/Red mode
                        if (mode == 1) {
                            // Red layer
                            if (color == 1) {
                                needNot = true;
                                if (isRed) {
                                    bitBuilder.append("0");
                                } else if (gray > 127) {
                                    bitBuilder.append("1");
                                } else {
                                    bitBuilder.append("1");
                                }
                            } else {
                                if (isRed) {
                                    bitBuilder.append("1");
                                } else if (gray > 127) {
                                    bitBuilder.append("1");
                                } else {
                                    bitBuilder.append("0");
                                }
                            }
                        } else {
                            if (gray > 127) {
                                bitBuilder.append("1");
                            } else {
                                bitBuilder.append("0");
                            }
                        }
                    }
                    data[i][j] = (byte)(needNot ? ~Integer.parseInt(bitBuilder.toString(), 2) : Integer.parseInt(bitBuilder.toString(), 2));
                }
            }
        } else {
            for (int i = 0; i < realWidth; i++) {
                for (int j = 0; j < realHeight; j++) {
                    needNot = false;
                    bitBuilder = new StringBuilder();
                    for (int k = 0; k <= 8; k++) {
                        isRed = false;
                        try {
                            tmpY = j * 8 + k;
                            point = image.getRGB(i, tmpY);

                            r = (point >> 16) & 0xff;
                            g = (point >> 8) & 0xff;
                            b = (point & 0xff);

                            isRed = r > 127 && g < 127 && b < 127;

                            gray = (r + g + b) / 3;
                        } catch (Exception e) {
                            gray = 255;
                        }
                        // White/Black/Red mode
                        if (mode == 1) {
                            // Red layer
                            if (color == 1) {
                                needNot = true;
                                if (isRed) {
                                    bitBuilder.append("0");
                                } else if (gray > 127) {
                                    bitBuilder.append("1");
                                } else {
                                    bitBuilder.append("1");
                                }
                            } else {
                                if (isRed) {
                                    bitBuilder.append("1");
                                } else if (gray > 127) {
                                    bitBuilder.append("1");
                                } else {
                                    bitBuilder.append("0");
                                }
                            }
                        } else {
                            if (gray > 127) {
                                bitBuilder.append("1");
                            } else {
                                bitBuilder.append("0");
                            }
                        }
                    }
                    data[i][j] = (byte)(needNot ? ~Integer.parseInt(bitBuilder.toString(), 2) : Integer.parseInt(bitBuilder.toString(), 2));
                }
            }
        }
        return data;
    }
}
