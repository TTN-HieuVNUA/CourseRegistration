package model;

public class registerCourse extends subjects{
	
	private String masv;
	private int slmax;
	private int slcl;

	public registerCourse(String maMonHoc, String tenMonHoc, String malop, int soTinChi) {
		super(maMonHoc, tenMonHoc, malop, soTinChi);
	}
	public registerCourse(String maMonHoc, String tenMonHoc, String malop, int soTinChi, String masv) {
		super(maMonHoc, tenMonHoc, malop, soTinChi);
		this.masv = masv;
	}
	public registerCourse(String maMonHoc, String tenMonHoc, String malop, int soTinChi, int slmax, int slcl) {
		super(maMonHoc, tenMonHoc, malop, soTinChi);
		this.masv = masv;
		this.slmax = slmax;
		this.slcl = slcl;
	}
	public String getMasv() {
		return masv;
	}
	public void setMasv(String masv) {
		this.masv = masv;
	}
	
	public int getSlmax() {
		return slmax;
	}
	public void setSlmax(int slmax) {
		this.slmax = slmax;
	}
	public int getSlcl() {
		return slcl;
	}
	public void setSlcl(int slcl) {
		this.slcl = slcl;
	}
	@Override
	public String toString() {
		return "registerCourse [masv=" + masv + ", slmax=" + slmax + ", slcl=" + slcl + ", toString()="
				+ super.toString() + "]";
	}

	

}