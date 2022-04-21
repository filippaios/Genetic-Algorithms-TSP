import java.util.*;

public class Salesmench {
	private int arithmos_diadromwn;
	private int sundiasmoi;
	private int arithmos_shmeiwn;
	private int reproductionSize;
	private int maxIterations;
	private float mutationRate;
	private int[][] kostos;
	private int arxiko_shmeio;
	private int targetFitness;

	public Salesmench(int arithmos_shmeiwn, int[][] kostos, int arxiko_shmeio, int targetFitness) {
		this.arithmos_shmeiwn = arithmos_shmeiwn;
		this.sundiasmoi = arithmos_shmeiwn - 1;
		this.kostos = kostos;
		this.arxiko_shmeio = arxiko_shmeio;
		this.targetFitness = targetFitness;

		arithmos_diadromwn = 20;
		reproductionSize = 5;
		maxIterations = 5;
		mutationRate = 0.1f;
	}

//ΑΝΑΠΑΡΑΓΩΓΗ ΜΕ ΤΗΝ ΜΕΘΟΔΟ ΡΟΥΛΕΤΑΣ

	public SalesmanGenome mutate(SalesmanGenome salesman) {
		Random random = new Random();
		float mutate = random.nextFloat();
		if (mutate < mutationRate) {
			List<Integer> diadromh = salesman.getGenome();
			Collections.swap(diadromh, random.nextInt(sundiasmoi), random.nextInt(sundiasmoi));
			return new SalesmanGenome(diadromh, arithmos_shmeiwn, kostos, arxiko_shmeio);
		}
		return salesman;
	}

//Αρχικη συνάρτηση
	public SalesmanGenome optimize() {

		List<SalesmanGenome> diadromes = new ArrayList<>(); // Φτιαχνει λιστα με διαδρομές
		for (int i = 0; i < arithmos_diadromwn; i++) { // κάνει Χ επαναληψεις οσες του εχουμε δωσει να κανει
			diadromes.add(new SalesmanGenome(arithmos_shmeiwn, kostos, arxiko_shmeio));
		}
		SalesmanGenome globalBestGenome = diadromes.get(0); // παιρνει το πρωτο απο την αλη σαν αρχικοποιηση
		for (int i = 0; i < maxIterations; i++) {
			List<SalesmanGenome> selected = selection(diadromes); // φτιαχνει λιστες με τους γονεις προς αναπαραγωγη
			diadromes = createGeneration(selected); // φτιαχνει διαδρομες για τα παιδια των γονεων
			globalBestGenome = Collections.min(diadromes);
			if (globalBestGenome.getFitness() < targetFitness)
				break;
		}
		return globalBestGenome;
	}

	// ΕΔΩ ΕΠΙΛΕΓΟΝΤΑΙ ΟΙ ΓΟΝΕΙΣ ΓΙΑ ΑΝΑΠΑΡΑΓΩΓΗ
	public List<SalesmanGenome> selection(List<SalesmanGenome> diadromes) {
		List<SalesmanGenome> selected = new ArrayList<>();
		for (int i = 0; i < reproductionSize; i++) { // ΠΌΣΑ ΘΑ ΑΝΑΠΑΡ’ΞΟΥΜΕ
			float total_fitnes = 0;
			for (SalesmanGenome diadromh : diadromes) {
				total_fitnes += (float) 1 / diadromh.getFitness(); // αθροιζουμε τα κοστοι
			}
			Random random = new Random();
			float random_fitnes = 0 + random.nextFloat() * (total_fitnes - 0); // RANDOM AΠΟ ΤΟ ΑΘΡΟΙΣΜΑ ΤΟΥ ΚΟΣΤΟΥΣ
																				// FLOAT

			float currentSum = 0;
			for (SalesmanGenome diadromh : diadromes) {
				currentSum += (float) 1 / diadromh.getFitness();
				if (currentSum >= random_fitnes) {
					selected.add(diadromh); // ΒΑΖΕΙ ΣΕ ΛΙΣΤΑ ΓΟΝΕΩΝ ΟΣΑ ΕΙΝΑΙ ΜΕΓΑΛΥΤΕΡΑ ΑΠΟ ΤΟ RANDOM
				}
			}
		}
		return selected;
	}

	// We're using a generational algorithm, so we make an entirely new diadromes of
	// children:
	public List<SalesmanGenome> createGeneration(List<SalesmanGenome> diadromes) {
		List<SalesmanGenome> generation = new ArrayList<>();
		int current_arithmos_diadromwn = 0;
		while (current_arithmos_diadromwn < arithmos_diadromwn) {
			List<SalesmanGenome> parents = pickNRandomElements(diadromes, 5); // ΘΑ ΒΓΟΥΝ ΟΙ 5 ΓΟΝΕΙΣ me ΜΙΚΡΑ ΚΟΣΤΟΙ
			List<SalesmanGenome> children = crossover(diadromes);
			children.set(0, mutate(children.get(0)));
			children.set(1, mutate(children.get(1)));
			generation.addAll(children);
			current_arithmos_diadromwn += 2;
		}
		return generation;
	}

	// Τροποποιημένες τεχνικές διασταύρωσης και μετάλλαξης, οι οποίες δίνουν πάντα
	// έγκυρα χρωμοσώματα.
	public static <E> List<E> pickNRandomElements(List<E> list, int n) {
		Random r = new Random();
		int length = list.size();

		if (length < n)
			return null;

		for (int i = length - 1; i >= length - n; --i) {
			Collections.swap(list, i, r.nextInt(i + 1));
		}
		return list.subList(length - n, length);
	}

	public List<SalesmanGenome> crossover(List<SalesmanGenome> parents) {
		// housekeeping
		Random random = new Random();
		int breakpoint = random.nextInt(sundiasmoi);
		List<SalesmanGenome> children = new ArrayList<>();

		// copy parental genomes - we copy so we wouldn't modify in case they were
		// chosen to participate in crossover multiple times
		List<Integer> parent1Genome = new ArrayList<>(parents.get(0).getGenome());
		List<Integer> parent2Genome = new ArrayList<>(parents.get(1).getGenome());

		// creating child 1
		for (int i = 0; i < breakpoint; i++) {
			int newVal;
			newVal = parent2Genome.get(i);
			Collections.swap(parent1Genome, parent1Genome.indexOf(newVal), i);
		}
		children.add(new SalesmanGenome(parent1Genome, arithmos_shmeiwn, kostos, arxiko_shmeio));
		parent1Genome = parents.get(0).getGenome(); // reseting the edited parent

		// creating child 2
		for (int i = breakpoint; i < sundiasmoi; i++) {
			int newVal = parent1Genome.get(i);
			Collections.swap(parent2Genome, parent2Genome.indexOf(newVal), i);
		}
		children.add(new SalesmanGenome(parent2Genome, arithmos_shmeiwn, kostos, arxiko_shmeio));

		return children;
	}

	public void printGeneration(List<SalesmanGenome> generation) {
		for (SalesmanGenome diadromh : generation) {
			System.out.println(diadromh);
		}
	}
}
