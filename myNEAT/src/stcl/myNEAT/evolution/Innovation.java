package stcl.myNEAT.evolution;


/**
 * This class serves as a way to record innovations specifically, so that an
 * innovation in one genome can be compared with other innovations in the same
 * epoch, and if they Are the same innovation, they can both be assigned the
 * same innnovation number. This class can encode innovations that represent a
 * new link forming, or a new node being added. In each case, two nodes fully
 * specify the innovation and where it must have occured. (Between them)
 * 
 * Source: The JNEAT implementation of NEAT
 * 
 */
public class Innovation {
	public enum InnovationType{NEW_NODE, NEW_CONN};
	
	/**
	 * Innovation type
	 */
	private InnovationType type;
	
	/**
	 * Two nodes specify where the innovation took place : this is the node
	 * input
	 */
	private int node_in_id;

	/**
	 * Two nodes specify where the innovation took place : this is the node
	 * output
	 */
	private int node_out_id;

	/**
	 * The number assigned to the innovation
	 */
	private int innovation_num1;

	/**
	 * If this is a new node innovation,then there are 2 innovations (connections)
	 * added for the new node
	 */
	private int innovation_num2;

	/**
	 * If a link is added, this is its weight
	 */
	private double new_weight;

	/**
	 * If a new node was created, this is its node_id
	 */
	private int newnode_id;
	
	/**
	 * Constructor used when adding a new connection
	 * @param node_in
	 * @param node_out
	 * @param innovationNumber
	 * @param weight
	 */
	public Innovation(int node_in, int node_out, int innovationNumber, double weight) {
		type = InnovationType.NEW_CONN;
		node_in_id = node_in;
		node_out_id = node_out;
		innovation_num1 = innovationNumber;
		new_weight = weight;

		// Unused parameters set to zero
		innovation_num2 = 0;
		newnode_id = 0;
	}

	/**
	 * Constructor used when adding a new node
	 * @param node_in
	 * @param node_out
	 * @param innovationNumber1 Innovation number for the incoming connection
	 * @param innovationNumber2 Innovation number for the outgoing connection
	 * @param newNodeID
	 * @param oldinnov
	 */
	public Innovation(int node_in, int node_out, int innovationNumber1, int innovationNumber2, int newNodeID,
			double oldinnov) {
		type = InnovationType.NEW_NODE;
		node_in_id = node_in;
		node_out_id = node_out;
		innovation_num1 = innovationNumber1;
		innovation_num2 = innovationNumber2;
		newnode_id = newNodeID;

		// Unused parameters set to zero
		new_weight = 0;

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Innovation other = (Innovation) obj;
		if (type != other.type)
			return false;
		if (innovation_num1 != other.innovation_num1)
			return false;
		if (innovation_num2 != other.innovation_num2)
			return false;
		if (Double.doubleToLongBits(new_weight) != Double
				.doubleToLongBits(other.new_weight))
			return false;
		if (newnode_id != other.newnode_id)
			return false;
		if (node_in_id != other.node_in_id)
			return false;
		if (node_out_id != other.node_out_id)
			return false;
		return true;
	}

}