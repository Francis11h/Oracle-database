package com.zzhs.entity;

public class Repair_Service {
	private int svid;
	private String specific_Problem;
	private int is_First_Time;
	private int report;
	private double diagnostic_Fee;
	private double repair_Fee;
	
	public Repair_Service() {
		
	}

	public Repair_Service(int svid, String specific_Problem, int is_First_Time, int report, double diagnostic_Fee,
			double repair_Fee) {
		super();
		this.svid = svid;
		this.specific_Problem = specific_Problem;
		this.is_First_Time = is_First_Time;
		this.report = report;
		this.diagnostic_Fee = diagnostic_Fee;
		this.repair_Fee = repair_Fee;
	}

	public int getSvid() {
		return svid;
	}

	public void setSvid(int svid) {
		this.svid = svid;
	}

	public String getSpecific_Problem() {
		return specific_Problem;
	}

	public void setSpecific_Problem(String specific_Problem) {
		this.specific_Problem = specific_Problem;
	}

	public int getIs_First_Time() {
		return is_First_Time;
	}

	public void setIs_First_Time(int is_First_Time) {
		this.is_First_Time = is_First_Time;
	}

	public int getReport() {
		return report;
	}

	public void setReport(int report) {
		this.report = report;
	}

	public double getDiagnostic_Fee() {
		return diagnostic_Fee;
	}

	public void setDiagnostic_Fee(double diagnostic_Fee) {
		this.diagnostic_Fee = diagnostic_Fee;
	}

	public double getRepair_Fee() {
		return repair_Fee;
	}

	public void setRepair_Fee(double repair_Fee) {
		this.repair_Fee = repair_Fee;
	}

	@Override
	public String toString() {
		return "Repair_Service [svid=" + svid + ", specific_Problem=" + specific_Problem + ", is_First_Time="
				+ is_First_Time + ", report=" + report + ", diagnostic_Fee=" + diagnostic_Fee + ", repair_Fee="
				+ repair_Fee + "]";
	}
	
}
