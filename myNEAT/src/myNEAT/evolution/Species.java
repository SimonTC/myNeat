package myNEAT.evolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import myNEAT.evolution.comparators.CompOrganismsFitness;

public class Species {
	private int speciesID;
	private int speciesAge;
	private Organism champion;
	private ArrayList<Organism> organisms;
	private boolean hasBeenSorted;
	private int numberOfOrganisms;
	
	public Species(int speciesID, int speciesAge){
		this.speciesID = speciesID;
		this.speciesAge = speciesAge;
		this.champion = null;
		this.hasBeenSorted = false;
		this.numberOfOrganisms = 0;
		this.organisms = new ArrayList<>();
	}
	
	public void addOrganism(Organism o){
		organisms.add(o);
		hasBeenSorted = false;
		numberOfOrganisms++;
	}
	
	/**
	 * Returns the first organism in the list as a representative for the species
	 * @return
	 */
	public Organism getReprensentative(){
		if (organisms.size() > 0){
			return organisms.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * Sorts the organisms by fitness in ascending order
	 */
	public void sort(){
		Comparator<Organism> c = new CompOrganismsFitness();				
		Collections.sort(organisms, c);	
		hasBeenSorted = true;
	}
	
	/**
	 * 
	 * @return the current set champion
	 */
	public Organism getChampion(){
		return champion;
	}
	
	/**
	 * Sorts the organisms and returns a champion based on the value of highestFitness
	 * @param highestFitness true: return organism with highest fitness. False: Return organism with lowest fitness
	 * @return
	 */
	public Organism getChampion(boolean highestFitness){
		if (!hasBeenSorted){
			this.sort();
		}
		if (highestFitness){
			this.champion = organisms.get(numberOfOrganisms - 1);
		} else {
			this.champion = organisms.get(0);
		}
		return champion;
	}
	
	public void addToAge(int ageIncrement){
		this.speciesAge =+ ageIncrement; 
	}
	
	public boolean setChampion(Organism champion){
		if (organisms.contains(champion)){
			this.champion = champion;
			return true;
		} else {
			return false;
		}
			
	}
	
	public ArrayList<Organism> getOrganisms(){
		return this.organisms;
	}

	public int getSpeciesAge() {
		return speciesAge;
	}

	public void setSpeciesAge(int speciesAge) {
		this.speciesAge = speciesAge;
	}

	public int getSpeciesID() {
		return speciesID;
	}

	public int getNumberOfOrganisms() {
		return numberOfOrganisms;
	}
	
	public boolean isSorted(){
		return this.hasBeenSorted;
	}
	
	
}
