package cmc.utils.redisson;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

//@Aspect
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class DistributeLockAop {
//    private static final String REDISSON_KEY_PREFIX = "RLOCK_";
//
//    private final RedissonClient redissonClient;
//    private final AopForTransaction aopForTransaction;
//
//
//    @Around("@annotation(cmc.utils.redisson.DistributeLock)")
//    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        DistributeLock distributeLock = method.getAnnotation(DistributeLock.class);     // (1)
//
//        String key = REDISSON_KEY_PREFIX + CustomSpringELParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), distributeLock.key());    // (2)
//
//        RLock rLock = redissonClient.getLock(key);    // (3)
//
//        try {
//            boolean available = rLock.tryLock(distributeLock.waitTime(), distributeLock.leaseTime(), distributeLock.timeUnit());    // (4)
//            if (!available) {
//                return false;
//            }
//
//            log.info("get lock success {}" , key);
//            return aopForTransaction.proceed(joinPoint);    // (5)
//        } catch (Exception e) {
//            log.warn("get lock fail {}" , key);
//            Thread.currentThread().interrupt();
//            throw new InterruptedException();
//        } finally {
//            rLock.unlock();    // (6)
//        }
//    }
//}
