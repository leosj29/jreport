package org.jasa.report.model;

/**
 * Person Class.
 * 
 * @author Leonardo Sanchez J.
 */
public class Person {

    private String name;
    private String alias;
    private Boolean alive;
    private String aliveStr;

    public Person(String name, String alias, Boolean alive) {
        this.name = name;
        this.alias = alias;
        this.alive = alive;
    }

    public String getName() {
        return this.name;
    }

    public String getAlias() {
        return this.alias;
    }

    public Boolean isAlive() {
        return this.alive;
    }

    public String getAliveStr() {
        this.aliveStr = alive ? "Yes" : "No";
        return this.aliveStr;
    }
}