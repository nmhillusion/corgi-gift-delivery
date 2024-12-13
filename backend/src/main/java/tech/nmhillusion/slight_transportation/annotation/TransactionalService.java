package tech.nmhillusion.slight_transportation.annotation;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-12-13
 */

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Transactional
@Service
public @interface TransactionalService {
}
