/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchid;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Deepak
 */
public class call_fxml_page {

    void open_fxml(String fxml_name, String title) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(fxml_name));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.show();

    }
}
