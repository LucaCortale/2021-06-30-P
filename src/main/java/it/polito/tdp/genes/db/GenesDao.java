package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Classificazione;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<Classificazione> getAllLocalizz(){
		String sql = "SELECT DISTINCT c.Localization "
				+ "FROM classification c "
				+ "ORDER BY c.Localization";
		List<Classificazione> result = new ArrayList<Classificazione>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Classificazione genes = new Classificazione(res.getString("c.Localization"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}

	public List<Adiacenza> getAdiacenze(){
		String sql = "SELECT c1.Localization, c2.Localization, COUNT(distinct i.`Type`) AS peso "
				+ "FROM classification c1,classification c2,interactions i "
				+ "WHERE c1.Localization > c2.Localization AND ((c1.GeneID = i.GeneID1 AND c2.GeneID = i.GeneID2) "
				+ "|| (c2.GeneID = i.GeneID1 AND c1.GeneID = i.GeneID2)) "
				+ "GROUP BY c1.Localization, c2.Localization";
		
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Classificazione c1 = new Classificazione(res.getString("c1.Localization"));
				Classificazione c2 = new Classificazione(res.getString("c2.Localization"));
				double peso = res.getDouble("peso");
				
				Adiacenza a = new Adiacenza(c1.getLocalizz(),c2.getLocalizz(),peso);
				result.add(a);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}

	
}
