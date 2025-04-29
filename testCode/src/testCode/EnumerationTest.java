package testCode;

public class EnumerationTest {

	public enum trafficSignal {
		RED("정지", 60), YELLOW("주의", 5), GREEN("진행", 30);

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
			System.out.println(light.name() + ": " + light.getDescription() + " (" + light.getWaitTime() + "초)");
		}
		trafficSignal signal = trafficSignal.YELLOW;
		switch (signal) {
		case RED -> System.out.println("멈추세요!");
		case YELLOW -> System.out.println("감속하세요!");
		case GREEN -> System.out.println("출발하세요!");
		}
	}
}