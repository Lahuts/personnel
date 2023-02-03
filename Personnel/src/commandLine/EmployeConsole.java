package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.time.LocalDate;

import commandLineMenus.ListOption;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.Employe;

public class EmployeConsole 
{
	private Option afficher(final Employe employe)
	{
		return new Option("Afficher l'employé", "l", () -> {System.out.println(employe);});
	}

	ListOption<Employe> editerEmploye()
	{
		return (employe) -> editerEmploye(employe);		
	}
	ListOption<Employe> gestionEmploye(){
		return (employe) -> gestionEmploye(employe);
	}

	
	Option gestionEmploye(Employe employe) {
		Menu menu = new Menu("Selectionner le compte " + employe.getNom(), "g");
		menu.add(afficher(employe));
		menu.add(supprimerEmploye(employe));
		menu.add(editerEmploye(employe));
		menu.addBack("q");
		menu.setAutoBack(true);
		return menu;
	}
	Option editerEmploye(Employe employe)
	{
			Menu menu = new Menu("Modifier le compte " + employe.getNom(), "c");
			menu.add(changerNom(employe));
			menu.add(changerPrenom(employe));
			menu.add(changerMail(employe));
			menu.add(changerPassword(employe));
			menu.add(changerDateDepart(employe));
			menu.addBack("q");
			return menu;
	}


	private Option changerNom(final Employe employe)
	{
		return new Option("Changer le nom", "n", () -> {employe.setNom(getString("Nouveau nom : "));});
	}
	
	private Option changerPrenom(final Employe employe)
	{
		return new Option("Changer le prénom", "p", () -> {employe.setPrenom(getString("Nouveau prénom : "));});
	}
	
	private Option changerMail(final Employe employe)
	{
		return new Option("Changer le mail", "e", () -> {employe.setMail(getString("Nouveau mail : "));});
	}
	
	private Option changerPassword(final Employe employe)
	{
		return new Option("Changer le password", "x", () -> {employe.setPassword(getString("Nouveau password : "));});
	}
	private Option supprimerEmploye(final Employe employe) {
		return new Option ("Supprimer l'employé","s", () -> 
		{
		  System.out.println("L'employé a été supprimer appuyer sur q pour revenir à l'écran précédent");
		  employe.remove();
		  
		  
		});
	}
	private Option changerDateDepart(final Employe employe) {
		return new Option("Modifier la date de départ","d",()->
		{
			int day = Integer.parseInt(getString("Jour :"));
			int month = Integer.parseInt(getString("Mois :"));
			int year = Integer.parseInt(getString("Année :"));
			
			employe.setDepart(LocalDate.of(year, month, day));
		});
	}


}
