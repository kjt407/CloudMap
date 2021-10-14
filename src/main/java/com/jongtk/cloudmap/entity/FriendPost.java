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

    @ManyToOne(cascade = CascadeType.ALL)
    private Member sender;

    @ManyToOne(cascade = CascadeType.ALL)
    private Member receiver;

}
