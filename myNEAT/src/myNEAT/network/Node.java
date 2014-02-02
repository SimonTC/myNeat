package myNEAT.network;

import java.util.ArrayList;

import myNEAT.network.activationFunctions.ActivationFunction;

public class Node {
	private int nodeType;
	private int nodeID;
	private double input, output;
	private boolean activated;
	private ActivationFunction activationFunction;
	private ArrayList<Connection> outgoing;

	public Node(int id, int nodeType, ActivationFunction activationFunction,
			ArrayList<Connection> outgoing) {
		this.nodeID = id;
		this.nodeType = nodeType;
		this.activationFunction = activationFunction;
		this.outgoing = new ArrayList<>();
		this.outgoing.addAll(outgoing);
		input = 0;
		output = 0;
		activated = false;
	}

	/**
	 * Activates the node. Resets total input to zero after activation
	 */
	public void activate() {
		output = activationFunction.getActivation(input);
		activated = true;
		input = 0;
	}

	/**
	 * Adds the input to the total input for the node
	 * 
	 * @param input
	 */
	public void addInput(double input) {
		this.input += input;
	}

	public void addOutgoingConnection(Connection out) {
		outgoing.add(out);
	}

	/**
	 * Flushes the node and resets input and output to 0
	 */
	public void flush() {
		this.input = 0;
		this.output = 0;
		activated = false;
	}

	public double getOutput() {
		return this.output;
	}

	public boolean activated() {
		return this.activated;
	}

	public void deActivate() {
		this.activated = false;
	}

	public int getType() {
		return this.nodeType;
	}

	public int getNodeID() {
		return nodeID;
	}

	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

	public ArrayList<Connection> getOutgoing() {
		return outgoing;
	}

}
