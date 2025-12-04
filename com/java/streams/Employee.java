package com.java.streams;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

class Employeee implements Comparable<Employeee>{
    int id;
    String name;

    Employeee(Integer id, String name){
        this.id =  id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Employeee e)) return false;
        return this.id == e.id;
    }

  /*  @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }*/

    public static void main(String[] args) {
        Employeee e1 = new Employeee(5, "John");
        Employeee e2 = new Employeee(2, "Ze");

        HashSet<Employeee> set = new HashSet<>();
        set.add(e1);
        System.out.println(set.contains(e2));
        List<Employeee> empL = new java.util.ArrayList<>(List.of(e1, e2));
        Collections.sort(empL);
        empL.stream().forEach(e-> System.out.println(e.id + " " + e.name));
        //Comparator<Employeee> comp = (es,eq)-> es.id - eq.id;
        Comparator<Employeee> comp = Comparator.comparingInt(es -> es.id);
        empL.sort(comp);
        empL.stream().forEach(e-> System.out.println(e.id + " " + e .name));

    }

    @Override
    public int compareTo(Employeee o) {
        return this.name.compareTo(o.name);
    }
}
