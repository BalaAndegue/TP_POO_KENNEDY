package com.example.tp_poo2.models;



import java.sql.Date;

public class StolenObjet {
    private int id;
    private String type;
    private String imei;
    private String macAddress;
    private String brand;
    private String color;
    private String storage;
    private String description;
    private Date dateReported;
    private int ownerId;

    // Constructeur
    public StolenObjet(int id, String type, String imei, String macAddress, String brand, String color, String storage, String description, Date dateReported, int ownerId) {
        this.id = id;
        this.type = type;
        this.imei = imei;
        this.macAddress = macAddress;
        this.brand = brand;
        this.color = color;
        this.storage = storage;
        this.description = description;
        this.dateReported = dateReported;
        this.ownerId = ownerId;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getImei() { return imei; }
    public void setImei(String imei) { this.imei = imei; }

    public String getMacAddress() { return macAddress; }
    public void setMacAddress(String macAddress) { this.macAddress = macAddress; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getStorage() { return storage; }
    public void setStorage(String storage) { this.storage = storage; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getDateReported() { return dateReported; }
    public void setDateReported(Date dateReported) { this.dateReported = dateReported; }

    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }
}
