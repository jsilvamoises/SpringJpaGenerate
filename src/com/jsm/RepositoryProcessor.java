/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jsm;

import java.io.BufferedWriter;
import java.util.Set;
import java.util.logging.Logger;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;

/**
 *
 * @author Moises
 */
@SupportedAnnotationTypes("com.jsm.Buildable")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class RepositoryProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Filer filer;

    Logger log = Logger.getLogger("RepositoryProcessor");

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv); //To change body of generated methods, choose Tools | Templates.
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }

    /*
    package com.jsm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsm.entities.Ncm;

public interface NcmRepository extends JpaRepository<Ncm, Long> {

}
     */

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)");
        String basePackage = "";
        for (Element element : roundEnv.getElementsAnnotatedWith(Buildable.class)) {

            if (element.getKind() == ElementKind.CLASS) {
                basePackage = element.getAnnotation(Buildable.class).rootPackage();
                basePackage += ".repository";
                System.out.println("Pacote base: " + basePackage );
                
                
                TypeElement classElement = (TypeElement) element;
                PackageElement packageElement = (PackageElement) element.getEnclosingElement();
                
                try {
                    JavaFileObject jfo = processingEnv.getFiler().createSourceFile(basePackage + "." + classElement.getSimpleName() + "Repository");
                    BufferedWriter bw = new BufferedWriter(jfo.openWriter());
                    bw.append("package ");
                    bw.append(basePackage);
                    bw.append(";");
                    bw.newLine();
                    bw.newLine();
                    bw.append("import org.springframework.data.jpa.repository.JpaRepository;");
                    bw.newLine();
                    bw.append("import org.springframework.stereotype.Repository;");
                    bw.newLine();
                    bw.append("import ");
                    bw.append(classElement.getQualifiedName());
                    bw.append(";");
                    bw.newLine();
                    bw.newLine();
                    //public interface NcmRepository extends JpaRepository<Ncm, Long> {
                    bw.append("@Repository");
                    bw.newLine();
                    bw.append("public interface ");
                    bw.append(classElement.getSimpleName() + "Repository ");
                    bw.append("extends JpaRepository<");
                    bw.append(element.getSimpleName());
                    bw.append(", Long>{");
                    bw.newLine();
                    bw.append("}");
                    bw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("É a vovozinha");
            }
        }
        return true;
    }

}
