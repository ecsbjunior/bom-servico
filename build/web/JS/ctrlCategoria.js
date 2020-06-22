function procurarCategorias(){
    let filtro = document.getElementById("cFiltro");
    let previewCat = document.getElementById("previewCategorias");
    let URL = "sCategoria?acao=consultar&filtro="+filtro.value;

    fetch(URL).then(response => {
        return response.text();
    }).then(result => {
        previewCat.innerHTML = result;
    }).catch(err => {
        console.log(err);
    });
}

function salvarCategoria() {
    event.preventDefault();
    let URL = "sCategoria";
    let form = document.getElementById("dCategoria");
    
    if(form.cCod.value === "") form.cCod.value = 0;

    const data = new URLSearchParams();
    for(const pair of new FormData(form))
        data.append(pair[0], pair[1]);
    data.append("acao", "salvar");

    fetch(URL, {method: "POST", body: data}).then(response => {
        return response.text();
    }).then(result => {
        if(result.startsWith("Erro"))
            showToast(result);
        else{
            form.reset();
            showToast(result);
            procurarCategorias();
        }
    }).catch(err => {
        console.log(err);
    })
}

function alterarCategoria(cod){
    let URL = "sCategoria?acao=alterar&cod=" + cod;
    let form = document.forms["dCategoria"];

    fetch(URL).then(response => {
        return response.text();
    }).then(result => {
        if(result === "Erro ao carregar!")
            showToast(result);
        else{
            showToast("Carregado com sucesso!");
            let aux = result.split(",");
            form.cCod.value = aux[0];
            form.cNome.value = aux[1];
        }
    }).catch(err => {
        console.log(err);
    })
}

function deletarCategoria(cod){
    let URL = "sCategoria?acao=deletar&cod=" + cod;
    
    fetch(URL).then(response => {
        return response.text();
    }).then(result => {
        showToast(result);
        procurarCategorias();
    }).catch(err => {
        console.log(err);
    });
}
