package Data_source;

import java.sql.*;

public class test {
    static String url = "jdbc:mysql://localhost:3306/gym_box";
    static String login = "root";
    static String pwd = "";
    static Connection con;
    static Statement ste;

    public test() {
    }

    public static void main(String[] args) {
        try {
            con = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion établie");

            ste = con.createStatement();
            String req = "INSERT INTO `User` (`id`, `nom`, `prenom`, `email`, `num`,`role`,`duree`) VALUES (NULL, 'test2', 'tes2', 'email@gmail.com', 12655,'adminn','dayy');";
            ste.executeUpdate(req);
            System.out.println("user ajouté");

            ResultSet rs = ste.executeQuery("SELECT * FROM User");

            while (rs.next()) {
                int id = rs.getInt(1);
                String nom = rs.getString(2);
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                int num = rs.getInt("num");
                String role = rs.getString("role");
                String duree = rs.getString("duree");
                System.out.println("ID : " + id + " Nom : " + nom + " Prénom : " + prenom + " Numéro : " + num + " Email : " + email + "role: " + role +"duree:"+duree);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'accès à la base de données : " + e.getMessage());
        } finally {
            // Fermer les ressources
            try {
                if (ste != null) ste.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }
}
