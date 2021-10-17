var rightClickLat=0;
var rightClickLng=0;
var mapContainer = document.getElementById('map'); // 지도를 표시할 div
var  mapOption = {
    center: new kakao.maps.LatLng(37.404088, 126.930657), // 지도의 중심좌표
    level: 4 // 지도의 확대 레벨
};
var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
//위치정보 동의
if (navigator.geolocation) {
    // GeoLocation을 이용해서 접속 위치를 얻어옵니다
    navigator.geolocation.getCurrentPosition(function(position) {
        var lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도
        var locPosition = new kakao.maps.LatLng(lat, lon)
           map.setCenter(locPosition);
    });
} else {
    var locPosition = new kakao.maps.LatLng(124.848488, 33.474968)
    map.setCenter(locPosition);
}

//알림창 마커 이미지
var writeImageSrc = "https://img.icons8.com/material/96/000000/marker--v1.png"
var writeImageSize = new kakao.maps.Size(45, 45);
var writearkerImage = new kakao.maps.MarkerImage(writeImageSrc, writeImageSize);
// 지도를 클릭한 위치에 표출할 마커입니다
var writeMarker = new kakao.maps.Marker({
    image: writearkerImage
});
var clickWriteInfoWindow = null;
// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();
var writeInfoWindow = new kakao.maps.InfoWindow({
    yAnchor: 1.42
});

var start = true;
// 지도 클릭 이벤트
var myLogin = true;
writeInfoWindow.setContent(document.getElementById('write-alert').innerHTML);
kakao.maps.event.addListener(map, 'rightclick', function (mouseEvent) {
    if(myLogin) {
        searchDetailAddrFromCoords(mouseEvent.latLng, function (result, status) {
            if (status === kakao.maps.services.Status.OK) {
                $("#write-section").attr("class", "write-section");
                closeReadInfoWindow();
                var jibun = result[0].address.address_name
                document.getElementById("ji-bun").innerHTML = jibun;
                $("#ji-bun").html(jibun)
                var latlng = mouseEvent.latLng;
                rightClickLat=latlng.getLat();
                rightClickLng=latlng.getLng();
                if (!writeMarker) {
                    console.log("마커없음")
                }
                if (start) {
                    writeInfoWindow.setContent(document.getElementById('write-alert').innerHTML);
                    start = false;
                }
                // 마커 위치를 클릭한 위치로 옮깁니다
                writeMarker.setMap(map);
                writeMarker.setPosition(latlng);
                if (clickWriteInfoWindow) {
                    clickWriteInfoWindow.close();
                }
                writeInfoWindow.setPosition(writeMarker.getPosition());
                writeInfoWindow.open(map, writeMarker);
                clickWriteInfoWindow = writeInfoWindow;
            }
        });
    }
});

//작성 알림창 끄기
function closeWriteOverlay() {
    writeInfoWindow.close();
    writeMarker.setMap(null);
}

function searchDetailAddrFromCoords(coords, callback) {
    // 좌표로 법정동 상세 주소 정보를 요청합니다
    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

/* 일지 확인하기 */
/* ************************************************************************ */
//var imageListSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
var imageListSrc = contextpath+"images/my-marker.png";
var readMarkerArray = [];
var markersArray = [];
getMyMapLogList();

//내 지도로 돌아가기
function getBackMyMapLogList(){
    $(".from-friend").remove();
    $(".from-friend-name").remove();
    $(".my_profile_home").attr("class", "friend_profile_home")
    $(".friend_profile_home").attr("src", contextpath+"images/map.png")
    $(".friend_profile_home").attr("onclick", "getFriendMapLogList(this)")
    $(".friend-list-li").css( "background","#212022")
    getMyMapLogList();
}
//친구지도 가져오기
function getFriendMapLogList(ele){
    imageListSrc = contextpath+"images/friend-marker.png"
    writeInfoWindow.close();
    writeMarker.setMap(null);
    closeReadInfoWindow();
    var data = {"friendEmail":$(ele).data("email")};
    //$('.friend_profile_home').data('email-check', $(ele).data("email"));
    $.ajax({
        type: "GET",
        url: contextpath+"getFriendMapLogList",
        data: data,
        dataType: 'json',
        success: function (data) {
            myLogin = false
            var name = "friend-read"
            var title = "friend-title"
            var lno = "friend-lno"
            getMapLogList(data, name, title, lno);
            $(".from-friend").remove();
            $(".from-friend-name").remove();
            $(".from-friend-info").append(" <span class='from-friend'> from </span><span class='from-friend-name'>"+$(ele).data("name")+"</span>");
            $(".my_profile_home").attr("class", "friend_profile_home")
            $(".friend_profile_home").attr("onclick", "getFriendMapLogList(this)")
            $(".friend_profile_home").attr("src", contextpath+"images/map.png")
            $(".friend-list-li").css( "background","#212022")
            $(ele).attr("src", contextpath+"images/exit.png")
            $(ele).attr("class", "my_profile_home")
            $(ele).attr("onclick", "getBackMyMapLogList()")
            $(ele).parent().css( "background","rgb(252, 94, 94)")
        },
        error: function (e) {
            console.log('fail');
        }
    });
}
//나의 지도 가져오기
function getMyMapLogList() {
    imageListSrc = contextpath+"images/my-marker.png";
    writeInfoWindow.close();
    writeMarker.setMap(null);
    closeReadInfoWindow();
    $.get("./getMyList", function (data) {
        myLogin = true;
        var name = "my-read"
        var title = "my-title"
        var lno = "my-lno"
        getMapLogList(data, name, title, lno);
    });
}

function getMapLogList(data, name, title, lno){
    for(var i = 0; i<markersArray.length; i++){
        markersArray[i].setMap(null);
    }
    markersArray = [];
    for (var i = 0; i < data.length; i++) {
        var imageSize = new kakao.maps.Size(37, 37);
        var markerImage = new kakao.maps.MarkerImage(imageListSrc, imageSize);

        // 마커를 생성합니다
        var markers= new kakao.maps.Marker({
        });
        markersArray.push(markers);
        markersArray[i].setMap(map);
        markersArray[i].setPosition(new kakao.maps.LatLng(data[i].lat, data[i].lng));
        markersArray[i].setTitle(data[i].title);
        markersArray[i].setImage(markerImage);

        var titleInfo;
        if (data[i].title.length > 13) {
            titleInfo = data[i].title.substring(0, 13) + "...";
        } else {
            titleInfo = data[i].title;
        }
        var lnoInfo = data[i].lno;
        document.getElementById(title).innerHTML = titleInfo;
        document.getElementById(lno).innerHTML = lnoInfo;

        var readInfowindow = new kakao.maps.InfoWindow({
            content: document.getElementById(name).innerHTML // 인포윈도우에 표시할 내용
        });
        readMarkerArray.push(readInfowindow);
        kakao.maps.event.addListener(markersArray[i], 'click', makeOverListener(map, markersArray[i], readInfowindow));
    }
}
// 인포윈도우를 표시하는 클로저를 만드는 함수입니다
function makeOverListener(map, markers, readInfowindow) {
    return function () {
        closeWriteOverlay();
        closeReadInfoWindow();
        readInfowindow.open(map, markers);
    };
}
function closeReadInfoWindow() {
    for (var idx = 0; idx < readMarkerArray.length; idx++) {
        readMarkerArray[idx].close();
    }
}
/* ************************************************************************ */

/* ************************************************************************ */
// 마커를 담을 배열입니다
var markers = [];
// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places();
// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
var infowindow = new kakao.maps.InfoWindow({zIndex:1});
// 키워드로 장소를 검색합니다
searchPlaces();
// 키워드 검색을 요청하는 함수입니다
function searchPlaces() {
    var keyword = document.getElementById('keyword').value;
    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        //alert('키워드를 입력해주세요!');
        return false;
    }
    // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
    ps.keywordSearch( keyword, placesSearchCB);
}
// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면
        // 검색 목록과 마커를 표출합니다
        displayPlaces(data);
        // 페이지 번호를 표출합니다
        displayPagination(pagination);
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        toastr.options = {closeButton: true, progressBar: true, showMethod: 'slideDown', timeOut: 1000 };
        toastr.info('검색 결과가 존재하지 않습니다.');
        return;
    } else if (status === kakao.maps.services.Status.ERROR) {

        return;
    }
}

// 검색 결과 목록과 마커를 표출하는 함수입니다
function displayPlaces(places) {
    var listEl = document.getElementById('placesList'),
        menuEl = document.getElementById('menu_wrap'),
        fragment = document.createDocumentFragment(),
        bounds = new kakao.maps.LatLngBounds(),
        listStr = '';
    // 검색 결과 목록에 추가된 항목들을 제거합니다
    removeAllChildNods(listEl);
    // 지도에 표시되고 있는 마커를 제거합니다
    removeMarker();
    for ( var i=0; i<places.length; i++ ) {
        // 마커를 생성하고 지도에 표시합니다
        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
            marker = addMarker(placePosition, i),
            itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다
        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(placePosition);
        // 마커와 검색결과 항목에 mouseover 했을때
        // 해당 장소에 인포윈도우에 장소명을 표시합니다
        // mouseout 했을 때는 인포윈도우를 닫습니다
        (function(marker, title) {
            kakao.maps.event.addListener(marker, 'click', function() {
                if(myLogin){
                    searchDetailAddrFromCoords(marker.getPosition(), function(result, status) {
                        if (status === kakao.maps.services.Status.OK) {
                            writeMarker.setMap(null);
                            closeReadInfoWindow();
                            $("#write-section").attr("class", "search-write-section");
                            var jibun = result[0].address.address_name

                            document.getElementById("ji-bun").innerHTML = jibun;
                            var latlng = marker.getPosition();
                            rightClickLat = latlng.getLat();
                            rightClickLng = latlng.getLng();
                            // 클릭한 위도, 경도 정보를 가져옵니다
                            if (!writeMarker) {

                            }
                            if (start) {
                                writeInfoWindow.setContent(document.getElementById('write-alert').innerHTML);
                                start = false;
                            }
                            if (clickWriteInfoWindow) {

                                clickWriteInfoWindow.close();
                            }
                            writeInfoWindow.setPosition(latlng);
                            writeInfoWindow.open(map, marker);
                            clickWriteInfoWindow = writeInfoWindow;
                        }
                    });
                }

            });
            kakao.maps.event.addListener(marker, 'mouseout', function() {
                infowindow.close();
            });
            itemEl.onmouseover =  function () {
                displayInfowindow(marker, title);
                map.setCenter(marker.getPosition())
            };
            itemEl.onmouseout =  function () {
                infowindow.close();
            };
        })(marker, places[i].place_name);
        fragment.appendChild(itemEl);
    }

    // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    map.setBounds(bounds);
}

// 검색결과 항목을 Element로 반환하는 함수입니다
function getListItem(index, places) {
    var el = document.createElement('li'),
        itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
            '<div class="info">' +
            '   <h5>' + places.place_name + '</h5>';
    if (places.road_address_name) {
        itemStr += '    <span>' + places.road_address_name + '</span>' +
            '   <span class="jibun gray">' +  places.address_name  + '</span>';
    } else {
        itemStr += '    <span>' +  places.address_name  + '</span>';
    }

    itemStr += '  <span class="tel">' + places.phone  + '</span>' +
        '</div>';

    el.innerHTML = itemStr;
    el.className = 'item';

    return el;
}

// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarker(position, idx, title) {
    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
        imgOptions =  {
            spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage
        });

    marker.setMap(map); // 지도 위에 마커를 표출합니다
    markers.push(marker);  // 배열에 생성된 마커를 추가합니다
    return marker;
}

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
    for ( var i = 0; i < markers.length; i++ ) {
        markers[i].setMap(null);
    }
    markers = [];
}

// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
function displayPagination(pagination) {
    var paginationEl = document.getElementById('pagination'),
        fragment = document.createDocumentFragment(),
        i;

    // 기존에 추가된 페이지번호를 삭제합니다
    while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild (paginationEl.lastChild);
    }

    for (i=1; i<=pagination.last; i++) {
        var el = document.createElement('a');
        el.href = "#";
        el.innerHTML = i;

        if (i===pagination.current) {
            el.className = 'on';
        } else {
            el.onclick = (function(i) {
                return function() {
                    pagination.gotoPage(i);
                }
            })(i);
        }

        fragment.appendChild(el);
    }
    paginationEl.appendChild(fragment);
}

// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
// 인포윈도우에 장소명을 표시합니다
function displayInfowindow(marker, title) {
    var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

    infowindow.setContent(content);
    infowindow.open(map, marker);
}

// 검색결과 목록의 자식 Element를 제거하는 함수입니다
function removeAllChildNods(el) {
    while (el.hasChildNodes()) {
        el.removeChild (el.lastChild);
    }
}

$(document).ready(function(){
    $("#search-check").change(function(){
        if($("#search-check").is(":checked")){
            //체크했음
            $("#search_btn").attr("class", "fas fa-reply");
        }else{
            //체크 풀음 여기서 검색 초기화 이벤트 해주면 됨
            $("#write-section").attr("class", "write-section");
            $("#info").attr("class", "info");
            $("#search_btn").attr("class", "fas fa-search");

            $('#keyword').val('');
            var listEl = document.getElementById('placesList')
            // 검색 결과 목록에 추가된 항목들을 제거합니다
            removeAllChildNods(listEl);
            // 지도에 표시되고 있는 마커를 제거합니다
            removeMarker();
            var paginationEl = document.getElementById('pagination')
            for (var idx = 0; idx < readMarkerArray.length; idx++) {
                readMarkerArray[idx].close();
            }
            writeInfoWindow.close();
            writeMarker.setMap(null);
            // 기존에 추가된 페이지번호를 삭제합니다
            while (paginationEl.hasChildNodes()) {
                paginationEl.removeChild (paginationEl.lastChild);
            }
        }
    });

});


// 마커 클러스터러를 생성합니다
var clusterer = new kakao.maps.MarkerClusterer({
    map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
    averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
    minLevel: 6 // 클러스터 할 최소 지도 레벨
});

// 데이터를 가져오기 위해 jQuery를 사용합니다
// 데이터를 가져와 마커를 생성하고 클러스터러 객체에 넘겨줍니다
// $.get("./getMyList", function(data) {
//     // 데이터에서 좌표 값을 가지고 마커를 표시합니다
//     // 마커 클러스터러로 관리할 마커 객체는 생성할 때 지도 객체를 설정하지 않습니다
//     console.log(data)
//     console.log("DATA")
//     var markers = $(data).map(function(i, position) {
//         return new kakao.maps.Marker({
//             position : new kakao.maps.LatLng(position.lat, data.lng)
//         });
//     });
//
//     // 클러스터러에 마커들을 추가합니다
//     clusterer.addMarkers(markers);
// });