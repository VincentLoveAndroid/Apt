package com.example;

import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by vincent on 2017/4/24.
 * email-address:674928145@qq.com
 * description:注解解析器
 */

public class AnnotationProcessor extends AbstractProcessor {
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnv);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(PostAfterAnimation.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(PostAfterAnimation.class);
        Map<String, ElementRecord> elementsInType = new HashMap<>();
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            String typeElementName = typeElement.getQualifiedName().toString();
            ElementRecord elementRecord = elementsInType.get(typeElementName);
            if (elementRecord == null) {
                elementRecord = new ElementRecord(typeElement);
                elementsInType.put(typeElementName, elementRecord);
            }
            elementRecord.addElement(element);
        }

        for (String typeElementName : elementsInType.keySet()) {
//            Writer writer = null;
            try {
//                JavaFileObject fileObject = filer.createSourceFile(typeElementName + "_Inject");
//                writer = fileObject.openWriter();
                String packageName = typeElementName.substring(0, typeElementName.lastIndexOf("."));
                ElementRecord elementRecord = elementsInType.get(typeElementName);
                JavaFile javaFile = JavaFile.builder(packageName, elementRecord.createSpec()).build();
                javaFile.writeTo(filer);

//                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                messager.printMessage(Diagnostic.Kind.ERROR, e.toString());
            } finally {
//                if(writer != null){
//                    try {
//                        writer.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        }
        return true;
    }
}
