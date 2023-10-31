package pojo.response;

import lombok.Data;
import lombok.Getter;

@Data
public class RegisterClientResponse {

    private String accessToken;

    private String error;

}