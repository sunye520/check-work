package com.yogee.youge.common.persistence.annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
/**
 * @
 * Created by HaipengRen on 2017/6/24 0024.
 */
public @interface ApiVersion {
    /**
     * 标识版本号
     * @return
     */
    int value();
}
