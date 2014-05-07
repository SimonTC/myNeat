package stcl.myNEAT.evolution.variationOperators.mutators;

/**
 * This class is responsible for the mutation of connection weights
 * @author Simon
 *
 */
public class WeightMutator extends Mutator {
	double minWeight, maxWeight;
	
	public WeightMutator(double minWeigth, double maxWeight) {
		this.minWeight = minWeigth;
		this.maxWeight=maxWeight;
	}

}
