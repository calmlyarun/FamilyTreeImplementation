/**
 * 
 */
package family.tree.implementation;

import java.util.ArrayList;
import java.util.Hashtable;

import family.tree.objects.FamilyTreeException;
import family.tree.objects.Node;
import family.tree.objects.Person;
import family.tree.objects.RelationShipContants;

/**
 * @author AR266832
 *
 */
public class FamilyTreeImplementation {

	private ArrayList<Node> ancestor = new ArrayList<Node>();
	private Hashtable<String, Node> familyNodeLookupHashTable = new Hashtable<String, Node>();

	/**
	 * @return the ancestor
	 */
	public ArrayList<Node> getAncestor() {
		return ancestor;
	}

	/**
	 * @param ancestor
	 *            the ancestor to set
	 */
	public void setAncestor(ArrayList<Node> ancestor) {
		this.ancestor = ancestor;
	}

	/**
	 * Construct new Node object and add into familyNodeLookupHashTable
	 * {@link Hashtable} for quick lookup
	 * 
	 * @param person
	 * @return
	 */
	protected Node constructNode(Person person) {
		Node personNode = new Node();
		personNode.setName(person.getName());
		personNode.setPerson(person);
		// Add person as key, value pair for quick lookup
		this.familyNodeLookupHashTable.put(person.getName().toLowerCase(), personNode);
		return personNode;
	}

	/**
	 * Return the Node object from name
	 * 
	 * @param name
	 * @return
	 */
	public Node getNodebyName(String name) {
		return this.familyNodeLookupHashTable.get("" + name.toLowerCase());
	}

	/**
	 * 
	 * @param male
	 * @param female
	 * @throws FamilyTreeException
	 */
	public void addAncestor(Person male, Person female) throws FamilyTreeException {
		Node maleNode = constructNode(male);
		ancestor.add(maleNode);
		Node femaleNode = constructNode(female);
		ancestor.add(femaleNode);
		this.updatePartner(maleNode, femaleNode);
	}

	/**
	 * 
	 * @param maleNode
	 * @param femaleNode
	 * @throws FamilyTreeException
	 */
	public void updatePartner(Node maleNode, Node femaleNode) throws FamilyTreeException {
		if (maleNode == null || femaleNode == null) {
			throw new FamilyTreeException("Partners should not be none.");
		}
		maleNode.setPartner(femaleNode);
		femaleNode.setPartner(maleNode);
	}

	/**
	 * lookup the partner node and add the new person as partner to them
	 * 
	 * @param partner
	 * @param personAddedAsPartner
	 * @throws FamilyTreeException
	 */
	public void addPartner(Person partner, Person personAddedAsPartner) throws FamilyTreeException {
		Node partnerNode = this.getNodebyName(partner.getName());
		if (partnerNode == null) {
			throw new FamilyTreeException(
					"Oops...Sorry, Unable to find " + partner.getName() + " on this family tree.");
		} else {
			Node personAddedAsPartnerNode = constructNode(personAddedAsPartner);
			updatePartner(partnerNode, personAddedAsPartnerNode);
		}
	}

	/**
	 * Add children to the each parent and make them each others are siblings
	 * 
	 * @param parents
	 * @param children
	 * @throws FamilyTreeException
	 */
	public void addChildren(ArrayList<Node> parents, ArrayList<Person> children) throws FamilyTreeException {
		if (children == null || parents == null) {
			throw new FamilyTreeException("Parents/Children must be exist to add on family tree.");
		} else {
			ArrayList<Node> childrenList = new ArrayList<Node>();
			for (Person person : children) {
				Node child = constructNode(person);
				child.setParents(parents);
				childrenList.add(child);
			}
			// Update siblings list by iterating children list twice
			for (Node childNode : childrenList) {
				ArrayList<Node> siblings = childNode.getSiblings();
				if (siblings == null) {
					// Create new siblings list for this child
					siblings = new ArrayList<Node>();
				} else {
					for (Node child : childrenList) {
						if (!childNode.getName().equalsIgnoreCase(child.getName())) {
							siblings.add(child);
						}
					}
				}
			}

			for (Node parent : parents) {
				parent.setChildren(childrenList);
			}
		}

	}

	/**
	 * Draw complete family tree
	 * 
	 * @return
	 */
	public String drawCompleteFamilyTree() {
		StringBuilder sb = new StringBuilder();
//		System.out.println(ancestor.size() + ancestor.get(0).getName() + "  " + ancestor.get(1).getName());
		for (Node ancestorNode : ancestor) {
			sb.append(ancestorNode.toString() + "\n");
			int familyTreeDepth = 1;
			// Recursive call to draw children for the family
			drawChildren(ancestorNode.getChildren(), sb, familyTreeDepth);
		}
		return sb.toString();
	}

	private void drawChildren(ArrayList<Node> children, StringBuilder sb, int familyTreeDepth) {
		if (children != null) {
			for (Node child : children) {
				int depth = familyTreeDepth;
				while (depth > 0) {
					sb.append("\t");
					depth--;
				}
				sb.append(child.toString() + "\n");
				drawChildren(child.getChildren(), sb, familyTreeDepth + 1);
			}
		} else {
			familyTreeDepth--;
		}
	}

	/**
	 * Its main function to identify the person(s) from the given relation to
	 * them. As of now, RelationShipContants class relationships are supported
	 * 
	 * @param personNode
	 * @param relationship
	 * @return ArrayList<Person>
	 */
	public ArrayList<Person> findRelationshipPersons(Node personNode, String relationship) {
		ArrayList<Person> personList = new ArrayList<Person>();
		if (relationship.equalsIgnoreCase(RelationShipContants.SIBLINGS)) {
			ArrayList<Node> siblings = personNode.getSiblings();
			if (siblings != null) {
				for (Node node : siblings) {
					personList.add(node.getPerson());
				}
			}

		} else if (relationship.equalsIgnoreCase(RelationShipContants.BROTHER)) {
			ArrayList<Node> siblings = personNode.getSiblings();
			if (siblings != null) {
				for (Node node : siblings) {
					if (node.getPerson().getGender() == "M") {
						personList.add(node.getPerson());
					}
				}
			}

		} else if (relationship.equalsIgnoreCase(RelationShipContants.SISTER)) {
			ArrayList<Node> siblings = personNode.getSiblings();
			if (siblings != null) {
				for (Node node : siblings) {
					if (node.getPerson().getGender() == "F") {
						personList.add(node.getPerson());
					}
				}
			}

		} else if (relationship.equalsIgnoreCase(RelationShipContants.SON)) {
			ArrayList<Node> children = personNode.getChildren();
			if (children != null) {
				for (Node node : children) {
					if (node.getPerson().getGender() == "M") {
						personList.add(node.getPerson());
					}
				}
			}

		} else if (relationship.equalsIgnoreCase(RelationShipContants.DAUGHTER)) {
			ArrayList<Node> children = personNode.getChildren();
			if (children != null) {
				for (Node node : children) {
					if (node.getPerson().getGender() == "F") {
						personList.add(node.getPerson());
					}
				}
			}

		} else if (relationship.equalsIgnoreCase(RelationShipContants.HUSBAND)) {
			Node partner = personNode.getPartner();
			if (partner != null) {
				if (partner.getPerson().getGender() == "M") {
					personList.add(partner.getPerson());
				}

			}

		} else if (relationship.equalsIgnoreCase(RelationShipContants.WIFE)) {
			Node partner = personNode.getPartner();
			if (partner != null) {
				if (partner.getPerson().getGender() == "F") {
					personList.add(partner.getPerson());
				}

			}

		} else if (relationship.equalsIgnoreCase(RelationShipContants.MOTHER)) {
			ArrayList<Node> parents = personNode.getParents();
			if (parents != null) {
				for (Node node : parents) {
					if (node.getPerson().getGender() == "F") {
						personList.add(node.getPerson());
					}
				}
			}

		} else if (relationship.equalsIgnoreCase(RelationShipContants.FATHER)) {
			ArrayList<Node> parents = personNode.getParents();
			if (parents != null) {
				for (Node node : parents) {
					if (node.getPerson().getGender() == "M") {
						personList.add(node.getPerson());
					}
				}
			}

		} else if (relationship.equalsIgnoreCase(RelationShipContants.GRANDFATHER)) {
			ArrayList<Node> parents = personNode.getParents();
			if (parents != null) {
				for (Node node : parents) {
					ArrayList<Node> grandParents = node.getParents();
					if (grandParents != null) {
						for (Node grandParentNode : grandParents) {
							if (grandParentNode.getPerson().getGender() == "M") {
								personList.add(grandParentNode.getPerson());
							}
						}
					}

				}
			}
		} else if (relationship.equalsIgnoreCase(RelationShipContants.GRANDMOTHER)) {
			ArrayList<Node> parents = personNode.getParents();
			if (parents != null) {
				for (Node node : parents) {
					ArrayList<Node> grandParents = node.getParents();
					if (grandParents != null) {
						for (Node grandParentNode : grandParents) {
							if (grandParentNode.getPerson().getGender() == "F") {
								personList.add(grandParentNode.getPerson());
							}
						}
					}

				}
			}
		} else if (relationship.equalsIgnoreCase(RelationShipContants.GRANDSON)) {
			ArrayList<Node> children = personNode.getChildren();
			if (children != null) {
				for (Node node : children) {
					ArrayList<Node> grandChildren = node.getChildren();
					if (grandChildren != null) {
						for (Node grandChildNode : grandChildren) {
							if (grandChildNode.getPerson().getGender() == "M") {
								personList.add(grandChildNode.getPerson());
							}
						}
					}

				}
			}
		} else if (relationship.equalsIgnoreCase(RelationShipContants.GRANDDAUGHTER)) {
			ArrayList<Node> children = personNode.getChildren();
			if (children != null) {
				for (Node node : children) {
					ArrayList<Node> grandChildren = node.getChildren();
					if (grandChildren != null) {
						for (Node grandChildNode : grandChildren) {
							if (grandChildNode.getPerson().getGender() == "F") {
								personList.add(grandChildNode.getPerson());
							}
						}
					}

				}
			}
		}

		return personList;

	}

	/**
	 * Add new person with specific HUSSBAND/WIFE or SON/DAUGHTER relationship
	 * 
	 * @param personNode
	 * @param otherPersonName
	 * @param otherPersonRelationship
	 * @throws FamilyTreeException
	 */
	public void addNewPersonIntoFamily(Node personNode, String otherPersonName, String otherPersonRelationship)
			throws FamilyTreeException {
		Node otherPersonNode = this.getNodebyName(otherPersonName);
		boolean isPartner = false;
		if (otherPersonNode == null) {
			if (otherPersonRelationship.equalsIgnoreCase(RelationShipContants.HUSBAND)) {
				isPartner = true;
				otherPersonNode = this.constructNode(new Person(otherPersonName, "M", 0));
			} else if (otherPersonRelationship.equalsIgnoreCase(RelationShipContants.WIFE)) {
				isPartner = true;
				otherPersonNode = this.constructNode(new Person(otherPersonName, "F", 0));
			} else if (otherPersonRelationship.equalsIgnoreCase(RelationShipContants.SON)) {
				otherPersonNode = this.constructNode(new Person(otherPersonName, "M", 0));
			} else if (otherPersonRelationship.equalsIgnoreCase(RelationShipContants.DAUGHTER)) {
				otherPersonNode = this.constructNode(new Person(otherPersonName, "F", 0));
			}
		} else {
			System.out.println(otherPersonName + " already part of Indiana family.");
		}
		if (isPartner) {
			this.updatePartner(personNode, otherPersonNode);
		} else {
			this.addChild(personNode, otherPersonNode);
		}

	}

	/**
	 * 
	 * @param parents
	 * @param otherPersonNode
	 */
	private void addChild(Node parent, Node child) {
		// Update parent relationship
		child.getParents().add(parent);
		child.getParents().add(parent.getPartner());

		ArrayList<Node> children = parent.getChildren();
		if (!children.isEmpty()) {
			// Update Siblings
			for (Node sibling : children) {
				sibling.getSiblings().add(child);
				child.getSiblings().add(sibling);
			}
		}

		// update child relationship
		parent.getChildren().add(child);
//		parent.getPartner().getChildren().add(child);

	}

}