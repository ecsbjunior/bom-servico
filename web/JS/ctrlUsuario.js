let ctrlPhoto = Photo(document.getElementById('gallery'));
let __acao = 'insert';

function procurarUsuario(){
    let filtro = document.getElementById("uFiltro");
    let previewCat = document.getElementById("previewUsuario");
    let URL = "sUsuario?acao=consultar&filtro="+filtro.value;

    fetch(URL).then(response => {
        return response.text();
    }).then(result => {
        previewCat.innerHTML = result;
    }).catch(err => {
        console.log(err);
    });
}

function deletarUsuario(cod){
    let URL = "sUsuario?acao=deletar&uLogin=" + cod;
    
    fetch(URL).then(response => {
        return response.text();
    }).then(result => {
        showToast(result);
        procurarUsuario();
    }).catch(err => {
        console.log(err);
    });
}

function alterarUsuario(cod){
    let URL = "sUsuario?acao=alterar&uLogin=" + cod;
    let form = document.forms["dUsuario"];

    fetch(URL).then(response => {
        return response.text();
    }).then(result => {
        if(result === "Erro ao carregar!")
            showToast(result);
        else{
            showToast("Carregado com sucesso!");
            let aux = result.split("$");
            console.log(aux);
            form.uLogin.value = aux[0];
            form.uPassword.value = aux[1];
            form.uConfirmedPassword.value = aux[1];
            form.uName.value = aux[2].split(" ")[0];
            form.uSobrenome.value = aux[2].split(" ")[1];
            form.uCPF.value = aux[3];
            form.uEmail.value = aux[4];
            form.uTelefone.value = aux[5];
            form.uEndRua.value = aux[6].split(',')[0];
            form.uEndNum.value = aux[6].split(',')[1].split(" - ")[0];
            form.uEndBairro.value = aux[6].split(',')[1].split(" - ")[1];
            if(form.uNivel) form.uNivel.value = aux[7];
            form.uDtNascimento.value = aux[9];
            form.uServicos.value = aux[10];
            ctrlPhoto.clearContainer();
            ctrlPhoto.addPhoto('Images\\DBImages\\Users\\'+aux[8]);
            __acao = 'update'
        }
    }).catch(err => {
        console.log(err);
    })
}

function salvarUsuario() {
    event.preventDefault();
    let ans = false;
    let URL = "sUsuario";
    let form = document.getElementById("dUsuario");

    var data = new FormData(form);
    data.append('acao', 'salvar');
    data.append('acaodaacao', __acao);

    fetch(URL, { method: 'POST', body: data }).then(response => {
        return response.text();
    }).then(result => {
        if (result.startsWith("Erro")) {
            showToast(result);
            ans = false;
        }
        else {
            form.reset();
            ctrlPhoto.clearContainer();
            showToast(result);
            procurarUsuario();
            __acao = 'insert';
            ans = true;
        }
    }).catch(err => {
        console.log(err);
    })

    return ans;
}

