package model.dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.bean.Conta;

public class ContaDAO {
    
    public void create(Conta c){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO conta (garcom, dataAbertura, horaAbertura, valorTotal) VALUES (?,?,?,?)");
            stmt.setString(1, c.getGarcom());
            stmt.setString(2, c.getDataAbertura());
            stmt.setString(3, c.getHoraAbertura());
            stmt.setDouble(4, c.getValorTotal());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
	
     public List <Conta> read() throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List <Conta> listaContas = new ArrayList <>();
        try {
            stmt = con.prepareStatement("SELECT * FROM conta");
            rs = stmt.executeQuery();
            while(rs.next()) {
                Conta c = new Conta();
                c.setCod(rs.getInt("cod"));
                c.setGarcom(rs.getNString("garcom"));
                c.setDataAbertura(rs.getNString("dataAbertura"));
                c.setHoraAbertura(rs.getNString("horaAbertura"));
                c.setValorTotal(rs.getDouble("valorTotal"));
                
                listaContas.add(c);
            }
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
            } finally {
                ConnectionFactory.closeConnection(con,stmt,rs);
        } return
                listaContas;
     }

    public void update(Conta c){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("UPDATE conta SET garcom=?, dataAbertura=?, horaAbertura=?, valorTotal=? WHERE cod=?");
            stmt.setString(1, c.getGarcom());
            stmt.setString(2, c.getDataAbertura());
            stmt.setString(3, c.getHoraAbertura());
            stmt.setDouble(4, c.getValorTotal());
            stmt.setInt(5, c.getCod());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    public void fecharConta(Conta c){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("UPDATE conta SET valorTotal=? WHERE cod=?");
            stmt.setDouble(1, c.getValorTotal());
            stmt.setInt(2, c.getCod());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ContaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
 public void delete(Conta c) throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE FROM conta WHERE cod=?" );
            stmt.setInt(1, c.getCod());
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}