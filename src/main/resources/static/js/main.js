/*사이드바 친구목록 창 크기 조절*/
window.addEventListener("resize", function() {
		var height = $('.sidebar').height()-450;
        console.log(height)
		$('.box').css('height',height+'px');
}, true);

$(document).ready(function(){
  $('.nav_btn').click(function(){
    console.log("asdasdsadas")
    $('.slide-box').toggleClass('active');
  });
});


$(document).on('click','#write-btn',function(){
  console.log("qqqqq")
  
  $('#dialog').dialog({
    title: '일지 작성하기',
    modal: true,
    width: '1000',
    height: '800',
    resizeable : false,
    buttons:{
      "확인":function(){
          $(this).dialog("close");
      },"취소":function(){
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


 
