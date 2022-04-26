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

		arithmos_diadromwn = 20;
		megethos_anaparagwghs = 5;
		arithmos_ekseliksewn = 5; // �������� ������� �������� ��� �� ����������
		pososto_metallakshs = 0.1f;
	}

//������ ���������
	public SalesmanGenome optimize() {

		List<SalesmanGenome> diadromes = new ArrayList<>(); // �������� ����� �� ���������
		for (int i = 0; i < arithmos_diadromwn; i++) { // ����� � ����������� ���� ��� ������ ����� �� �����
			diadromes.add(new SalesmanGenome(arithmos_shmeiwn, kostos, arxiko_shmeio));
		}
		SalesmanGenome globalBestGenome = diadromes.get(0); // ������� �� ����� ��� ��� ��� ��� ������������
		for (int i = 0; i < arithmos_ekseliksewn; i++) {
			List<SalesmanGenome> selected = selection(diadromes); // �������� ������ �� ���� ������ ���� �����������
			diadromes = createGeneration(selected); // ������� ��� ����� �� ���� ������� ���� ����������� ��� �������
													// ���� ����������� ��������� ��� �������� ���������
			globalBestGenome = Collections.min(diadromes); // ������� ��� ��������� ��������
			if (globalBestGenome.getkostos() < stoxos_kostous)
				break;
		}
		return globalBestGenome;
	}

	// ��� ����������� �� ������ ��� �����������
	public List<SalesmanGenome> selection(List<SalesmanGenome> diadromes) {
		List<SalesmanGenome> selected = new ArrayList<>();
		for (int i = 0; i < megethos_anaparagwghs; i++) { // м�� �� �����Ѣ�����
			float total_fitnes = 0;
			for (SalesmanGenome diadromh : diadromes) {
				total_fitnes += (float) 1 / diadromh.getkostos(); // ���������� �� ������
			}
			Random random = new Random();
			float random_fitnes = 0 + random.nextFloat() * (total_fitnes - 0); // RANDOM A�� �� �������� ��� �������
																				// FLOAT

			float currentSum = 0;
			for (SalesmanGenome diadromh : diadromes) {
				currentSum += (float) 1 / diadromh.getkostos();
				if (currentSum >= random_fitnes) {
					selected.add(diadromh); // ����� �� ����� ������ ��� ����� ���������� ��� �� RANDOM
				}
			}
		}
		return selected;
	}

	// ����������� ������
	public List<SalesmanGenome> createGeneration(List<SalesmanGenome> diadromes) {
		List<SalesmanGenome> generation = new ArrayList<>();
		int current_arithmos_diadromwn = 0;
		while (current_arithmos_diadromwn < arithmos_diadromwn) { // �������� ��������� ��� �� ������ ���� ��� ��
																	// �������.
			List<SalesmanGenome> paidia = crossover(diadromes);
			paidia.set(0, mutate(paidia.get(0))); // ����ˢ�� ���� ������ "0"
			paidia.set(1, mutate(paidia.get(1)));// ����ˢ�� ���� ������ "1"
			generation.addAll(paidia); // ��������� ���� ����� ������������ �� ������ ���� ��� ��� ��������� ��
											// ������� 10%
			current_arithmos_diadromwn += 2;
		}
		return generation; // �� ����������� �� ����� �� DIADROMES/2 ������.
	}

	public List<SalesmanGenome> crossover(List<SalesmanGenome> parents) {

		Random random = new Random();
		int breakpoint = random.nextInt(sundiasmoi);
		List<SalesmanGenome> paidia = new ArrayList<>();

		List<Integer> diadromh_1_gonea = new ArrayList<>(parents.get(0).getdiadromh()); // ������� ��� ��������� ���������
																						// ��� 1�� ���������
		List<Integer> diadromh_2_gonea = new ArrayList<>(parents.get(1).getdiadromh());// ������� ��� ��������� ���������
																					// ��� 2�� ���������

		// ���������� �������
		for (int i = 0; i < breakpoint; i++) {
			int newVal;
			newVal = diadromh_2_gonea.get(i);
			Collections.swap(diadromh_1_gonea, diadromh_1_gonea.indexOf(newVal), i);
		}
		paidia.add(new SalesmanGenome(diadromh_1_gonea, arithmos_shmeiwn, kostos, arxiko_shmeio));
		diadromh_1_gonea = parents.get(0).getdiadromh(); // reseting the edited parent

		// ���������� �������
		for (int i = breakpoint; i < sundiasmoi; i++) {
			int newVal = diadromh_1_gonea.get(i);
			Collections.swap(diadromh_2_gonea, diadromh_2_gonea.indexOf(newVal), i);
		}
		paidia.add(new SalesmanGenome(diadromh_2_gonea, arithmos_shmeiwn, kostos, arxiko_shmeio));

		return paidia;
	}

	// �������� �� ������� 10%
	public SalesmanGenome mutate(SalesmanGenome salesman) {
		Random random = new Random();
		float mutate = random.nextFloat();
		if (mutate < pososto_metallakshs) {
			List<Integer> diadromh = salesman.getdiadromh();
			Collections.swap(diadromh, random.nextInt(sundiasmoi), random.nextInt(sundiasmoi));
			return new SalesmanGenome(diadromh, arithmos_shmeiwn, kostos, arxiko_shmeio);
		}
		return salesman;
	}

}
