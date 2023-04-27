package dragon.util.tsv.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TsvColumnIndex {
    /**
     * @return the desired column index of the field when it is deserialized from tsv files
     */
    int value();
}
