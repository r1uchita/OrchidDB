package orchid;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Window;
import javafx.util.Callback;
import oracle_util.connection_oracle;
import static oracle_util.connection_oracle.conn;

public class Delete_Record implements Initializable {

    private ObservableList<ObservableList> data;
    @FXML
    private JFXComboBox table_names;
    @FXML
    private TableView table_data;
    @FXML
    private JFXButton delete_button;
    String name = null;

    Vector col_names;
    connection_oracle obj = new connection_oracle();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        get_table_details object = new get_table_details();
        object.get_table_names(table_names);
        table_names.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newvalue) -> {
            try {
                        col_names = buildData(newvalue.toString(), table_data);
            } catch (SQLException ex) {
                Logger.getLogger(Fetch_Record.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    void record_delete() throws SQLException {
        Window owner = delete_button.getScene().getWindow();
        if (table_data.getSelectionModel().getSelectedItems().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning", "Pls Selct any Record");
        } else {
            Object selectedItem = table_data.getSelectionModel().getSelectedItem();
            String s = selectedItem.toString();
            s = s.replace("[", "");
            s = s.replace("]", "");
            System.out.println(s);
            String[] array = s.split("\\s*,\\s*");
            System.out.println("Array:" + array[0]);
            //  slectedItem.toString();e
            System.out.println("Selected item" + selectedItem.toString());

            String query = "Delete from " + table_names.getSelectionModel().getSelectedItem().toString() + " where ";

            String values = " ";
            for (int i = 0; i < table_data.getColumns().size(); i++) {
                if (i != table_data.getColumns().size() - 1) {
                    values += col_names.get(i) + " = ? And ";
                } else {
                    values += col_names.get(i) + " = ? ";
                }

            }
            query = query + values;
            System.out.println(query);

            try {
                obj.get_DB_connection();

                PreparedStatement ps = obj.conn.prepareStatement(query);
                for (int i = 1; i <= array.length; i++) {
                    ps.setString(i, array[i - 1]);
                }

                ps.executeUpdate();
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, " Result Message", "Record deletedSucessfully\nNo.of records updates:");
                table_names.getSelectionModel().clearSelection();
                table_data.getItems().clear();
            } catch (Exception e) {
                AlertHelper.showAlert(Alert.AlertType.WARNING, owner, " Warning", e.toString());
            }
            System.out.println(query);
        }
    }

    public Vector buildData(String name, TableView table_data) throws SQLException {

        ObservableList data = FXCollections.observableArrayList();
        Vector col_names = new Vector();
        //ResultSet
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
                //System.out.println("Column [" + i + "] ");
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

                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            if (data.isEmpty()) {
                Window owner = table_data.getScene().getWindow();
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, " Message!",
                        "No Rows in Table");
                table_data.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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
