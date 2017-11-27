function statusChangeCallback(response){
    if(response.status ==='connected'){

    }
}
function checkLoginState() {
    FB.getLoginStatus(function(response) {
        statusChangeCallback(response);
    });
}