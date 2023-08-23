package com.agrhub.app.smart_retail.utils;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class BlackAndWhiteImage extends BufferedImage {
    private static final int DEFAULT_THRESHOLD = 110;

    public static BlackAndWhiteImage make(final BufferedImage image, final int threshold)
    {
        return toBlackAndWhite(image, threshold);
    }

    public static BlackAndWhiteImage make(final BufferedImage image)
    {
        return make(image, DEFAULT_THRESHOLD);
    }

    /* Private constructor to create underlying BufferedImage */
    private BlackAndWhiteImage(ColorModel colourModel, WritableRaster raster, boolean alphaPremult)
    {
        super(colourModel, raster, alphaPremult, null);
    }

    private static BlackAndWhiteImage toBlackAndWhite(final BufferedImage image, final int threshold)
    {
        /* Conversion stuff is all the same */
        return new BlackAndWhiteImage( image.getColorModel(),
                image.getRaster(),
                image.isAlphaPremultiplied() );
    }
}
