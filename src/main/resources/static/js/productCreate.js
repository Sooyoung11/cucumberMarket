// 파일 확장자
function fileCheck(event) {
    pathpoint = event.value.lastIndexOf('.');
    filepoint = event.value.substring(pathpoint + 1, event.length);
    filetype = filepoint.toLowerCase();
    if (filetype == 'jpg' || filetype == 'png' || filetype == 'jpeg') {

        return true;
    } else {
        alert('첨부파일은 jpg,png,jpeg로 된 이미지만 가능합니다.')
        parentObj = event.parentNode
        node = parentObj.replaceChild(event.cloneNode(true), event);
        return false;
    }

    if (event.files && event.files[0]) {
        var fr = new FileReader();

        fr.onload = function (events) {
            document.getElementById('preview').src = events.target.result;
        };
        fr.readAsDataURL(event.files[0]);
        // 이미지 파일을 읽어온다.
    } else {
        document.getElementById('preview').src = "";
    }

// $('input[name="imgFile"]').change(function(){
//         setImageFromFile(this, '#preview');
//     });
//
//     function setImageFromFile(input, expression) {
//         if(input.files && input.files[0]) {
//             var reader = new FileReader();
//             reader.onload = function(e) {
//                 $(expression).attr('src', e.target.result);
//             }
//             reader.readAsDataURL(input.files[0]);
//         }
//     }
}

