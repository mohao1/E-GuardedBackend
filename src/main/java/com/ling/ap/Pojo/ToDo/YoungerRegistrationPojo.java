package com.ling.ap.Pojo.ToDo;

import com.ling.ap.Pojo.Entity.UserLogin;
import com.ling.ap.Pojo.Entity.Younger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YoungerRegistrationPojo {
    UserLogin userLogin;
    Younger younger;
    String code;
}
