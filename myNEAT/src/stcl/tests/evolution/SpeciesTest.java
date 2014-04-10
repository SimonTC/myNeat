package stcl.tests.evolution;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import stcl.myNEAT.evolution.Organism;
import stcl.myNEAT.evolution.Species;

public class SpeciesTest {
	Species s;
	@Before
	public void setUp() throws Exception {
		s = new Species(0, 0);
	}

	@Test
	/*
	 * Test if setup of a new species work
	 */
	public void testSpecies() {
		s = new Species(5, 8);
		assertTrue(s.getSpeciesID() == 5);
		assertTrue(s.getSpeciesAge() == 8);
		assertTrue(s.getNumberOfOrganisms()==0);
	}

	@Test
	/*
	 * Test that number of organisms is incremented and that hasBeenSorted is set to false
	 */
	public void testAddOrganism() {
		Organism o0 = new Organism(0, 0, 0, null, null);
		Organism o1 = new Organism(1, 0, 0, null, null);
		s.addOrganism(o0);
		s.sort();
		s.addOrganism(o1);
		assertTrue(s.getNumberOfOrganisms() == 2);
		assertFalse(s.isSorted());
		
	}

	@Test
	public void testGetReprensentative() {
		Organism r = s.getReprensentative();
		assertTrue(r==null);
		Organism o0 = new Organism(0, 0, 0, null, null);
		s.addOrganism(o0);
		r = s.getReprensentative();
		assertTrue(r!= null);
	}

	@Test
	public void testSortAscending() {
		addRandomOrganisms(10);
		s.sort();
		assertTrue(isSorted());
	}
	
		
	private void addRandomOrganisms(int numberOfOrganisms){
		Organism o;
		Random rand = new Random();
		for (int i = 1; i <= numberOfOrganisms; i++){
			o =  new Organism(i, (double) rand.nextDouble() * 100, 0, null, null);
			s.addOrganism(o);
		}
	}
	
	private boolean isSorted(){
		double e = 0.0001;
		double oldFitness;
		oldFitness = Double.NEGATIVE_INFINITY;
		for (Organism o : s.getOrganisms()){
			if (o.getFitness() < (oldFitness + e)) {
				return false;
			}
		}		
		return true;
	}
	
	@Test
	public void testSetChampion() {
		addRandomOrganisms(10);
		Organism o0 = new Organism(0, 0, 0, null, null);
		assertFalse(s.setChampion(o0));
		
		s.addOrganism(o0);
		assertTrue(s.setChampion(o0));
		
	}

	@Test
	public void testGetChampion() {
		addRandomOrganisms(10);
		Organism o0 = new Organism(0, -500, 0, null, null);
		Organism c = s.getChampion();
		
		assertTrue(c==null);
		
		s.addOrganism(o0);
		s.setChampion(o0);
		c = s.getChampion();
		
		assertTrue(c.equals(o0));
		
	}
	
	

	@Test
	public void testGetChampionBoolean() {
		addRandomOrganisms(10);
		Organism o0 = new Organism(11, -500, 0, null, null);
		s.addOrganism(o0);
		Organism o1 = new Organism(12, 500, 0, null, null);
		s.addOrganism(o1);
		Organism o2 = new Organism(13, 0.005, 0, null, null); //Adding this to make sure max and min are not in the end
		s.addOrganism(o2);
		
		//Test for returning smallest value
		Organism c;
		c = s.getChampion(false);
		assertTrue(c.equals(o0));
		
		Organism o3 = new Organism(14, 0.0052, 0, null, null); //Adding this to make sure max and min are not in the end
		s.addOrganism(o3);
		
		//Test for returning highest value
		Organism d;
		d = s.getChampion(true);
		assertTrue(d.equals(o1));
	}

	@Test
	public void testAddToAge() {
		s.addToAge(3);
		assertTrue(s.getSpeciesAge() == 3);
	}

	@Test
	public void testGetOrganisms() {
		ArrayList<Organism> l = new ArrayList<>();
		
		Random rand = new Random();
		for (int i = 1; i <= 10; i++){
			Organism o =  new Organism(i, (double) rand.nextDouble() * 100, 0, null, null);
			l.add(o);
			s.addOrganism(o);
		}
		
		boolean status = true;
		ArrayList<Organism> addedOrganisms = s.getOrganisms();
		for (Organism o : l){
			if (!addedOrganisms.contains(o)){
				status = false;
				break;
			}
		}
		assertTrue(status);
		
	}

	@Test
	public void testGetNumberOfOrganisms() {
		addRandomOrganisms(10);
		int n = s.getNumberOfOrganisms();
		assertTrue(n==10);
	}

}
