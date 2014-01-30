package myNEAT.network;

public class Connection {
	private int connectionID;
	private Node in, out;
	private double weight;
	private boolean enabled, activated;
	
	public Connection(int id, Node in, Node out, double weight, boolean enabled){
		this.connectionID = id;
		this.in=in;
		this.out = out;
		this.weight = weight;
		this.enabled = enabled;
	}
	
	/**
	 * Propagates output from ingoing node to outgoing node
	 */
	public void propagateSignal(){
		if (enabled){
			double input = in.getOutput();
			double output = input * weight;
			out.addInput(output);
			activated = true;
		}		
	}
	
	public void reset(){
		activated = false;
	}
	
	public void addWeight(double weight){
		this.weight += weight;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getConnectionID() {
		return connectionID;
	}

	public Node getIn() {
		return in;
	}

	public Node getOut() {
		return out;
	}

	public double getWeight() {
		return weight;
	}

	public boolean isActivated() {
		return activated;
	}
}