package personnel;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Employé d'une ligue hébergée par la M2L. Certains peuvent 
 * être administrateurs des employés de leur ligue.
 * Un seul employé, rattaché à aucune ligue, est le root.
 * Il est impossible d'instancier directement un employé, 
 * il faut passer la méthode {@link Ligue#addEmploye addEmploye}.
 */

public class Employe implements Serializable, Comparable<Employe>
{
	private static final long serialVersionUID = 4795721718037994734L;
	private String nom, prenom, password, mail;
	private LocalDate date_arrive, date_depart;
	private Ligue ligue;
	private GestionPersonnel gestionPersonnel;
	
	public Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String password, LocalDate arrive_en, LocalDate partit_en)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.mail = mail;
		this.ligue = ligue;
		this.date_arrive = arrive_en;
		this.date_depart = partit_en;
	}
	
	/**
	 * Retourne vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @return vrai ssi l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @param ligue la ligue pour laquelle on souhaite vérifier si this 
	 * est l'admininstrateur.
	 */
	
	public boolean estAdmin(Ligue ligue)
	{
		return ligue.getAdministrateur() == this;
	}
	
	/**
	 * Retourne vrai ssi l'employé est le root.
	 * @return vrai ssi l'employé est le root.
	 */
	
	public boolean estRoot()
	{
		return gestionPersonnel.getRoot() == this;
	}
	
	/**
	 * Retourne le nom de l'employé.
	 * @return le nom de l'employé. 
	 */
	
	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom de l'employé.
	 * @param nom le nouveau nom.
	 */
	
	public void setNom(String nom)
	{
		this.nom = nom;
	}

	/**
	 * Retourne le prénom de l'employé.
	 * @return le prénom de l'employé.
	 */
	
	public String getPrenom()
	{
		return prenom;
	}
	
	/**
	 * Change le prénom de l'employé.
	 * @param prenom le nouveau prénom de l'employé. 
	 */

	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
	}

	/**
	 * Retourne le mail de l'employé.
	 * @return le mail de l'employé.
	 */
	
	public String getMail()
	{
		return mail;
	}
	
	/**
	 * Change le mail de l'employé.
	 * @param mail le nouveau mail de l'employé.
	 */

	public void setMail(String mail)
	{
		this.mail = mail;
	}

	/**
	 * Retourne vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @return vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @param password le password auquel comparer celui de l'employé.
	 */
	
	public boolean checkPassword(String password)
	{
		return this.password.equals(password);
	}

	/**
	 * Change le password de l'employé.
	 * @param password le nouveau password de l'employé. 
	 */
	
	public void setPassword(String password)
	{
		this.password= password;
	}

	/**
	 * Retourne la ligue à laquelle l'employé est affecté.
	 * @return la ligue à laquelle l'employé est affecté.
	 */
	
	public Ligue getLigue()
	{
		return ligue;
	}

	/* retourne la date d'arrive d'un employer
	@return retourne la date d'arrive d'un employer  */
	public LocalDate getArrive() {
		return this.date_arrive;
	}

	/* retourne la date de depart d'un employer
	@return retourne la date de depart d'un employer  */	
	public LocalDate getDepart() {
		return this.date_depart;
	}

	// TODO: retirer depart si arrivé
	/* affecte une nouvelle valeur a la date d'arrive
	@param un objet LocalDate qui represente la nouvelle date d'arrive */
	public void setArrive(LocalDate with_date) throws MauvaiseDate {
		
		if ( this.date_arrive != null && with_date.isBefore(this.date_arrive) ) {
			throw new MauvaiseDate("impossible de definir une date de d'arrive qui est avant l'ancienne date d'arrive");
		}
		
		if ( this.date_depart != null ) {
			this.date_depart = null;
		}
		
		this.date_arrive = with_date;
	}

	// TODO: check si depart < a arrivé
	/* affecte une nouvelle valeur a la date depart
	@param un objet LocalDate qui represente la nouvelle date de depart */
	public void setDepart(LocalDate with_date) throws MauvaiseDate{
		
		if ( this.date_arrive == null ) {
			throw new MauvaiseDate("impossible de definir uen date de depart si il n'y a pas de date d'arrivé");
		}
		
		if ( with_date.isBefore(this.date_arrive) ) {
			throw new MauvaiseDate("la date de départ de peux pas etre avant la date d'arrivé");
		}
		
		this.date_depart = with_date;
	}
	
	// TODO: ajouter method pour ajouter depuis un string, throw si pas bon
	public void setDepart(String with_date) throws DateTimeParseException, MauvaiseDate {
		LocalDate date = LocalDate.parse(with_date);
		this.setDepart(date);
	}
	public void setArrive(String with_date) throws MauvaiseDate {
		LocalDate date = LocalDate.parse(with_date);
		this.setDepart(date);
	}
	

	/**
	 * Supprime l'employé. Si celui-ci est un administrateur, le root
	 * récupère les droits d'administration sur sa ligue.
	 */
	
	public void remove()
	{
		Employe root = gestionPersonnel.getRoot();
		if (this != root)
		{
			if (estAdmin(getLigue()))
				getLigue().setAdministrateur(root);
			getLigue().remove(this);
		}
		else
			throw new ImpossibleDeSupprimerRoot();
	}

	@Override
	public int compareTo(Employe autre)
	{
		int cmp = getNom().compareTo(autre.getNom());
		if (cmp != 0)
			return cmp;
		return getPrenom().compareTo(autre.getPrenom());
	}
	
	@Override
	public String toString()
	{
		String res = nom + " " + prenom + " " + mail + " " + this.date_arrive +" "+this.date_depart+ " (";
		if (estRoot())
			res += "super-utilisateur";
		else
			res += ligue.toString();
		return res + ")";
	}
}
