package com.jongtk.cloudmap.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FriendPost extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pid;

    @ManyToOne
    private Member sender;

    @ManyToOne
    private Member receiver;

}
