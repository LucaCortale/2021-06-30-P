package it.polito.tdp.genes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.genes.model.Classificazione;
import it.polito.tdp.genes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnStatistiche;

    @FXML
    private Button btnRicerca;

    @FXML
    private ComboBox<Classificazione> boxLocalizzazione;

    @FXML
    private TextArea txtResult;

    @FXML
    void doRicerca(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	Classificazione c = boxLocalizzazione.getValue();
    	List<String> best = this.model.ricercaCammino(c);
    	for(String s : best) {
    		txtResult.appendText(" "+s+"\n");
    	}
    
    	txtResult.appendText(""+this.model.contaPesoCammino(best));
    	
    }

    @FXML
    void doStatistiche(ActionEvent event) {

    	txtResult.clear();
    	
    	Classificazione c = boxLocalizzazione.getValue();
    	
    	txtResult.appendText(this.model.Statistiche(c));
    	
    	
    }

    @FXML
    void initialize() {
        assert btnStatistiche != null : "fx:id=\"btnStatistiche\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicerca != null : "fx:id=\"btnRicerca\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxLocalizzazione != null : "fx:id=\"boxLocalizzazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
		boxLocalizzazione.getItems().addAll(this.model.getAllLocalizz());
		
		this.model.creaGrafo();
		txtResult.appendText(this.model.getVertici());
    	txtResult.appendText(this.model.getEdge());
	}
}
