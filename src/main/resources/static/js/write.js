jQuery(document).ready(function () {

    var storedFiles = [];
    $('body').on('change', '.user_picked_files', function () {
        var files = this.files;
        var i = 0;

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
                            "<a href = '#' class = 'cvf_delete_image' title = 'Cancel'><img class = 'delete-btn' src='images/close.png' /></a>" +
                            "</li>"
                        );
                    };
                })(file);
                readImg.readAsDataURL(file);
            } else {
                alert('the file ' + file.name + ' is not an image<br/>');
            }
        }
        if (storedFiles.length === 0) {
            $("#width-scroll").css("display", "none");
            $("#content").css("height", "580px");
        } else {
            $("#width-scroll").css("display", "inline-block");
            $("#content").css("height", "374px");
        }
    });

    // 하나씩 제거하기
    $('body').on('click', 'a.cvf_delete_image', function (e) {
        $(this).parent().remove('');
        var file = $(this).parent().attr('file');
        console.log(file)
        for (var i = 0; i < storedFiles.length; i++) {
            if (storedFiles[i].name == file) {
                storedFiles.splice(i, 1);
                break;
            }
        }
        if (storedFiles.length === 0) {
            $("#width-scroll").css("display", "none");
            $("#content").css("height", "580px");
        } else {
            $("#width-scroll").css("display", "inline-block");
            $("#content").css("height", "374px");
        }
        console.log(storedFiles)
    });
    $('body').on('click', '.btn-save', function (e) {
        console.log("클릭?")
        var form = $('#write')[0];
        // Create an FormData object
        var data = new FormData();

        var title = $("input[name='title']").val();
        var content = $("#content").val();
        var writer = $("#username-check").val();
        var lat = document.getElementById("lat").innerHTML;
        var lng = document.getElementById("lng").innerHTML;

        console.log(lat)
        console.log(lng)

        var inputFile = $("input[type='file']");
        var files = inputFile[0].files;

        for(var i = 0; i < files.length; i++){
            console.log(files[i]);
            data.append("files",files[i]);
        }

        data.append("title", title);
        data.append("content", content);
        data.append("writer", writer);
        data.append("lat", lat);
        data.append("lng", lng);

        console.log(data)
        $.ajax({
            type: "POST",
            url: getContextPath()+"/register",
            data: data,
            processData: false,
            contentType: false,
            dataType: 'json',
            success: function (data) {
                $('#btnUpload').prop('disabled', false);
                alert('success')
            },
            error: function (e) {
                $('#btnUpload').prop('disabled', false);
                alert('fail');
            }
        });

    });

});
$("#width-scroll").on('mousewheel', function (e) {
    var wheelDelta = e.originalEvent.wheelDelta;
    if (wheelDelta > 0) {
        $(this).scrollLeft(-wheelDelta + $(this).scrollLeft());
    } else {
        $(this).scrollLeft(-wheelDelta + $(this).scrollLeft());
    }
});

function getContextPath() {
    var hostIndex = location.href.indexOf( location.host ) + location.host.length;
    var context = location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
    if(context != '/cloudmap'){
        console.log("컨텍스트가 /cloudmap 이 아님");
        context = '';
    }
    return context;
}
