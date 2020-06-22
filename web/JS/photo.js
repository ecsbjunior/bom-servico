function Photo(container){
    function addPhoto(path){
        img = document.createElement('img');
        img.className = 'rounded w-25';
        img.style['margin'] = '5px';
        img.src = path;
        container.appendChild(img);
    }   

    function clearContainer(){
        container.innerHTML = "";
    }

    return {
        addPhoto,
        clearContainer
    };
}