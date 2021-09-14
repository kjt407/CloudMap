var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(37.404088, 126.930657), // 지도의 중심좌표
        level: 4 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

var imageSrc = "https://img.icons8.com/material/96/000000/marker--v1.png"
var imageSize = new kakao.maps.Size(45, 45);
var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
// 지도를 클릭한 위치에 표출할 마커입니다
var marker = new kakao.maps.Marker({ 
    // 지도 중심좌표에 마커를 생성합니다 
    //position: map.getCenter(),
    image : markerImage
}); 


var clickOverlay = null;

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

// 지도 클릭 이벤트
kakao.maps.event.addListener(map, 'rightclick', function(mouseEvent) {        
    
    searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            
            var jibun = result[0].address.address_name
            console.log(jibun)
            document.getElementById("jibun").innerHTML=jibun;

           // 클릭한 위도, 경도 정보를 가져옵니다 
           console.log(marker.getPosition())
           if(!marker){
               console.log("마커없음")
           }
           
            var latlng = mouseEvent.latLng; 
            // 마커 위치를 클릭한 위치로 옮깁니다
            marker.setMap(map);
            marker.setPosition(latlng);
            
            var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
            message += '경도는 ' + latlng.getLng() + ' 입니다';
            
            console.log(message);
            //infowindow.open(map, marker);  
            if(clickOverlay){
                console.log("없에기")
                clickOverlay.setMap(null)
            }
            console.log("생성")
            overlay.setPosition(marker.getPosition());
            overlay.setMap(map);

            clickOverlay = overlay;

        }   
    });
    
});

var coContent = document.getElementById('write').innerHTML;



// 커스텀 오버레이
var overlay = new kakao.maps.CustomOverlay({
    content: coContent,
    map: map,
    yAnchor: 1.42
      
});
// 마커에 클릭이벤트를 등록합니다
kakao.maps.event.addListener(marker, 'click', function() {
      // 마커 위에 인포윈도우를 표시합니다
      
});

function closeOverlay(){
    overlay.setMap(null);
    marker.setMap(null);
}


function searchAddrFromCoords(coords, callback) {
    // 좌표로 행정동 주소 정보를 요청합니다
    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);         
}

function searchDetailAddrFromCoords(coords, callback) {
    // 좌표로 법정동 상세 주소 정보를 요청합니다
    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}



