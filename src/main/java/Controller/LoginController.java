
//@Eyach//
package Controller;

// Importing necessary libraries
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Controller class for the login functionality
 */
public class LoginController implements Initializable {

    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnSignin;

    // Database connection variables
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    // This method is called when the sign-in button is clicked
    @FXML
    public void handleButtonAction(MouseEvent event) {
        if (event.getSource() == btnSignin) {
            // Attempt login
            if (logIn(event).equals("Success")) {
                try {
                    // If login is successful, redirect to appropriate page
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/Fxml/login.fxml")));
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    // Initialize method called when the controller is initialized
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Check database connection status
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Server is up : Good to go");
        }
    }

    // Constructor initializing database connection
    public LoginController() {
        con = utils.DbConnection.conDB();
    }

    // Method to handle login functionality
    private String logIn(MouseEvent event) {
        String status = "Success";
        String nom = txtnom.getText();
        String password = txtPassword.getText();
        if(nom.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
        } else {
            // Query to check username and password
            String sql = "SELECT * FROM user Where nom = ? and password = ?";
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, nom);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    // If no user found with given credentials
                    setLblError(Color.TOMATO, "Enter Correct username/Password");
                    status = "Error";
                } else {
                    // Redirect based on user role
                    if (nom.equals("admin")) {
                        redirectToHelloPage(event);
                    } else {
                        redirectToByePage(event);
                    }
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }
        return status;
    }

    // Redirect to admin dash
    private void redirectToHelloPage(MouseEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/Fxml/user_interface.fxml")));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    // Redirect to client dash
    private void redirectToByePage(MouseEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/Fxml/clientDash.fxml")));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    // Method to redirect to sign-up page
    @FXML
    private void redirectSignUpPage(MouseEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/Fxml/signup.fxml")));
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    // Method to set error label
    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }
}
