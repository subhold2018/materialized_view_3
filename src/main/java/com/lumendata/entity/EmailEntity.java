package com.lumendata.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "email_lookup_identity")
@Data
@IdClass(CustomerUniqueId.class)
public class EmailEntity {

    @Id
    @Column(name="source_id")
    private String sourceId;

    @Id
    @Column(name="source_name")
    private String source;

    @Column(name = "constituent_id", nullable = false)
    private String partyUid;

    @Column(name = "email_type", nullable = false)
    private String emailType;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "ucb_uuid", nullable = false)
    private String ucbId;

    @Column(name = "ucb_identity_key", nullable = false)
    private String ucbIdentityKey;

}
