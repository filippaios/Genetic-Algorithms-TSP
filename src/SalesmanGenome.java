import java.util.*;

public class SalesmanGenome implements Comparable {
	List<Integer> diadromh;
	int[][] kostoi;
	int arxiko_point;
	int arithmos_shmeiwn = 0;
	int kostos;

	public SalesmanGenome(int arithmos_shmeiwn, int[][] kostoi, int arxiko_point) {
		this.kostoi = kostoi;
		this.arxiko_point = arxiko_point;
		this.arithmos_shmeiwn = arithmos_shmeiwn;
		diadromh = randomSalesman(); // random 4 ������ ���������
		kostos = this.upologismos_kostous(); // ���� �� ���������� �� ������ ����
	}

	public SalesmanGenome(List<Integer> metalagmenes_diadromes, int arithmos_shmeiwn, int[][] kostoi,
			int arxiko_point) {
		diadromh = metalagmenes_diadromes;
		this.kostoi = kostoi;
		this.arxiko_point = arxiko_point;
		this.arithmos_shmeiwn = arithmos_shmeiwn;
		kostos = this.upologismos_kostous();
	}

//����������� �������
	public int upologismos_kostous() {
		int kostos = 0;
		int shmeio = arxiko_point;
		for (int gene : diadromh) {
			kostos += kostoi[shmeio][gene]; // �� ���� ��������� ��������� 1 �������� ���� ������ ��� ���������� ������
			shmeio = gene;
		}
		kostos += kostoi[diadromh.get(arithmos_shmeiwn - 2)][arxiko_point]; // ��������� ��� ��������� �������� ��� ��
																			// ������ ���� ���� ����
		return kostos;
	}

	// ������ ���������� �������
	private List<Integer> randomSalesman() { // ����� ������ ����� �� ��������� ������
		List<Integer> lista = new ArrayList<Integer>();
		for (int i = 0; i < arithmos_shmeiwn; i++) {
			if (i != arxiko_point)
				lista.add(i);
		}
		Collections.shuffle(lista);
		return lista;
	}

	public List<Integer> getdiadromh() {
		return diadromh;
	}

	public int getarxiko_point() {
		return arxiko_point;
	}

	public int getkostos() {
		return kostos;
	}

	public void setkostos(int kostos) {
		this.kostos = kostos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Path: ");
		builder.append(arxiko_point);
		for (int gene : diadromh) {
			builder.append(" ");
			builder.append(gene);
		}
		builder.append(" ");
		builder.append(arxiko_point);
		builder.append("\nLength: ");
		builder.append(this.kostos);
		return builder.toString();
	}

	@Override
	public int compareTo(Object o) {
		SalesmanGenome diadromh = (SalesmanGenome) o;
		if (this.kostos > diadromh.getkostos())
			return 1;
		else if (this.kostos < diadromh.getkostos())
			return -1;
		else
			return 0;
	}
}
