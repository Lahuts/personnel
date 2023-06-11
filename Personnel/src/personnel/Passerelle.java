package personnel;

public interface Passerelle 
{
	public GestionPersonnel getGestionPersonnel();
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel)  throws SauvegardeImpossible;
	public int insert(Ligue ligue) throws SauvegardeImpossible;
	public int insertEmploye(Employe employe) throws SauvegardeImpossible;

	public void delete(Ligue ligue) throws SauvegardeImpossible;
	public void deleteEmploye(Employe employe) throws SauvegardeImpossible;
	public int update(Ligue ligue) throws SauvegardeImpossible;
	public int updateEmploye(Employe employe,String columnName,String value) throws SauvegardeImpossible;
	public String[] checkAdmin();
	public int[] checkUser(String email,String password);

}
