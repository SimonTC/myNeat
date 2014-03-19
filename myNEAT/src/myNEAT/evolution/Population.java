package myNEAT.evolution;

import java.util.ArrayList;

public class Population {
	//Containers
	private ArrayList<Organism> organisms;
	private ArrayList<Species> species;
	
	//Fitness info
		//Current generation
		double minFitness;
		double avgFitness;
		double maxFitness;
		double variance;
		double standardDeviation;
	
		//Fitness info used for stagnation detection
		double highestFitness;
		int generationsSinceChangeInHighestFitness;
		
	
	
	//Innovation info
	
	
	//General info
	int generationNumber;
	int lastSpeciesID; //Used when creating new species
	int lastOrganismID; //Used when new organisms are created
	
	
	
}
