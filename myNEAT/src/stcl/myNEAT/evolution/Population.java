package stcl.myNEAT.evolution;

import java.util.ArrayList;

import stcl.myNEAT.genotypes.Genome;

public class Population {
	/*Containers*/
	private ArrayList<Organism> organisms;
	private ArrayList<Species> species;
	
	/*Fitness info*/
		//Current generation
		private double minFitness;
		private double avgFitness;
		private double maxFitness;
		private double variance;
		private double standardDeviation;
	
		//Fitness info used for stagnation detection
		private double highestFitnessEver;
		private int generationWithLastFitnessIncrease;
		
	
	
	/*Innovation info*/
	private ArrayList<Innovation> innovations; //This list holds all innovations that has happened during the epoch
	private int lastInnovationNumber; 
	private int lastNodeID;
		
	
	/*General info*/
	private int generationNumber;
	private int lastSpeciesID; //Used when creating new species
	private int lastOrganismID; //Used when new organisms are created
	
	/**
	 * Creates a new population based on a genome file.
	 * @param genomeFilePath path to the genome file
	 * @param size Number of organisms in the population
	 */
	public Population(String genomeFilePath, int size){
		//Create ancestor
		
		
		//Clone ancestor and mutate the clones (only weight)
		
		
		//Speciate organisms
	}
	
	/**
	 * Creates a copy of the population in the population file
	 * @param populationFilePath path to the generation file
	 */
	public Population (String populationFilePath){
		System.out.println("Not yet implemented");
	}
	
	
	
}
