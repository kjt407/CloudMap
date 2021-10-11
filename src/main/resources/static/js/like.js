
//getLike(lnos);
jQuery(document).ready(function () {
    var myLike = ".my-like"
    var myLno = "my-lno"
    var friendLike = ".friend-like"
    var friendLno = "friend-lno"

    addMyLike(myLike, myLno);
    addFriendLike(friendLike, friendLno);

});

function addMyLike(myLike,myLno){
    $(myLike).change(function(){
        if($("#like").is(":checked")){
            var lno = document.getElementById(myLno).innerHTML;
            var data = new FormData();
            data.append("lno", lno);
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
                            $("label[for='like']").html("&nbsp; x"+data.likesCount);
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
            var lno = document.getElementById(myLno).innerHTML;
            var data = new FormData();
            data.append("lno", lno);
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
                            $("label[for='like']").html("&nbsp; x"+data.likesCount);
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
}

function addFriendLike(friendLike,friendLno){
    $(friendLike).change(function(){

        if($("#like").is(":checked")){
            var lno = document.getElementById(friendLno).innerHTML;
            var data = new FormData();
            data.append("lno", lno);
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
                            $("label[for='like']").html("&nbsp; x"+data.likesCount);
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
            var lno = document.getElementById(friendLno).innerHTML;
            var data = new FormData();
            data.append("lno", lno);
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
                            $("label[for='like']").html("&nbsp; x"+data.likesCount);
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
}