package stcl.myNEAT.evolution.variationOperators.mutators;

import stcl.myNEAT.evolution.variationOperators.VariationOperator;

public abstract class Mutator implements VariationOperator {
	public abstract void mutate(Organism o);
	
}
