
import Data_source.config;

import java.sql.Connection;

public class main {
    public main() {
    }

    public static void main(String[] args) {
        Connection con1 = config.getInstance().getCon();
        Connection con2 = config.getInstance().getCon();
        System.out.println(con1);
        System.out.println(con2);
    }
}
