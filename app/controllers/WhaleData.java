package controllers;

import play.data.validation.Constraints;

/**
 * A form processing DTO that maps to the Whale form.
 *
 * Using a class specifically for form binding reduces the chances
 * of a parameter tampering attack and makes code clearer, because
 * you can define constraints against the class.
 */
public class WhaleData {

    @Constraints.Required
    private String species;

    @Constraints.Min(0)
    @Constraints.Max(173000) //largest whale ever recorded
    private int weight;

    @Constraints.Required
    private String gender;

    @Constraints.Required
    private long id;

    public WhaleData() {
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int size) {
        this.weight = size;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getId() {
        return id;
    }

}
