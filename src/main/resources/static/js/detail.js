




$('body').on('click', '.btn-save', function (e) {
  
});

jQuery(document).ready(function () {



});

$(document).on('click', '#detail', function () {
    var lno = document.getElementById("lno").innerHTML;
    console.log(lno)
    $.ajax({
        type: "GET",
        url: "/getMyLog/"+lno,
        processData: false,
        contentType: false,
        dataType: 'json',
        success: function (data) {

            console.log(data.imageDTOList)
            if(data.imageDTOList.length === 0 ){
                $(".images").css("display", "none");
                $("#content").css("height", "660px");
            }else{
                console.log("없음")
                $(".images").css("display", "block");
                $("#content").css("height", "380px");
            }
            $('#width-scroll').empty()
            $('#title').val(data.title);
            $('#content').val(data.content);
            //$('#image').attr("src","display?imgUrl="+imageURL);

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


        },
        error: function (e) {
            $('#btnUpload').prop('disabled', false);
            alert('fail');
        }
    });
    $.ajax({
        type: "GET",
        url: "/getLikes/?lno="+lno,
        processData: false,
        contentType: false,
        dataType: 'json',
        success: function (data) {
           console.log(data.liked)
            console.log(data)

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
    $('.detail.modal').modal({
        remote : contextpath+'server/detail.html'
    });
});
$('body').on('click', '.btn-Like', function (e) {



});

