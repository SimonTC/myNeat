package myNEAT.network.activationFunctions;

public class AFBias extends ActivationFunction {
	
	double biasValue;
	
	public AFBias(double biasValue){
		this.biasValue = biasValue;
		this.functionType = BIAS;
	}
	
	@Override
	public double getActivation(double input) {
		return biasValue;
	}

	public boolean equals(Object activationFunction){
		ActivationFunction af = (ActivationFunction) activationFunction;
		if (this.functionType != (af.getType())) return false;
		double e = 0.0001;
		if (!(this.biasValue > af.getActivation(0) - e && this.biasValue < af.getActivation(0) + e )) return false;
		return true;
	}

}
