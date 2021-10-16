package com.jongtk.cloudmap.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private double lat;

    private double lng;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    @Builder.Default
    @OneToMany(mappedBy = "mapLog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Likes> likes = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "mapLog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MapLogImage> images = new ArrayList<>();

    public void editMapLog(String title, String content){
        this.title = title;
        this.content = content;
    }

}
