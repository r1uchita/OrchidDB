
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchid;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.sun.javaws.Main;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Text;
import java.io.IOException;
import oracle_util.connection_oracle;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import static javafx.scene.paint.Color.color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import static orchid.captcha.randomString;

/**
 *
 * @author Ruchita
 */
public class Login implements Initializable {

    @FXML
    private AnchorPane ap;

    @FXML
    private Label sun_label;

    @FXML
    private Label moon_label;

    @FXML
    private JFXTextField user_name_textfield;

    @FXML
    private JFXToggleButton them_changer;

    @FXML
    private JFXPasswordField password_textfield;

    @FXML
    private JFXButton connect_button;
    @FXML
    private Text captcha_label;

    @FXML
    private JFXTextField captch_textfield;

    @FXML
    private JFXButton reload_captcha;

    @FXML
    void connect_me() throws SQLException, IOException {
        DropShadow shadow = new DropShadow();
        connect_button.setEffect(shadow);
        String user_name = user_name_textfield.getText();
        String password = password_textfield.getText();
        Window owner = connect_button.getScene().getWindow();
        if (user_name_textfield.getText().isEmpty() | password_textfield.getText().isEmpty() | captch_textfield.getText().isEmpty()) {
            if (user_name_textfield.getText().isEmpty() | password_textfield.getText().isEmpty()) {

                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Error!",
                        "Please enter  Data");
                return;
            }
            if (captch_textfield.getText().isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Error!",
                        "Please Captcha");
                return;
            }

        } else {
            connection_oracle obj = new connection_oracle();
            obj.userid = user_name;
            obj.password = password;
            String s = obj.get_DB_connection();
            if (!captch_textfield.getText().toString().equalsIgnoreCase(captcha_label.getText().toString())) {

                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Error!",
                        "Please enter  correct Captch!!");
                return;
            } else {
                if (s.equals("sucess")) {
                    AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, " Result", "Openning project ");
                    Stage cstage = (Stage) ap.getScene().getWindow();
                    cstage.close();
                    Parent root = FXMLLoader.load(getClass().getResource("Menu_Bar.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                //stage.initModality(Modality.APPLICATION_MODAL);
                    // stage.initStyle(StageStyle.UTILITY);
                    stage.setOnCloseRequest(null);
                    stage.setTitle("DashBoard");
                    stage.setResizable(false);
                    stage.show();

                } else {
                    AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warnning", s);
                }

            }
        }

    }

    @FXML
    void theam_change() {

        if (!them_changer.isSelected()) {

            Image image = new Image(getClass().getResourceAsStream("moon.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            moon_label.setGraphic(imageView);
            moon_label.setVisible(true);
            sun_label.setVisible(false);
            //Object o=  connect_button.getScene().getStylesheets().add("dark_them.css");

        } else {
            Image image = new Image(getClass().getResourceAsStream("sun.png"));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            sun_label.setGraphic(imageView);
            sun_label.setVisible(true);
            moon_label.setVisible(false);
            //Object  o=them_changer.getScene().getStylesheets().get(0);
            //  connect_button.getScene().getStylesheets().remove(o);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO0
        ORCHID obj1 = new ORCHID();
        if (!ORCHID.issplahloaded) {
            ORCHID.issplahloaded = true;
            loadSplashScreen();
        }
        captcha captcha1 = null;
        randomString(8);

        captcha_label.setText(captcha.randomString(8));
        captcha_label.setStyle("letter-spacing: 2px");
        connection_oracle obj = new connection_oracle();
        them_changer.setSelected(true);
        //connect_button.setStyle("-fx-border-radius: 30;");
        Image image = new Image(getClass().getResourceAsStream("sun.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        sun_label.setGraphic(imageView);
        //moon_label.setDisable(true);
        moon_label.setVisible(false);
        them_changer.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                // scene.getStylesheets().add(Client.class.getResource("main.css").toExternalForm());
                //them_changer.getScene().getStylesheets().add("dark_them.css");

            } else {
                them_changer.getScene().getStylesheets().remove("dark_them.css");
            }
        });
    }

    private void loadSplashScreen() {
        try {
            //Load splash screen view FXML
            AnchorPane pane = FXMLLoader.load(getClass().getResource(("splash_screen.fxml")));
            //Add it to root container (Can be StackPane, AnchorPane etc)
            ap.getChildren().setAll(pane);

            //Load splash screen with fade in effect
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            //Finish splash with fade out effect
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            //After fade in, start fade out
            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            //After fade out, load actual content
            fadeOut.setOnFinished((e) -> {
                try {
                    AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("Login.fxml")));
                    ap.getChildren().setAll(parentContent);

                } catch (IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void load_captcha() {
        System.out.println("infunction");
        captcha captcha = new captcha();
        captcha_label.setText(randomString(8));
        System.out.println(captcha_label.getText());
    }

}

class captcha {

    public static final String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static Random RANDOM = new Random();

    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++) {
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }

        return sb.toString();
    }
}
