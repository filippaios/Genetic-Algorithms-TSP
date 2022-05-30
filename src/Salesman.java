import java.util.*;

public class Salesman {
	private int arithmos_diadromwn;
	private int sundiasmoi;
	private int arithmos_shmeiwn;
	private int megethos_anaparagwghs;
	private int arithmos_ekseliksewn;
	private float pososto_metallakshs;
	private int[][] kostos;
	private int arxiko_shmeio;
	private int stoxos_kostous;

	public Salesman(int arithmos_shmeiwn, int[][] kostos, int arxiko_shmeio, int stoxos_kostous) {
		this.arithmos_shmeiwn = arithmos_shmeiwn;
		this.sundiasmoi = arithmos_shmeiwn - 1;
		this.kostos = kostos;
		this.arxiko_shmeio = arxiko_shmeio;
		this.stoxos_kostous = stoxos_kostous;

		arithmos_diadromwn = 5;
		megethos_anaparagwghs = 5;
		arithmos_ekseliksewn = 5; // ΜΕΓΙΣΤΟΣ ΑΡΙΘΜΟΣ ΑΠΟΓΟΝΩΝ ΠΟΥ ΘΑ ΕΞΕΛΙΧΘΟΥΝ
		pososto_metallakshs = 0.1f;
	}

//Αρχική Συνάρτηση Αλγορίθμου για Βελτιστοποίηση
	public SalesmanGenome veltistopoihsh() {

		List<SalesmanGenome> diadromes = new ArrayList<>(); // Φτιαχνει λιστα με διαδρομές
		System.out.println("ΔΙΑΔΡΟΜΕΣ:");
		for (int i = 0; i < arithmos_diadromwn; i++) { // Δημιουργεί 5 διαδρομές (όσες αναθέσαμε στην μεταβλητη)		
			diadromes.add(new SalesmanGenome(arithmos_shmeiwn, kostos, arxiko_shmeio));  
			System.out.println("Κόστος:" + diadromes.get(i).getkostos() +"-"+ "Διαδρομή"+ diadromes.get(i).getdiadromh()+ "Με αρχή και Τέλος το σημείο"+" "+diadromes.get(i).arxiko_point );
			
		}
		SalesmanGenome kaluterh_diadromh = diadromes.get(0); // Παιρνει το πρωτο απο την λίστα διαδρομών
		for (int i = 0; i < arithmos_ekseliksewn; i++) {
			List<SalesmanGenome> selected = selection(diadromes); // Φτιάχνει λίστα με τους γονεις προς αναπαραγωγη
			diadromes = dimiourgia_apogonwn(selected); // Παιρνει την λιστα με τους γοενεις προς αναπαραγωγη που εφτιαξε
													// στην προηγουμενη συναρτηση και φτιαχνει διαδρομες
			kaluterh_diadromh = Collections.min(diadromes); // ΠΑΙΡΝΕΙ ΤΗΝ ΜΙΚΡΟΤΕΡΗ ΔΙΑΔΡΟΜΗ
			if (kaluterh_diadromh.getkostos() < stoxos_kostous)
				break;
		}
		return kaluterh_diadromh;
	}

	// ΕΔΩ ΕΠΙΛΕΓΟΝΤΑΙ ΟΙ ΓΟΝΕΙΣ ΓΙΑ ΑΝΑΠΑΡΑΓΩΓΗ
	public List<SalesmanGenome> selection(List<SalesmanGenome> diadromes) {
		List<SalesmanGenome> selected = new ArrayList<>();
		//System.out.println("Λίστα Γονέων Διαδρομής:");
		for (int i = 0; i < megethos_anaparagwghs; i++) { // ΠΌΣΑ ΘΑ ΑΝΑΠΑΡΆΞΟΥΜΕ
			float total_fitnes = 0;
			for (SalesmanGenome diadromh : diadromes) {
				total_fitnes += (float) 1 / diadromh.getkostos(); // αθροιζουμε τα κοστη
			}
			//System.out.println("Συνολικό Κόστος Διαδρομής="+ total_fitnes);
			Random random = new Random();
			float random_fitnes = 0 + random.nextFloat() * (total_fitnes - 0); // RANDOM AΠΟ ΤΟ ΑΘΡΟΙΣΜΑ ΤΟΥ ΚΟΣΤΟΥΣ
																				// FLOAT
			//System.out.println("Random από το Κόστος Διαδρομής="+ random_fitnes);
			float currentSum = 0;
			for (SalesmanGenome diadromh : diadromes) {
				currentSum += (float) 1 / diadromh.getkostos();
				if (currentSum >= random_fitnes) {
					selected.add(diadromh); // ΒΑΖΕΙ ΣΕ ΛΙΣΤΑ ΓΟΝΕΩΝ ΟΣΑ ΕΙΝΑΙ ΜΕΓΑΛΥΤΕΡΑ ΑΠΟ ΤΟ RANDOM
				}		
				
			}
			
			//System.out.println("Κόστος:" + selected.get(i).getkostos() +"-"+ "Διαδρομή"+ selected.get(i).getdiadromh()+ "Με αρχή και Τέλος το σημείο"+" "+selected.get(i).arxiko_point );
		}
		
		return selected;
	}

	// ΑΝΑΠΑΡΑΓΩΓΗ ΓΩΝΕΩΝ
	public List<SalesmanGenome> dimiourgia_apogonwn(List<SalesmanGenome> diadromes) {
		List<SalesmanGenome> generation = new ArrayList<>();
		int current_arithmos_diadromwn = 0;
		while (current_arithmos_diadromwn < arithmos_diadromwn) { // φτιαχνει διαδρομες για τα παιδια όσες και οι
																	// αρχικες.
			List<SalesmanGenome> paidia = crossover(diadromes);
			paidia.set(0, metalaksh(paidia.get(0))); // ΜΕΤΑΛΆΞΗ ΕΝΟΣ ΨΗΦΙΟΥ "0"
			paidia.set(1, metalaksh(paidia.get(1)));// ΜΕΤΑΛΆΞΗ ΕΝΟΣ ΨΗΦΙΟΥ "1"
			generation.addAll(paidia); // ΠΡΟΣΘΕΤΕΙ ΣΤΗΝ ΛΙΣΤΑ ΑΝΑΠΑΡΑΓΩΓΗΣ ΤΑ ΠΑΙΔΙΑ ΜΕΤΑ ΚΑΙ ΤΗΝ ΜΕΤΑΛΛΑΞΗ ΜΕ
											// ΠΟΣΟΣΤΟ 10%
			current_arithmos_diadromwn += 2;
		}
		return generation; // ΟΙ ΑΝΑΠΑΡΑΓΩΓΗ ΘΑ ΓΙΝΕΙ ΣΕ DIADROMES/2 ΠΑΙΔΙΑ.
	}

	public List<SalesmanGenome> crossover(List<SalesmanGenome> parents) {
		Random random = new Random();
		int breakpoint = random.nextInt(sundiasmoi);
		List<SalesmanGenome> paidia = new ArrayList<>();

		List<Integer> diadromh_1_gonea = new ArrayList<>(parents.get(0).getdiadromh()); // παιρνει τον ενδυαμεσο συνδιασμο
																						// του 1ου στοιχειου
		List<Integer> diadromh_2_gonea = new ArrayList<>(parents.get(1).getdiadromh());// παιρνει τον ενδυαμεσο συνδιασμο
																					// του 2ου στοιχειου

		// ΔΗΜΙΟΥΡΓΙΑ ΠΑΙΔΙΟΥ
		for (int i = 0; i < breakpoint; i++) {
			int newVal;
			newVal = diadromh_2_gonea.get(i);
			Collections.swap(diadromh_1_gonea, diadromh_1_gonea.indexOf(newVal), i);
		}
		paidia.add(new SalesmanGenome(diadromh_1_gonea, arithmos_shmeiwn, kostos, arxiko_shmeio));
		diadromh_1_gonea = parents.get(0).getdiadromh(); 

		// ΔΗΜΙΟΥΡΓΙΑ ΠΑΙΔΙΟΥ
		for (int i = breakpoint; i < sundiasmoi; i++) {
			int newVal = diadromh_1_gonea.get(i);
			Collections.swap(diadromh_2_gonea, diadromh_2_gonea.indexOf(newVal), i);
		}
		paidia.add(new SalesmanGenome(diadromh_2_gonea, arithmos_shmeiwn, kostos, arxiko_shmeio));

		return paidia;
	}

	// ΜΕΤΑΛΑΞΗ ΜΕ ΠΟΣΟΣΤΟ 10%
	public SalesmanGenome metalaksh(SalesmanGenome salesman) {
		Random random = new Random();
		float metalaksh = random.nextFloat();
		if (metalaksh < pososto_metallakshs) {
			List<Integer> diadromh = salesman.getdiadromh();
			Collections.swap(diadromh, random.nextInt(sundiasmoi), random.nextInt(sundiasmoi));
			return new SalesmanGenome(diadromh, arithmos_shmeiwn, kostos, arxiko_shmeio);
		}
		return salesman;
	}

}
