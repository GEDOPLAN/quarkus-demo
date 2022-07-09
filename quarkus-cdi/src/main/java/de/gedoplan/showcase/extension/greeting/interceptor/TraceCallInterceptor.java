package de.gedoplan.showcase.interceptor;

import de.gedoplan.baselibs.utils.util.ClassUtil;

import java.io.Serializable;

import javax.annotation.Priority;
import javax.interceptor.AroundConstruct;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Interceptor for {@link TraceCall}.
 *
 * @author dw
 */
@TraceCall
@Interceptor
@Priority(2500)
public class TraceCallInterceptor implements Serializable {

  @AroundInvoke
  public Object traceMethod(InvocationContext invocationContext) throws Exception {
    // As simple solution output into STDOUT is sufficient.
    // In real life use a logging framework instead!
    // System.out.println(invocationContext.getMethod());

    log(invocationContext.getTarget().getClass(), invocationContext.getMethod().getName());
    return invocationContext.proceed();
  }

  @AroundConstruct
  public Object traceCtor(InvocationContext invocationContext) throws Exception {
    // invocationContext.getTarget() returns null, if called before proceed()!
    log(invocationContext.getConstructor().getDeclaringClass(), "constructor");
    return invocationContext.proceed();
  }

  // @PostConstruct
  public Object tracePostConstruct(InvocationContext invocationContext) throws Exception {
    log(invocationContext.getTarget().getClass(), "@PostConstruct method");
    return invocationContext.proceed();
  }

  // @PreDestroy
  public Object tracePreDestroy(InvocationContext invocationContext) throws Exception {
    log(invocationContext.getTarget().getClass(), "@PreDestroy method");
    return invocationContext.proceed();
  }

  private static void log(Class<? extends Object> clazz, String text) {
    Log log = LogFactory.getLog(ClassUtil.getProxiedClass(clazz));
    if (log.isTraceEnabled()) {
      log.trace("Call " + text);
    }
  }
}
