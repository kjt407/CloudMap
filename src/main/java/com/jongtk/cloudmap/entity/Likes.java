package com.jongtk.cloudmap.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
public class Likes extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;


    @ManyToOne(fetch = FetchType.EAGER)
    private MapLog mapLog;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

}
