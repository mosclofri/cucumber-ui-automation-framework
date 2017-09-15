package com.support.framework.support;

import com.support.framework.base.AbstractBase;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.support.framework.support.CmdLine.executeShellCommand;
import static com.support.framework.support.Property.COMPARE_IMAGE;
import static com.support.framework.support.Property.DEVICE_NAME;
import static com.support.framework.support.Property.PLATFORM_NAME;
import static com.support.framework.support.Util.threadWait;
import static org.junit.Assert.fail;

public class ImageCompare {

    private static final Logger LOG = Logger.getLogger(ImageCompare.class);
    private static final double COLOR_THRESHOLD = 0.5;
    private static final int DISTANCE_THRESHOLD = 25;
    private static LinkedList<Rectangle> rectangles = new LinkedList<>();

    public static void compareImage(WebDriver driver, int errorThreshold) {
        String currentMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Pattern p = Pattern.compile("given|and|when|then");
        Matcher m = p.matcher(currentMethodName);
        if (m.find()) {
            currentMethodName = currentMethodName.substring(m.end());
        }
        compareImage(driver, errorThreshold, currentMethodName);
    }

    public static void compareImage(WebDriver driver, int errorThreshold, String expectedImage) {
        //ToDo Add Browser size control for Selenium and separate into smaller methods
        File screenShot;
        String deviceName;

        if (COMPARE_IMAGE.toString().matches("true|record")) {
            if (PLATFORM_NAME.toString().equalsIgnoreCase("android")) {
                deviceName = executeShellCommand("adb -s " + DEVICE_NAME.toString() + " shell getprop ro.product.model");
            } else {
                deviceName = DEVICE_NAME.toString();
            }
            screenShot = new File("./src/test/resources/expected_images/" + deviceName + "/" + expectedImage + ".png");
        } else {
            return;
        }

        if (COMPARE_IMAGE.toString().matches("true")) {
            if (!screenShot.exists()) {
                LOG.warn("There is no existing image for : " + expectedImage + ".png for device: " + deviceName);
                return;
            }
            LOG.info("Comparing current screen with: '" + expectedImage + "' with using given threshold value: " + errorThreshold);
            ImageCompare imageCompare = new ImageCompare();

            BufferedImage imgInput1 = loadImage(screenShot.getPath());
            threadWait(0.25);
            BufferedImage imgInput2 = loadImage(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).getAbsolutePath());
            assert imgInput1 != null;
            assert imgInput2 != null;

            int width = imgInput1.getWidth();
            int differenceCounter = 0;
            Color black = new Color(0, 0, 0);
            Color white = new Color(255, 255, 255);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < Math.min(imgInput1.getHeight(), imgInput2.getHeight()); y++) {
                    Color color1 = new Color(imgInput1.getRGB(x, y));
                    Color color2 = new Color(imgInput2.getRGB(x, y));

                    int differenceRed = Math.abs(color1.getRed() - color2.getRed());
                    int differenceGreen = Math.abs(color1.getGreen() - color2.getGreen());
                    int differenceBlue = Math.abs(color1.getBlue() - color2.getBlue());
                    int difference = differenceRed + differenceGreen + differenceBlue;

                    double relativeDifference = (double) difference / (256 * 3);

                    if (relativeDifference > ImageCompare.COLOR_THRESHOLD) {
                        imgInput1.setRGB(x, y, black.getRGB());
                        addToRectangles(x, y);
                        differenceCounter++;
                    } else {
                        imgInput1.setRGB(x, y, white.getRGB());
                    }
                }
            }

            int differenceThreshold = differenceCounter / width;
            if (differenceThreshold > errorThreshold) {
                LOG.info(differenceThreshold + " differences found that is greater than given threshold value: " + errorThreshold);
                AbstractBase.scenario.embed(saveByteImage(imgInput1), "image/png");
                fail(differenceThreshold + " differences found that is greater than given threshold value: " + errorThreshold);
            }
        }

        if (COMPARE_IMAGE.toString().matches("record")) {
            threadWait(0.25);
            File deviceScreen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            if (!screenShot.exists()) {
                try {
                    LOG.info("Saving current screen as: " + expectedImage + ".png for device:" + deviceName);
                    FileUtils.copyFile(deviceScreen, new File(screenShot.getAbsolutePath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                LOG.info("There is already saved screenShot: " + expectedImage + ".png for device: " + deviceName);
            }
        }
    }

    private static void addToRectangles(int x, int y) {
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

    private static Rectangle findRectangleNearby(int x, int y) {
        for (Rectangle r : rectangles) {
            if (x > r.x - DISTANCE_THRESHOLD && y > r.y - DISTANCE_THRESHOLD && x < r.x + r.width + DISTANCE_THRESHOLD && y < r.y + r.height + DISTANCE_THRESHOLD)
                return r;
        }
        return null;
    }

    private static BufferedImage loadImage(String input) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(input));
            return bufferedImage;
        } catch (IOException e) {
            System.err.println("ERROR: could not load " + input);
        }
        return null;
    }

    private static byte[] saveByteImage(BufferedImage bufferedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
}

