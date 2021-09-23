package com.jongtk.cloudmap.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MapLog extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lno;

    private String title;

    private String content;

    private double lat;

    private double lng;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    @Builder.Default
    @OneToMany(mappedBy = "mapLog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Likes> likes = new HashSet<>();


}
