package org.ulco.introspection;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
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
        List<Method> Methode = classeAbstraite("GraphicsObject");
        for (Method cl : Methode) {
            System.out.println( cl.getName());
        }
        boolean res= complet("GraphicsObject");

        if(res){
            System.out.println("Toutes les méthodes sont implémentes dans la sous classe");
        }

        question4(rep);
    }

    public static List<Method> classeAbstraite(String nom) throws ClassNotFoundException {

        String nom2 = pack + nom ;
        Class classe = Class.forName(nom2);
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

    public static boolean complet(String nomClasse) throws ClassNotFoundException{

        List<Method> methodesClassesAbstraites = classeAbstraite(nomClasse);

        Class classeAVerifier = Class.forName(pack + "Triangle");
        Method[] methodes = classeAVerifier.getMethods();

        List<String> nomsMethodes = new ArrayList<>();
        for(Method m : methodesClassesAbstraites){
            nomsMethodes.add(m.getName());
        }
        System.out.println("---");

        List<String> methodeAutreFonction = new ArrayList<>();
        for(Method m : methodes){
                methodeAutreFonction.add(m.getName());
        }

        boolean complet = true;
        for(String s: methodeAutreFonction){
            if(methodeAutreFonction.indexOf(s) == -1){
                complet = false;

            }
        }


        return complet;
    }


    public static void question4(List<String>  nomClasse)throws ClassNotFoundException{
        List<Class> sousClasses = SousClass.creationListe(nomClasse);

        for(Class c : sousClasses){
            Constructor[] constructors = c.getConstructors();
            for(Constructor cons : constructors){
                System.out.println(cons);

                Parameter[] paramaters = cons.getParameters();
                for(Parameter p : paramaters){
                    System.out.println(p);
                }
            }
        }

    }
}
