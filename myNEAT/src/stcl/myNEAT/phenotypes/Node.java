package stcl.myNEAT.phenotypes;

import java.util.ArrayList;
import java.util.HashMap;

import stcl.myNEAT.phenotypes.NeuralNetwork.NodeType;
import stcl.myNEAT.phenotypes.activationFunctions.ActivationFunction;

public class Node {
	private NodeType nodeType;
	private int nodeID;
	private double input, output;
	private boolean activated;
	private ActivationFunction activationFunction;
	private HashMap<Integer, Connection> outgoing;

	public Node(int id, NodeType nodeType, ActivationFunction activationFunction,
			HashMap<Integer, Connection> outgoing) {
		this.nodeID = id;
		this.nodeType = nodeType;
		this.activationFunction = activationFunction;
		this.outgoing = new HashMap<>();
		this.outgoing.putAll(outgoing);
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
		outgoing.put(out.getConnectionID(),out);
	}

	/**
	 * Flushes the node and resets input and output to 0
	 */
	public void flush() {
		this.input = 0;
		this.output = 0;
		activated = false;
	}
	
	public boolean equals(Object node){
		Node n = (Node)node;
		if (this.nodeID != n.getNodeID()) return false;
		if (!this.activationFunction.equals(n.getActivationFunction())) return false;
		if (this.nodeType != n.getType()) return false;
		return true;
		
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

	public NodeType getType() {
		return this.nodeType;
	}

	public int getNodeID() {
		return nodeID;
	}

	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

	public HashMap<Integer, Connection> getOutgoing() {
		return outgoing;
	}

}
