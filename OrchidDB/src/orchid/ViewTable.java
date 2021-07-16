package orchid;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
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

/**
 * FXML Controller class
 *
 * @author Deepak
 */
public class ViewTable implements Initializable {

    @FXML
    private JFXComboBox table_names;

    @FXML
    private TableView table_structure;

    ObservableList data = FXCollections.observableArrayList();
    connection_oracle obj = new connection_oracle();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        table_structure.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        get_table_details object = new get_table_details();
        object.get_table_names(table_names);
        table_names.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newvalue) -> {
            table_structure.getItems().clear();
            describe_table(newvalue.toString());
        });
    }

    void describe_table(String table_name) {
        data = FXCollections.observableArrayList();
        data.clear();
        try {
            table_structure.getItems().clear();
            table_structure.getColumns().clear();
            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY * ********************************
             */
            String[] Columnheadres = {"COLUMN_NAME", "COLUMN_DATATYPE", "COLUMN_SIZE", "PRIMARY KEY", "NOT NULL", "AUTO INCREEMNT"};
            for (int i = 0; i < Columnheadres.length; i++) {
                //We are using non property style for making dynamic table
                final int j = i;

                TableColumn col = new TableColumn(Columnheadres[i]);

                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                table_structure.getColumns().addAll(col);

            }
            ResultSet rs = obj.get_column_name(table_name);
            ResultSetMetaData metaData = rs.getMetaData();
            System.out.println(metaData.getColumnName(1));
            DatabaseMetaData meta = obj.conn.getMetaData();

            ResultSet rsColumns = meta.getColumns(null, null, table_name, null);
            //GetPrimarykeys
        /*    ResultSet PK = meta.getPrimaryKeys(null, null, table_name);
             System.out.println("------------PRIMARY KEYS-------------");
             while (PK.next()) {
             System.out.println(PK.getString("COLUMN_NAME") + "===" + PK.getString("PK_NAME"));
             }*/
            while (rsColumns.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                String columnName = rsColumns.getString("COLUMN_NAME");
                //row.add(columnName);
                String datatype = rsColumns.getString("TYPE_NAME");
                //row.add(datatype);
                String columnsize = rsColumns.getString("COLUMN_SIZE");
                //row.add(columnsize);
                String decimaldigits = rsColumns.getString("DECIMAL_DIGITS");
                //row.add(decimaldigits);
                String isNullable = rsColumns.getString("IS_NULLABLE");
                // row.add(isNullable);
                String is_autoIncrment = rsColumns.getString("IS_AUTOINCREMENT");
                //  row.add(is_autoIncrment);
                //Printing results
                String pk = "";
                ResultSet PK = meta.getPrimaryKeys(null, null, table_name);
                String col_pk = table_name + columnName;
                while (PK.next()) {
                    System.out.println("PK nmae:" + PK.getString("PK_NAME") + "\t" + col_pk);
                    if (col_pk.equalsIgnoreCase(PK.getString("PK_NAME"))) {
                        System.out.println(columnName.equalsIgnoreCase(PK.getString("PK_NAME")));
                        pk = "Yes";
                    } else {
                        pk = "No";
                    }
                }

                row.addAll(columnName, datatype, columnsize, pk, isNullable, is_autoIncrment);
                data.add(row);
            }
            if (data.isEmpty()) {
                Window owner = table_structure.getScene().getWindow();
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, " Message!",
                        "No Rows in Table");
            } else {
                table_structure.setItems(data);
                table_structure.setVisible(true);
                table_structure.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            }
        } catch (Exception e) {
            Window owner = table_structure.getScene().getWindow();
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, " Message!",
                    e.toString());
        }

    }
}
