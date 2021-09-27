package com.jongtk.cloudmap.dto;

import lombok.*;

import java.net.URLEncoder;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {

    private String uuid;

    private String imgName;

    private String path;

    public String getImageURL(){
        try{
            return URLEncoder.encode(path+"/"+uuid+"_"+imgName, "UTF-8");
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }


}
