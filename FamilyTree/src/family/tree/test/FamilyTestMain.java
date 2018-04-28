/**
 * 
 */
package family.tree.test;

import java.util.ArrayList;
import java.util.Scanner;

import family.tree.implementation.FamilyTreeImplementation;
import family.tree.objects.FamilyTreeException;
import family.tree.objects.Node;
import family.tree.objects.Person;

/**
 * @author AR266832
 *
 */
public class FamilyTestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		FamilyTreeImplementation implementation = new FamilyTreeImplementation();
		FamilyTestMain.addInitialFamilyMembersOnTree(implementation);
		System.out.println(implementation.drawCompleteFamilyTree());
		Scanner scanner = null;
		try {
			scanner = new Scanner(System.in);
			System.out.println("Enter Input: E.g:Person=Alex Relation=Brothers");
			System.out.println("or Mother=Zoe Son=Boris or Alex Son");
			System.out.println("or draw [To draw complete family tree]");
			while (true) {

				String userInput = scanner.nextLine();
				if (userInput.equalsIgnoreCase("exit")) {
					System.out.println("Thanks. See you again soon.");
					System.exit(0);
				}
				String[] splitByRelationshipVsName = userInput.split(" ");
				String relationship = null;
				String name = null;
				boolean isInputValid = false;
				String otherPersonName = null;
				String otherPersonRelationship = null;
				// TODO: Optimise this flow. Due to time constraint, only the
				// assignment query logic are implemented[Not in generic].
				if (userInput.equalsIgnoreCase("draw")) {
					System.out.println(implementation.drawCompleteFamilyTree());
				} else if (userInput.contains("=")) {
					if (userInput.contains("Person")) {
						if (splitByRelationshipVsName != null) {
							for (String relationShipVsName : splitByRelationshipVsName) {
								String[] params = relationShipVsName.split("=");
								if (params[0].equalsIgnoreCase("Person")) {
									name = params[1];
								} else if (params[0].equalsIgnoreCase("Relation")) {
									relationship = params[1];
								}
								isInputValid = true;
							}
						}
					} else {
						if (splitByRelationshipVsName != null) {
							relationship = splitByRelationshipVsName[0].split("=")[0];
							name = splitByRelationshipVsName[0].split("=")[1];
							otherPersonRelationship = splitByRelationshipVsName[1].split("=")[0];
							otherPersonName = splitByRelationshipVsName[1].split("=")[1];
							isInputValid = true;
						}
					}
				} else {
					if (splitByRelationshipVsName.length == 2) {
						name = splitByRelationshipVsName[0];
						relationship = splitByRelationshipVsName[1];
						isInputValid = true;
					}

				}
				if (isInputValid) {
					Node personNode = implementation.getNodebyName(name);
					if (personNode == null) {
						System.out.println("Oops...sorry. " + name + " not exist on Indiana family tree.");
					} else {
						if (otherPersonName != null) {
							try {
								implementation.addNewPersonIntoFamily(personNode, otherPersonName,
										otherPersonRelationship);
								System.out.println("Welcome to the family, " + otherPersonName);
							} catch (FamilyTreeException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							ArrayList<Person> personList = implementation.findRelationshipPersons(personNode,
									relationship);
							StringBuilder resultSB = new StringBuilder();
							resultSB.append(relationship + "=");
							if (personList.isEmpty()) {
								resultSB.append(" None.  ");
							} else {
								for (Person person : personList) {
									resultSB.append(person.getName() + ", ");
								}
							}
							System.out.println("Output: " + resultSB.substring(0, resultSB.length() - 2));
						}
					}
				} else {
					System.out.println("Invalid format. Please try again...");
				}

			}
		} finally {
			scanner.close();
		}

	}

	private static void addInitialFamilyMembersOnTree(FamilyTreeImplementation implementation) {
		Person male = new Person("Evan", "M", 60);
		Person female = new Person("Diana", "F", 60);
		try {
			implementation.addAncestor(male, female);
		} catch (FamilyTreeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<Person> children = new ArrayList<Person>();
		Person childJohn = new Person("John", "M", 40);
		Person childAlex = new Person("Alex", "M", 40);
		Person childJoe = new Person("Joe", "M", 40);
		Person childNisha = new Person("Nisha", "F", 40);
		children.add(childJohn);
		children.add(childAlex);
		children.add(childJoe);
		children.add(childNisha);

		try {
			implementation.addChildren(implementation.getAncestor(), children);
		} catch (FamilyTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Person partnerNancy = new Person("Nancy", "F", 38);
		Person partnerNiki = new Person("Niki", "F", 38);
		Person partnerAdam = new Person("Adam", "M", 40);
		try {
			implementation.addPartner(childAlex, partnerNancy);
			implementation.addPartner(childJoe, partnerNiki);
			implementation.addPartner(childNisha, partnerAdam);
		} catch (FamilyTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Add children to Alex/Nancy family
		ArrayList<Node> parentAlexNancy = new ArrayList<Node>();
		parentAlexNancy.add(implementation.getNodebyName("Alex"));
		parentAlexNancy.add(implementation.getNodebyName("NanCY"));
		ArrayList<Person> parentAlexNancyChildren = new ArrayList<Person>();
		Person childJacob = new Person("Jacob", "M", 20);
		Person childShaun = new Person("Shaun", "M", 20);

		parentAlexNancyChildren.add(childJacob);
		parentAlexNancyChildren.add(childShaun);

		try {
			implementation.addChildren(parentAlexNancy, parentAlexNancyChildren);
		} catch (FamilyTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Person partnerRufi = new Person("Rufi", "F", 20);
		try {
			implementation.addPartner(childJacob, partnerRufi);
		} catch (FamilyTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Add children to Alex/Nancy family
		ArrayList<Node> parentJacobRufi = new ArrayList<Node>();
		parentJacobRufi.add(implementation.getNodebyName("Jacob"));
		parentJacobRufi.add(implementation.getNodebyName("Rufi"));
		ArrayList<Person> parentJacobRufiChildren = new ArrayList<Person>();
		Person childBern = new Person("Bern", "M", 20);
		Person childSophia = new Person("Sophia", "F", 20);

		parentJacobRufiChildren.add(childBern);
		parentJacobRufiChildren.add(childSophia);

		try {
			implementation.addChildren(parentJacobRufi, parentJacobRufiChildren);
		} catch (FamilyTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Add Sophia partner
		Person partnerGeorge = new Person("George", "M", 20);

		try {
			implementation.addPartner(childSophia, partnerGeorge);
		} catch (FamilyTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Add Joe/Niki children
		Person personPiers = new Person("Piers", "M", 35);
		Person personPippa = new Person("Pippa", "F", 35);
		Person personOwen = new Person("Owen", "M", 35);
		Person personSally = new Person("Sally", "F", 35);
		Person personSarah = new Person("Sarah", "F", 20);
		Person personPeter = new Person("Peter", "M", 20);

		ArrayList<Node> familyJoeNiki = new ArrayList<Node>();
		familyJoeNiki.add(implementation.getNodebyName("Joe"));
		familyJoeNiki.add(implementation.getNodebyName("Niki"));

		ArrayList<Person> joeNikiChildren = new ArrayList<Person>();
		joeNikiChildren.add(personPiers);
		joeNikiChildren.add(personSally);

		ArrayList<Person> piersPippaChildren = new ArrayList<Person>();
		piersPippaChildren.add(personSarah);

		try {
			implementation.addChildren(familyJoeNiki, joeNikiChildren);
			implementation.addPartner(personPiers, personPippa);
			implementation.addPartner(personSally, personOwen);
			ArrayList<Node> familyPippaPiers = new ArrayList<Node>();
			familyPippaPiers.add(implementation.getNodebyName("Piers"));
			familyPippaPiers.add(implementation.getNodebyName("Pippa"));

			implementation.addChildren(familyPippaPiers, piersPippaChildren);
			implementation.addPartner(personSarah, personPeter);

		} catch (FamilyTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Add Adam/Nisha children
		Person personNeil = new Person("Neil", "M", 35);
		Person personRuth = new Person("Ruth", "F", 35);
		Person personWilliam = new Person("William", "M", 35);
		Person personRose = new Person("Rose", "F", 33);
		Person personZoe = new Person("Zoe", "F", 25);
		Person personPaul = new Person("Paul", "M", 20);
		Person personSteve = new Person("Steve", "M", 10);
		Person personRoger = new Person("Roger", "M", 7);

		ArrayList<Node> familyAdamNisha = new ArrayList<Node>();
		familyAdamNisha.add(implementation.getNodebyName("Adam"));
		familyAdamNisha.add(implementation.getNodebyName("Nisha"));

		ArrayList<Person> adamNishaChildren = new ArrayList<Person>();
		adamNishaChildren.add(personRuth);
		adamNishaChildren.add(personWilliam);
		adamNishaChildren.add(personPaul);

		ArrayList<Person> paulZoeChildren = new ArrayList<Person>();
		paulZoeChildren.add(personRoger);

		ArrayList<Person> williamroseChildren = new ArrayList<Person>();
		williamroseChildren.add(personSteve);

		try {
			implementation.addChildren(familyAdamNisha, adamNishaChildren);
			implementation.addPartner(personRuth, personNeil);
			implementation.addPartner(personWilliam, personRose);
			implementation.addPartner(personPaul, personZoe);

			ArrayList<Node> familyPaulZoe = new ArrayList<Node>();
			familyPaulZoe.add(implementation.getNodebyName("Paul"));
			familyPaulZoe.add(implementation.getNodebyName("Zoe"));

			implementation.addChildren(familyPaulZoe, paulZoeChildren);

			ArrayList<Node> familyWilliamRose = new ArrayList<Node>();
			familyWilliamRose.add(implementation.getNodebyName("William"));
			familyWilliamRose.add(implementation.getNodebyName("Rose"));

			implementation.addChildren(familyWilliamRose, williamroseChildren);

		} catch (FamilyTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
