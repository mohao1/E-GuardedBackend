package com.ling.ap.Pojo.ToDo;

import com.ling.ap.Pojo.Entity.Oldster;
import com.ling.ap.Pojo.Entity.UserLogin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OldsterRegistrationPojo {
    UserLogin userLogin;
    Oldster oldster;
    String code;
}
