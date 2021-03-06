package io.scalecube.socketio.examples.perf;

import com.google.monitoring.runtime.instrumentation.AllocationRecorder;
import com.google.monitoring.runtime.instrumentation.Sampler;

/**
 * It requires flag: -javaagent:path/to/allocation.jar
 *
 * See: https://github.com/google/allocation-instrumenter
 */
public class AllocationRecorderExample {
  public static void main(String[] args) throws Exception {
    AllocationRecorder.addSampler(new Sampler() {
      public void sampleAllocation(int count, String desc, Object newObj, long size) {
        System.out.println("I just allocated the object " + newObj + " of type " + desc + " whose size is " + size);
        if (count != -1) {
          System.out.println("It's an array of size " + count);
        }
      }
    });
    new String("foo");
  }
}
