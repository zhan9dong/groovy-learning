package com.dearle.groovydsl.chapters.eight.transforms

import org.codehaus.groovy.transform.GroovyASTTransformationClass

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target([ElementType.TYPE])
@Retention(RetentionPolicy.SOURCE)
@GroovyASTTransformationClass(["com.dearle.groovydsl.chapters.eight.transforms.PrettyBasicASTTransformation"])
public @interface PrettyBasic {

}