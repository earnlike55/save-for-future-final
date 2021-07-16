package com.saving.saveforfuture.model;


import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@Accessors(chain = true)
public class CustomerInsertRequest {

    private long customerId;
//    @Id
//    @GeneratedValue(generator = "sequence-generator")
//    @GenericGenerator(
//            name = "sequence-generator",
//            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
//            parameters = {
//                    @Parameter(name = "sequence_name", value = "user_sequence"),
//                    @Parameter(name = "initial_value", value = "1"),
//                    @Parameter(name = "increment_size", value = "1")
//            }
//    )
    private long savingId;
    private String customerName;
    private Date dob;
    private String gender;
    private String email;
    private String password;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpense;
    private int memberNo;
    private int expectAge;
    private int ageOfRetirement;
}
