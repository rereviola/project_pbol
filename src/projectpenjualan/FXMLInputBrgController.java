/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projectpenjualan;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FXMLInputBrgController implements Initializable {

    boolean editdata = false;
    
    @FXML
    private Button btnkeluar;
    @FXML
    private Button btnbatal;
    @FXML
    private Button btnsimpan;
    @FXML
    private TextField txttarif;
    @FXML
    private TextField txtnamabrg;
    @FXML
    private TextField txtkode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void execute(BrgModel d){
        if(!d.getKodebrg().isEmpty()){
          editdata=true;
          txtkode.setText(d.getKodebrg());
          txtnamabrg.setText(d.getNamabrg());          
          txttarif.setText(String.valueOf(d.getTarif()));          
          txtkode.setEditable(false);          
          txtnamabrg.requestFocus();         
        }
    }


    @FXML
    private void simpanklik(ActionEvent event) {
      BrgModel n=new BrgModel();        
        n.setKodebrg(txtkode.getText());
        n.setNamabrg(txtnamabrg.getText());     
        n.setTarif(Double.parseDouble(txttarif.getText()));
        FXMLDocumentController.dtbrg.setBrgModel(n);
        if(editdata){
            if(FXMLDocumentController.dtbrg.update()){
               Alert a=new Alert(Alert.AlertType.INFORMATION,"Data berhasil diubah",ButtonType.OK);
               a.showAndWait();   
               txtkode.setEditable(true);        
               batalklik(event);                
            } else {               
                Alert a=new Alert(Alert.AlertType.ERROR,"Data gagal diubah",ButtonType.OK);
                a.showAndWait(); 
            }
            }else if(FXMLDocumentController.dtbrg.validasi(n.getKodebrg())<=0){
            if(FXMLDocumentController.dtbrg.insert()){
               Alert a=new Alert(Alert.AlertType.INFORMATION,"Data berhasil disimpan",ButtonType.OK);
               a.showAndWait();            
               batalklik(event);
            } else {
               Alert a=new Alert(Alert.AlertType.ERROR,"Data gagal disimpan",ButtonType.OK);
               a.showAndWait();            
            }
        }else{Alert a=new Alert(Alert.AlertType.ERROR,"Data sudah ada",ButtonType.OK);
            a.showAndWait();
            txtkode.requestFocus();
        }
    }

    @FXML
    private void batalklik(ActionEvent event) {
        txtkode.setText("");        
        txtnamabrg.setText("");
        txttarif.setText("");  
        txtkode.requestFocus();
    }

    @FXML
    private void keluarklik(ActionEvent event) {
        btnkeluar.getScene().getWindow().hide();
    }
    
}
