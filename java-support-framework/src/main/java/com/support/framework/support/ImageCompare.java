package com.support.framework.support;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class ImageCompare {

    public static final double colorThreshold = 0.5;
    private static final int distanceThreshold = 25;
    private LinkedList<Rectangle> rectangles = new LinkedList<>();

    public void addToRectangles(int x, int y) {
        Rectangle rectangle = findRectangleNearby(x, y);

        if (rectangle == null) {
            rectangles.add(new Rectangle(x, y, 1, 1));
        } else {
            if (x > rectangle.x + rectangle.width) {
                rectangle.width += x - rectangle.x - rectangle.width + 1;
            } else if (x < rectangle.x) {
                rectangle.width += Math.abs(rectangle.x - x) + 1;
                rectangle.x = x;
            }
            if (y > rectangle.y + rectangle.height) {
                rectangle.height += y - rectangle.y - rectangle.height + 1;
            } else if (y < rectangle.y) {
                rectangle.height += Math.abs(rectangle.y - y) + 1;
                rectangle.y = y;
            }
        }
    }

    public BufferedImage loadImage(String input) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(input));
            return bufferedImage;
        } catch (IOException e) {
            System.err.println("ERROR: could not load " + input);
        }
        return null;
    }

    public byte[] saveByteImage(BufferedImage bufferedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    private Rectangle findRectangleNearby(int x, int y) {
        for (Rectangle r : rectangles) {
            if (x > r.x - distanceThreshold && y > r.y - distanceThreshold && x < r.x + r.width + distanceThreshold && y < r.y + r.height + distanceThreshold)
                return r;
        }
        return null;
    }

}
