package myNEAT.evolution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

import myNEAT.network.Connection;
import myNEAT.network.NeuralNetwork;
import myNEAT.network.Node;
import myNEAT.network.activationFunctions.AFBias;
import myNEAT.network.activationFunctions.AFSame;
import myNEAT.network.activationFunctions.ActivationFunction;

public class Genome {
	//Variables used for the creation of the network
	private NeuralNetwork phenotype;	
	private int genomeID;
	private HashMap<Integer, Node> inputNodes, outputNodes, hiddenNodes, allNodes;
	private HashMap<Integer, Connection> connections;
	String delimiter = " ";
	
	/**
	 * Creates a genome based on the genome file
	 * @param genomePath
	 * @throws FileNotFoundException
	 */
	public Genome (String genomePath) throws FileNotFoundException{
		createGenomeFromFile(genomePath);		
	}
	
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
		phenotype = new NeuralNetwork(genomeID, inputNodes, outputNodes, hiddenNodes, connections);
	}
	
	/**
	 * Reads a genome file and creates the genome from that
	 * @param genomePath
	 * @throws FileNotFoundException
	 */
	private void createGenomeFromFile(String genomePath) throws FileNotFoundException{
		//Open file and scanner
				File file = new File(genomePath);
				Scanner inputFile = new Scanner(file);
							
				//Ready lists
				inputNodes = new HashMap<>();
				outputNodes = new HashMap<>();
				hiddenNodes = new HashMap<>();
				allNodes = new HashMap<>();
				connections = new HashMap<>();
					
				//Scan file
				Scanner curLine;
						
				while (inputFile.hasNext()){
					curLine = new Scanner(inputFile.nextLine());
					curLine.useLocale(Locale.US); //Has to use US locale to make sure that '.' is used as decimal delimiter
					if (!analyzeLine(curLine)){
						//The line started with 'genomeend'
						break;
					}			
				}
					
				//Create neural network
				phenotype = new NeuralNetwork(genomeID, inputNodes, outputNodes, hiddenNodes, connections);
					
				//Close scanner
				inputFile.close();
	}
	private boolean analyzeLine(Scanner curLine){
		if (curLine.hasNext()){
			String token = curLine.next();
							
			if (token.equalsIgnoreCase("genomestart")){
				genomeID = curLine.nextInt();					
			} else if (token.equalsIgnoreCase("node")) {
				createNode(curLine);
			} else if (token.equalsIgnoreCase("conn")){
				createConnection(curLine);
			} else if (token.equalsIgnoreCase("genomeend")) {
				return false;
			}			
		}
		//Only return false if the statement 'genomeend' is encountered. Else continue
		return true;		
	}
		
		private void createNode(Scanner curLine){
			//TODO Add exception handler if read values are of the wrong type
			//TODO add exception handler if nodetype is wrong
			
			//Read values
			int nodeID = curLine.nextInt();
				int nodeTypeID = curLine.nextInt();
				int activationFunctionID = curLine.nextInt();
				double biasValue = curLine.nextDouble();
				
			//Create activation function
				ActivationFunction af = createActivationFunction(activationFunctionID, biasValue);
				
			//Create node
				Node n = new Node(nodeID, nodeTypeID, af, new HashMap<Integer, Connection>());
			
			//Add node to correct list
				switch (nodeTypeID){
				case NeuralNetwork.BIAS: inputNodes.put(nodeID, n); break;
				case NeuralNetwork.INPUT: inputNodes.put(nodeID, n); break;
				case NeuralNetwork.HIDDEN: hiddenNodes.put(nodeID, n); break;
				case NeuralNetwork.OUTPUT: outputNodes.put(nodeID, n); break;			
				}
				
				allNodes.put(nodeID, n);
		
		}
		
		private ActivationFunction createActivationFunction(int activationFunctionID, double biasValue){
			//TODO link this method with activation function. No need to keep track of types in two classes
			ActivationFunction af;
			switch (activationFunctionID){
			case 0: af = new AFSame(); break;
			case 1: af = new AFBias(biasValue); break;
			default: af = null; break;
			}
			return af;
		}
		
		private void createConnection(Scanner curLine){
			//TODO Add exception handler for wrong types
			
			//Read values
			int connectionID = curLine.nextInt();
			int inNodeID = curLine.nextInt();
			int outNodeID = curLine.nextInt();
			double connectionWeight = curLine.nextDouble();
			int enabled = curLine.nextInt();
			
			//Collect in and out node
			Node in = allNodes.get(inNodeID);
			Node out = allNodes.get(outNodeID);
			
			//Create connection
			Connection c = new Connection(connectionID, in, out, connectionWeight, enabled == 1);
			
			//Add connection to list
			connections.put(connectionID, c);
			
		}
		
		public void printToFile(String filePath) throws FileNotFoundException, IOException{
			
			//Write to file
			File genomeFile = new File(filePath);
			PrintWriter output = new PrintWriter(genomeFile);
			
			//Write first line
			output.println("genomestart " + genomeID);
			
			//Write nodes
			printNodes(output);
			
			//Write connections
			printConnections(output);
			
			//Write end line
			output.println("genomeend");
			
			output.close();
			
		}
		
		private void printNodes(PrintWriter output){
			//Write input nodes
			Node bias = null;
			for (Node n : inputNodes.values()){
				if (n.getType() == NeuralNetwork.BIAS){
					//We save bias for later
					bias = n;
				} else {
					output.println(writeNodeInfo(n));
				}
			}
			output.println(writeNodeInfo(bias));
			
			//Write output nodes
			for (Node n : outputNodes.values()){
				output.println(writeNodeInfo(n));			
			}
			
			//Write hidden nodes
					for (Node n : hiddenNodes.values()){
						output.println(writeNodeInfo(n));			
					}		
			
		}
		
		private String writeNodeInfo(Node n){
			String s;
			
			//Read values
					int nodeID = n.getNodeID();
					int nodeTypeID = n.getType();
					int activationFunctionID = n.getActivationFunction().getType();
					double biasValue;
					if (nodeTypeID == NeuralNetwork.BIAS){
						biasValue = n.getActivationFunction().getActivation(0); //Input value doesn't matter as output from bias is constant
					} else {
						biasValue = 0;
					}
			s = "node" + delimiter + nodeID + delimiter + nodeTypeID + delimiter + activationFunctionID + delimiter + biasValue;
			return s;
		}
		
		private void printConnections(PrintWriter output){
			for (Connection c : connections.values()){
				//Read values
				int connectionID = c.getConnectionID();
				int inNodeID = c.getIn().getNodeID();
				int outNodeID = c.getOut().getNodeID();
				double connectionWeight = c.getWeight();
				int enabled;
				if (c.isEnabled()){
					enabled = 1;
				} else {
					enabled = 0;
				}
				
				//Write info
				String s = "conn" + delimiter + connectionID + delimiter + inNodeID + delimiter + outNodeID + delimiter + connectionWeight + delimiter + enabled;
				output.println(s);
			}
		}
		
		public boolean equal(Object genome){
			Genome g = (Genome) genome;
			//Test ID
			if (this.genomeID != g.getGenomeID()) return false;
			
			if(this.phenotype != g.getPhenotype()) return false;
			
			return true;
			
		}

		public NeuralNetwork getPhenotype() {
			return phenotype;
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

		public void setPhenotype(NeuralNetwork phenotype) {
			this.phenotype = phenotype;
		}

		public void setGenomeID(int genomeID) {
			this.genomeID = genomeID;
		}
}
