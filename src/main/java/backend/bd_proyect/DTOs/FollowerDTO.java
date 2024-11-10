package backend.bd_proyect.DTOs;

public class FollowerDTO {
    private String full_name;
    private String description;

    public FollowerDTO(String full_name, String description) {
        this.full_name = full_name;
        this.description = description;
    }

    // Getters y setters
    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
