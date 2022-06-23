package com.learn.myzhxy.pojo;

/**
 * <p>
 *
 * </p>
 *
 * @author yzd
 * @since 2022/5/30
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {

    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;

}

