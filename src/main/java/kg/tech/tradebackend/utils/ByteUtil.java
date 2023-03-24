package kg.tech.tradebackend.utils;

import lombok.experimental.UtilityClass;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Iterator;

@UtilityClass
public class ByteUtil {

    public static void compressImage(InputStream is, OutputStream os, String format, float quality) throws IOException {
        BufferedImage image = ImageIO.read(is);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(format);

        if (!writers.hasNext())
            throw new IllegalStateException("No writers found");

        ImageWriter writer = writers.next();

        try (ImageOutputStream ios = ImageIO.createImageOutputStream(os)) {
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(quality);
            }

            writer.write(null, new IIOImage(image, null, null), param);
        } finally {
            writer.dispose();
        }
    }

    public static void compressImage(InputStream is, OutputStream os, String format) throws IOException {
        compressImage(is, os, format, 0.2f);
    }

    public static byte[] toByte(String base64) {
        byte[] rawFileData = new byte[0];
        if (base64 != null) {
            rawFileData = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
        }
        return rawFileData;
    }
}
