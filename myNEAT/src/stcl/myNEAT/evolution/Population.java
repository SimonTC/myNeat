package stcl.myNEAT.evolution;

import java.util.ArrayList;

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
	
	
	/*General info*/
	private int generationNumber;
	private int lastSpeciesID; //Used when creating new species
	private int lastOrganismID; //Used when new organisms are created
	
	
	
}
