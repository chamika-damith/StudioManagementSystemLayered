package lk.ijse.controller.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QrGenerator {
    private String data;
    private String path;
    public void setData(String data) {
        this.data = data;
    }
    public String getPath() {
        return path;
    }

    public void getGenerator(String data) throws IOException, WriterException {

        data = data.replaceAll("\\r\\n|\\r|\\n", "_");

        path = "C:/Users/Chamika/OneDrive/Desktop/qr/" + data + ".png";
        BitMatrix encode = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 200, 200);
        Path path1 = Paths.get(path);
        MatrixToImageWriter.writeToPath(encode, path.substring(path.lastIndexOf('.') + 1), path1);
        Image image=new Image("/Icon/iconsOk.png");
        try {
            Notifications notifications=Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("QR Code Generate Success ");
            notifications.title("Success");
            notifications.hideAfter(Duration.seconds(4));
            notifications.position(Pos.TOP_RIGHT);
            notifications.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getGeneratorBarcode(String data) throws IOException, WriterException {

        data = data.replaceAll("\\r\\n|\\r|\\n", "_");

        path = "C:/Users/Chamika/OneDrive/Desktop/qr/BarCode/" + data + ".png";
        BitMatrix encode = new MultiFormatWriter().encode(data, BarcodeFormat.CODE_128, 200, 100);
        Path path1 = Paths.get(path);
        MatrixToImageWriter.writeToPath(encode, path.substring(path.lastIndexOf('.') + 1), path1);

        Image image=new Image("/Icon/iconsOk.png");
        try {
            Notifications notifications=Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("Bar Code Generate Success ");
            notifications.title("Success");
            notifications.hideAfter(Duration.seconds(4));
            notifications.position(Pos.TOP_RIGHT);
            notifications.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
