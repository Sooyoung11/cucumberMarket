/**
 * 프로필 사진 수정
 */

window.addEventListener('DOMContentLoaded', event => {
    const profileImageChange = document.querySelector('#profileImageChange');
    profileImageChange.addEventListener('click', getModal);

    function getModal(){
        const memberNo = document.querySelector('#memberNo').value;
        console.log(memberNo);

        axios
            .get('/api/profileImage/'+ memberNo)
            .then(response => {
                console.log(response);
            })
            .catch(error =>{
                console.log(error);
            })

        const divModal = document.querySelector('#profileImageModal');
        const profileImageModal = new bootstrap.Modal(divModal);
        profileImageModal.show();
    }
})