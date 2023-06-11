package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import personnel.*;

public class JDBC implements Passerelle 
{
	Connection connection;

	public JDBC()
	{
		try
		{
			System.out.println("new JDBC");
			Class.forName(Credentials.getDriverClassName());
			connection = DriverManager.getConnection(Credentials.getUrl(), Credentials.getUser(), Credentials.getPassword());
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
			{
				gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
			}
			for(Ligue ligue : gestionPersonnel.getLigues()) {
				String requeteEmp = "SELECT * from employe where `id_ligue` = "+ligue.getId();
				Statement instructionEmp = connection.createStatement();
				ResultSet employes = instructionEmp.executeQuery(requeteEmp);
				while(employes.next())
				{
					try {
						ligue.addEmploye(employes.getString(2), employes.getString(3), employes.getString(4), employes.getString(5),LocalDate.parse(employes.getString(6)),LocalDate.parse(employes.getString(7)),employes.getInt(1),employes.getInt(9));
					} catch (SauvegardeImpossible e) {
						e.printStackTrace();
					}
					catch(NullPointerException npe) {
						try {
							ligue.addEmploye(employes.getString(2), employes.getString(3), employes.getString(4), employes.getString(5),LocalDate.parse(employes.getString(6)),null,employes.getInt(1),employes.getInt(9));
						} catch (SauvegardeImpossible e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			}
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return gestionPersonnel;
	}
	
	/*
	 * Check le Super Admin
	 */
	public String[] checkAdmin() {
		try {
			String requeteAdmin = "SELECT mail,password FROM employe WHERE id = -1";
			Statement instructionAdmn = connection.createStatement();
			ResultSet admin = instructionAdmn.executeQuery(requeteAdmin);
			if(admin.next()) {
				return new String[] {admin.getString(1),admin.getString(2)} ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public int[] checkUser(String email,String password) {
		try {
			String requeteUser = "Select password,id_ligue,id_role FROM employe where mail ='"+email+"'";
			Statement instructionUsr = connection.createStatement();
			ResultSet user = instructionUsr.executeQuery(requeteUser);
			if(user.next()) {
				if(user.getString(1).equals(password)) {
					int[] data = new int[2];
					data[0] = user.getInt(2);
					data[1] = user.getInt(3);
					return data;
				}
			}
			else {
				int[] data = new int[2];
				data[0] = -1;
				data[1] = -1;
				return data;
				}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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

			instruction = connection.prepareStatement("INSERT into employe (nom,premom,mail,password,id_ligue,DateArrive,id_role) values(?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);

			instruction.setString(1,employe.getNom());
			instruction.setString(2,employe.getPrenom());
			instruction.setString(3,employe.getMail());
			instruction.setString(4,employe.getPassword());
			instruction.setInt(5,employe.getLigue().getId());
			instruction.setString(6,employe.getArrive().toString());

			instruction.setInt(7,0);

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

	

	@Override
	public void delete(Ligue ligue) throws SauvegardeImpossible {
		// TODO Auto-generated method stub
		PreparedStatement instruction;
		try {
			instruction = connection.prepareStatement("DELETE FROM employe WHERE `id_ligue` = ?",Statement.RETURN_GENERATED_KEYS);
			instruction.setInt(1,ligue.getId());
			instruction.executeUpdate();
			
			instruction = connection.prepareStatement("DELETE FROM ligue WHERE `id` = ?",Statement.RETURN_GENERATED_KEYS);
			instruction.setInt(1,ligue.getId());
			instruction.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
		
	}

	@Override
	public void deleteEmploye(Employe employe) throws SauvegardeImpossible {
		
		PreparedStatement instruction;
		try {
			instruction = connection.prepareStatement("DELETE FROM employe WHERE `id` = ?",Statement.RETURN_GENERATED_KEYS);
			instruction.setInt(1,employe.getId());
			instruction.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}

	@Override
	public int update(Ligue ligue) throws SauvegardeImpossible {
		PreparedStatement instruction;
		try {
			instruction = connection.prepareStatement("Update ligue SET nom = ? WHERE `id` = ?",Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1,ligue.getNom());
			instruction.setInt(2,ligue.getId());
			instruction.executeUpdate();
			
	
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}

	@Override
	public int updateEmploye(Employe employe,String columnName,String value) throws SauvegardeImpossible {
		PreparedStatement instruction;
		try {
			instruction = connection.prepareStatement("Update employe SET "+columnName+" = ? WHERE `id` = ?",Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1,value);
			instruction.setInt(2,employe.getId());
			instruction.executeUpdate();
	
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SauvegardeImpossible(e);
		}
	}

}
