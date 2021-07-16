package orchid;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Callback;
import oracle_util.connection_oracle;

/**
 * FXML Controller class
 *
 * @author Deepak
 */
public class Update_Record implements Initializable {

    private ObservableList<ObservableList> data;
    @FXML
    private JFXComboBox table_names;
    @FXML
    private TableView table_data;
    @FXML
    private JFXButton update_btn;
    String name = null;
    Vector column_names;
    connection_oracle obj = new connection_oracle();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        get_table_details object = new get_table_details();
        object.get_table_names(table_names);

        // TODOdetails 
        table_names.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newvalue) -> {
            try {

                column_names = buildData(table_names.getSelectionModel().getSelectedItem().toString(), table_data);

            } catch (SQLException ex) {
                Logger.getLogger(Fetch_Record.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        table_data.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newvalue) -> {

        });

    }

    @FXML
    void update_records() throws IOException {
        Window owner = update_btn.getScene().getWindow();
        if (table_names.getSelectionModel().isEmpty() | table_data.getSelectionModel().isEmpty()) {
            if (table_names.getSelectionModel().isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!",
                        "Please Select Table  Name");
            }

            if (table_data.getSelectionModel().isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning!",
                        "No Data in Table");
            }
        } else {
            table_data.setVisible(true);
            int wNroIdxRow = table_data.getSelectionModel().getSelectedIndex();
            String selected = table_data.getItems().get(wNroIdxRow).toString();
            selected = selected.replace(" ", "");
            selected=selected.replace("[","");
             selected = selected.replace("]", "");
            String[] words=selected.split(",");
             FXMLLoader loader = new FXMLLoader();
       
             loader.setLocation(getClass().getResource("update_data_new.fxml"));
     
             try {
  
             loader.setControllerFactory(c -> {
    return new Update_data_new(table_names.getSelectionModel().getSelectedItem().toString(),column_names,words);
    });
             loader.load();    
             } catch (IOException ex) {
             Logger.getLogger(Update_Record.class.getName()).log(Level.SEVERE, null, ex);
             }
             Update_data_new new_update = loader.getController();
           
       
             Parent root = loader.getRoot();
             Stage stage = new Stage();
             stage.setScene(new Scene(root));
             stage.setTitle("Update data");
             stage.initStyle(StageStyle.UTILITY);
             stage.setResizable(true);
             stage.showAndWait();
        }
        table_names.getSelectionModel().select(0);
        table_data.getItems().clear();

    }

    public Vector buildData(String name, TableView table_data) throws SQLException {

        ObservableList data = FXCollections.observableArrayList();
        Vector col_names = new Vector();
       
        try {

            ResultSet rs = obj.get_column_name(name);
            table_data.getColumns().clear();
            table_data.getItems().clear();
            col_names.clear();
            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY * ********************************
             */

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;

                col_names.add(rs.getMetaData().getColumnName(i + 1));
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));

                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                table_data.getColumns().addAll(col);
             
            }

            /**
             * ******************************
             * Data added to ObservableList * ******************************
             */
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }

                data.add(row);

            }

            //FINALLY ADDED TO TableView
            if (data.isEmpty()) {
                Window owner = table_data.getScene().getWindow();
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, " Message!",
                        "No Rows in Table");

            } else {

                table_data.setItems(data);
                table_data.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return col_names;
    }

}
