let foto = Photo(document.getElementById('gallery'));

function procurarAnuncio() {
    let filtro = document.getElementById("cFiltro");
    let preview = document.getElementById("previewAnuncios");
    let URL = "sAnuncio?acao=consultar&filtro=" + filtro.value;

    fetch(URL).then(response => {
        return response.text();
    }).then(result => {
        preview.innerHTML = result;
    }).catch(err => {
        console.log(err);
    });
}

function salvarAnuncio() {
    event.preventDefault();
    let ans = false;
    let URL = "sAnuncio";
    let form = document.getElementById("dAnuncio");

    if (form.aCod.value === "") form.aCod.value = 0;

    var data = new FormData(form);
    data.append('acao', 'salvar');

    fetch(URL, { method: "POST", body: data }).then(response => {
        return response.text();
    }).then(result => {
        if (result.startsWith("Erro")) {
            showToast(result);
            ans = false;
        } else {
            form.reset();
            foto.clearContainer();
            showToast(result);
            procurarAnuncio();
            ans = true;
        }
    }).catch(err => {
        console.log(err);
    });

    return ans;
}

function alterarAnuncio(cod) {
    let URL = "sAnuncio?acao=alterar&cod=" + cod;
    let form = document.forms["dAnuncio"];

    fetch(URL).then(response => {
        return response.text();
    }).then(result => {
        if (result === "Erro ao carregar!")
            showToast(result);
        else {
            showToast("Carregado com sucesso!");
            let aux = result.split("$");
            form.aCod.value = aux[0];
            form.uLogin.value = aux[6];
            form.aTitle.value = aux[1];
            form.aDescricao.value = aux[2];
            form.aDiasDeTrabalho.value = aux[3];
            form.aHorInicio.value = aux[4];
            form.aHorFim.value = aux[5];
            form.aCategoria.value = aux[7];
            foto.clearContainer();
            for (let i = 8; i < aux.length; i++)
                foto.addPhoto('Images\\DBImages\\Ads\\' + aux[i])
        }
    }).catch(err => {
        console.log(err);
    });
}

function deletarAnuncio(cod) {
    let URL = "sAnuncio?acao=deletar&cod=" + cod;

    fetch(URL).then(response => {
        return response.text();
    }).then(result => {
        showToast(result);
        procurarAnuncio();
    }).catch(err => {
        console.log(err);
    });
}

/*=================MENSAGENS=====================*/
function loadMessage(cod){
    //let filtro = document.getElementById("cFiltro");
    let preview = document.getElementById("pMessages");
    let URL = "sMensagem?acao=consultar&aCod="+cod;//&filtro=" + filtro.value;

    fetch(URL).then(response => {
        return response.text();
    }).then(result => {
        preview.innerHTML = result;
    }).catch(err => {
        console.log(err);
    });
}

function deleteMessage(cod, anu_cod) {
    let URL = "sMensagem?acao=deletar&cod=" + cod;

    fetch(URL).then(response => {
        return response.text();
    }).then(result => {
        showToast(result);
        loadMessage(anu_cod);
    }).catch(err => {
        console.log(err);
    });
}

function sendMessage(cod) {
    event.preventDefault();
    let URL = "sMensagem";
    let form = document.getElementById("dMensagem");
    
    const data = new URLSearchParams();
    for(const pair of new FormData(form))
        data.append(pair[0], pair[1]);
    data.append("acao", "salvar");
    data.append('aCod', cod);
    

    fetch(URL, { method: "POST", body: data }).then(response => {
        return response.text();
    }).then(result => {
        if (result.startsWith("Erro")) {
            showToast(result);
        }
        else {
            form.reset();
            showToast(result);
            loadMessage(cod);
        }
    }).catch(err => {
        console.log(err);
    });

    return false;
}