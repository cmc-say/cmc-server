package cmc.utils.redisson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributeLock {
    String key(); // (1)

    TimeUnit timeUnit() default TimeUnit.MILLISECONDS; // (2)

    long waitTime() default 300L; // (3)

    long leaseTime() default 500L; // (4)
}
