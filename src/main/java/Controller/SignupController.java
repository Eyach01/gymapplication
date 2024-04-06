
//@Eyach//
package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// Controller class for the signup functionality
public class SignupController implements Initializable {

    // FXML elements
    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNum;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private Button btnSignup;

    // Database connection variables
    private Connection con = null;
    private PreparedStatement preparedStatement = null;

    // Initialize method called when the controller is initialized
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        con = utils.DbConnection.conDB(); // Establish DB connection
    }

    // Method to handle signup button click event
    @FXML
    public void handleSignupAction(ActionEvent event) {
        // Retrieve user input
        String nom = txtNom.getText().trim();
        String prenom = txtPrenom.getText().trim();
        String email = txtEmail.getText().trim();
        String num = txtNum.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();

        // Validate fields
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || num.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            setLblError(Color.TOMATO, "Please fill in all fields.");
            return;
        }

        // Validate password
        if (password.length() < 8 || !password.matches(".*\\d.*") || !password.matches(".*[a-zA-Z].*") || !password.matches(".*[!@#$%^&*()-_=+{};:,<.>/?].*")) {
            setLblError(Color.TOMATO, "Validate password \n(must have at least 8 characters with a mix of symbols, characters, and numbers)");
            return;
        }

        if (!password.equals(confirmPassword)) {
            setLblError(Color.TOMATO, "Passwords do not match.");
            return;
        }

        // Perform signup
        try {
            String sql = "INSERT INTO user (nom, prenom, email, num, password) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, num);
            preparedStatement.setString(5, password);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                setLblError(Color.GREEN, "Signup successful!");
                // Redirect to login page
                redirectToLoginPage(event);
            } else {
                setLblError(Color.TOMATO, "Signup failed. Please try again.");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            setLblError(Color.TOMATO, "Error: " + ex.getMessage());
        }
    }

    // Method to redirect to the login page
    @FXML
    private void redirectToLoginPage(MouseEvent event) {
        try {
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

    // Additional methods for redirecting to login page with different event types
    @FXML
    private void redirectToLoginPageMouse(MouseEvent event) {
        try {
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

    @FXML
    private void redirectToLoginPage(ActionEvent event) {
        try {
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

    // Method to set error label
    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
    }
}
