package stcl.myNEAT.evolution.comparators;

import java.util.Comparator;

import stcl.myNEAT.evolution.Organism;

public class CompOrganismsFitness implements Comparator<Organism> {

	@Override
	public int compare(Organism o1, Organism o2) {
		double f1 = o1.getFitness();
		double f2 = o2.getFitness();
		if(f1 < f2) return -1;
		if(f1>f2) return +1;
		return 0;
	}

}
