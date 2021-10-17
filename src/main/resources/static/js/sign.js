var status;

$(document).ready(function(){
	$("#snow").load("../html/snow.html")
	fullset();
	goRegister();
	// goLogin();
	singUp();
    goLoginUpArrow()
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
		var length=0;
		length=$(".full").height();
		$("#fullpage").animate({"top": -length + "px"},500, "swing");
		status = '1';
}
function goLoginUpArrow(){
	$(".arrow").click(function(){
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
			$("#email").attr("disabled",false);
		}else{ 
			$("#email").val($(this).text()); 
			$("#email").attr("disabled",true);
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
function success(email){
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
	$("#form-login").find('input[name="username"]').val(email);
}

function singUp(){
	$("#form-register").on("submit",function(event){
		event.preventDefault();
		var data = {
			"email1" : $(this).find('input[name="email1"]').val(),
			"email2" : $(this).find('input[name="email2"]').val(),
			"password" : $(this).find('input[name="password"]').val(),
			"passwordCheck" : $(this).find('input[name="passwordCheck"]').val(),
			"name" : $(this).find('input[name="name"]').val(),
			"phone" : $(this).find('input[name="phone"]').val()
		};
		register(data);
	})
}

function register(param){
	registerBtnSwitch(true);
	$.ajax({
		type : "POST",
		url : getContextPath()+"/sign/register",
		dataType : 'json',
		contentType : 'application/json',
		data : JSON.stringify(param),
		success : function (data){
			if(data.result){
				toastr.success(data.email+'님<br/>가입을 축하드립니다', {timeOut: 5000});
				goLogin();
				success(data.email);
			} else{
				data.errors.forEach(function(error){
					toastr.error('회원가입 실패',error, {timeOut: 5000});
				})
			}
			registerBtnSwitch(false);
		},
		error : function(){
			toastr.error('회원가입 실패',"서버와 통신할 수 없습니다", {timeOut: 5000});
			registerBtnSwitch(false);
		}
	});
}
function registerBtnSwitch(action){
	$(".register-success-btn").attr("disabled",action);
}
function getContextPath() {
	var hostIndex = location.href.indexOf( location.host ) + location.host.length;
	var context = location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
	if(context != '/cloudmap'){
		context = '';
	}
	return context;
};
