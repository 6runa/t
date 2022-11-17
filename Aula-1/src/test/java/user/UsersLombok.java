package user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class UsersLombok {

    private String email;
    private String gender;
    private String name;
    private String status;

}
