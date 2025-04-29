package testCode;

public class EnumerationTest {

	public enum trafficSignal {
		RED("����", 60), YELLOW("����", 5), GREEN("����", 30);

		private String description;
		private int seconds;

		private trafficSignal(String description, int seconds) {
			this.description = description;
			this.seconds = seconds;
		}

		public String getDescription() {
			return this.description;
		}

		public int getWaitTime() {
			return this.seconds;
		}

	}

	public static void main(String[] args) {
		for (trafficSignal light : trafficSignal.values()) {
			System.out.println(light.name() + ": " + light.getDescription() + " (" + light.getWaitTime() + "��)");
		}
		trafficSignal signal = trafficSignal.YELLOW;
		switch (signal) {
		case RED -> System.out.println("���߼���!");
		case YELLOW -> System.out.println("�����ϼ���!");
		case GREEN -> System.out.println("����ϼ���!");
		}
	}
}