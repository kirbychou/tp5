package org.ulco.introspection;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcarpent on 21/01/16.
 */
public class SousClass {
    Class lesClasses;
   static String pack = "org.ulco.";

    public SousClass(String lesClasses) throws ClassNotFoundException {
        this.lesClasses = Class.forName(lesClasses);
    }


    private static List<String> listerFichier(File rep) {

        String[] fichier = rep.list();
        List<String> listeFichier = new ArrayList<>();
        for (String f : fichier) {
            if (f.contains(".java")) {
                String str[] = f.split("\\.");
                listeFichier.add(str[0]);
            }
        }
        return listeFichier;
    }

    private static List<Class> creationListe(List<String> rep) throws ClassNotFoundException {
        List<Class> sousClasse = new ArrayList<>();
        for (String s : rep) {
            SousClass sc = new SousClass("org.ulco." + s);
            if (sc.lesClasses.getSuperclass().toString().equals("class org.ulco.GraphicsObject")) {
                sousClasse.add(sc.lesClasses);
            }
        }
        return sousClasse;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        List<String> rep = listerFichier(new File("./src/org/ulco"));
        List<Class> sousClasse = creationListe(rep);

        for (Class cl : sousClasse) {
            System.out.println("GraphicsObject: " + cl.getName());
        }
            System.out.println("__________");
        List<Method> Methode = classeAbstraite();
        for (Method cl : Methode) {
            System.out.println( cl.getName());
        }


    }

    public static List<Method> classeAbstraite() throws ClassNotFoundException {

        String nom = pack + "GraphicsObject";
        Class classe = Class.forName(nom);
        List<Method> methode = new ArrayList<>();
        if (Modifier.isAbstract(classe.getModifiers())) {
            Method[] champs = classe.getMethods();
            for (Method m : champs) {
                if (Modifier.isAbstract(m.getModifiers())) {
                   // System.out.println("\t" + m.getName());
                    methode.add(m);
                }

            }
        } else {
            //System.out.println("la classe n'est pas abstraite");
        }
        return methode;
    }
}
