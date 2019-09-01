import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Main extends Application {

    public void start(Stage pStage) throws SocketException {
        Label label = new Label();
        Scene scene = new Scene(label,640,480);
        pStage.setScene(scene);
        pStage.show();
       DatagramSocket ds = new DatagramSocket(5000);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {

                    byte[] data = new byte[30000];
                    DatagramPacket dp = new DatagramPacket(data,data.length);
                    ds.receive(dp);
                    BufferedImage buf = ImageIO.read(new ByteArrayInputStream(data));
                    Image img = SwingFXUtils.toFXImage(buf,null);
                    label.setGraphic(new ImageView(img));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.playFromStart();
    }
}
