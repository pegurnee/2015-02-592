package os.proj.p4.work;

public class WorkFactory {
	private int jobCount = 0;

	public JobWorkable createWork() {
		this.jobCount++;
		return new WorkWorkWork();
	}
}
