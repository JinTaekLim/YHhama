package com.YH.yeohaenghama.domain.account.entity;

import com.YH.yeohaenghama.domain.itinerary.entity.Place;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String pw;
    private String photoUrl;
    private String nickname;
    private Date stop=null;

    @Column(unique = true)
    private String externalId = null;

    private AccountRole role = AccountRole.ACCOUNT;

    @OneToMany
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private List<AccountReport> accountReports;
}
