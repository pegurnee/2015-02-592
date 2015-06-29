package os.proj.p2.workers;

public enum WorkerStatus {
	FINISHED("Finished"), WAITING("Waiting"), WORKING("Working");
	private String state;

	private WorkerStatus(String state) {
		this.state = state;
	}

	public String getState() {
		return this.state;
	}

}
