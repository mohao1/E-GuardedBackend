package com.ling.ap.Pojo.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//药品使用宝典
public class Drug_atlas {
    String drugId;
    String drugName;
    String usage;
    String instructions;
}
