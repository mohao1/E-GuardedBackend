package com.ling.ap.Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityAdmin {
    String adminName;
    String adminPassword;
    String communityId;
    String name;
    String adminId;
}
