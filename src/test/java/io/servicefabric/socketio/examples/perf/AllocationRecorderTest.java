package io.servicefabric.socketio.examples.perf;

import java.lang.instrument.UnmodifiableClassException;
import com.google.monitoring.runtime.instrumentation.ConstructorInstrumenter;
import com.google.monitoring.runtime.instrumentation.ConstructorCallback;

public class AllocationRecorderTest {

	static int count = 0;

	int x;
	AllocationRecorderTest() {
		x = count;
	}

	public static void main(String[] args) {
		try {
			ConstructorInstrumenter.instrumentClass(
					AllocationRecorderTest.class, new ConstructorCallback<AllocationRecorderTest>() {
						@Override public void sample(AllocationRecorderTest t) {
							System.out.println("Constructing an element of type AllocationRecorderTest with x = " + t.x);
							count++;
						}
					});
		} catch (UnmodifiableClassException e) {
			System.out.println("Class cannot be modified");
		}
		for (int i = 0; i < 10; i++) {
			new AllocationRecorderTest();
		}
		System.out.println("Constructed " + count + " instances of Test");
	}

}
