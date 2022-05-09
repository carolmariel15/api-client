package com.nttdata.api.client.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "client")
public class Client {

    @Id
    private Integer id;
    private String name;
    private String surname;
    private String imei;
    private Integer cellPhoneNumber;
    private String email;
    private String cardNumber;

}
