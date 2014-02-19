package myNEAT.evolution;

import myNEAT.network.NeuralNetwork;

public class Organism {
	private int id;
	double fitness;
	int age;
	NeuralNetwork network;
	
	public Organism(int id, double initialFitness, int age, NeuralNetwork network){
		this.id = id;
		this.fitness = initialFitness;
		this.network = network;
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
		return network;
	}
	
	public boolean equals(Object organism){
		Organism o = (Organism) organism;
		if (this.id == o.id) return true;
		return false;		
	}
	
}
