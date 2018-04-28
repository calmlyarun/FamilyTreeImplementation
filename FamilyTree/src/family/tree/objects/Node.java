/**
 * 
 */
package family.tree.objects;

import java.util.ArrayList;

/**
 * @author AR266832
 *
 */
public class Node {
	
	private ArrayList<Node> parents = new ArrayList<Node>();
	private ArrayList<Node> children= new ArrayList<Node>();
	private ArrayList<Node> siblings= new ArrayList<Node>();
	private Node partner;
	private Person person;
	private String name;
	
	/**
	 * @return the partner
	 */
	public Node getPartner() {
		return partner;
	}
	/**
	 * @param partner the partner to set
	 */
	public void setPartner(Node partner) {
		this.partner = partner;
	}
	/**
	 * @return the person
	 */
	public Person getPerson() {
		return person;
	}
	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the parents
	 */
	public ArrayList<Node> getParents() {
		return parents;
	}
	/**
	 * @param parents the parents to set
	 */
	public void setParents(ArrayList<Node> parents) {
		this.parents = parents;
	}
	/**
	 * @return the children
	 */
	public ArrayList<Node> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}
	/**
	 * @return the siblings
	 */
	public ArrayList<Node> getSiblings() {
		return siblings;
	}
	/**
	 * @param siblings the siblings to set
	 */
	public void setSiblings(ArrayList<Node> siblings) {
		this.siblings = siblings;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(name + " ");
		if(partner != null){
			sb.append(", partner="+partner.getName());
		}else{
			sb.append(", partner=None.");
		}
		sb.append(", parents=[");
		if(parents != null){
			for (Node parentNode :parents){
				sb.append(parentNode.getName()+" ");
			}
			sb.append("] ");
		}		
		if(children != null){
			sb.append("children=[");
			for (Node node :children){
				sb.append(node.getName()+" ");
			}
			sb.append("] ");
		}else{
			sb.append("], children=None. ");
		}
		if(siblings != null){
			sb.append(" siblings=[");
			for (Node node :siblings){
				sb.append(node.getName()+" ");
			}
			sb.append("] ");
		}else{
			sb.append("], siblings=None. ");

		}
		return sb.toString();
	}

	
	
	
	

}
