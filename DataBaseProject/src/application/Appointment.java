package application;

public class Appointment {
	private String appointment_id;
	private String employee_id;
	private String customer_id;
	private String appointment_date;
	private String appointment_time;

	public Appointment(String employee_id, String customer_id, String date, String time) {
		super();
		this.employee_id = employee_id;
		this.customer_id = customer_id;
		this.appointment_date = date;
		this.appointment_time = time;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public String getAppointment_id() {
		return appointment_id;
	}

	public void setAppointment_id(String appointment_id) {
		this.appointment_id = appointment_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getAppointment_date() {
		return appointment_date;
	}

	public void setAppointment_date(String appointment_date) {
		this.appointment_date = appointment_date;
	}

	public String getAppointment_time() {
		return appointment_time;
	}

	public void setAppointment_time(String appointment_time) {
		this.appointment_time = appointment_time;
	}

}
