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


    public Car() {
        connect();
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
