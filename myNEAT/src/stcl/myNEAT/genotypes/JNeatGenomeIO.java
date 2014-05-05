package stcl.myNEAT.genotypes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

import org.apache.log4j.Logger;

import stcl.LogTest;
import stcl.myNEAT.phenotypes.Connection;
import stcl.myNEAT.phenotypes.NeuralNetwork;
import stcl.myNEAT.phenotypes.Node;
import stcl.myNEAT.phenotypes.NeuralNetwork.NodeType;
import stcl.myNEAT.phenotypes.activationFunctions.AFBias;
import stcl.myNEAT.phenotypes.activationFunctions.AFSame;
import stcl.myNEAT.phenotypes.activationFunctions.ActivationFunction;

/**
 * This class is capable of parsing genome files used by the JNEAT implementation
 * (http://nn.cs.utexas.edu/?jneat)
 * @author Simon
 *
 */
public class JNeatGenomeIO extends GenomeIO {
	
	private static final Logger logger = Logger.getLogger(LogTest.class);
	
	private final String DELIMITER = " ";
	
	//Containers used in the construction
	private HashMap<Integer, Node> inputNodes, outputNodes, hiddenNodes, allNodes;
	private HashMap<Integer, Connection> connections;
	
	//Info about the genome. 
	private int genomeID;
	
	@Override
	public Genome readGenomeFile(String filePath) throws FileNotFoundException {
							
		//Instantiate containers
		readyContainers();			
		
		
			File file = new File(filePath);
			Scanner inputFile;
			inputFile = new Scanner(file);
			while (inputFile.hasNext()){
				Scanner curLine = new Scanner(inputFile.nextLine());
				curLine.useLocale(Locale.US); //Has to use US locale to make sure that '.' is used as decimal delimiter
				if (!analyzeLine(curLine)){
					//The line started with 'genomeend'
					break;
				}
				curLine.close();
			}
			inputFile.close();
		
		return new Genome(genomeID, inputNodes, outputNodes, hiddenNodes, connections);					
		
	}

	@Override
	public void printGenomeToFile(Genome genome, String filePath) throws FileNotFoundException {
		//Load values for the containers
		loadContainerValues(genome);
		
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
	
	/**
	 * Instantiates the needed containers for the parsing
	 */
	private void readyContainers(){
		inputNodes = new HashMap<>();
		outputNodes = new HashMap<>();
		hiddenNodes = new HashMap<>();
		allNodes = new HashMap<>();
		connections = new HashMap<>();
	}
	
	//Reads info from the genome
	private void loadContainerValues(Genome genome){
		//Get genomeID
		genomeID = genome.getGenomeID();
		
		inputNodes = genome.getInputNodes();
		outputNodes = genome.getOutputNodes();
		hiddenNodes = genome.getHiddenNodes();
		connections = genome.getConnections();
	}
	
	
	/**
	 * Reads the first word in the line to decide where to send the line
	 * Only recognizes 'genomestart', 'node', 'conn', 'genomeend'
	 * @param curLine
	 * @return false if the end of the genome has been reached
	 */
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
	
	/**
	 * Creates a new node based on the info in the line
	 * @param curLine
	 */
	private void createNode(Scanner curLine){
		//TODO Add exception handler if read values are of the wrong type
		//TODO add exception handler if nodetype is wrong
		
		//Read values
		int nodeID = curLine.nextInt();
		NodeType nodeType = NodeType.values()[curLine.nextInt()];
			int activationFunctionID = curLine.nextInt();
			double biasValue = curLine.nextDouble();
			
		//Create activation function
			ActivationFunction af = createActivationFunction(activationFunctionID, biasValue);
			
		//Create node
			Node n = new Node(nodeID, nodeType, af, new HashMap<Integer, Connection>());
		
		//Add node to correct list
			switch (nodeType){
			case BIAS: inputNodes.put(nodeID, n); break;
			case INPUT: inputNodes.put(nodeID, n); break;
			case HIDDEN: hiddenNodes.put(nodeID, n); break;
			case OUTPUT: outputNodes.put(nodeID, n); break;			
			}
			
			allNodes.put(nodeID, n);
	
	}
	
	/**
	 * Creates the activation function for the node.
	 * @param activationFunctionID
	 * @param biasValue
	 * @return
	 */
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
	
	/**
	 * Creates a new connection based on the info in the file
	 * @param curLine
	 */
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
	
	private void printNodes(PrintWriter output){
		//TODO: What to do if there is more than one BIAS-nodes?
		//Write input nodes
		Node bias = null;
		for (Node n : inputNodes.values()){
			if (n.getType() == NodeType.BIAS){
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
				NodeType nodeType = n.getType();
				int activationFunctionID = n.getActivationFunction().getType();
				double biasValue;
				if (nodeType == NodeType.BIAS){
					biasValue = n.getActivationFunction().getActivation(0); //Input value doesn't matter as output from bias is constant
				} else {
					biasValue = 0;
				}
		s = "node" + DELIMITER + nodeID + DELIMITER + nodeType.getValue() + DELIMITER + activationFunctionID + DELIMITER + biasValue;
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
			String s = "conn" + DELIMITER + connectionID + DELIMITER + inNodeID + DELIMITER + outNodeID + DELIMITER + connectionWeight + DELIMITER + enabled;
			output.println(s);
		}
	}

}
