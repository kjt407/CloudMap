var k
$(document).ready(function () {
  //친구 목록 검색 기능
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

});



// onClick 호출 함수
function btnOnClick(ele){
  console.log('넘겨받은 이메일값'+$(ele).data("email"));
  postFriend($(ele).data("email"), ele);
}
function friendReceiveAction(ele){
  receiveFriendAction($(ele).data("option"),$(ele).data("email"),ele);
}

// 친구기능 ajax 함수
function getFriendList(){
  $.ajax({
    type: "GET",
    url: "/getFriendList",
    dataType: 'json',
    success: function (data) {
      console.log("data")
      console.log(data)
      var friendLi = "";
      data.forEach(friend => {
        
        //프로필이미지 선언
        var profileImg = '/images/default_profile.png';
        if(friend.profileImg){
          profileImg = friend.profileImg;
        }

        friendLi += '<li class="friend-list-li"><img src="'+profileImg+'" class="friend_profile_image"><label class="friend_profile_name">'+friend.name+'</label><img onclick="getFriendMapLogList(this)" data-name="'+ friend.name+'" data-email="'+friend.email+'" src="../images/map.png" id="sprofile_home" class="friend_profile_home"></li>'

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
    url: "/getReceiveList",
    dataType: 'json',
    success: function (data) {
      console.log(data)
      var friendLi = "";
      data.forEach(friend => {
        //프로필이미지 선언
        var profileImg = '/images/default_profile.png';
        if(friend.profileImg){
          profileImg = friend.profileImg;
        }

        friendLi += '<li><img src="'+profileImg+'" class="friend_profile_image"><label class="friend_profile_name">'+friend.name+'</label><i src="../images/received-friend.png" class="fas fa-user-plus accept-friend-img" onclick="friendReceiveAction(this)" data-option="accept" data-email="'+friend.email+'"></i><i src="../images/refuse-friend.png" class="fas fa-user-minus refuse-friend-img" onclick="friendReceiveAction(this)" data-option="refuse" data-email="'+friend.email+'"></i></li>'
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
  if(option == 'accept'){
    url = '/acceptFriend'
  } else if(option == 'refuse'){
    url = '/refuseFriend'
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
      }
    },
    error: function (e) {
      console.log('fail');
    }
  });
}
function searchFriend(str){
  var data = {"str":str};
  $.ajax({
    type: "GET",
    url: "/searchFriend",
    data: data,
    dataType: 'json',
    success: function (data) {
      console.log(data)
      var html = "";

      data.forEach(friend => {

        //프로필이미지 선언
        var profileImg = '/images/default_profile.png';
        if(friend.profileImg){
          profileImg = friend.profileImg;
        }

        html += '<li><img src="'+profileImg+'" class="friend_profile_image"><div class="friend_profile_section"><label class="friend_profile_name">'+friend.name+'</label><label class="friend_profile_email">'+friend.email+'</label></div>';
        if(friend.state == 'no'){
          html += '<a class="add-friend" style="cursor: pointer" onclick="btnOnClick(this)" data-search="post" data-email="'+friend.email+'"><img class="add-friend-img" src="../images/add-friend.png"></a>';
        }else if(friend.state == 'friend'){
          html += '<a class="isFriend"><img class="isFriend-img" src="../images/friend.png"/></a>';
        }else if(friend.state == 'sent'){
          html += '<a class="loading"><img class="loading-img" src="../images/loading.png"/></a>';
        }else if(friend.state == 'received'){
          html += '<a class="received-friend" style="cursor: pointer" onclick="friendReceiveAction(this)" data-search="receive" data-option="accept" data-email="'+friend.email+'"><img class="received-friend-img" src="../images/received-friend.png"></a>';
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
    url: "/postFriend",
    data: data,
    dataType: 'json',
    success: function (data) {
      console.log(data)
      if(data){
        alert("친구요청완료");
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
      $(ele).replaceWith('<a class="loading"><img class="loading-img" src="../images/loading.png"/></a>');
    }else if($(ele).data('search') == 'receive'){
      $(ele).replaceWith('<a class="isFriend"><img class="isFriend-img" src="../images/friend.png"/></a>');
    }
  }
  return;
}


$(document).ready(function(){
  getFriendList();
  // getReceiveList();

  $("#sidebar-check").change(function(){
    if($("#sidebar-check").is(":checked")){
      $("#my-page-btn").css("display", "none")
    }else{
      $("#my-page-btn").css("display", "")
    }
  });
});




