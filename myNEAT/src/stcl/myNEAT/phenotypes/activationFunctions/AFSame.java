package stcl.myNEAT.phenotypes.activationFunctions;
/**
 * Activation function that passes the input on to the output.
 * The input does not change
 * Functiontype: 0
 * @author Simon
 *
 */
public class AFSame extends ActivationFunction {

	public AFSame(){
		this.functionType = SAME;
	}
	
	@Override
	public double getActivation(double input) {
		return input;
	}

	
}
