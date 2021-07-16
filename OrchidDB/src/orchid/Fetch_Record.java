package orchid;

import com.jfoenix.controls.JFXComboBox;
import java.sql.*;
import java.util.*;
import javafx.scene.*;
import javafx.fxml.*;
import javafx.beans.*;
import javafx.collections.*;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.stage.Window;
import javafx.util.Callback;
import oracle_util.connection_oracle;

public class Fetch_Record implements Initializable {

    private ObservableList<ObservableList> data;
    @FXML
    private JFXComboBox table_names;
    @FXML
    private TableView table_data;
    String name = null;
    String column_names[];
    connection_oracle obj = new connection_oracle();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        get_table_names();
        table_names.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newvalue) -> {
            try {
               table_data.getItems().clear();
               table_data.getColumns().clear();
                buildData(newvalue.toString(),table_data);
            } catch (SQLException ex) {
                Logger.getLogger(Fetch_Record.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    void get_table_names() {

        ResultSet rs;
        try {
            rs = obj.get_table_name();

            ObservableList<String> data;
            int count = 3;
            //System.out.println(rs.last());
            rs.last();
            count = rs.getRow();
            String[] items = new String[count];
            //System.out.println(count);
            int i = 0;
            rs = obj.get_table_name();
            while (rs.next()) {

                items[i] = rs.getString(1);
                //System.out.println(items[i]);
                i++;

                data = FXCollections.observableArrayList(items);
                // System.out.println(data);
                table_names.setItems(data);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Fetch_Record.class.getName()).log(Level.SEVERE, null, ex);
        }
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
