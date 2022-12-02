/**
 * 프로필 사진 수정
 */

window.addEventListener('DOMContentLoaded', event => {
    const memberNo = document.querySelector('#memberNo').value;
    console.log(memberNo);

    readUserImage();

    const profileImageChange = document.querySelector('#profileImageChange');
    profileImageChange.addEventListener('click', getModal);

    function getModal(){


        axios
            .get('/api/profileImage/'+ memberNo)
            .then(response => {
                console.log(response);
                showModal(response.data)
            })
            .catch(error =>{
                console.log(error);
            })

    }

    const divModal = document.querySelector('#profileImageModal');
    const profileImageModal = new bootstrap.Modal(divModal);
    let modalImgName = document.querySelector('#modalImgName');

    function showModal(profileImageData){
        modalImgName.value = profileImageData.userImgName;

        profileImageModal.show();
    }

    const modalBtnDelete = document.querySelector('#modalBtnDelete');
    const modalBtnUpdate = document.querySelector('#modalBtnUpdate');
    const modalImgUrl = document.querySelector('#imageFile');
    let fileName = null;
    let files = null;
    let imageFile= null

    modalImgUrl.addEventListener('change', function (event) {
        files = event.currentTarget.files;
        console.log("files!!!{}", files);

        fileName = event.currentTarget.files[0].name;
        modalImgName.value = fileName;
        console.log(typeof fileName, fileName);
        imageFile = "/images/mypage/"+ fileName;

        // //이미지 저장
        // const saveFile = new File(imageFile, fileName);
        // files.transferTo(saveFile);
    })

    //modalBtnDelete.addEventListener('click', deleteImg)
    modalBtnUpdate.addEventListener('click', updateImg);

    function updateImg(event){
        const modalMemberNo = memberNo;
        console.log("files!!!"+files);
        // const imageFile = "/images/mypage/"+ fileName;
        const result = confirm("정말 수정하시겠습니까?")

        if(result){
            const data = {
                userImgUrl:imageFile,
                userImgName:fileName
                            }
            axios
                .put('/api/profileImage/'+modalMemberNo, data, files)
                .then(response =>{
                    console.log(response);
                    alert('이미지 수정 성공');

                    console.log(data);
                    // //이미지 저장
                    // const saveFile = new File(data.userImgUrl, data.userImgName);
                    // files.transferTo(saveFile);

                    readUserImage();
                })
                .catch(error=>{
                    console.log(error);
                })
                .then(function (){
                    profileImageModal.hide();
                })
        }
    }

    function readUserImage(){
        axios.get('/api/profileImage/userImage/' + memberNo)
            .then(response =>{
                console.log(response);
                imageView(response.data);
            })
            .catch(error => {
                console.log(error);
            })
    }

    function imageView(data){
        const userProfileImage = document.querySelector('#userProfileImage');

        console.log("데이터 URL={}", data.userImgUrl);
        userProfileImage.src =  data.userImgUrl;

    }
})