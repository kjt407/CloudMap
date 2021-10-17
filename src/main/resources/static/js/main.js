var k
$(document).ready(function () {
  getFriendList();
  //친구 목록 검색 기능 시작
  $(".search-friend-list-scroll > .friend-list > li").hide();
  $('.nav_btn').click(function () {
    $('.slide-box').toggleClass('active');
  });
  $("#friend-search-bar").keyup(function () {
     k = $(this).val();
    searchFriend(k);
  })
  $("#modify-name").css("display", "none")
  $("#modify-profile-image").css("display", "none")

  //사이드바 버튼 눌렀을 때 마이페이지 버튼 생성 및 삭제
  $("#sidebar-check").change(function(){
    if($("#sidebar-check").is(":checked")){
      $("#my-page-btn").css("display", "none")
    }else{
      $("#my-page-btn").css("display", "")
    }
  });
});
//친구 기능 시작--------------------------------------------------------------------------------------------
// 친구기능 ajax 함수
function getFriendList(){
  $.ajax({
    type: "GET",
    url: contextpath+"getFriendList",
    dataType: 'json',
    success: function (data) {
      console.log("data")
      console.log(data)
      var friendLi = "";
      data.forEach(friend => {
        //프로필이미지 선언
        var profileImg = contextpath+'images/default_profile.png';
        if(friend.profileImg){
          if(friend.profileImg.indexOf('displayProfile') != -1){
            profileImg = contextpath+friend.profileImg;
          }else {
            profileImg = friend.profileImg;
          }
        }
        friendLi += '<li class="friend-list-li"><img src="'+profileImg+'" class="friend_profile_image"><label class="friend_profile_name">'+friend.name+'</label><img onclick="getFriendMapLogList(this)" data-name="'+ friend.name+'" data-email="'+friend.email+'" src="'+contextpath+'images/map.png" id="sprofile_home" class="friend_profile_home"></li>'
      })
      $('#main-friend-list').html(friendLi)
    },
    error: function (e) {
      console.log('fail');
    }
  });
}
function getReceiveList(){
  $.ajax({
    type: "GET",
    url: contextpath+"getReceiveList",
    dataType: 'json',
    success: function (data) {
      var friendLi = "";
      data.forEach(friend => {
        //프로필이미지 선언
        var profileImg = contextpath+'images/default_profile.png';
        if(friend.profileImg){
          if(friend.profileImg.indexOf('displayProfile') != -1){
            profileImg = contextpath+friend.profileImg;
          }else {
            profileImg = friend.profileImg;
          }
        }
        friendLi += '<li><img src="'+profileImg+'" class="friend_profile_image"><label class="friend_profile_name">'+friend.name+'</label><i src="'+contextpath+'images/received-friend.png" class="accept-friend-img fas fa-user-plus" onclick="friendReceiveAction(this)" data-option="accept" data-name="'+friend.name+'" data-email="'+friend.email+'"></i><i src="'+contextpath+'images/refuse-friend.png" class="refuse-friend-img fas fa-user-minus" onclick="friendReceiveAction(this)" data-option="refuse" data-name="'+friend.name+'" data-email="'+friend.email+'"></i></li>'
      })
      $('.alert-friend-list-scroll > ul.friend-list').html(friendLi);

    },
    error: function (e) {
      console.log('fail');
    }
  });
}
function receiveFriendAction(option, targetEmail, ele){
  var data = {"targetEmail":targetEmail};
  var url = '';
  var alert = '';
  if(option == 'accept'){
    url = contextpath+'acceptFriend'
    alert = $(ele).data("name")+'님이 친구로 등록되었습니다.'
  } else if(option == 'refuse'){
    url = contextpath+'refuseFriend'
    alert = $(ele).data("name")+'님의 친구신청을 거절했습니다..'
  }
  $.ajax({
    type: "POST",
    url: url,
    data: data,
    dataType: 'json',
    success: function (data) {
      console.log(data)
      if(data){
        if($(ele).hasClass('receive-btn')){
          $(ele).parent('li').remove();
        }
        getReceiveList();
        getFriendList();
        searchRefresh(ele);
        toastr.options = {closeButton: true, progressBar: true, showMethod: 'slideDown', timeOut: 1500 };
        toastr.info(alert);
        
      }
    },
    error: function (e) {
      toastr.options = {closeButton: true, progressBar: true, showMethod: 'slideDown', timeOut: 1500 };
      toastr.error('오류 : 관리자에게 문의 바랍니다.');
    }
  });
}
//친구 검색 기능
function searchFriend(str){
  var data = {"str":str};
  $.ajax({
    type: "GET",
    url: contextpath+"searchFriend",
    data: data,
    dataType: 'json',
    success: function (data) {
      console.log(data)
      var html = "";
      data.forEach(friend => {
        //프로필이미지 선언
        var profileImg = contextpath+'images/default_profile.png';
        if(friend.profileImg){
          if(friend.profileImg.indexOf('displayProfile') != -1){
            profileImg = contextpath+friend.profileImg;
          }else {
            profileImg = friend.profileImg;
          }
        }
        html += '<li><img src="'+profileImg+'" class="friend_profile_image"><div class="friend_profile_section"><label class="friend_profile_name">'+friend.name+'</label><label class="friend_profile_email">'+friend.email+'</label></div>';
        if(friend.state == 'no'){
          html += '<a class="add-friend" style="cursor: pointer" onclick="btnOnClick(this)" data-search="post" data-email="'+friend.email+'"><img class="add-friend-img" src="'+contextpath+'images/add-friend.png"></a>';
        }else if(friend.state == 'friend'){
          html += '<a class="isFriend"><img class="isFriend-img" src="'+contextpath+'images/friend.png"/></a>';
        }else if(friend.state == 'sent'){
          html += '<a class="loading"><img class="loading-img" src="'+contextpath+'images/loading.png"/></a>';
        }else if(friend.state == 'received'){
          html += '<a class="received-friend" style="cursor: pointer" onclick="friendReceiveAction(this)" data-search="receive" data-option="accept" data-name="'+friend.name+'" data-email="'+friend.email+'"><img class="received-friend-img" src="'+contextpath+'images/received-friend.png"></a>';
        }
        html += '</li>'
      })
      $(".search-friend-list-scroll > ul.friend-list").html(html);
      if(k===""){
        $(".search-friend-list-scroll > .friend-list > li").hide();
      }
    },
    error: function (e) {
      console.log('fail');
    }
  });
}
function postFriend(targetEmail,ele){
  var data = {"targetEmail":targetEmail};
  $.ajax({
    type: "POST",
    url: contextpath+"postFriend",
    data: data,
    dataType: 'json',
    success: function (data) {
      console.log(data)
      if(data){
        searchRefresh(ele);
      }
    },
    error: function (e) {
      console.log('fail');
    }
  });
}
function searchRefresh(ele){
  if($(ele).data('search')){
    if($(ele).data('search') == 'post'){
      $(ele).replaceWith('<a class="loading"><img class="loading-img" src="'+contextpath+'images/loading.png"/></a>');
    }else if($(ele).data('search') == 'receive'){
      $(ele).replaceWith('<a class="isFriend"><img class="isFriend-img" src="'+contextpath+'images/friend.png"/></a>');
    }
  }
  return;
}
function btnOnClick(ele){
  console.log('넘겨받은 이메일값'+$(ele).data("email"));
  postFriend($(ele).data("email"), ele);
}
function friendReceiveAction(ele){
  receiveFriendAction($(ele).data("option"),$(ele).data("email"),ele);
}
//친구기능 기능 끝--------------------------------------------------------------------------------------------

// const contextpath= /*[[@{/}]]*/"/";
//마이 프로필 불러오기 시작----------------------------------------------------------------------------------------
$(document).ready(function (){
  getMyInfo();
});
function refreshProfile(data){
  // 소셜프로필 옵션이 존재한다면 요소 제거
  $('#edit-social-profile').remove();
  if(!data.profileImg){
    $('.profile_image').attr('src',contextpath+'images/default_profile.png');
  }else {
    $('.profile_image').attr('src',data.profileImg);
  }
  $('.my-name').val(data.name);
  if(data.fromSocial){
    var socialHTML =
        '                <div id="edit-social-profile" style="position: relative; display: flex; flex-direction: column; justify-content: center; align-items: center;">\n' +
        '                    <img src="'+data.socialProfileImg+'" id="social_img" class="btn-edit-profile" style=" height: 150px; width: 150px; border-radius: 50%; box-sizing: border-box;" ></img>\n' +
        '                    <p id="social-profile-label" class=" profile-label">소셜이미지</p>\n' +
        '                </div>';
    $('#popup-container').append(socialHTML);
    if(!data.socialImg){
      $('#social_img').attr('onclick','setSocialImg()');
    } else {
      $('#edit-social-profile').append("<span style='position: absolute; top: 3px; display: flex; justify-content: center; align-items: center; width: 100%; height: 100%; background: rgba(0,0,0,0.4); color: white; height: 150px; width: 150px; border-radius: 50%; box-sizing: border-box;'>적용됨</span>");
    }
  }
  getMyLikeList()
}
function profileEditToggle(option){
  if(option == 'open') {
    $('#profile-edit-popup').css('display', 'flex');
  }else if(option == 'close') {
    $('#profile-edit-popup').css('display', 'none');
  }
}
function setSocialImg(){
  $.ajax({
    type: "PUT",
    url: contextpath+"setSocialProfile",
    dataType: 'text',
    success: function (data) {
      getMyInfo();
    },
    error: function (e) {
      console.log('fail');
      console.log(e);
    }
  });
}
function getMyInfo(){
  $.ajax({
    type: "GET",
    url: contextpath+"getMyInfo",
    dataType: 'json',
    success: function (data) {
      refreshProfile(data);
    },
    error: function (e) {
      console.log('fail');
      console.log(e);
    }
  });
}
//마이프로필 불러오기 끝--------------------------------------------------------------------------------------------
//일지 작성하기 버튼 클릭 기능 시작----------------------------------------------------------------------------------
$(document).on('click', '#write', function () {
  $('.write.modal').modal({
    remote : contextpath+'server/write.html'
  });
});
//일지 작성하기 버튼 클릭 기능 끝----------------------------------------------------------------------------------
//마이페이지 클릭 기능 시작-----------------------------------------------------------------------------------------
$("#my-page-check").change(function(){
  if($("#my-page-check").is(":checked")){
    $("#modify-name").css("display", "");
    $("#modify-profile-image").css("display", "");
    $("#my-page-btn").attr("class", "fas fa-times");
    getBackMyMapLogList();
    getMyLikeList();
  }else{
    $("#modify-name").css("display", "none");
    $("#modify-profile-image").css("display", "none");
    $(".my-name").css("background", "#212022")
    $(".my-name").css("color", "white")
    $(".my-name").attr("disabled", true)
    $("#my-page-btn").attr("class", "fas fa-user")
    $("#modify-name").attr("class", "fas fa-pen")
    getBackMyMapLogList();
  }
});
//이름 수정
$(document).on("click", "#modify-name", function () {
  console.log("설정")
  $(".my-name").css("background", "white")
  $(".my-name").css("color", "black")
  $(".my-name").attr("disabled", false)
  $("#modify-name").attr("class", "fas fa-times")
});
//마이페이지 클릭 해제
$(document).on("click", ".fa-times", function () {
  $(".my-name").css("background", "#212022")
  $(".my-name").css("color", "white")
  $(".my-name").attr("disabled", true)
  $("#modify-name").attr("class", "fas fa-pen")
});
//마이페이지 클릭 기능 끝-----------------------------------------------------------------------------------------
//마이페이지 기능 시작-------------------------------------------------------------------------------------
//프로필이미지변경
$("#upload-profile-img").change(function(e){
  var data = new FormData();
  data.append('file',$('input[type=file]')[0].files[0]);
  $.ajax({
    type: "PUT",
    url: contextpath+"setLocalProfile",
    data: data,
    processData: false,
    contentType: false,
    dataType: 'text',
    success: function (data) {
      getMyInfo();
    },
    error: function (e) {
      console.log('fail');
    }
  });
});
//이름 수정후 엔터 눌렀을 때
$(".my-name").keydown(function(key) {
  if (key.keyCode == 13) {
    const data = {'name':$('input.my-name').val()};
    $.ajax({
      type: "PUT",
      url: contextpath+"editMyName",
      dataType: 'text',
      data: data,
      success: function (data) {
        $('input.my-name').val(data);
        $(".my-name").css("background", "#212022");
        $(".my-name").css("color", "white");
        $(".my-name").attr("disabled", true);
        getMyLikeList()
      },
      error: function (e) {

      }
    });
  }
});

//내가 누른 좋아요 목록 가져오기
function getMyLikeList(){
  $.ajax({
    type: "GET",
    url: contextpath+"getMyLikes",
    dataType: 'json',
    success: function (data) {
      $('.my-like-list').children('li').remove();
      data.forEach(likedList=>{
        var editTitle="";
        if (likedList.title.length > 10) {
          editTitle = likedList.title.substring(0, 10) + "...";
        } else {
          editTitle = likedList.title;
        }
        $('.my-like-list').append(
            '<li data-name="'+likedList.writerName+'"data-email="'+likedList.writerEmail+'" data-lng="'+likedList.lng+'" data-me="'+likedList.me+'"data-lat="'+likedList.lat+'" class="click_my_like" onclick="goMyLikeMap(this);">'
            + '<div class="my-like-left"><img src="'+likedList.writerProfileImg+'" class="my-like-writer-image"><label class="my-like-writer-name">'+likedList.writerName+'</label></div><div class="my-like-right"><label class="my-like-writer-title">'+editTitle+'</label></li></div>'
        )
      });
    },
    error: function (e) {
    }
  });
}
//내가 누른 좋아요 마커로 이동하기
function goMyLikeMap(ele){
  imageListSrc = contextpath+"images/friend-marker.png"
  writeInfoWindow.close();
  writeMarker.setMap(null);
  closeReadInfoWindow();
  var data = {"friendEmail":$(ele).data("email")};
  if( $(ele).data("me")){
    //내일지일때
    getBackMyMapLogList();
    map.setCenter(new kakao.maps.LatLng($(ele).data("lat"), $(ele).data("lng")+0.00035))
    map.setLevel(1)
  }else{
    //아닐때
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
        map.setCenter(new kakao.maps.LatLng($(ele).data("lat"), $(ele).data("lng")+0.00035))
        map.setLevel(1)
        $(".from-friend").remove();
        $(".from-friend-name").remove();
        $(".from-friend-info").append(" <span class='from-friend'> from </span><span class='from-friend-name'>"+$(ele).data("name")+"</span>");
      },
      error: function (e) {

      }
    });
  }
}
//친구 설정 버튼 클릭 기능----------------------------------------------------------------------------------------
$("#friend-setting-check").change(function(){
  if($("#friend-setting-check").is(":checked")){
    $(".friend-list-scroll .friend-list-li #sprofile_home").attr("src", contextpath+"images/minus.png" )
    $(".friend-list-scroll .friend-list-li #sprofile_home").attr("class", "delete_friend" )
    $(".friend-list-scroll .friend-list-li #sprofile_home").attr("onclick", "deleteFriend(this)" )
    getBackMyMapLogList();
  }else{
    $(".friend-list-scroll .friend-list-li").remove();
    getFriendList();
  }
});

//친구삭제 기능
function deleteFriend(ele){
  const data = {'targetEmail':$(ele).data("email")};
  $.ajax({
    type: "DELETE",
    url: contextpath+"deleteFriend",
    data: data,
    dataType: 'json',
    success: function (data) {
      $.ajax({
        type: "GET",
        url: contextpath+"getFriendList",
        dataType: 'json',
        success: function (data) {
          var friendLi = "";
          data.forEach(friend => {
            //프로필이미지 선언
            var profileImg = '/images/default_profile.png';
            if(friend.profileImg){
              profileImg = friend.profileImg;
            }
            friendLi += '<li class="friend-list-li"><img src="'+profileImg+'" class="friend_profile_image"><label class="friend_profile_name">'+friend.name+'</label><img onclick="deleteFriend(this)" data-name="'+ friend.name+'" data-email="'+friend.email+'" src="'+contextpath+'images/minus.png" id="sprofile_home" class="delete_friend"></li>'
          })
          $('#main-friend-list').html(friendLi)
          toastr.options = {closeButton: true, progressBar: true, showMethod: 'slideDown', timeOut: 1500 };
          toastr.info($(ele).data("name")+'님을 친구목록에서 삭제했습니다.');
        },
        error: function (e) {
        }
      });
    },
    error: function (e) {
    }
  });
}
//친구 검색 버튼 클릭시
$("#friend-search-check").change(function(){
  if($("#friend-search-check").is(":checked")){
    $("#friend-search-bar").val("");
    $(".search-friend-list-scroll .friend-list").children().remove()
  }else{

  }
});

//회원탈퇴 기능 시작--------------------------------------------------------------------------------------------
$(".member-leave-section").on("click", ".member-leave", function(){
  $("#leave-popup-section").show();   //팝업 오픈
  $("body").append('<div class="backon"></div>'); //뒷배경 생성
  setting();
  $(".confirm-first").on("click", ".btn-yes", function(){
    $(".confirm-first").hide()
    $(".confirm-second").show()
    $("#leave-popup-section").css("height","365px")
    setTimeout(function() {
      $(".confirm-third").show()
    }, 700);
  })
  $(".confirm-first").on("click", ".btn-no", function(){
    $("#leave-popup-section").hide();
    $(".backon").hide();
    setting();
  })
  $(".popup-close").on("click", "i", function(){
    $("#leave-popup-section").hide();
    $(".backon").hide();
    setting();
  })
})
function setting(){
  $(".confirm-first").show()
  $(".confirm-second").hide()
  $(".confirm-third").hide()
  $(".confirm-fourth").hide()
  $("#confirm-text").val("")
  $("#confirm-pw").val("")
  $("#leave-popup-section").css("height","250px")
}
$(".confirm-third").on("click", ".leave-cloudmap", function(){
  const pw = $("#confirm-pw").val();
  const text = $("#confirm-text").val();
  const data = {'password':pw , 'checkStr':text}
  $.ajax({
    type: "DELETE",
    url: contextpath+"resign",
    data: data,
    dataType: 'json',
    success: function (data) {
      if(data){
        $(".confirm-first").hide()
        $(".confirm-second").hide()
        $(".confirm-third").hide()
        $(".confirm-fourth").show()
        $("#leave-popup-section").css("height","250px")
        var count = 2;
        //카운트다운함수
        var countdown = setInterval(function(){
          //해당 태그에 아래 내용을 출력
          $(".confirm-fourth .question").html(count + "초 후 로그인 페이지로 이동 합니다.");
          //0초면 초기화 후 이동되는 사이트
          if (count == 0) {
            clearInterval(countdown);
            window.location.href = contextpath+"sign/login";
          }
          count--;//카운트 감소
        }, 1000);
      } else {
        alert('패스워드 또는 확인문자를 정확히 입력해주세요');
      }
    },
    error: function (e) {
      alert('패스워드 또는 확인문자를 정확히 입력해주세요');
    }
  });
})
//회원탈퇴 기능 끝--------------------------------------------------------------------------------------------
