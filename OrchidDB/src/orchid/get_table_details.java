/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchid;

import com.jfoenix.controls.JFXComboBox;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Window;
import javafx.util.Callback;
import oracle_util.connection_oracle;


public class get_table_details {

    connection_oracle obj = new connection_oracle();

    public void get_table_names(JFXComboBox table_names) {

        ResultSet rs;
        try {
            rs = obj.get_table_name();

            ObservableList<String> data;
            int count = 3;
       
            rs.last();
            count = rs.getRow();
            String[] items = new String[count];
   
            int i = 0;
            rs = obj.get_table_name();
            while (rs.next()) {

                items[i] = rs.getString(1);

                i++;

                data = FXCollections.observableArrayList(items);

                table_names.setItems(data);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Add_column.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buildData(String name, TableView table_data) throws SQLException {

        ObservableList data = FXCollections.observableArrayList();
        //ResultSet
        try {

            ResultSet rs = obj.get_column_name(name);
            table_data.getColumns().clear();
            table_data.getItems().clear();
            Vector col_names = new Vector();
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
                return;
            } else {

                table_data.setItems(data);
                table_data.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public void get_column_names(String table_name, JFXComboBox column_names) throws SQLException {
        connection_oracle obj = new connection_oracle();
        ObservableList col_names = FXCollections.observableArrayList();
        //ResultSet
        ResultSet rs = obj.get_column_name(table_name);
        /**
         * ********************************
         * TABLE COLUMN ADDED DYNAMICALLY * ********************************
         */
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            col_names.add(rs.getMetaData().getColumnName(i + 1));

        }
        column_names.setItems(col_names);

    }
}
