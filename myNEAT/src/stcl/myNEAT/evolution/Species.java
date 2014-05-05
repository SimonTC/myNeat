package stcl.myNEAT.evolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import stcl.myNEAT.evolution.comparators.CompOrganismsFitness;
/**
 * The Species class is a container holding all the organisms that are part of this species
 * @author Simon
 *
 */
public class Species {
	private int speciesID;
	private int speciesAge;
	private Organism champion;
	private ArrayList<Organism> organisms;
	private boolean hasBeenSorted;
	private int numberOfOrganisms;
	private double avgFitness;
	private double maxFitness;
	private double minFitness;
	
	public Species(int speciesID, int speciesAge){
		this.speciesID = speciesID;
		this.speciesAge = speciesAge;
		this.champion = null;
		this.hasBeenSorted = false;
		this.numberOfOrganisms = 0;
		this.organisms = new ArrayList<>();
	}
	
	/**
	 * Adds an organism to the species
	 * Adding an organism breaks the sorting of the species list.
	 * @param o
	 */
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
	 * @return the current champion
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
	
	/**
	 * Computes min, max and avg fitness of the organisms in this species
	 */
	public void computeFitnessValues(){
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;
		double avg;
		double total = 0;
		double fitness;
		
		for (Organism o : organisms){
			fitness = o.getFitness();
			if (fitness < min) min = fitness;
			if (fitness > max) max = fitness;
			total += fitness;
		}
		
		double size = organisms.size();
		avg = total / size;
		
		minFitness = min;
		maxFitness = max;
		avgFitness = avg;
		
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

	public double getAvgFitness() {
		return avgFitness;
	}

	public void setAvgFitness(double avgFitness) {
		this.avgFitness = avgFitness;
	}

	public double getMaxFitness() {
		return maxFitness;
	}

	public void setMaxFitness(double maxFitness) {
		this.maxFitness = maxFitness;
	}

	public double getMinFitness() {
		return minFitness;
	}

	public void setMinFitness(double minFitness) {
		this.minFitness = minFitness;
	}
	
	
}
