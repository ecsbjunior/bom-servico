function procurarServico(){
    let filtro = document.getElementById("sFiltro");
    let previewSer = document.getElementById("previewServicos");
    let URL = "sServico?acao=consultar&filtro="+filtro.value;

    fetch(URL).then(response => {
        return response.text();
    }).then(result => {
        previewSer.innerHTML = result;
    }).catch(err => {
        console.log(err);
    });
}

function salvarServico() {
    event.preventDefault();
    let URL = "sServico";
    let form = document.getElementById("dServico");
    
    if(form.sCod.value === "") form.sCod.value = 0;

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
            procurarServico();
        }
    }).catch(err => {
        console.log(err);
    })
}

function alterarServico(cod){
    let URL = "sServico?acao=alterar&cod=" + cod;
    let form = document.forms["dServico"];

    fetch(URL).then(response => {
        return response.text();
    }).then(result => {
        if(result === "Erro ao carregar!")
            showToast(result);
        else{
            showToast("Carregado com sucesso!");
            let aux = result.split(",");
            form.sCod.value = aux[0];
            form.sNome.value = aux[1];
        }
    }).catch(err => {
        console.log(err);
    })
}

function deletarServico(cod){
    let URL = "sServico?acao=deletar&cod=" + cod;
    
    fetch(URL).then(response => {
        return response.text();
    }).then(result => {
        showToast(result);
        procurarServico();
    }).catch(err => {
        console.log(err);
    });
}

