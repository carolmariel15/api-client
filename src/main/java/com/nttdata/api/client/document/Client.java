package com.nttdata.api.client.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "client")
public class Client {

    @Id
    private String id;
    private String name;
    private String surname;
    @NotNull
    private String imei;
    @NotNull
    private String phone;
    @Email
    private String email;

}
