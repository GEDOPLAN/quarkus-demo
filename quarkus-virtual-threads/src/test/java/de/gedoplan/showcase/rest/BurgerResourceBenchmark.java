package de.gedoplan.showcase.rest;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark for rough comparison of thread and completion stage approaches.
 *
 * Notes:
 * - The api url called is coded into the benchmark
 * - The called application must be running before starting the benchmark
 * - All logging and delays must be switched off (see application.properties)
 */
@State(Scope.Benchmark)
public class BurgerResourceBenchmark {

  @Param({ "cs", "vt" })
  public String path;

  @Benchmark
  @Fork(1)
  @Warmup(iterations = 3, time = 5)
  @Measurement(iterations = 5, time = 5)
  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.MICROSECONDS)
  public void requestBurger(Blackhole blackhole) {
    Client client = null;
    try {
      client = ClientBuilder.newClient();
      WebTarget target = client.target("http://localhost:8080/" + this.path + "/burger");
      blackhole.consume(target.request().get());
    } finally {
      client.close();
    }
  }

  public static void main(String... args) throws Exception {
    Options opt = new OptionsBuilder()
      .include(BurgerResourceBenchmark.class.getSimpleName())
      .build();
    Collection<RunResult> runResults = new Runner(opt).run();
  }

}
