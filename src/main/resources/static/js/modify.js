var storedFiles = [];
var data = new FormData();
jQuery(document).ready(function () {

    $('body').on('change', '.user_picked_files', function () {
        var files = this.files;
        var i = 0;
        console.log(files)
        for (i = 0; i < files.length; i++) {
            var readImg = new FileReader();
            var file = files[i];

            if (file.type.match('image.*')) {
                storedFiles.push(file);
                readImg.onload = (function (file) {
                    return function (e) {
                        $('.cvf_uploaded_files').append(
                            "<li class='all-image' file = '" + file.name + "'>" +
                            "<img class = 'img-thumb' src = '" + e.target.result + "' />" +
                            "<a href = '#' class = 'cvf_delete_image' title = 'Cancel'><img class = 'delete-btn' src='../images/close.png' /></a>" +
                            "</li>"
                        );
                    };
                })(file);
                readImg.readAsDataURL(file);
                console.log(readImg)
            } else {
                alert('the file ' + file.name + ' is not an image<br/>');
            }
        }
        if (storedFiles.length === 0) {
            $(".write #width-scroll").css("display", "none");
            $(".write #content").css("height", "580px");
        } else {
            $(".write #width-scroll").css("display", "inline-block");
            $(".write #content").css("height", "374px");
        }
    });

    // 하나씩 제거하기
    $('body').on('click', 'a.cvf_delete_image', function (e) {
        $(this).parent().remove('');
        var file = $(this).parent().attr('file');
        console.log("삭제")
        console.log(file)
        for (var i = 0; i < storedFiles.length; i++) {
            if (storedFiles[i].name == file) {
                storedFiles.splice(i, 1);
                break;
            }
        }
        if (storedFiles.length === 0) {
            $(".write #width-scroll").css("display", "none");
            $(".write #content").css("height", "580px");
        } else {
            $(".write #width-scroll").css("display", "inline-block");
            $(".write #content").css("height", "374px");
        }
        console.log(storedFiles)
    });



    $('body').on('click', '.btn-modify', function (e) {
        console.log("클릭?")
        var form = $('#write')[0];
        // Create an FormData object


        var title = $(".modify input[name='title']").val();
        var content = $(".modify #content").val();
        var writer = $(".modify #username-check").val();
        var lat = rightClickLat;
        var lng = rightClickLng;

        console.log(title)
        console.log(content)
        console.log(writer)

        if(title===null||title===""||title===undefined||title==="undefined"){
            alert("제목을 입력해주세요")
            return;
        }
        if(content===null||content===""||content===undefined||content==="undefined"){
            alert("내용을 입력해주세요")
            return;
        }

        for(var i = 0; i <storedFiles.length; i++){
            console.log(storedFiles[i]);
            console.log(storedFiles.length);
            console.log('storedFiles.length');
            data.append("files",storedFiles[i]);
        }

        data.append("title", title);
        data.append("content", content);
        data.append("writer", writer);
        console.log(lno)

        console.log('data')
        console.log(data)
        for(var pair of data.entries()){
            console.log(pair[0]+","+ pair[1])
        }
        // $.ajax({
        //     type: "POST",
        //     url: "/register",
        //     data: data,
        //     processData: false,
        //     contentType: false,
        //     dataType: 'json',
        //     success: function (data) {
        //         $('#btnUpload').prop('disabled', false);
        //         alert('success')
        //         $('.write.modal').modal("hide");
        //
        //         $(".write .cvf_uploaded_files .all-image").remove('');
        //         $(".write #width-scroll").css("display", "none");
        //         $(".write #content").css("height", "580px");
        //         $(".write-section").find('form')[0].reset()
        //         start = true;
        //         getMyMapLogList();
        //     },
        //     error: function (e) {
        //         $('#btnUpload').prop('disabled', false);
        //         alert('fail');
        //     }
        //
        // });

    });

});



$(".write #width-scroll").on('mousewheel', function (e) {
    var wheelDelta = e.originalEvent.wheelDelta;
    if (wheelDelta > 0) {
        $(this).scrollLeft(-wheelDelta + $(this).scrollLeft());
    } else {
        $(this).scrollLeft(-wheelDelta + $(this).scrollLeft());
    }
});
