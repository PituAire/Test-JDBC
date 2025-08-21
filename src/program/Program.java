package program;

import db.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {
    public static void main(String[] args) {

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = DB.getConnection();

            st = conn.createStatement();

            rs = st.executeQuery("select * from seller");

            while (rs.next()) {
                System.out.println(rs.getInt("id") + "-" + rs.getString("Name"));
            }


        } catch (SQLException e) {
           e.printStackTrace();
        }

        //para encerrar a conexão quando não estiver em execução
        finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
            DB.closeConnection();
        }

        //   System.out.println("Conexão realidaza com sucesso!");

      //  DB.closeConnection();
    }
}
