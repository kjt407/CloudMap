/*사이드바 친구목록 창 크기 조절*/
window.addEventListener("resize", function () {
  var height = $('.sidebar').height() - 450;
  console.log(height)
  $('.box').css('height', height + 'px');
}, true);

$(document).ready(function () {
  $(".search-friend-list-scroll > .friend-list > li").hide();
  $('.nav_btn').click(function () {
    console.log("asdasdsadas")
    $('.slide-box').toggleClass('active');
  });
  $("#friend-search-bar").keyup(function () {
    var k = $(this).val();
    console.log("k : " + k)
    $(".search-friend-list-scroll > .friend-list > li").hide();
    var temp = $(".friend-list > li:contains('" + k + "')")
    console.log(temp)
    $(temp).show();

    if (k === "") {
      $(".search-friend-list-scroll > .friend-list > li").hide();
    }
  })

});


$(document).on('click', '#write-btn', function () {
  $('#dialog').dialog({
    title: '일지 작성하기',
    modal: true,
    width: '1000',
    height: '800',
    resizeable: false,
    open: function () {
      document.getElementById("jibun-data").innerHTML = document.getElementById("jibun").innerHTML;
      $('#summernote').summernote({
        height: 500,                 // 에디터 높이
        minHeight: null,             // 최소 높이
        maxHeight: null,             // 최대 높이
        focus: false,                  // 에디터 로딩후 포커스를 맞출지 여부
        lang: "ko-KR",					// 한글 설정
        placeholder: '최대 2048자까지 쓸 수 있습니다'	//placeholder 설정

      });

    },
    buttons: {
      "확인": function () {
        $(this).dialog("close");
      }, "취소": function () {
        $(this).dialog("close");
      }
    },
    background: '#fff',
    show: {                // 애니메이션 효과 - 보여줄때
      effect: "blind",
      duration: 500
    },
    hide: {                // 애니메이션 효과 - 감출때
      effect: "blind",
      duration: 500
    }
  });




});


