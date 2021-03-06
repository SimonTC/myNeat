package stcl.myNEAT.evolution;

import stcl.myNEAT.genotypes.Genome;
import stcl.myNEAT.phenotypes.NeuralNetwork;
/**
 * The organism is the class that binds the genotype and the phenotype together
 * This is the class that is used in the experiments
 * @author Simon
 *
 */
public class Organism {
	private int id;
	double fitness;
	int age;
	NeuralNetwork phenotype;
	Genome genotype;
	
	
	public Organism(int id, double initialFitness, int age, Genome genotype, NeuralNetwork phenotype){
		this.id = id;
		this.fitness = initialFitness;
		this.genotype = genotype;
		this.phenotype=phenotype;
	}
	
	public void addFitness(double fitness){
		this.fitness += fitness;
	}
	
	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public int getId() {
		return id;
	}

	public int getAge() {
		return age;
	}

	public NeuralNetwork getNetwork() {
		return phenotype;
	}
	
	public boolean equals(Object organism){
		Organism o = (Organism) organism;
		if (this.id == o.id) return true;
		return false;		
	}
	
}
