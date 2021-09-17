package com.jongtk.cloudmap.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    private MapLog mapLog;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

}
