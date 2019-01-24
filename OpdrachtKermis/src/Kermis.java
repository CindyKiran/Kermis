import java.util.Scanner;

public class Kermis {
	static boolean status = true;
	static double belastingLadder;
	
	public static void main(String[]args) {
		//maak attracties
		Attractie botsauto = new BotsAuto("Bots Auto's", 2.50);
		RisicoRijkeAttracties spin = new Spin("De Spin", 2.25);
		Attractie spiegelPaleis = new SpiegelPaleis("Spiegel Paleis", 2.75);
		Attractie spookhuis = new Spookhuis("Spookhuis", 3.20);
		RisicoRijkeAttracties hawaii = new Hawaii("Hawaii", 2.90);
		Attractie ladderKlimmen = new LadderKlimmen("Ladder Klimmen", 5.00);
		
		displayMenu();
		
		//wanneer user input blijft geven
		while (status == true) {
			Kassa kassa = new Kassa();
			Scanner scanner = new Scanner(System.in);
			
			String input = scanner.nextLine();
			
			switch(input) {
			case "1":
				kassa.payTicket(botsauto);
				break;
			case "2":
				kassa.payTicket(spin);
				spin.draaiLimiet++;
				if((spin.draaiLimiet%5)==0) { //5x draaien, roep onderhoudsbeurt
					spin.opstellingsKeuring();
				}
				break;
			case "3":
				kassa.payTicket(spiegelPaleis);
				break;
			case "4":
				kassa.payTicket(spookhuis);
				break;
			case "5":
				kassa.payTicket(hawaii);
				hawaii.draaiLimiet++;
				if((hawaii.draaiLimiet%10)==0) { //10x draaien, roep onderhoudsbeurt
					hawaii.opstellingsKeuring();
				}
				break;
			case "6":
				kassa.payTicket(ladderKlimmen);
				double omzetLadder = kassa.BerekenOmzet(ladderKlimmen);
				belastingLadder = new LadderKlimmen("Ladder Klimmen", 5.00).kansSpelBelastingBetalen(omzetLadder);
				break;
			case "x":
			case "X":
				System.out.println("Vaarwel!");
				status =false;
				break;
			case "O":
			case "o":
				//omzet per attractie
				kassa.showOmzet(botsauto);
				kassa.showOmzet(spin);
				kassa.showOmzet(spiegelPaleis);
				kassa.showOmzet(spookhuis);
				kassa.showOmzet(hawaii);
				kassa.showOmzet(ladderKlimmen);
				//omzet totaal met belasting
				System.out.println("---------------------------------\n"
						+ "Belasting LadderKlimmen: " + belastingLadder + "\n"
						+ "Totaal omzet: " + Kassa.totaalOmzet);
				break;
			case "k":
			case "K":
				//print uit aantal verkochte kaarten
				kassa.showTicket(botsauto);
				kassa.showTicket(spin);
				kassa.showTicket(spiegelPaleis);
				kassa.showTicket(spookhuis);
				kassa.showTicket(hawaii);
				kassa.showTicket(ladderKlimmen);
				break;
			case "m":
			case "M":
				displayMenu();
				break;
			default:
				System.out.println("Incorrect input");
				break;
			}
		}
	}
	
	static void displayMenu() {
		System.out.println("--------------------------------------\n"
				+ "Welkom bij de Kermis\n"
				+ "Kies een van de volgende opties:\n"
				+ "--------------------------------------\n"
				+ "Typ 1 voor botsauto's \n"
				+ "Typ 2 voor de Spin\n"
				+ "Typ 3 voor de Spiegel Paleis\n"
				+ "Typ 4 voor het Spookhuis\n"
				+ "Typ 5 voor Hawaii\n"
				+ "Typ 6 voor Ladder Klimmen\n"
				+"--------------------------------------\n"
				+ "Typ o voor omzet\n"
				+ "Typ k voor aantal verkochte kaarten\n"
				+ "Typ m voor menu\n"
				+ "Typ x om de Kermis te verlaten");
	}
}

class Kassa{
	static double totaalOmzet;
	
	int payTicket(Attractie attractie) {
		attractie.draaien();
		attractie.aantalK++;
		totaalOmzet+=attractie.getPrijs();
		return attractie.aantalK;
	}
	
	double BerekenOmzet(Attractie attractie){
		double omzet =(attractie.aantalK*attractie.getPrijs());
		return omzet;
	}
	
	void showOmzet(Attractie attractie){
		double omzet =(attractie.aantalK*attractie.getPrijs());
		System.out.println(attractie.getNaam() + "'s omzet: " + omzet);
	}
	
	void showTicket(Attractie attractie) {
		System.out.println(attractie.getNaam() + " ticket sold: " + attractie.aantalK);
	}	
}

class Attractie{
	private String naam = "attractie";
	private double prijs;
	int aantalK;

	public Attractie(String naam, double prijs) {
		super();
		this.naam = naam;
		this.prijs = prijs;
	}

	public double getPrijs() {
		return prijs;
	}

	void draaien() {
		System.out.println("De " + naam + " draait");
	}

	public String getNaam() {
		return naam;
	}
}

abstract class RisicoRijkeAttracties extends Attractie{
	int draaiLimiet;
	
	public RisicoRijkeAttracties(String naam, double prijs) {
		super(naam, prijs);
	}
	
	public abstract void opstellingsKeuring();
	
}

class BotsAuto extends Attractie{

	public BotsAuto(String naam, double prijs) {
		super(naam, prijs);
	}
}

class Spin extends RisicoRijkeAttracties{

	public Spin(String naam, double prijs) {
		super(naam, prijs);
	}

	@Override
	public void opstellingsKeuring() {
		System.out.println("De Spin attractie heeft onderhoudsbeurt nodig!");		
	}	
}

class SpiegelPaleis extends Attractie{

	public SpiegelPaleis(String naam, double prijs) {
		super(naam, prijs);
	}
}

class Spookhuis extends Attractie{

	public Spookhuis(String naam, double prijs) {
		super(naam, prijs);
	}
}

class Hawaii extends RisicoRijkeAttracties{

	public Hawaii(String naam, double prijs) {
		super(naam, prijs);
	}
	
	@Override
	public void opstellingsKeuring() {
		System.out.println("De Hawaii attractie heeft onderhoudsbeurt nodig!");
	}
}

interface GokAttractie{
	double kansSpelBelastingBetalen(double omzet);
}

class LadderKlimmen extends Attractie implements GokAttractie{

	public LadderKlimmen(String naam, double prijs) {
		super(naam, prijs);
	}

	@Override
	public double kansSpelBelastingBetalen(double omzet) {
		double belasting = omzet*0.3; //30% belasting 
		return belasting;
	}	
}