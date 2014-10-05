package ak.duorum.hibernate.validation;

import org.hibernate.validator.constraints.ConstraintComposition;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hibernate.validator.constraints.CompositionType.OR;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/28/14
 */
@ConstraintComposition(OR)
@Size(min = 2, max = 3)
@Null
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface NullOrLength {

    public abstract String message() default "{PatternOrSize.message}";

    public abstract Class<?>[] groups() default {};

    public abstract Class<? extends Payload>[] payload() default {};
}
