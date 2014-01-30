package myNEAT.network.activationFunctions;

public class AFBias extends ActivationFunction {
	
	double biasValue;
	
	public AFBias(double biasValue){
		this.biasValue = biasValue;
	}
	
	@Override
	public double getActivation(double input) {
		return biasValue;
	}

	@Override
	public int getType() {
		return BIAS;
	}

}
