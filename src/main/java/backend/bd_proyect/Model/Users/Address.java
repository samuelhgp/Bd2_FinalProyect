package backend.bd_proyect.Model.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Address {
    private String location;
    private String neighborhood;
    private String city;
    private String postal_code;
}