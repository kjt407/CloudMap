var desc = false;
var lnoDesc = 0;
var likeCount = 0;
var likeListHtml = "";
$(document).on('click', '#my-detail', function () {
    var lno = document.getElementById("my-lno").innerHTML;
    $.ajax({
        type: "GET",
        url: contextpath+"getMyLog/"+lno,
        processData: false,
        contentType: false,
        dataType: 'json',
        success: function (data) {
            detailFile(data)
            $(".check-like").attr("data-lno", lno)
            $(".btn-modify").remove();
            $(".btn-delete").remove();
            $(".btsn").append("<button type='button' class='btn-modify'>수정하기</button>" +
                "<button type='button' class='btn-delete'>삭제하기</button>")
            desc = false;
            lnoDesc = lno;
        },
        error: function (e) {
            $('#btnUpload').prop('disabled', false);
            alert('fail');
        }
    });
    getLike(lno)
    $('.detail.modal').modal({
        remote : contextpath+'server/detail.html'
    });

});

$(document).on('click', '#friend-detail', function () {
    var lno = document.getElementById("friend-lno").innerHTML;
    $.ajax({
        type: "GET",
        url: contextpath+"getFriendMapLog/"+lno,
        processData: false,
        contentType: false,
        dataType: 'json',
        success: function (data) {
            detailFile(data);
            desc = true;
            lnoDesc = lno;
            $(".detail .check-like").attr("data-lno", lno)
            $(".detail .btn-modify").remove();
            $(".detail .btn-delete").remove();
        },
        error: function (e) {
            $('#btnUpload').prop('disabled', false);
            alert('fail');
        }
    });
    getLike(lno)
    $('.detail.modal').modal({
        remote : contextpath+'server/detail.html'
    });
});

function detailFile(data){
    if(data.imageDTOList.length === 0 ){
        $(".detail .images").css("display", "none");
        $(".detail #content").css("height", "660px");
    }else{
        $(".detail .images").css("display", "block");
        $(".detail #content").css("height", "380px");
    }
    $('.detail #width-scroll').empty()
    $('.detail #title').val(data.title);
    $('.detail #content').val(data.content);
    data.imageDTOList.forEach(i => {
        var html = '<li><img id="image" src="'+contextpath+'display?imgUrl='+i.imageURL+'" > </li>'
        $('.detail #width-scroll').append(html)
    })
    $(".detail #width-scroll").on('mousewheel', function (e) {
        var wheelDelta = e.originalEvent.wheelDelta;
        if (wheelDelta > 0) {
            $(this).scrollLeft(-wheelDelta + $(this).scrollLeft());
        } else {
            $(this).scrollLeft(-wheelDelta + $(this).scrollLeft());
        }
    });
}
function getLike(lno){
    $.ajax({
        type: "GET",
        url: contextpath+"getLikes/?lno="+lno,
        processData: false,
        contentType: false,
        dataType: 'json',
        success: function (data) {
            if(data.liked){
                $("input:checkbox[id='like']").prop("checked", true);
            }else{
                $("input:checkbox[id='like']").prop("checked", false);
            }

            likeCount = data.likesCount;
            $("label[for='like']").html("&nbsp; x"+data.likesCount);

            likeListHtml = "";
            data.likesList.forEach(likeMember=>{
                var profileImg = contextpath+'images/default_profile.png';
                if(likeMember.profileImg){
                    if(likeMember.profileImg.indexOf('displayProfile') != -1){
                        profileImg = contextpath+likeMember.profileImg;
                    }else {
                        profileImg = likeMember.profileImg;
                    }
                }
                likeListHtml += '<li class="like-member-li"><img src="'+profileImg+'" class="profile_image"><label class="profile_name">'+likeMember.name+'</label></li>'
            })
        },
        error: function (e) {
            $('#btnUpload').prop('disabled', false);
            alert('fail');
        }
    });
}
$(".like").change(function(){
    if($("#like").is(":checked")){
        var data = new FormData();
        data.append("lno", lnoDesc);
        $.ajax({
            type: "POST",
            url: contextpath+"addLikes",
            data: data,
            processData: false,
            contentType: false,
            dataType: 'json',
            success: function (data) {
                $("input:checkbox[id='like']").prop("checked", true);
                $.ajax({
                    type: "GET",
                    url: contextpath+"getLikes/?lno="+lnoDesc,
                    processData: false,
                    contentType: false,
                    dataType: 'json',
                    success: function (data) {
                        if(data.liked){
                            $("input:checkbox[id='like']").prop("checked", true);
                        }else{
                            $("input:checkbox[id='like']").prop("checked", false);
                        }
                        $("label[for='like']").html("&nbsp; x"+data.likesCount);
                        likeListHtml = "";
                        data.likesList.forEach(likeMember=>{
                            var profileImg = contextpath+'images/default_profile.png';
                            if(likeMember.profileImg){
                                if(likeMember.profileImg.indexOf('displayProfile') != -1){
                                    profileImg = contextpath+likeMember.profileImg;
                                }else {
                                    profileImg = likeMember.profileImg;
                                }
                            }
                            likeListHtml += '<li class="like-member-li"><img src="'+profileImg+'" class="profile_image"><label class="profile_name">'+likeMember.name+'</label></li>'

                        })
                        $('.like-member-ul').html(likeListHtml);
                    },
                    error: function (e) {
                        $('#btnUpload').prop('disabled', false);
                        alert('fail');
                    }
                });
            },
            error: function (e) {
                $('#btnUpload').prop('disabled', false);
                alert('fail');
            }
        });
    }else{
        var data = new FormData();
        data.append("lno", lnoDesc);
        $.ajax({
            type: "DELETE",
            url: contextpath+"deleteLikes",
            data: data,
            processData: false,
            contentType: false,
            dataType: 'json',
            success: function (data) {
                $("input:checkbox[id='like']").prop("checked", false);
                $.ajax({
                    type: "GET",
                    url: contextpath+"getLikes/?lno="+lnoDesc,
                    processData: false,
                    contentType: false,
                    dataType: 'json',
                    success: function (data) {
                        if(data.liked){
                            $("input:checkbox[id='like']").prop("checked", true);
                        }else{
                            $("input:checkbox[id='like']").prop("checked", false);
                        }
                        $("label[for='like']").html("&nbsp; x"+data.likesCount);

                        likeListHtml = "";
                        data.likesList.forEach(likeMember=>{
                            var profileImg = contextpath+'images/default_profile.png';
                            if(likeMember.profileImg){
                                if(likeMember.profileImg.indexOf('displayProfile') != -1){
                                    profileImg = contextpath+likeMember.profileImg;
                                }else {
                                    profileImg = likeMember.profileImg;
                                }
                            }
                            likeListHtml += '<li class="like-member-li"><img src="'+profileImg+'" class="profile_image"><label class="profile_name">'+likeMember.name+'</label></li>'

                        })
                        $('.like-member-ul').html(likeListHtml);
                    },
                    error: function (e) {
                        $('#btnUpload').prop('disabled', false);
                        alert('fail');
                    }
                });
            },
            error: function (e) {
                $('#btnUpload').prop('disabled', false);
                alert('fail');
            }
        });
    }
});

$("body").on("click", ".btn-close", function(){
    $('.detail.modal').modal("hide");
})
//수정하기 버튼 눌렀을때
$(document).on('click', '.btn-modify', function () {
    var lno = document.getElementById("my-lno").innerHTML;

    $('.detail.modal').modal("hide");
    $('.modify.modal').modal({
        remote : contextpath+'server/modify.html'
    });
    $.ajax({
        type: "GET",
        url: "/getMyLog/" + lno,
        processData: false,
        contentType: false,
        dataType: 'json',
        success: function (datas) {
            $('.modify #title').val(datas.title);
            $('.modify #content').val(datas.content);
            if (datas.imageDTOList.length) {
                $(".modify #width-scroll").css("display", "inline-block");
                $(".modify #content").css("height", "374px");
            } else {
                $(".modify #width-scroll").css("display", "none");
                $(".modify #content").css("height", "580px");
            }
            $('.modify .cvf_uploaded_files').empty()
            datas.imageDTOList.forEach(i => {
                areadyStoredFiles.push(i)
                var html = "<li class='all-image' file = '" + i.imgName + "'>" +
                    "<img class = 'img-thumb' src='display?imgUrl=" + i.imageURL + "' />" +
                    "<a href = '#' class = 'cvf_delete_image' title = 'Cancel'><img class = 'delete-btn' src='../images/close.png' /></a>" +
                    "</li>"
                $('.modify .cvf_uploaded_files').append(html)
            })
        },
        error: function (e) {
            $('#btnUpload').prop('disabled', false);
            alert('fail');
        }
    });
});

//삭제버튼 눌렀을때
$(".detail .footer").on('click', '.btn-delete', function () {
    var lno = document.getElementById("my-lno").innerHTML;
    var data = {"lno" : lno}
    $.ajax({
        type: "DELETE",
        url: "/deleteMapLog",
        data : data,
        dataType: 'json',
        success: function () {
            $('.detail.modal').modal("hide");
            getMyMapLogList();
            console.log("success????")
            alert('success');
        },
        error: function (e) {
            console.log("fail????")
            alert('fail');
        }
    });
});

$(function(){
    $('.like').mouseover(function(e) { // mouse hover 좌표
        getLike(lnoDesc)
        $('#divLikeLayer').show();
        $('.like-member-ul').html(likeListHtml);

    });
    $('.like').mouseout(function() {
        $('#divLikeLayer').hide();
    });
});
var likeLiskHeight = 0;
$(".like").on('mousewheel',function(e){
    var wheel = e.originalEvent.wheelDelta;
    if(likeLiskHeight>$('.like-member-ul').height()-200){
        likeLiskHeight = $('.like-member-ul').height()-200;
    }
    if(likeLiskHeight<0){
        likeLiskHeight=0;
    }
//스크롤값을 가져온다.
    if(wheel>0){
//스크롤 올릴때
        likeLiskHeight -=30;
        $('.like-list-body').scrollTop(likeLiskHeight)
    } else {
//스크롤 내릴때
        likeLiskHeight +=30;
        $('.like-list-body').scrollTop(likeLiskHeight)
    }
});
