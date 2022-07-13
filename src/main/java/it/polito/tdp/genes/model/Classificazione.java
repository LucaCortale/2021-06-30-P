package it.polito.tdp.genes.model;

public class Classificazione {
	
	private Genes geneId;
	private String localizz;
	
	public Classificazione(Genes geneId, String localizz) {
		super();
		this.geneId = geneId;
		this.localizz = localizz;
	}
	
	public Classificazione( String localizz) {
	
		this.localizz = localizz;
	}
	
	public Genes getGeneId() {
		return geneId;
	}
	public void setGeneId(Genes geneId) {
		this.geneId = geneId;
	}
	public String getLocalizz() {
		return localizz;
	}
	public void setLocalizz(String localizz) {
		this.localizz = localizz;
	}

	@Override
	public String toString() {
		return  localizz ;
	}
	
	

}
