package stcl.myNEAT.phenotypes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class NeuralNetwork {
	/* Node type constants */
	public enum NodeType {
		BIAS(0), INPUT(1), OUTPUT(2), HIDDEN(3);
		private final int value;
		private NodeType(int value){
			this.value = value;
		}
		public int getValue(){
			return value;
		}
	};
	

	/* Typology */
	private HashMap<Integer, Node> allNodes, inputNodes, outputNodes, hiddenNodes;
	private HashMap<Integer, Connection> connections;
	
	/**
	 * Creates the neural network based on the lists of nodes and connections.
	 * Bias-nodes must be included in the list of input nodes;
	 * 
	 * @param inputNodes
	 * @param outputNodes
	 * @param hiddenNodes
	 * @param connections
	 */
	public NeuralNetwork(HashMap<Integer, Node> inputNodes,
			HashMap<Integer, Node> outputNodes, HashMap<Integer, Node> hiddenNodes,
			HashMap<Integer, Connection> connections) {

		// Create lists
		this.inputNodes = new HashMap<>();
		this.hiddenNodes = new HashMap<>();
		this.outputNodes = new HashMap<>();
		this.allNodes = new HashMap<>();
		this.connections = new HashMap<>();

		// Copy values
		this.inputNodes.putAll(inputNodes);
		this.outputNodes.putAll(outputNodes);
		this.hiddenNodes.putAll(hiddenNodes);
		this.allNodes.putAll(inputNodes);
		this.allNodes.putAll(outputNodes);
		this.allNodes.putAll(hiddenNodes);
		this.connections.putAll(connections);

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
		for (Node n : allNodes.values()) {
			n.deActivate();
		}

		// Load inputvalues
		for (int i = 0; i < inputs.length; i++) {
			Node in = inputNodes.get(i + 1);
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
			outputs[i] = outputNodes.get(i + 5).getOutput();
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
		for (Node n : outputNodes.values()) {
			if (!n.activated()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Does one full activation where all nodes as activated once
	 * Recurrent connections does not lead to more than one activations of a node
	 * @param inputs
	 */
	private void fullActivation(double[] inputs) {

		Queue<Node> activationQueue = new LinkedList<Node>();

		// Add all input nodes
		for (Node in : inputNodes.values()) {
			activationQueue.add(in);
		}

		while (!activationQueue.isEmpty()) {
			Node n = activationQueue.poll();
			Node out;
			n.activate();
			for (Connection c : n.getOutgoing().values()) {
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
	
	/**
	 * Activates all nodes at the same time. Signal travels forward by one.
	 * @param inputs
	 */
	private void continouousActivation(double[] inputs) {
		// Activate all nodes once
		for (Node n : allNodes.values()) {
			n.activate();
		}

		// Propagate all signals
		for (Connection c : connections.values()) {
			c.propagateSignal();
		}
	}
	
	/**
	 * Resets all connections and flushes all nodes for input and output values.
	 */
	public void flush(){
		for (Node n : allNodes.values()){
			n.flush();
		}
		
		for (Connection c : connections.values()){
			c.reset();
		}
	}
	
	public HashMap<Integer, Node> getAllNodes(){
		return allNodes;
	}
	
	public HashMap<Integer, Connection> getConnections(){
		return connections;
	}
	
	/**
	 * Tests if two neural networks are equal
	 */
	public boolean equals(Object neuralNetwork){
		NeuralNetwork nn = (NeuralNetwork) neuralNetwork;
				
		//Test nodes
		if (this.allNodes.size() != nn.getAllNodes().size()) return false;
		for (Node n : this.allNodes.values()){
			int key = n.getNodeID();
			Node x = nn.getAllNodes().get(key);
			if (x == null) return false;
			if (!n.equals(x)) return false;
		}
		
		//Test connections
		if (this.getConnections().size() != nn.getConnections().size()) return false;
		for (Connection c : this.connections.values()){
			int key = c.getConnectionID();
			Connection x = nn.getConnections().get(key);
			if (x == null) return false;
			if (!c.equals(x)) return false;
		}
		
		return true;
	}
}
