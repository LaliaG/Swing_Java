package org.example.model;

import lombok.Data;

@Data
public class Personne {

    private String name;
    private String email;
    private String gender;

    @Override
    public String toString() {
        return "Name : " + name + "\nEmail : " + email + "\nGender : " + gender;
    }

   /* @Override
    public String toString() {
        return "Personne{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }*/
}
