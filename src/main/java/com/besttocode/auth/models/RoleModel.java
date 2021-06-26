package com.besttocode.auth.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "roles")
@EqualsAndHashCode(callSuper = true)
public class RoleModel extends  GenericModel {

    private String role;

    public RoleModel() {
        super();
    }
}
