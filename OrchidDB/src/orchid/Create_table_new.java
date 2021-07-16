package orchid;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import oracle_util.connection_oracle;

public class Create_table_new extends javax.swing.JFrame implements ActionListener {

    public String createQuery = " ";
    JTable create_tabel;
    JTextField tabel_name_textfield, column_name_textfield;
    JLabel tabel_name_label;
    String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",};
    String[] values = {"Varchar2", "Number", "Date", "float"};
    JComboBox datatypes_combobox = new JComboBox(values);
    JComboBox size_combobox = new JComboBox(numbers);
    JCheckBox primary_key = new JCheckBox();
    JCheckBox not_null = new JCheckBox();
    JCheckBox auto_increment = new JCheckBox();
    JButton submit, cancel;
    DefaultTableModel model;

    /**
     * Creates new form CreateTable
     */
    public Create_table_new() {

        tabel_name_label = new JLabel("Enter Table Name");
        tabel_name_textfield = new JTextField(30);
        column_name_textfield = new JTextField(30);
        submit = new JButton("Submit");
        cancel = new JButton("Cancel");

        model = new DefaultTableModel();
        create_tabel = new JTable(model);
        create_tabel.setRowHeight(30);
        model.addColumn("Attribute Name");
        model.addColumn("Data Type");
        model.addColumn("Length");
        model.addColumn("Primary Key");
        model.addColumn("Not Null");
        model.addColumn("Auto Increment");

        create_tabel.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(column_name_textfield));
        create_tabel.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(datatypes_combobox));
        create_tabel.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(size_combobox));
        size_combobox.setName("Char");
        create_tabel.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(primary_key));//primary key
        create_tabel.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(not_null));//not null
        create_tabel.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(auto_increment));//auto increment
        create_tabel.setPreferredScrollableViewportSize(new Dimension(500, 100));
        create_tabel.setFillsViewportHeight(true);

        JScrollPane sp = new JScrollPane();
        sp.setViewportView(create_tabel);

        setLayout(null);
        setTitle("CreateTable");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        submit.addActionListener(this);
        create_tabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    model.addRow(new Object[]{null, null, null, null, null, null, null});
                    getColumn();
                }
            }
        });

        cancel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    createQuery="";
                    model.setRowCount(0);
                    tabel_name_textfield.setText(" ");
                }
            }
        });

        tabel_name_label.setBounds(10, 10, 120, 40);
        tabel_name_textfield.setBounds(150, 10, 200, 40);
        sp.setBounds(10, 50, 1000, 400);
        submit.setBounds(210, 460, 150, 40);
        //submit.set
        cancel.setBounds(400, 460, 150, 40);
        model.addRow(new Object[]{null, null, null, null, null, null});
        add(tabel_name_label);
        add(tabel_name_textfield);
        add(sp);
        add(submit);
        add(cancel);
        setSize(1400, 800);
        setLocationRelativeTo(null);
        tabel_name_textfield.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Text=" + tabel_name_textfield.getText());
                int i = 0;
                connection_oracle obj = new connection_oracle();
                try {
                    ResultSet rs = obj.get_table_name();
                    while (rs.next()) {
                           String items= rs.getString(1);
                            if (tabel_name_textfield.getText().equalsIgnoreCase(items)) {
                           
                                JOptionPane.showMessageDialog(null, "This Table name alreeady Exist");
                             
                         
                           }
                          
                      }
                } 
                catch (SQLException ex) {
                    Logger.getLogger(Create_table_new.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            });
    }

    @SuppressWarnings("unchecked")

    public void getTableName() {
        String table_name = "student";
        createQuery = "create table ";
        createQuery += table_name;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 555, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 454, Short.MAX_VALUE)
        );

        pack();
    }

    public void getColumn() {
        String name;
        String data;

        name = column_name_textfield.getText();

        data = datatypes_combobox.getSelectedItem().toString();
        if (datatypes_combobox.getSelectedItem().equals("Date")) {

            createQuery += name;
            createQuery += " " + data;

        } else {
            createQuery += name;
            createQuery += " " + data;
            String length = size_combobox.getSelectedItem().toString();
            String datatype = "(" + length + ")";
            createQuery += datatype;
        }

        boolean PK = primary_key.isSelected();
        boolean NN = not_null.isSelected();
        boolean AI = auto_increment.isSelected();

        if (PK) {
            createQuery += " Primary Key ";
        }

        if (NN) {
            createQuery += " Not Null ";
        }
        if (AI) {
            createQuery += "Auto Increment";
        }
        name="";
        data="";
        column_name_textfield.setText("");
        primary_key.setSelected(false);
        not_null.setSelected(false);
        auto_increment.setSelected(false);
        PK = false;
        NN = false;
        createQuery += ",";
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Create_table_new.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Create_table_new.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Create_table_new.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Create_table_new.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Create_table_new ct = new Create_table_new();
                ct.setVisible(true);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getColumn();
        try {
            connection_oracle obj = new connection_oracle();
            obj.get_DB_connection();

            int n = createQuery.length();
            char[] query = createQuery.toCharArray();
            query[n - 1] = ')';
            createQuery = String.valueOf(query);

            String create = "create table ";
            create += tabel_name_textfield.getText();
            create += "(";
            create += createQuery;
            //JOptionPane.showMessageDialog(null,create);
            System.out.println(create);
            String result = obj.create_table(create);
            if (result.equals("Sucess")) {
                JOptionPane.showMessageDialog(null, "Table Created Successfully !!!");
                createQuery="";
            } else {
                JOptionPane.showMessageDialog(null, result);
                createQuery="";
            }
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(null, e1.getMessage());
        }
        model.setRowCount(0);
        tabel_name_textfield.setText(" ");
    }
}
