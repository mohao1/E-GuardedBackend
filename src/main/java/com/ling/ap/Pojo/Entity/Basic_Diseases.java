package com.ling.ap.Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//基础病情
public class Basic_Diseases {
    String diseaseId;
    String userId;
    String diseaseDescription;
    String diseaseName;

}
