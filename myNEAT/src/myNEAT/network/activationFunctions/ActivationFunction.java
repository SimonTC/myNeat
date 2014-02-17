package myNEAT.network.activationFunctions;

public abstract class ActivationFunction {
	
	/*List of activation function types */
	protected final int SAME = 0;
	protected final int BIAS = 1;
	protected final int SIGMOID = 2;
	
	protected int functionType;
		
	public abstract double getActivation(double input);
	
	public int getType(){
		return this.functionType;
	}
	
	public boolean equals (Object activationFunction){
		return this.functionType ==
				((ActivationFunction)activationFunction).getType();
	}
}
