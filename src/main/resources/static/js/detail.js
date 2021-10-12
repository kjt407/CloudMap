var desc = false;
var lnoDesc = 0;
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
            console.log(data)
            console.log(data.likesCount)
            $("label[for='like']").html("&nbsp; x"+data.likesCount);

        },
        error: function (e) {
            $('#btnUpload').prop('disabled', false);
            alert('fail');
        }
    });
}

$(".like").change(function(){
    //var lno = $(".check-like").data("lno")
    console.log("lnoDesc")
    console.log(lnoDesc)

    console.log("desc")
    console.log(desc)

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

