import java.util.*;

public class Salesmench {
	private int generationSize;
	private int genomeSize;
	private int numberOfCities;
	private int reproductionSize;
	private int maxIterations;
	private float mutationRate;
	private int[][] travelPrices;
	private int startingCity;
	private int targetFitness;

	public Salesmench(int numberOfCities, int[][] travelPrices, int startingCity, int targetFitness) {
		this.numberOfCities = numberOfCities;
		this.genomeSize = numberOfCities - 1;
		this.travelPrices = travelPrices;
		this.startingCity = startingCity;
		this.targetFitness = targetFitness;

		generationSize = 20;
		reproductionSize = 5;
		maxIterations = 5;
		mutationRate = 0.1f;
	}

//����������� �� ��� ������ ��������

//�������������� �������� ������������ ��� ����������, �� ������ ������ ����� ������
	// �����������.
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

	public SalesmanGenome mutate(SalesmanGenome salesman) {
		Random random = new Random();
		float mutate = random.nextFloat();
		if (mutate < mutationRate) {
			List<Integer> genome = salesman.getGenome();
			Collections.swap(genome, random.nextInt(genomeSize), random.nextInt(genomeSize));
			return new SalesmanGenome(genome, numberOfCities, travelPrices, startingCity);
		}
		return salesman;
	}

//We're using a generational algorithm, so we make an entirely new population of children:
	public List<SalesmanGenome> createGeneration(List<SalesmanGenome> population) {
		List<SalesmanGenome> generation = new ArrayList<>();
		int currentGenerationSize = 0;
		while (currentGenerationSize < generationSize) {
			List<SalesmanGenome> parents = pickNRandomElements(population, 5); // �� ����� �� 2 ������
			List<SalesmanGenome> children = crossover(parents);
			children.set(0, mutate(children.get(0)));
			children.set(1, mutate(children.get(1)));
			generation.addAll(children);
			currentGenerationSize += 2;
		}
		return generation;
	}

	public List<SalesmanGenome> crossover(List<SalesmanGenome> parents) {
		// housekeeping
		Random random = new Random();
		int breakpoint = random.nextInt(genomeSize);
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
		children.add(new SalesmanGenome(parent1Genome, numberOfCities, travelPrices, startingCity));
		parent1Genome = parents.get(0).getGenome(); // reseting the edited parent

		// creating child 2
		for (int i = breakpoint; i < genomeSize; i++) {
			int newVal = parent1Genome.get(i);
			Collections.swap(parent2Genome, parent2Genome.indexOf(newVal), i);
		}
		children.add(new SalesmanGenome(parent2Genome, numberOfCities, travelPrices, startingCity));

		return children;
	}

	public SalesmanGenome optimize() {

		List<SalesmanGenome> population = new ArrayList<>(); // �������� ����� �� Genome
		for (int i = 0; i < generationSize; i++) { // ����� � ����������� ���� ��� ������ ����� �� �����
			population.add(new SalesmanGenome(numberOfCities, travelPrices, startingCity));
		}
		SalesmanGenome globalBestGenome = population.get(0); // ������� �� ����� ��� ��� ��� ��� ������������
		for (int i = 0; i < maxIterations; i++) {
			List<SalesmanGenome> selected = selection(population);
			population = createGeneration(selected);
			globalBestGenome = Collections.min(population);
			if (globalBestGenome.getFitness() < targetFitness)
				break;
		}
		return globalBestGenome;
	}

	// ��� ����������� �� ������ ��� �����������
	public List<SalesmanGenome> selection(List<SalesmanGenome> population) {
		List<SalesmanGenome> selected = new ArrayList<>();
		for (int i = 0; i < reproductionSize; i++) { // м�� �� �����Ѣ�����	
			float total_fitnes = 0;
			for (SalesmanGenome genome : population) {
				total_fitnes += (float) 1 / genome.getFitness();
			}
			Random random = new Random();
			float random_fitnes = 0 + random.nextFloat() * (total_fitnes - 0); //RANDOM A�� �� �������� ��� ������� FLOAT

			float currentSum = 0;
			for (SalesmanGenome genome : population) {
				currentSum += (float) 1 / genome.getFitness();
				if (currentSum >= random_fitnes) {
					selected.add(genome); // ����� �� ����� ������ ��� ����� ���������� ��� �� RANDOM
				}
			}
		}
		return selected;
	}

	public void printGeneration(List<SalesmanGenome> generation) {
		for (SalesmanGenome genome : generation) {
			System.out.println(genome);
		}
	}
}
