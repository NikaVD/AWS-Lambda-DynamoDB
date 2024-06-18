package com.veronica.aws.lambda.ass2.dto;

public class Customer {
    public int id;
    public String firstName;
    public String lastName;
    public int rewardPoints;

    public Customer() {
    }

    public Customer(int id, String firstName, String lastName, int rewardPoints) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rewardPoints = rewardPoints;
    }
}
