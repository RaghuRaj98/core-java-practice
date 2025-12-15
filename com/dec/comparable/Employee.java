package com.dec.comparable;

class Employee implements Comparable<Employee> {
    int id;
    String name;

    @Override
    public int compareTo(Employee e) {
        if(this.id == e.id) {
            return 0; // equal
        } else if(this.id < e.id) {
            return -1; // this comes before e
        } else {
            return 1; // this comes after e
            }
    }

}
