package com.example.onemoremile;

public class dataclass {

    String imgurl,name,location,placename,number,des,rating,budget;

    public dataclass(String imgurl, String name, String location, String placename, String number, String des, String rating, String budget) {
        this.imgurl = imgurl;
        this.name = name;
        this.location = location;
        this.placename = placename;
        this.number = number;
        this.des = des;
        this.rating = rating;
        this.budget = budget;
    }

    public dataclass(){

    }

    public String getImgurl() {
        return imgurl;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getPlacename() {
        return placename;
    }

    public String getNumber() {
        return number;
    }

    public String getDes() {
        return des;
    }

    public String getRating() {
        return rating;
    }

    public String getBudget() {
        return budget;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }
}
