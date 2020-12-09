package controllers;

import models.Whale;
import play.data.validation.Constraints;

import java.util.ArrayList;

/**
 * Code based off
 * https://github.com/playframework/play-java-forms-example
 *
 * Form processing tutorials.
 * https://playframework.com/documentation/latest/JavaForms
 * https://adrianhurt.github.io/play-bootstrap/
 */
public class ObservationData extends WhaleData {

    //@Constraints.Required
    private ArrayList<Whale> whales;

    //@Constraints.Required
    private String location;

    //@Constraints.Required
    private String date;

    //@Constraints.Required
    private String time;

    //@Constraints.Required
    private int numWhales;

    //@Constraints.Required
    private String weights;

    private String genders;

    private String species;

    public ObservationData() {
    }

    public ArrayList<Whale> getWhales() {
        return whales;
    }

    public void setWhales(ArrayList<Whale> whales) {
        this.whales = whales;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNumWhales() {
        return numWhales;
    }

    public void setNumWhales(int numWhales) {
        this.numWhales = numWhales;
    }

    public String getWeights() {
        return weights;
    }

    public void setWeights(String weights) {
        this.weights = weights;
    }

    public String getGenders() {
        return genders;
    }

    public void setGenders(String genders) {
        this.genders = genders;
    }

    public String getSpeciesList() {
        return species;
    }

    public void setSpeciesList(String species) {
        this.species = species;
    }
}
