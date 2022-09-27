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
public class DBBrg {
    private BrgModel dt=new BrgModel();    
    public BrgModel getBrgModel(){ return(dt);}
    public void setBrgModel(BrgModel s){ dt=s;}
    
    public ObservableList<BrgModel>  Load() {
        try {
            ObservableList<BrgModel> tableData=FXCollections.observableArrayList();
            Koneksi con = new Koneksi();            
            con.bukaKoneksi();
            con.statement = con.db_pbol.createStatement();
            ResultSet rs = con.statement.executeQuery("Select kodebrg, namabrg, tarif from barang");
            int i = 1;
            while (rs.next()) {
                BrgModel d=new BrgModel();
                d.setKodebrg(rs.getString("kodebrg"));                
                d.setNamabrg(rs.getString("namabrg"));
                //d.setGambar(rs.getString("gambar"));                
                d.setTarif(rs.getDouble("tarif"));           
                
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
            ResultSet rs = con.statement.executeQuery(  "select count(*) as jml from barang where kodebrg = '" + nomor + "'");
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
            con.preparedStatement = con.db_pbol.prepareStatement("insert into barang (kodebrg, namabrg, tarif) values (?,?,?)");
            con.preparedStatement.setString(1, getBrgModel().getKodebrg());           
            con.preparedStatement.setString(2, getBrgModel().getNamabrg());
            //con.preparedStatement.setString(3, getBrgModel().getGambar());           
            con.preparedStatement.setDouble(4, getBrgModel().getTarif());        
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
            con.preparedStatement = con.db_pbol.prepareStatement("delete from barang where kodebrg  = ? ");
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
            con.preparedStatement = con.db_pbol.prepareStatement("update barang set namabrg = ?, tarif = ?  where  kodebrg = ? ");
            con.preparedStatement.setString(1, getBrgModel().getKodebrg());           
            con.preparedStatement.setString(2, getBrgModel().getNamabrg());
            //con.preparedStatement.setString(3, getBrgModel().getGambar());           
            con.preparedStatement.setDouble(4, getBrgModel().getTarif());
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
    
    public ObservableList<BrgModel>  CariBrg(String kode, String nama) {
        try {    
            ObservableList<BrgModel> 	tableData;
            tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi(); 
            con.bukaKoneksi();
            con.statement = (Statement) con.db_pbol.createStatement();
            ResultSet rs = (ResultSet) con.statement.executeQuery("select * from barang WHERE kodebrg LIKE '" + kode + "%' OR namabrg LIKE '" + nama + "%'");
        int i = 1;
        while(rs.next()){
            BrgModel d = new BrgModel();
            d.setKodebrg(rs.getString("kodebrg"));
            d.setNamabrg(rs.getString("namabrg"));
            //d.setGambar(rs.getString("alamat"));
            d.setTarif(rs.getDouble("tarif"));
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
