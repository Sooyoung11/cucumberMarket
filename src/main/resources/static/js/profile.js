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
    let imageFile= null;

    modalImgUrl.addEventListener('change', function (event) {
        files = event.currentTarget.files;

        //image upload
        let formData = new FormData();
        formData.append("userProfileImage", event.target.files[0]);

            axios({
                headers: {
                    "Content-Type": "multipart/form-data",
                    "Access-Control-Allow-Origin": "*",
                },
                url: "http://localhost:8889/api/profileImage/upload",
                method: "POST",
                data: formData,
            }).then((response) => {
                console.log(response)
            });

        fileName = event.currentTarget.files[0].name;
        modalImgName.value = fileName;
        imageFile = "/images/mypage/"+ fileName;

    })

    //modalBtnDelete.addEventListener('click', deleteImg)
    modalBtnUpdate.addEventListener('click', updateImg);

    function updateImg(event){
        const modalMemberNo = memberNo;

        const result = confirm("정말 수정하시겠습니까?")

        if(result){
            const data = {
                userImgUrl:imageFile,
                userImgName:fileName,
                            }

            axios
                .post('/api/profileImage/'+modalMemberNo, data)
                .then(response =>{
                    console.log(response);
                    alert('이미지 수정 성공');
                    readUserImage();
                    modalImgUrl.value = "";
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
        userProfileImage.src =  data.userImgUrl;

    }
})