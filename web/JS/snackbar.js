function showToast(msg){
    var toast = document.getElementById("snackbar");
    toast.className = "show";
    toast.innerText = msg;
    setTimeout(function(){
        toast.className = toast.className.replace("show", "");
        toast.innerText = "";
    }, 3000);
}
