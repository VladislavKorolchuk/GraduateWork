package ru.work.graduatework.dto;

import lombok.Data;
import ru.work.graduatework.Entity.Ads;
import ru.work.graduatework.Entity.Image;

import java.util.Collection;
import java.util.List;

@Data
public class UserDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private String regDate;

    private String city;

    private Image image;

    // private Collection<Ads> adsCollection;

    public UserDto() {

    }

    public UserDto(Integer id, String firstName, String lastName, String phone,
                   String email, String regDate, String city, Image image) {
        //, Collection<Ads> adsCollection
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.regDate = regDate;
        this.city = city;
        this.image = image;
        //   this.adsCollection = adsCollection;
    }

}
