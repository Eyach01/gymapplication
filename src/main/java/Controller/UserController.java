
//@Eyach//
package Controller;

import Entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

// Controller class for managing user data
public class UserController implements Initializable {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    int id = 0;

    // FXML elements
    @FXML
    private TextField tEmail;
    @FXML
    private TextField tNom;
    @FXML
    private TextField tNum;
    @FXML
    private TextField tPrenom;
    @FXML
    private TextField tDuree;
    @FXML
    private Button btnadd;
    @FXML
    private Button btnclear;
    @FXML
    private Button btndelete;
    @FXML
    private Button btnupdate;
    @FXML
    private TableColumn<User, String> colEmail;
    @FXML
    private TableColumn<User, String> colNom;
    @FXML
    private TableColumn<User, Integer> colNum;
    @FXML
    private TableColumn<User, String> colPrenom;
    @FXML
    private TableColumn<User, String> colRole;
    @FXML
    private TableColumn<User, String> colDuree;
    @FXML
    private TableColumn<User, Integer> colid;
    @FXML
    private TableView<User> table;
    @FXML
    private ComboBox<String> cbRole;

    // Constructor
    public UserController() {}

    // Initialize method called when the controller is initialized
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbRole.setItems(FXCollections.observableArrayList("client", "coach"));
        showUsers();
    }

    // Method to fetch users from the database
    public ObservableList<User> getUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String query = "SELECT * FROM user WHERE nom != 'admin' AND prenom != 'admin'"; // Exclude admin users
        con = Data_source.config.getInstance().getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("Nom"));
                user.setPrenom(rs.getString("Prenom"));
                user.setEmail(rs.getString("Email"));
                user.setNum(rs.getInt("Num"));
                user.setRole(rs.getString("role"));
                user.setDuree(rs.getString("duree"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    // Method to display users in the TableView
    public void showUsers() {
        ObservableList<User> list = getUsers();
        table.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        colNum.setCellValueFactory(new PropertyValueFactory<>("Num"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("Role"));
        colDuree.setCellValueFactory(new PropertyValueFactory<>("Duree"));
    }

    // Event handler to clear fields
    @FXML
    void clearField(ActionEvent event) {
        clear();
    }

    // Event handler to create a new user
    @FXML
    void createUser(ActionEvent event) {
        String insert = "INSERT INTO User (nom, prenom, num, email, role, duree) VALUES (?, ?, ?, ?, ?, ?)";
        con = Data_source.config.getInstance().getCon();
        try {
            st = con.prepareStatement(insert);
            st.setString(1, tNom.getText());
            st.setString(2, tPrenom.getText());
            st.setString(3, tNum.getText());
            st.setString(4, tEmail.getText());
            st.setString(5, cbRole.getValue());
            st.setString(6, tDuree.getText());
            st.executeUpdate();
            clear();
            showUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Event handler to get data of selected user
    @FXML
    void getData(MouseEvent event) {
        User user = table.getSelectionModel().getSelectedItem();
        id = user.getId();
        tNom.setText(user.getNom());
        tPrenom.setText(user.getPrenom());
        tNum.setText(String.valueOf(user.getNum()));
        tEmail.setText(user.getEmail());
        cbRole.setValue(user.getRole());
        tDuree.setText(user.getDuree());
        btnadd.setDisable(true);
    }

    // Method to clear fields
    void clear() {
        tNom.clear();
        tPrenom.clear();
        tEmail.clear();
        tNum.clear();
        cbRole.setValue(null);
        tDuree.clear();
        btnadd.setDisable(false);
    }

    // Event handler to delete user
    @FXML
    void deleteUser(ActionEvent event) {
        String delete = "DELETE FROM User WHERE id=?";
        con = Data_source.config.getInstance().getCon();
        try {
            st = con.prepareStatement(delete);
            st.setInt(1, id);
            st.executeUpdate();
            showUsers();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Event handler to update user data
    @FXML
    void updateUser(ActionEvent event) {
        String update = "UPDATE User SET nom = ?, prenom = ?, num = ?, email = ?, role = ?, duree = ? WHERE id = ?";
        con = Data_source.config.getInstance().getCon();
        try {
            st = con.prepareStatement(update);
            st.setString(1, tNom.getText());
            st.setString(2, tPrenom.getText());
            st.setString(3, tNum.getText());
            st.setString(4, tEmail.getText());
            st.setString(5, cbRole.getValue());
            st.setString(6, tDuree.getText());
            st.setInt(7, id);
            st.executeUpdate();
            showUsers();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
