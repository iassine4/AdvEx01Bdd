/**
 * 
 */
package fr.fms.adv;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TestJdbc {
	
	//Methode de validation								//la methode peut échouer
	private static void validateArticle(Article article) throws InvalidArticleException {

	    if (article == null) {
	        throw new InvalidArticleException("Article null : impossible de continuer.");
	    }

	    if (article.getDescription() == null || article.getDescription().trim().isEmpty()) {
	        throw new InvalidArticleException("Description obligatoire (vide ou null).");
	    }

	    if (article.getBrand() == null || article.getBrand().trim().isEmpty()) {
	        throw new InvalidArticleException("Brand obligatoire (vide ou null).");
	    }

	    if (article.getUnitaryPrice() <= 0) {
	        throw new InvalidArticleException("Prix invalide : il doit être strictement > 0.");
	    }
	}


	public static void main(String[] args) throws Exception {
		
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:mariadb://localhost:3300/shop";
		String login = "root";
		String password = "fms2025";
		
		ArrayList<Article> articles = new ArrayList<Article>();
		
		try(Connection connection = DriverManager.getConnection(url, login,password)){
			
			String strSql = "SELECT * FROM t_articles";
			try(Statement statement = connection.createStatement()){
				try(ResultSet resultSet = statement.executeQuery(strSql)){
					while(resultSet.next()) {
						int rsIdUser = resultSet.getInt(1);
						String rsDescription = resultSet.getString(2);
						String rsBrand = resultSet.getString(3);
						double rsPrice = resultSet.getDouble(4);
						articles.add(new Article(rsIdUser, rsDescription, rsBrand, rsPrice));
					}
				}
				statement.close();
			}
			
			for (Article a : articles) System.out.println(a);

		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		// INSERT requete
		Article newArt = new Article();
        newArt.setDescription("");
        newArt.setBrand("Philips");
        newArt.setUnitaryPrice(35);
        
        try {
        	//on tente la validation + l’insert
	        validateArticle(newArt); // peut déclencher InvalidArticleException
	        
			String sql = "INSERT INTO t_articles (description, brand, UnitaryPrice) VALUES (?, ?, ?)";
	
	        try (Connection connection = DriverManager.getConnection(url, login,password);
	             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	
	            ps.setString(1, newArt.getDescription());
	            ps.setString(2, newArt.getBrand());
	            ps.setDouble(3, newArt.getUnitaryPrice());
	
	            int nb = ps.executeUpdate();
	            if (nb == 1) {
	                try (ResultSet keys = ps.getGeneratedKeys()) {
	                    if (keys.next()) newArt.setId(keys.getInt(1));
	                }
	            }
	            System.out.println("INSERT => " + newArt);
	            
	          //pour les problèmes techniques (driver, requête, colonne, etc.)  
	        } catch (SQLException e) {
	            //e.printStackTrace();
	        	System.out.println("Erreur BDD pendant INSERT : " + e.getMessage());
	        }
	        
	     //si l’article est invalide, on n’essaie même pas d’aller en base   
        }catch (InvalidArticleException e) {
        	System.out.println("Erreur de saisie (INSERT) : " + e.getMessage());
        }
        
        //Update
        String updateSql = "UPDATE t_articles SET Description = ?, Brand = ?, UnitaryPrice = ? WHERE IdArticle = ?";

        try (Connection connection = DriverManager.getConnection(url, login, password);
             PreparedStatement ps = connection.prepareStatement(updateSql)) {

            newArt.setDescription("Casque");
            newArt.setBrand("Gondor");
            newArt.setUnitaryPrice(1500);

            ps.setString(1, newArt.getDescription());
            ps.setString(2, newArt.getBrand());
            ps.setDouble(3, newArt.getUnitaryPrice());
            ps.setInt(4, newArt.getId());

            int nb = ps.executeUpdate();
            System.out.println("UPDATE => " + (nb == 1) + " | " + newArt);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        //Delete
        String deleteSql = "DELETE FROM t_articles WHERE IdArticle = ?";

        try (Connection connection = DriverManager.getConnection(url, login, password);
             PreparedStatement ps = connection.prepareStatement(deleteSql)) {

            ps.setInt(1, newArt.getId());
            int nb = ps.executeUpdate();

            System.out.println("DELETE => " + (nb == 1) + " | id=" + newArt.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
}
