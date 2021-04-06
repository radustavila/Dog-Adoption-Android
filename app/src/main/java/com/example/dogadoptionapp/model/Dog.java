package com.example.dogadoptionapp.model;

public class Dog {

    private Integer id;
    private String name;
    private String gender;
    private Integer age;
    private String description;
    private String breed;
    private String imageUrl;



    public Dog(Integer id, String name, String gender, Integer age, String description, String breed, String imageUrl) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.description = description;
        this.breed = breed;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", description='" + description + '\'' +
                ", breed='" + breed + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
