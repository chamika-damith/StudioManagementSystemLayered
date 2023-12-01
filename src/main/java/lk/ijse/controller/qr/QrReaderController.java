package lk.ijse.controller.qr;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import lk.ijse.controller.Order.OrderFormController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

public class QrReaderController implements Initializable {

    public AnchorPane mainPanel;
    public Label txtLable;
    public AnchorPane root;
    @FXML
    private Button btnEnd;

    @FXML
    private Button btnStart;

    @FXML
    private AnchorPane miniPanel;

    @FXML
    private TextArea txtArea;

    private Webcam webcam;
    private WebcamPanel webcamPanel;
    private boolean isReading = false;

    private static String id;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set whatever you want when starting the application
    }

    private boolean startWebcam() {
        if (webcam == null) {
            Dimension size = WebcamResolution.QVGA.getSize();
            webcam = Webcam.getDefault();
            webcam.setViewSize(size);

            webcamPanel = new WebcamPanel(webcam);
            webcamPanel.setPreferredSize(size);
            webcamPanel.setFPSDisplayed(true);
            webcamPanel.setMirrored(true);

            SwingNode swingNode = new SwingNode();
            swingNode.setContent(webcamPanel);

            miniPanel.getChildren().clear();
            miniPanel.getChildren().add(swingNode);
        }

        Thread thread = new Thread(() -> {
            while (isReading) {
                try {
                    Thread.sleep(1000);
                    BufferedImage image = webcam.getImage();
                    if (image != null) {
                        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
                        Result result = new MultiFormatReader().decode(binaryBitmap);
                        Platform.runLater(() -> {
                            if (result != null) {
                                webcam.close();
                                txtLable.setText(result.getText() + "\n");
                                id=result.getText();
                                new Alert(Alert.AlertType.INFORMATION, "Data Scanned Successfully!").showAndWait();
                                stopWebcam();
                            } else {
                                new Alert(Alert.AlertType.ERROR, "No Data Found!").showAndWait();
                            }
                        });
                    } else {
                        System.out.println("Image is null");
                    }
                } catch (NotFoundException | InterruptedException | RuntimeException ignored) {
                    // ignored
                }
            }
        });
        thread.start();
        return true;
    }

    private boolean stopWebcam() {
        if (webcamPanel != null) {
            webcamPanel.stop();
            webcamPanel = null;
        }
        if (webcam != null) {
            webcam.close();
            webcam = null;
        }
        return false;
    }

    public void txtAreaClearOnAction(ActionEvent actionEvent) {
        txtArea.clear();
    }

    public void StartOnAction(ActionEvent actionEvent) {
        isReading = (!isReading) ? startWebcam() : stopWebcam();
    }

    public void EndOnAction(ActionEvent actionEvent) {
        stopWebcam();
    }

}
