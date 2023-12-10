package com.bolun.hotel.dao;

public class ApartmentTypeDao {

    private static final ApartmentTypeDao INSTANCE = new ApartmentTypeDao();



    public static ApartmentTypeDao getInstance() {
        return INSTANCE;
    }
}
