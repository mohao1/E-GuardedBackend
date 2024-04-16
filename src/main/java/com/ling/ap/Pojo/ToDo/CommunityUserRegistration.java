package com.ling.ap.Pojo.ToDo;

import com.ling.ap.Pojo.Entity.CommunityUser;
import com.ling.ap.Pojo.Entity.UserLogin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityUserRegistration {
    CommunityUser communityUser;
    UserLogin userLogin;
    String Code;
}
