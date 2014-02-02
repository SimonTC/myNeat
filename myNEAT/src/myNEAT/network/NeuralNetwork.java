package myNEAT.network;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class NeuralNetwork {
	/* Node type constants */
	public static final int BIAS = 0;
	public static final int INPUT = 1;
	public static final int OUTPUT = 2;
	public static final int HIDDEN = 3;

	/* Typology */
	private ArrayList<Node> allNodes, inputNodes, outputNodes, hiddenNodes;
	private ArrayList<Connection> connections;

	/**
	 * Creates the neural network based on the lists of nodes and connections.
	 * Bias-nodes must be included in the list of input nodes;
	 * 
	 * @param inputNodes
	 * @param outputNodes
	 * @param hiddenNodes
	 * @param connections
	 */
	public NeuralNetwork(ArrayList<Node> inputNodes,
			ArrayList<Node> outputNodes, ArrayList<Node> hiddenNodes,
			ArrayList<Connection> connections) {

		// Create lists
		this.inputNodes = new ArrayList<>();
		this.hiddenNodes = new ArrayList<>();
		this.outputNodes = new ArrayList<>();
		this.allNodes = new ArrayList<>();
		this.connections = new ArrayList<>();

		// Copy values
		this.inputNodes.addAll(inputNodes);
		this.outputNodes.addAll(outputNodes);
		this.hiddenNodes.addAll(hiddenNodes);
		this.allNodes.addAll(inputNodes);
		this.allNodes.addAll(outputNodes);
		this.allNodes.addAll(hiddenNodes);
		this.connections.addAll(connections);

	}

	public double[] activate(double[] inputs, boolean fullActivation) {
		// Test if number of inputs is correct
		if (inputNodes.size() != inputs.length) {
			// Not good. return an error
			System.out
					.println("The number of inputs is different from the number of inputnodes");
			return null;
		}

		// Set all nodes to not activated
		for (Node n : allNodes) {
			n.deActivate();
		}

		// Load inputvalues
		for (int i = 0; i < inputs.length; i++) {
			Node in = inputNodes.get(i);
			double input = inputs[i];
			in.addInput(input);
		}

		// Do the activation
		if (fullActivation) {
			fullActivation(inputs);
		} else {
			continouousActivation(inputs);
		}

		// Read output values
		double[] outputs = new double[outputNodes.size()];
		for (int i = 0; i < outputs.length; i++) {
			outputs[i] = outputNodes.get(i).getOutput();
		}

		return outputs;
	}

	/**
	 * Tests if all outputs are connected to the input layer
	 * 
	 * @return
	 */
	public boolean isAllOutputsReachable() {
		double[] inputs = new double[inputNodes.size()];

		// Do full activation of network.
		// The specific input values are not important
		double[] output = this.activate(inputs, true);

		// Test if all outputs has been activated
		for (Node n : outputNodes) {
			if (!n.activated()) {
				return false;
			}
		}
		return true;
	}

	private void fullActivation(double[] inputs) {

		Queue<Node> activationQueue = new LinkedList<Node>();

		// Add all input nodes
		for (Node in : inputNodes) {
			activationQueue.add(in);
		}

		while (!activationQueue.isEmpty()) {
			Node n = activationQueue.poll();
			Node out;
			n.activate();
			for (Connection c : n.getOutgoing()) {
				if (c.isEnabled()) {
					c.propagateSignal();
					out = c.getOut();
					if (!out.activated()) {
						activationQueue.add(out);
					}
				}
			}
		}
	}

	private void continouousActivation(double[] inputs) {
		// Activate all nodes once
		for (Node n : allNodes) {
			n.activate();
		}

		// Propagate all signals
		for (Connection c : connections) {
			c.propagateSignal();
		}
	}
	
	/**
	 * Resets all connections and flushes all nodes for input and output values.
	 */
	public void flush(){
		for (Node n : allNodes){
			n.flush();
		}
		
		for (Connection c : connections){
			c.reset();
		}
	}
}
