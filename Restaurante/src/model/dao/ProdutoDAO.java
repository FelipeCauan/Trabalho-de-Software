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
import model.bean.Produto;

public class ProdutoDAO {
    
    public void create(Produto p){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO produto (nome, valor, categoria, quantidade) VALUES (?,?,?,?)");
            stmt.setString(1, p.getNome());
            stmt.setDouble(2, p.getValor());
            stmt.setString(3, p.getCategoria());
            stmt.setInt(4, p.getQuantidade());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
	
     public List <Produto> read() throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List <Produto> listaProdutos = new ArrayList <>();
        try {
            stmt = con.prepareStatement("SELECT * FROM produto");
            rs = stmt.executeQuery();
            while(rs.next()) {
                Produto p = new Produto();
                p.setCod(rs.getInt("cod"));
                p.setNome(rs.getNString("nome"));
                p.setValor(rs.getDouble("valor"));
                p.setCategoria(rs.getNString("categoria"));
                p.setQuantidade(rs.getInt("quantidade"));
                
                listaProdutos.add(p);
            }
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
            } finally {
                ConnectionFactory.closeConnection(con,stmt,rs);
        } return
                listaProdutos;
     }

    public void update(Produto p){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("UPDATE produto SET nome=?, valor=?, categoria=?, quantidade=? WHERE cod=?");
            stmt.setString(1, p.getNome());
            stmt.setDouble(2, p.getValor());
            stmt.setString(3, p.getCategoria());
            stmt.setInt(4, p.getQuantidade());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
 public void delete(Produto p) throws SQLException{
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("DELETE FROM produto WHERE cod=?" );
            stmt.setInt(1, p.getCod());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}