package stcl.myNEAT.evolution;

import java.util.HashMap;
import stcl.myNEAT.network.Connection;
import stcl.myNEAT.network.Node;

public class Genome {
	//Variables used for the creation of the network
	private int genomeID;
	private HashMap<Integer, Node> inputNodes, outputNodes, hiddenNodes, allNodes;
	private HashMap<Integer, Connection> connections;
	
	/**
	 * Creates a new genome based on the list of nodes and connections in the genome
	 * @param id
	 * @param inputNodes
	 * @param outputNodes
	 * @param hiddenNodes
	 * @param connections
	 */
	public Genome (int id, HashMap<Integer, Node> inputNodes, HashMap<Integer, Node> outputNodes, HashMap<Integer, Node> hiddenNodes, HashMap<Integer, Connection> connections){
		this.genomeID=id;
		this.inputNodes = inputNodes;
		this.hiddenNodes = hiddenNodes;
		this.outputNodes = outputNodes;
		this.connections = connections;
		this.allNodes = new HashMap<>();
		allNodes.putAll(inputNodes);
		allNodes.putAll(hiddenNodes);
		allNodes.putAll(outputNodes);
	}
	
		public int getGenomeID() {
			return genomeID;
		}

		public HashMap<Integer, Node> getInputNodes() {
			return inputNodes;
		}

		public void setInputNodes(HashMap<Integer, Node> inputNodes) {
			this.inputNodes = inputNodes;
		}

		public HashMap<Integer, Node> getOutputNodes() {
			return outputNodes;
		}

		public void setOutputNodes(HashMap<Integer, Node> outputNodes) {
			this.outputNodes = outputNodes;
		}

		public HashMap<Integer, Node> getHiddenNodes() {
			return hiddenNodes;
		}

		public void setHiddenNodes(HashMap<Integer, Node> hiddenNodes) {
			this.hiddenNodes = hiddenNodes;
		}

		public HashMap<Integer, Node> getAllNodes() {
			return allNodes;
		}

		public void setAllNodes(HashMap<Integer, Node> allNodes) {
			this.allNodes = allNodes;
		}

		public HashMap<Integer, Connection> getConnections() {
			return connections;
		}

		public void setConnections(HashMap<Integer, Connection> connections) {
			this.connections = connections;
		}

		public void setGenomeID(int genomeID) {
			this.genomeID = genomeID;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Genome other = (Genome) obj;
			if (genomeID != other.genomeID)
				return false;
			return true;
		}
}
