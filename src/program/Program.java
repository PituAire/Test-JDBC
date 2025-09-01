package program;

import db.DB;
import db.DbIntegrityException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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


        System.out.println("---------------------------------------------------");  //INSERT

        SimpleDateFormat niversario = new SimpleDateFormat("dd/MM/yyyy");

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                    "INSERT INTO seller"
                    +   "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
                    +   "VALUES "
                    +   "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, "Taylor Swift");
            ps.setString(2, "taylor@gmail.com");
            ps.setDate(3, new java.sql.Date(niversario.parse("13/12/1989").getTime()));
            ps.setDouble(4, 2500.00);
            ps.setInt(5,  3);

            int rowsAffected = ps.executeUpdate();

            System.out.println("Finalize! linha alteradas! "+ rowsAffected);

            if (rowsAffected > 0){
                ps.getGeneratedKeys();

                while (rs.next()){
                    int id = rs.getInt(1);
                    System.out.println("Done! id = " + 10);
                }
            }else {
                System.out.println("nenhua linha alterada!");
            }

        }catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        //para encerrar a conexão quando não estiver em execução
        finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
            //DB.closeConnection();
        }


        //   System.out.println("Conexão realidaza com sucesso!");

      //  DB.closeConnection();

        System.out.println("---------------------------------------------------"); //update

        try{
            ps = conn.prepareStatement(
                    "UPDATE seller "
                        + "SET BaseSalary = BaseSalary + ?"
                        + "WHERE" + "(DepartmentId = ?)"
            );
            ps.setDouble(1,150000.0);
            ps.setInt(2, 3);

            int rowsAffected = ps.executeUpdate();

            System.out.println("Finalizado" + rowsAffected);

        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
        }

        System.out.println("---------------------------------------------------"); //delete

        try{
            ps = conn.prepareStatement(
                    "DELETE FROM department "
                            + "WHERE"
                            + "Id = ?"
            );
            ps.setInt(7, 3);

            int rowsAffected = ps.executeUpdate();

            System.out.println("Finalizado" + rowsAffected);

        }catch (SQLException |DbIntegrityException e){
            throw new DbIntegrityException(e.getMessage());
        } finally {
            DB.closeStatement(ps);
        }



    }
}
