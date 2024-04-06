package Data_source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class config {
    private String url = "jdbc:mysql://localhost:3306/gym_box";
    private String login = "root";
    private String pwd = "";
    private static config data;
    private static Connection con;

    private config() {
        try {
            this.con = DriverManager.getConnection(this.url, this.login, this.pwd);
            System.out.println("connexion établie");
        } catch (SQLException var2) {
            System.out.println(var2);
        }

    }

    public static Connection getCon() {
        return con;
    }

    public static config getInstance() {
        if (data == null) {
            data = new config();
        }

        return data;
    }
}