
$(document).ready(function () {
  //친구 목록 검색 기능
  $(".search-friend-list-scroll > .friend-list > li").hide();
  $('.nav_btn').click(function () {
    $('.slide-box').toggleClass('active');
  });
  $("#friend-search-bar").keyup(function () {
    var k = $(this).val();
    console.log("k : " + k)
    $(".search-friend-list-scroll > .friend-list > li").hide();
    var temp = $(".friend-list > li:contains('" + k + "')")
    $(temp).show();
    searchFriend(k);
    if (k === "") {
      $(".search-friend-list-scroll > .friend-list > li").hide();
    }
  })

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

        friendLi += '<li><img src="'+profileImg+'" class="friend_profile_image"><label class="friend_profile_name">'+friend.name+'</label><img onclick="getFriendMapLogList(this)" data-name="'+ friend.name+'" data-email="'+friend.email+'" src="../images/map.png" class="friend_profile_home"></li>'

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

        friendLi += '<li><img src="'+profileImg+'" class="friend_profile_image"><label class="friend_profile_name">'+friend.name+'</label><i class="fas fa-user-plus accept-friend receive-btn" onclick="friendReceiveAction(this)" data-option="accept" data-email="'+friend.email+'"></i><i class="fas fa-user-minus delete-friend receive-btn" onclick="friendReceiveAction(this)" data-option="refuse" data-email="'+friend.email+'"></i></li>'
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

        html += '<li><img src="'+profileImg+'" class="friend_profile_image"><label class="friend_profile_name">'+friend.name+'</label>';
        if(friend.state == 'no'){
          html += '<a style="cursor: pointer" onclick="btnOnClick(this)" data-search="post" data-email="'+friend.email+'">'+'친구신청'+'</a>';
        }else if(friend.state == 'friend'){
          html += '<a >'+'친구임'+'</a>';
        }else if(friend.state == 'sent'){
          html += '<a >'+'요청중'+'</a>';
        }else if(friend.state == 'received'){
          html += '<a style="cursor: pointer" onclick="friendReceiveAction(this)" data-search="receive" data-option="accept" data-email="'+friend.email+'">'+'요청수락'+'</a>';
        }
        html += '</li>'
      })
      $(".search-friend-list-scroll > ul.friend-list").html(html);
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
      $(ele).replaceWith('<a >'+'요청중'+'</a>');
    }else if($(ele).data('search') == 'receive'){
      $(ele).replaceWith('<a >'+'친구임'+'</a>');
    }
  }
  return;
}


$(document).ready(function(){
  getFriendList();
  // getReceiveList();
});


