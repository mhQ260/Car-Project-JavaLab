import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Car {
    private JPanel Main;
    private JTextField txtBrand;
    private JTextField txtModel;
    private JTextField txtPrice;
    private JButton saveButton;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField txtId;
    private JTextField txtYearOfProduction;
    private JTextField txtEngineType;
    private JTextField txtEngineCapacity;
    private JTextField txtEngineHp;
    private JScrollPane table;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee");
        frame.setContentPane(new Car().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    Connection con;
    PreparedStatement pst;

    public void connect() {



        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/car?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "zaq1@WSX");
            System.out.println("Success!");

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void tableLoad() {
        try {
            pst = con.prepareStatement("select * from cars");
            ResultSet rs = pst.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Car() {
        connect();
        tableLoad();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String brand, model, price, yearOfproduction, engineType, engineCapacity, engineHp;

                brand = txtBrand.getText();
                model = txtModel.getText();
                price = txtPrice.getText();
                yearOfproduction = txtYearOfProduction.getText();
                engineType = txtEngineType.getText();
                engineCapacity = txtEngineCapacity.getText();
                engineHp = txtEngineHp.getText();

                try {
                    pst = con.prepareStatement("insert into cars (brand, model, price, yearOfProduction, engineType, engineCapacity, engineHp) values (?, ?, ?, ?, ?, ?, ?)");
                    pst.setString(1, brand);
                    pst.setString(2, model);
                    pst.setString(3, price);
                    pst.setString(4, yearOfproduction);
                    pst.setString(5, engineType);
                    pst.setString(6, engineCapacity);
                    pst.setString(7, engineHp);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Added!");
                    tableLoad();
                    txtBrand.setText("");
                    txtModel.setText("");
                    txtPrice.setText("");
                    txtYearOfProduction.setText("");
                    txtEngineType.setText("");
                    txtEngineCapacity.setText("");
                    txtEngineHp.setText("");

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    String id = txtId.getText();

                    pst = con.prepareStatement("select brand, model, price, yearOfProduction, engineType, engineCapacity, engineHp from cars where id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next() == true) {
                        String brand = rs.getString(1);
                        String model = rs.getString(2);
                        String price = rs.getString(3);
                        String yearOfProduction = rs.getString(4);
                        String engineType = rs.getString(5);
                        String engineCapacity = rs.getString(6);
                        String engineHp = rs.getString(7);

                        txtBrand.setText(brand);
                        txtModel.setText(model);
                        txtPrice.setText(price);
                        txtYearOfProduction.setText(yearOfProduction);
                        txtEngineType.setText(engineType);
                        txtEngineCapacity.setText(engineCapacity);
                        txtEngineHp.setText(engineHp);

                    } else {
                        txtBrand.setText("");
                        txtModel.setText("");
                        txtPrice.setText("");
                        txtYearOfProduction.setText("");
                        txtEngineType.setText("");
                        txtEngineCapacity.setText("");
                        txtEngineHp.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid data!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }


        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id, brand, model, price, yearOfproduction, engineType, engineCapacity, engineHp;

                brand = txtBrand.getText();
                model = txtModel.getText();
                price = txtPrice.getText();
                yearOfproduction = txtYearOfProduction.getText();
                engineType = txtEngineType.getText();
                engineCapacity = txtEngineCapacity.getText();
                engineHp = txtEngineHp.getText();
                id = txtId.getText();

                try {
                    pst = con.prepareStatement("update cars set brand = ?,model = ?,price = ?,yearOfProduction = ?,engineType = ?,engineCapacity = ?,engineHp = ? where id = ?");
                    pst.setString(1, brand);
                    pst.setString(2, model);
                    pst.setString(3, price);
                    pst.setString(4, yearOfproduction);
                    pst.setString(5, engineType);
                    pst.setString(6, engineCapacity);
                    pst.setString(7, engineHp);
                    pst.setString(8, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Updated!");
                    tableLoad();
                    txtBrand.setText("");
                    txtModel.setText("");
                    txtPrice.setText("");
                    txtYearOfProduction.setText("");
                    txtEngineType.setText("");
                    txtEngineCapacity.setText("");
                    txtEngineHp.setText("");

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id;
                id = txtId.getText();

                try {
                    pst = con.prepareStatement("delete from cars where id = ?");

                    pst.setString(1, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Deleted!");
                    tableLoad();
                    txtBrand.setText("");
                    txtModel.setText("");
                    txtPrice.setText("");
                    txtYearOfProduction.setText("");
                    txtEngineType.setText("");
                    txtEngineCapacity.setText("");
                    txtEngineHp.setText("");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
