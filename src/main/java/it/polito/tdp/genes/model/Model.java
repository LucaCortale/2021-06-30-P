package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	GenesDao dao;
	Graph<String, DefaultWeightedEdge> grafo;
	List<String> best;
	
	public Model () {
		
		dao = new GenesDao();
		
	}
	
	public void creaGrafo() {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		for(Classificazione c : dao.getAllLocalizz()) {
			if(!this.grafo.vertexSet().contains(c)) {
				this.grafo.addVertex(c.getLocalizz());
			}
		}
		
		List<Adiacenza> lista = new LinkedList<>(this.dao.getAdiacenze());
		
		for(Adiacenza a : lista) {
		//	if(!this.grafo.containsVertex(a.getC1()) && !this.grafo.containsVertex(a.getC2()))
			Graphs.addEdgeWithVertices(this.grafo, a.getC1(),a.getC2(), a.getPeso());
		}
		
	}
	
	public String Statistiche(Classificazione partenza){
		
		List<Classificazione> result = new ArrayList<>();
		String s = "";
		
		for(String ss  : Graphs.neighborListOf(this.grafo, partenza.getLocalizz())) {
			String  c = ss;
			DefaultWeightedEdge d = this.grafo.getEdge(partenza.getLocalizz(), ss);
			int peso = (int)this.grafo.getEdgeWeight(d);
			s += " "+c+" :       "+peso+"\n";
		}
		
		return s;
		
	}
	
	public List<String> ricercaCammino(Classificazione partenza){
		
		List<String> parziale = new ArrayList<>();
		this.best = new ArrayList<>();
		//List<String> connesse = new ArrayList<>();
		
		
//		ConnectivityInspector<String, DefaultWeightedEdge> ci = new ConnectivityInspector<>(this.grafo);
//		connesse.addAll(ci.connectedSetOf(partenza.getLocalizz()));
		
		parziale.add(partenza.getLocalizz());
		cerca(parziale);
		
		return best;
	}
	
	private void cerca(List<String> parziale) {
		
		
		//SOLUZIONE MIGLIORE?
		if(contaPesoCammino(parziale) > contaPesoCammino(best)) {
			best = new ArrayList<>(parziale);
		}
		//TERMINAZIONE
		for(String s : Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1))) {
			
			if(!parziale.contains(s)) {
				
				parziale.add(s);
			
				cerca(parziale);
				parziale.remove(parziale.size()-1);
				
				
			}
			
		}
		
		
	}

	public double contaPesoCammino(List<String> parziale) {
		double peso=0.0;
		
		if(parziale.size() > 1) {
			for(int i =1;i<parziale.size();i++)

			peso += this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(i-1), parziale.get(i)));
			
		}
		
		return peso;
	}

	public String getVertici() {
		return "#VERTICI : "+this.grafo.vertexSet().size()+"\n";
	}
		
	public String getEdge() {
		return "#ARCHI : "+this.grafo.edgeSet().size()+"\n";
	}
	
	
	public List<Classificazione> getAllLocalizz(){
		return dao.getAllLocalizz();
	}

}