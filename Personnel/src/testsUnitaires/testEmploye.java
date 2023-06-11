package testsUnitaires;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import personnel.*;

class testEmployer
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void test_date_arrive() throws SauvegardeImpossible {
		LocalDate now = LocalDate.now();
		Employe an_employer = new Employe(null, null, "bob", "", "", "toor", now, now,0);
		assertEquals(an_employer.getArrive(), now);
	}
	@Test
	void test_date_depart() throws SauvegardeImpossible {
		LocalDate now = LocalDate.now();
		Employe an_employer = new Employe(null, null, "bob", "", "", "toor", now, now,0);
		assertEquals(an_employer.getDepart(), now);
	}
	
	@Test
	void test_date_depart_bad_text() throws SauvegardeImpossible {
		LocalDate now = LocalDate.now();
		Employe an_employer = new Employe(null, null, "bob", "", "", "toor", now, now,0);
	    assertThrows(DateTimeParseException.class, () -> {
	    	an_employer.setDepart("hello-12-07");
	    });
	}
	@Test
	void test_date_arrive_bad_text() throws SauvegardeImpossible {
		LocalDate now = LocalDate.now();
		Employe an_employer = new Employe(null, null, "bob", "", "", "toor", now, now,0);	
	    assertThrows(DateTimeParseException.class, () -> {
	    	an_employer.setArrive("hello-12-07");
	    });
	}
	
	@Test
	void test_date_depart_before() throws SauvegardeImpossible {
		LocalDate now = LocalDate.now();
		Employe an_employer = new Employe(null, null, "bob", "", "", "toor", now, now,0);
	    assertThrows(MauvaiseDate.class, () -> {
	    	an_employer.setDepart("2002-12-07");
	    });
	}
	@Test
	void test_date_arrive_before() throws SauvegardeImpossible {
		LocalDate now = LocalDate.now();
		Employe an_employer = new Employe(null, null, "bob", "", "", "toor", now, now,0);
	    assertThrows(MauvaiseDate.class, () -> {
	    	an_employer.setArrive("2002-12-07");
	    });
	}
	@Test
	void test_date_depart_add_before_date_arrive() throws MauvaiseDate, SauvegardeImpossible {
		LocalDate now = LocalDate.now();
		Employe an_employer = new Employe(null, null, "bob", "", "", "toor", null, null,0);
		an_employer.setArrive("2022-12-07");
	    assertThrows(MauvaiseDate.class, () -> {
	    	an_employer.setDepart("2021-12-07");
	    });
	}
	
}
