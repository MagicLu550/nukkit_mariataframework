package net.mariataframework.noyark.nukkit.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target({TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface CommandHandler {

    String fallbackPrefix();

}
