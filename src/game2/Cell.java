package game2;


public class Cell {
	private int i;
	private int j;
	private int secondI;
	private int secondJ;
	public Cell(int i, int j, int ii, int jj){
		this.setI(i);
		this.setJ(j);
		setSecondI(ii);
		setSecondJ(jj);
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		this.j = j;
	}
	public int getSecondI() {
		return secondI;
	}
	public void setSecondI(int secondI) {
		this.secondI = secondI;
	}
	public int getSecondJ() {
		return secondJ;
	}
	public void setSecondJ(int secondJ) {
		this.secondJ = secondJ;
	}
}
