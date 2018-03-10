/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Moises
 */
@Target({
    ElementType.TYPE,  
    ElementType.ANNOTATION_TYPE,
    ElementType.LOCAL_VARIABLE
})
@Retention(RetentionPolicy.SOURCE)
public @interface Buildable {
       String rootPackage();
       boolean generateRepository() default  true;
       boolean generateService() default  true;
       boolean generateResource() default  true;
      
}
