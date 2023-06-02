package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import personnel.*;

public class JDBC implements Passerelle 
{
	Connection connection;

	public JDBC()
	{
		try
		{
			System.out.println("new JDBC");
			Class.forName(CredentialsExample.getDriverClassName());
			connection = DriverManager.getConnection(CredentialsExample.getUrl(), CredentialsExample.getUser(), CredentialsExample.getPassword());
			System.out.println(this);
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Pilote JDBC non installé.");
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}
	
	@Override
	public GestionPersonnel getGestionPersonnel() 
	{
		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try 
		{
			String requete = "select * from ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete);
			while (ligues.next())
				gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return gestionPersonnel;
	}

	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible 
	{
		close();
	}
	
	public void close() throws SauvegardeImpossible
	{
		try
		{
			if (connection != null)
				connection.close();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}
	
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into ligue (nom) values(?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());		
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			System.out.println(id);
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}
	@Override
	public int insertEmploye(Employe employe) throws SauvegardeImpossible
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("INSERT into employe (nom,premom,mail,password,id_ligue,DateArrive) values(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1,employe.getNom());
			instruction.setString(2,employe.getPrenom());
			instruction.setString(3,employe.getMail());
			instruction.setString(4,employe.getPassword());
			instruction.setInt(5,employe.getLigue().getId());
			instruction.setString(6,employe.getArrive().toString());
//			instruction.setString(7,employe.getDepart().toString());
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			System.out.println(id);
			return id.getInt(1);
		}
		catch(SQLException exception)
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}
		
	}
}
