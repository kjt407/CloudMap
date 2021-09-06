/*사이드바 친구목록 창 크기 조절*/
window.addEventListener("resize", function() {
		var height = $('.sidebar').height()-450;
        console.log(height)
		$('.box').css('height',height+'px');
}, true);

$(document).ready(function(){
    $('.nav_btn').click(function(){
      $('.slide-box').toggleClass('active');
    });
  });
	