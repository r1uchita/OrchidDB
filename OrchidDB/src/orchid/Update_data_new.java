/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchid;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oracle_util.connection_oracle;
import static oracle_util.connection_oracle.conn;

/**
 * FXML Controller class
 *
 * @author Rupa
 */
public class Update_data_new implements Initializable {

    @FXML
    private VBox vbox;
    
        @FXML
    private AnchorPane ap;

    @FXML
    private JFXButton update_data_btn;

    JFXTextField[] textfields;
    Vector<String> col_names;
    String[] data;
    String table_name;

    Update_data_new(String table_name, Vector col_names, String[] data) {
        this.col_names = col_names;
        this.data = data;
        this.table_name = table_name;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        vbox.getChildren().remove(0, vbox.getChildren().size());
        textfields = new JFXTextField[col_names.size()];
        for (int i = 0; i < col_names.size(); i++) {
            textfields[i] = new JFXTextField();
            //textfields[i].setText(word[i]);
            textfields[i].setText(data[i]);
            textfields[i].setPrefHeight(35);
            textfields[i].setPrefWidth(280);          //  textfields[i].setPromptText(" pls Enter " + col_names.get(i));
            vbox.getChildren().add(textfields[i]);

        }
    }

    @FXML

    void update_values() throws SQLException {
        connection_oracle obj = new connection_oracle();
        javafx.stage.Window owner = update_data_btn.getScene().getWindow();
        String query = "update " + table_name + " set ";
        String values = "";
        String where = " where ";
        for (int i = 0; i < col_names.size(); i++) {
            query += col_names.get(i);
            query += "=";
            query += "?";
            if (i == col_names.size() - 1) {
            } else {
                query += " , ";
            }
        }
        query += " where ";

        for (int i = 0; i < data.length; i++) {
            JFXTextField j = (JFXTextField) vbox.getChildren().get(i);
            if (j.getText().isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!",
                        "Please Enter data");
            } else {

                query += col_names.get(i);
                query += "=";
                query += "?";
                if (i == col_names.size() - 1) {
                } else {
                    query += " and ";
                }
            }

        }
        try{
            connection_oracle obectj=new connection_oracle();
            obectj.get_DB_connection();
               PreparedStatement stmt;
            stmt = conn.prepareStatement(query);
            //System.out.println(query);             
            for(int i=1;i<=col_names.size();i++){
                     JFXTextField j = (JFXTextField) vbox.getChildren().get(i-1);

                stmt.setString(i,  j.getText());
            }
            for(int i=col_names.size()+1,j=0;j<col_names.size();i++,j++){
                stmt.setString(i, data[j]);
        
            }
            stmt.executeUpdate();
           AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, " Result!", "Sucess Recoreds is Updated");
             Stage cstage = (Stage) ap.getScene().getWindow();
              cstage.close();
        }
        catch(Exception e){
            AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Error!", e.toString());
        }
      
    }
}
