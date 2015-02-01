package vairy.invoker.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface VCmdParamAnnotation{
	int num() default -1;
}
