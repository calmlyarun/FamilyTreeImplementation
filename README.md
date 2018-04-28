# FamilyTreeImplementation
Java program which implements the Family tree. 

Developer Assignment - Family Tree Implementation

Java program which implements the Family tree. To run the program please follow below notes
  1.	Make sure JAVA_1.8 runtime available in your machine.
  2.	Copy the FamilyTree.jar on your machine
  3.	Open CMD prompt & navigate to the directory
  4.	Run “java -jar FamilyTree.jar”  
Design Important Notes:
  •	Used Tree implementation to store the family relationships.
  •	Node is class which having bidirectional relationships.
      o	Parents as ArrayList<Node> [Father, Mother]
      o	Siblings as ArrayList<Node> [Brother, Sister]
      o	Children as ArrayList<Node> [Son, Daughter]
      o	Partner as Node [Husband or Wife]
  •	Person class stores name, age & gender information’s
  •	FamilyTreeImplementation class is responsible for all business activities like add person & their relationships
      o	This class maintaining HashTable-Searching time is O(1)
  •	FamilTestMain.java is main class having all UI operation 
  •	TestFamilTreeImplementationClass is Junit test class




