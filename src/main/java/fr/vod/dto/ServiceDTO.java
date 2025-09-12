package fr.vod.dto;

public class ServiceDTO {
    private String name;

    // Constructors
    public ServiceDTO() {}
    public ServiceDTO(String name) {
        this.name = name;
    }

    // Getter/Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

