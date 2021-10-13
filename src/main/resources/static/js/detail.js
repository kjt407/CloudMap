var desc = false;
var lnoDesc = 0;
var likeCount = 0;
var likeListHtml = "";
$(document).on('click', '#my-detail', function () {
    var lno = document.getElementById("my-lno").innerHTML;
    console.log(lno)
    $.ajax({
        type: "GET",
        url: "/getMyLog/"+lno,
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
        url: "/getFriendMapLog/"+lno,
        processData: false,
        contentType: false,
        dataType: 'json',
        success: function (data) {
            detailFile(data);
            desc = true;
            lnoDesc = lno;
            $(".check-like").attr("data-lno", lno)
            $(".btn-modify").remove();
            $(".btn-delete").remove();
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
        $(".images").css("display", "none");
        $("#content").css("height", "660px");
    }else{
        $(".images").css("display", "block");
        $("#content").css("height", "380px");
    }
    $('#width-scroll').empty()
    $('#title').val(data.title);
    $('#content').val(data.content);
    data.imageDTOList.forEach(i => {
        console.log(i)
        var html = '<li><img id="image" src="display?imgUrl='+i.imageURL+'" > </li>'
        $('#width-scroll').append(html)
    })
    $("#width-scroll").on('mousewheel', function (e) {
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
        url: "/getLikes/?lno="+lno,
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
                var profileImg = '/images/default_profile.png';
                if(likeMember.profileImg){
                    profileImg = likeMember.profileImg;
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
    //var lno = $(".check-like").data("lno")


    if($("#like").is(":checked")){

        var data = new FormData();
        data.append("lno", lnoDesc);
        $.ajax({
            type: "POST",
            url: "/addLikes",
            data: data,
            processData: false,
            contentType: false,
            dataType: 'json',
            success: function (data) {
                $("input:checkbox[id='like']").prop("checked", true);
                $.ajax({
                    type: "GET",
                    url: "/getLikes/?lno="+lnoDesc,
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
                            var profileImg = '/images/default_profile.png';
                            if(likeMember.profileImg){
                                profileImg = likeMember.profileImg;
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
            url: "/deleteLikes",
            data: data,
            processData: false,
            contentType: false,
            dataType: 'json',
            success: function (data) {
                $("input:checkbox[id='like']").prop("checked", false);
                $.ajax({
                    type: "GET",
                    url: "/getLikes/?lno="+lnoDesc,
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
                            var profileImg = '/images/default_profile.png';
                            if(likeMember.profileImg){
                                profileImg = likeMember.profileImg;
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



