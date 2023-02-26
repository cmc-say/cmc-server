package cmc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.aspectj.bridge.MessageUtil.fail;

public class DatabaseConnectionTest extends TestSupport{
    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

//    @Disabled("db 연결 테스트")
    @Test
    public void testConnection() {

        try(Connection con =
                    DriverManager.getConnection(
                            "jdbc:mariadb://localhost:3307/cmc_database",
                            "root",
                            "your password!!")){
            System.out.println(con);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

}
