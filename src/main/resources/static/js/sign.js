var status;

$(document).ready(function(){
	
	$("#snow").load("snow.html")    
	fullset();
	goRegister();
	goLogin();
	
});

window.addEventListener("resize", function() {
	if(status==='0'){
		var height = $('.fullsection').height() * 2;
		$('#fullpage').css('top',-height+'px');
	}
}, true);
	
function fullset(){
	var pageindex = $("#fullpage > .fullsection").size(); //fullpage 안에 섹션이(.fullsection) 몇개인지 확인하기
	for(var i=1;i<=pageindex;i++){
		$("#fullpage > .quick > ul").append("<li></li>");
	}

}

function goRegister(){
	$(".sign-btns").click(function(){
		var length=0;
		length=$(".full"+1).height()+$(".full"+2).height();
		$("#fullpage").animate({"top": -length + "px"},500, "swing");
		status = '0';
		return false;
	});
}
function goLogin(){
	$(".register-success-btn").click(function(){
		var length=0;
		length=$(".full").height();
		$("#fullpage").animate({"top": -length + "px"},500, "swing");
		status = '1';
		return false;
	});
}

$('#selectEmail').change(function(){
	$("#selectEmail option:selected").each(function () {
		if($(this).val()== '1'){
			$("#email").val('');
			$("#email").attr("readonly",false);
		}else{ 
			$("#email").val($(this).text()); 
			$("#email").attr("readonly",true);
		} 
	}); 
});
       
$(".inputRegister").on("propertychange change keyup paste input", function() {
var id = $("#id").val();
var email = $("#email").val();
var selectEmail = $("#selectEmail").val();
var name = $("#name").val();
var phone = $("#phone").val();
var pwd1 = $("#pwd1").val();
var pwd2 = $("#pwd2").val();
var check = false;
var num = pwd1.search(/[0-9]/g);
var eng = pwd1.search(/[a-z]/ig);
var spe = pwd1.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
		
		if(pwd1.length===0){
			$("#alert-danger1").css('display', 'none');
			$("#alert-danger2").css('display', 'none');
			$("#alert-danger3").css('display', 'none');
			$("#alert-success1").css('display', 'none');
			checkPwd1Pwd2(pwd1, pwd2, check);
			return false;
		}else if(pwd1.length < 8 || pwd1.length > 20){
			$("#alert-danger1").css('display', 'inline-block');
			$("#alert-danger2").css('display', 'none');
			$("#alert-danger3").css('display', 'none');
			$("#alert-success1").css('display', 'none');
			checkPwd1Pwd2(pwd1, pwd2, check);
		return false;
		}else if(pwd1.search(/\s/) != -1){
			$("#alert-danger1").css('display', 'none');
			$("#alert-danger2").css('display', 'inline-block');
			$("#alert-danger3").css('display', 'none');
			$("#alert-success1").css('display', 'none');
			checkPwd1Pwd2(pwd1, pwd2, check);
		return false;
		}else if(num < 0 || eng < 0 || spe < 0 ){
			$("#alert-danger1").css('display', 'none');
			$("#alert-danger2").css('display', 'none');
			$("#alert-danger3").css('display', 'inline-block');
			$("#alert-success1").css('display', 'none');
			checkPwd1Pwd2(pwd1, pwd2, check);
		return false;
		}else {
		
			$("#alert-danger1").css('display', 'none');
			$("#alert-danger2").css('display', 'none');
			$("#alert-danger3").css('display', 'none');
			$("#alert-success1").css('display', 'inline-block');
			if(id&&name&&phone&&email&&selectEmail){ check=true; }
			checkPwd1Pwd2(pwd1, pwd2, check);		
		return false;
		}
});

function checkPwd1Pwd2(pwd1, pwd2, check){
	if ( pwd1 != '' && pwd2 == '' ) {
        null;
		return false;
	} else if (pwd1 != "" || pwd2 != "") {
		if ((pwd1 == pwd2)) {
			$("#alert-success2").css('display', 'inline-block');
			$("#alert-danger4").css('display', 'none');
			if(check){
				$(".register-success-btn").attr("disabled",false);
				return true;	
			}
			$(".register-success-btn").attr("disabled",true);
			return false;	  
		} else {
			$("#alert-success2").css('display', 'none');
			$("#alert-danger4").css('display', 'inline-block');
			$(".register-success-btn").attr("disabled",true);
			return false;
		}
	}
}

var autoHypenPhone = function(str){
	str = str.replace(/[^0-9]/g, '');
	var tmp = '';
	if( str.length < 4){
		return str;
	}else if(str.length < 7){
		tmp += str.substr(0, 3);
		tmp += '-';
		tmp += str.substr(3);
		return tmp;
	}else if(str.length < 11){
		tmp += str.substr(0, 3);
		tmp += '-';
		tmp += str.substr(3, 3);
		tmp += '-';
		tmp += str.substr(6);
		return tmp;
	}else{              
		tmp += str.substr(0, 3);
		tmp += '-';
		tmp += str.substr(3, 4);
		tmp += '-';
		tmp += str.substr(7);
		return tmp;
	}

	return str;
}
var phoneNum = document.getElementById('phone');
phoneNum.onkeyup = function(){
console.log(this.value);
this.value = autoHypenPhone( this.value ) ;  
}

function success(){
	$("#id").val('');
	$("#email").val('naver.com');
	$("#name").val('');
	$("#phone").val('');
	$("#pwd1").val('');
	$("#pwd2").val('');
	$("#alert-danger1").css('display', 'none');
	$("#alert-danger2").css('display', 'none');
	$("#alert-danger3").css('display', 'none');
	$("#alert-danger4").css('display', 'none');
	$("#alert-danger4").css('display', 'none');
	$("#alert-success1").css('display', 'none');
	$("#alert-success2").css('display', 'none');
	$(".register-success-btn").attr("disabled",true);
	alert("회원가입 완료! 로그인을 해주세요.");
}