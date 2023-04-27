package dragon.util.tsv.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TsvColumnName {
    /**
     * @return the desired name of the field when it is serialized or deserialized
     */
    String value();
//    /**
//     * @return the alternative names of the field when it is deserialized
//     */
//    String[] alternate() default {};
}
