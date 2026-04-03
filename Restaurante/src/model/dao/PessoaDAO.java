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
import model.bean.Pessoa;

public class PessoaDAO {
    
    public void create(Pessoa p){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO pessoa (nome, telefone, cpf, login, senha, tipo) VALUES (?,?,?,?,?,?)");
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getTelefone());
            stmt.setString(3, p.getCpf());
            stmt.setString(4, p.getLogin());
            stmt.setString(5, p.getSenha());
            stmt.setString(6, p.getTipo());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
	
     public List <Pessoa> read() throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List <Pessoa> listaPessoas = new ArrayList <>();
        try {
            stmt = con.prepareStatement("SELECT * FROM pessoa");
            rs = stmt.executeQuery();
            while(rs.next()) {
                Pessoa p = new Pessoa();
                p.setCod(rs.getInt("cod"));
                p.setNome(rs.getNString("nome"));
                p.setTelefone(rs.getNString("telefone"));
                p.setCpf(rs.getNString("cpf"));
                p.setLogin(rs.getNString("login"));
                p.setSenha(rs.getNString("senha"));
                p.setTipo(rs.getNString("tipo"));
                listaPessoas.add(p);
            }
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
            } finally {
                ConnectionFactory.closeConnection(con,stmt,rs);
        } return
                listaPessoas;
     }
     
     public List <Pessoa> ListarGarcons() throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List <Pessoa> listaPessoas = new ArrayList <>();
        try {
            stmt = con.prepareStatement("SELECT * FROM pessoa WHERE tipo=?");
            stmt.setString(1, "Garçom");
            
            rs = stmt.executeQuery();
            
            while(rs.next()) {
                Pessoa p = new Pessoa();
                p.setCod(rs.getInt("cod"));
                p.setNome(rs.getNString("nome"));
                p.setTelefone(rs.getNString("telefone"));
                p.setCpf(rs.getNString("cpf"));
                p.setLogin(rs.getNString("login"));
                p.setSenha(rs.getNString("senha"));
                p.setTipo(rs.getNString("tipo"));
                listaPessoas.add(p);
            }
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
            } finally {
                ConnectionFactory.closeConnection(con,stmt,rs);
        } return
                listaPessoas;
     }

    public void update(Pessoa p){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("UPDATE pessoa SET nome= ?, telefone=?, cpf=?, login=?, senha=?, tipo=? WHERE cod=?");
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getTelefone());
            stmt.setString(3, p.getCpf());
            stmt.setString(4, p.getLogin());
            stmt.setString(5, p.getSenha());
            stmt.setString(6, p.getTipo());
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PessoaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
 public void delete(Pessoa p) throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE FROM pessoa WHERE cod=?" );
            stmt.setInt(1, p.getCod());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
 
 public String identificar(Pessoa p){
     Connection con = ConnectionFactory.getConnection();
     PreparedStatement stmt = null;
     
     String tipo = null;
     
     try{
         stmt = con.prepareStatement("SELECT tipo FROM pessoa WHERE login=? AND senha=?");
         
         stmt.setString(1, p.getLogin());
         stmt.setString(2, p.getSenha());
         
         ResultSet rs = stmt.executeQuery();
         
         while(rs.next()){
             p.setTipo(rs.getNString("tipo"));
         }
         tipo = p.getTipo();
         
 } catch(SQLException ex){
     JOptionPane.showMessageDialog(null, ex);
 } return
         tipo;
 }
 
 public boolean login(Pessoa p){
     Connection con = ConnectionFactory.getConnection();
     PreparedStatement stmt = null;
     
     try{
         stmt = con.prepareStatement("SELECT * FROM pessoa WHERE login=? AND senha=?");
         
         stmt.setString(1, p.getLogin());
         stmt.setString(2, p.getSenha());
         
         ResultSet rs = stmt.executeQuery();
         
         if (rs.next()) {
             return true;
            } else {
                JOptionPane.showMessageDialog(null, "Nome de Usuário ou Senha inválida");
            }
     } catch(SQLException ex){
         JOptionPane.showMessageDialog(null, ex);
     } finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
     return false;
 }
}