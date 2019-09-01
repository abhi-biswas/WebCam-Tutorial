import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Main {

    public static void main(String[] args) {
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640,480));
        webcam.open();
        while(true)
        {
            BufferedImage ima = webcam.getImage();
            ByteArrayOutputStream baos = new ByteArrayOutputStream(30000);
            try {
                ImageIO.write(ima,"JPG",baos);
                baos.flush();
                byte[] data = baos.toByteArray();
                DatagramSocket ds = new DatagramSocket();
                InetAddress ip = InetAddress.getByName("localhost");
                DatagramPacket dp = new DatagramPacket(data,data.length,ip,5000);
                ds.send(dp);
                ds.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
