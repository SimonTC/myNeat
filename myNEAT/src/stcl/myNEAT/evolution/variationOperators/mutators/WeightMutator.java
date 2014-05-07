package stcl.myNEAT.evolution.variationOperators.mutators;

import java.util.Random;

import stcl.myNEAT.evolution.Organism;

/**
 * This class is responsible for the mutation of connection weights
 * @author Simon
 *
 */
public class WeightMutator extends Mutator {
	private double minWeight, maxWeight;
	private Random rand;	
	public WeightMutator(double minWeigth, double maxWeight) {
		this.minWeight = minWeigth;
		this.maxWeight=maxWeight;
		rand = new Random();
	}

	@Override
	/**
	 * Mutates all the weights in the organism
	 */
	public void mutate(Organism o) {
		// TODO Auto-generated method stub
		
	}

}
