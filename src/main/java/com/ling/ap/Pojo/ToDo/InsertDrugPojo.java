package com.ling.ap.Pojo.ToDo;

import com.ling.ap.Pojo.Entity.Drug;
import com.ling.ap.Pojo.Entity.DrugNum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertDrugPojo {
    private Drug drug;
    private DrugNum drugNum;
    private long setTime;
    private long drugTime;
}
