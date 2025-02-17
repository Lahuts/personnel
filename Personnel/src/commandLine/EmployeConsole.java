package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.time.format.DateTimeParseException;

import commandLineMenus.ListOption;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.Employe;
import personnel.MauvaiseDate;
import personnel.SauvegardeImpossible;

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
			menu.add(changerDateArrive(employe));
			menu.addBack("q");
			return menu;
	}


	private Option changerNom(final Employe employe)
	{
		return new Option("Changer le nom", "n", () -> {try {
			employe.setNom(getString("Nouveau nom : "));
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	
	private Option changerPrenom(final Employe employe)
	{
		return new Option("Changer le prénom", "p", () -> {try {
			employe.setPrenom(getString("Nouveau prénom : "));
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	
	private Option changerMail(final Employe employe)
	{
		return new Option("Changer le mail", "e", () -> {try {
			employe.setMail(getString("Nouveau mail : "));
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	
	private Option changerPassword(final Employe employe)
	{
		return new Option("Changer le password", "x", () -> {try {
			employe.setPassword(getString("Nouveau password : "));
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	private Option supprimerEmploye(final Employe employe) {
		return new Option ("Supprimer l'employé","s", () -> 
		{
		  System.out.println("L'employé a été supprimer appuyer sur q pour revenir à l'écran précédent");
		  try {
			employe.remove();
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	  
		});
		
	}
	private Option changerDateDepart(final Employe employe) {
		return new Option("Modifier la date de départ","d",()->
		{			
			try 
			{
				employe.setDepart(getString("Veuillez saisir la date au format (année-mois-jour) : "));
			} 
			catch (DateTimeParseException e) 
			{
				System.out.println("Format incorrect | format : année-mois-jour");
			}
			catch (MauvaiseDate e) 
			{
				System.out.println("Veuillez saisir une date superieur à "+employe.getArrive());
				
			} catch (SauvegardeImpossible e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	private Option changerDateArrive(final Employe employe) {
		return new Option("Modifier la date d'arrivé","f",()->
		{			
			try 
			{
				try {
					employe.setArrive(getString("Veuillez saisir la date au format (année-mois-jour) : "));
				} catch (SauvegardeImpossible e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			catch (DateTimeParseException e) 
			{
				System.out.println("Format incorrect | format : année-mois-jour");
			}
			catch (MauvaiseDate e) 
			{
				System.out.println("Veuillez saisir une date inférieur à "+employe.getDepart());
				
			}
		});
	}


}
