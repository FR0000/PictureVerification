package reconquista.internet.util;

import reconquista.internet.thread.FileDestroyThread;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class Utils {

    private static File fetchFromURL(String urlAsString) throws IOException {
        URL url = new URL(urlAsString);
        HttpURLConnection connection = (HttpURLConnection) url
                .openConnection();
        connection.setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
        BufferedImage image = ImageIO.read(connection.getInputStream());
        BufferedImage newImage = new BufferedImage(image.getWidth(),
                image.getHeight(), BufferedImage.TYPE_INT_RGB);
        newImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
        File file = new File(UUID.randomUUID().toString() + ".jpg");
        ImageIO.write(newImage, "jpg", file);
        connection.disconnect();
        return file;
    }

    public static File fromURL(String url) {
        try {
            return fetchFromURL(url);
        } catch (IOException ignored) {
        }
        return null;
    }

    public static void destroyFile(File file) {
        new FileDestroyThread(file).start();
    }

}
