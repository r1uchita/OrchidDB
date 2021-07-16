package orchid;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class create_table_bean {

//Declare Employees Table Columns
    private StringProperty Column_name;
     private StringProperty column_dataype;
    private StringProperty column_size;
    private StringProperty column_constarints;
   private StringProperty column_position;

    public StringProperty getColumn_position() {
        return column_position;
    }

    public void setColumn_position(StringProperty column_position) {
        this.column_position = column_position;
    }

    
    public create_table_bean() {
        this.Column_name = new SimpleStringProperty();
        this.column_dataype = new SimpleStringProperty();
        this.column_size = new SimpleStringProperty();
        this.column_constarints = new SimpleStringProperty();
        this.column_position=new SimpleStringProperty();
    }
    public StringProperty getColumn_name() {
        return Column_name;
    }

    public void setColumn_name(StringProperty Column_name) {
        this.Column_name = Column_name;
    }

    public StringProperty getColumn_dataype() {
        return column_dataype;
    }

    public void setColumn_dataype(StringProperty column_dataype) {
        this.column_dataype = column_dataype;
    }

    public StringProperty getColumn_size() {
        return column_size;
    }

    public void setColumn_size(StringProperty column_size) {
        this.column_size = column_size;
    }

    public StringProperty getColumn_constarints() {
        return column_constarints;
    }

    public void setColumn_constarints(StringProperty column_constarints) {
        this.column_constarints = column_constarints;
    }
     public StringProperty column_name_property() {
        return Column_name;
    }
        public StringProperty column_datatype_property() {
        return column_dataype;
    }
           public StringProperty column_size_property() {
        return column_size;
    }
              public StringProperty column_constarints_property() {
        return column_constarints;
    }
           public StringProperty column_possition_property() {
        return column_position;
    }  
}
