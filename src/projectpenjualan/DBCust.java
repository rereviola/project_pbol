/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projectpenjualan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author LIKMI
 */
public class DBCust {
    private CustModel dt=new CustModel();    
    public CustModel getCustModel(){ return(dt);}
    public void setCustModel(CustModel s){ dt=s;}
    
    public ObservableList<CustModel>  Load() {
        try {
            ObservableList<CustModel> tableData=FXCollections.observableArrayList();
            Koneksi con = new Koneksi();            
            con.bukaKoneksi();
            con.statement = con.db_pbol.createStatement();
            ResultSet rs = con.statement.executeQuery("Select idmember, nama, alamat, total from customer");
            int i = 1;
            while (rs.next()) {
                CustModel d=new CustModel();
                d.setIdmember(rs.getString("idmember"));                
                d.setNama(rs.getString("nama"));
                d.setAlamat(rs.getString("alamat"));                
                d.setTotal(rs.getDouble("total"));           
                double total = rs.getDouble("total");
                String status;
                
                if (total>=1000000) status = "gold";
                    else if (total>=500000) status = "silver";
                    else status = "reguler";
                d.setStatus(status);
                tableData.add(d);                
                i++;            
            }
            con.tutupKoneksi();            
            return tableData;
        } catch (Exception e) {            
            e.printStackTrace();            
            return null;        
        }
    }
    
    public int validasi(String nomor) {
        int val = 0;
        try {         
            Koneksi con = new Koneksi();            
            con.bukaKoneksi();
            con.statement = con.db_pbol.createStatement();
            ResultSet rs = con.statement.executeQuery(  "select count(*) as jml from customer where idmember = '" + nomor + "'");
            while (rs.next()) {                
                val = rs.getInt("jml");            
            }            
            con.tutupKoneksi();
        } catch (SQLException e) {            
            e.printStackTrace();        
        }
        return val;
    }
    
    public boolean insert() {
        boolean berhasil = false;        
        Koneksi con = new Koneksi();
        try {       
            con.bukaKoneksi();
            con.preparedStatement = con.db_pbol.prepareStatement("insert into customer (idmember,nama, alamat, total) values (?,?,?,?)");
            con.preparedStatement.setString(1, getCustModel().getIdmember());           
            con.preparedStatement.setString(2, getCustModel().getNama());
            con.preparedStatement.setString(3, getCustModel().getAlamat());           
            con.preparedStatement.setDouble(4, getCustModel().getTotal());        
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {            
            e.printStackTrace();            
            berhasil = false;
        } finally {            
            con.tutupKoneksi();            
            return berhasil;        
        }    
     }
    
    public boolean delete(String nomor) {
        boolean berhasil = false;        
        Koneksi con = new Koneksi();
        try {            
            con.bukaKoneksi();;
            con.preparedStatement = con.db_pbol.prepareStatement("delete from customer where idmember  = ? ");
            con.preparedStatement.setString(1, nomor);
            con.preparedStatement.executeUpdate();            
            berhasil = true;
        } catch (Exception e) {            
            e.printStackTrace();
        } finally {            
            con.tutupKoneksi();            
            return berhasil;        
        }
    }
    
    public boolean update() {
        boolean berhasil = false;        
        Koneksi con = new Koneksi();
        try {            
            con.bukaKoneksi();
            con.preparedStatement = con.db_pbol.prepareStatement("update customer set nama = ?, alamat = ?, total = ?  where  idmember = ? ");
            con.preparedStatement.setString(1, getCustModel().getNama());
            con.preparedStatement.setString(2, getCustModel().getAlamat());
            con.preparedStatement.setDouble(3, getCustModel().getTotal());
            con.preparedStatement.setString(4, getCustModel().getIdmember());
            con.preparedStatement.executeUpdate();            
            berhasil = true;
        } catch (Exception e) {            
            e.printStackTrace();            
            berhasil = false;
        } finally {            
            con.tutupKoneksi();            
            return berhasil;        
        }    
    }
    
    public ObservableList<CustModel>  CariCust(String kode, String nama) {
        try {    
            ObservableList<CustModel> 	tableData;
            tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi(); 
            con.bukaKoneksi();
            con.statement = (Statement) con.db_pbol.createStatement();
            ResultSet rs = (ResultSet) con.statement.executeQuery("select * from customer WHERE idmember LIKE '" + kode + "%' OR nama LIKE '" + nama + "%'");
        int i = 1;
        while(rs.next()){
            CustModel d = new CustModel();
            d.setIdmember(rs.getString("idmember"));
            d.setNama(rs.getString("nama"));
            d.setAlamat(rs.getString("alamat"));
            d.setTotal(rs.getDouble("total"));
            double total = rs.getDouble("total");
                String status;
                
                if (total>=1000000) status = "gold";
                    else if (total>=500000) status = "silver";
                    else status = "reguler";
                d.setStatus(status);
            tableData.add(d);
            i++;
        }
        con.tutupKoneksi();
        return tableData;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
