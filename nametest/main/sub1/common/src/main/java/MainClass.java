public class MainClass{

	public static void main(String[] args) {

		MainClass mainObject = new MainClass();
		MainClass mainObject2 = mainObject;

		System.out.println("mainObject: " + mainObject);
		System.out.println("mainObject2: " + mainObject2);

		SecondClass secondObject = new SecondClass();

		secondObject.execute( mainObject );

	}

}
