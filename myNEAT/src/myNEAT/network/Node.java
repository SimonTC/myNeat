package myNEAT.network;

import java.util.ArrayList;

import myNEAT.network.activationFunctions.ActivationFunction;

public class Node {
	private int nodeType;
	private int nodeID;
	private double input, output;
	private boolean activated;
	private ActivationFunction activationFunction;
	
	public Node(int id, int nodeType, ActivationFunction activationFunction ){
		this.nodeID=id;
		this.nodeType = nodeType;
		this.activationFunction = activationFunction;
		input = 0;
		output = 0;
		activated=false;
	}
	
	/**
	 * Activates the node. Resets total input to zero after activation
	 */
	public void activate(){
		output = activationFunction.getActivation(input);
		activated = true;
		input = 0;
	}
	
	/**
	 * Adds the input to the total input for the node
	 * @param input
	 */
	public void addInput(double input){
		this.input += input;
	}
	
	
	/**
	 * Flushes the node and resets input, output and inputNExt to 0
	 */
	public void flush(){
		this.input=0;
		this.output=0;
		activated = false;
	}
	
	public double getOutput(){
		return this.output;
	}
	
	public boolean activated(){
		return this.activated;
	}
	
	public void deActivate(){
		this.activated = false;
	}
	
	public int getType(){
		return this.nodeType;
	}

	public int getNodeID() {
		return nodeID;
	}

	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}
	
}
