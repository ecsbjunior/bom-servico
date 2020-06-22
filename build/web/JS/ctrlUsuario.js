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

    if(handleUser()){
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
    }

    return ans;
}

function maskCPF(){
    const cpf = document.getElementById("uCPF");
    cpf.value = cpf.value.replace(/\D/g, "");
    cpf.value = cpf.value.replace(/(\d{3})(\d)/, "$1.$2");
    cpf.value = cpf.value.replace(/(\d{3})(\d)/, "$1.$2");
    cpf.value = cpf.value.replace(/(\d{3})(\d{1,2})$/, "$1-$2");
}

function maskPhone(){
    const phone = document.getElementById("uTelefone");
    phone.value = phone.value.replace(/\D/g, "");
    phone.value = phone.value.replace(/^(\d{2})(\d)/g, "($1)$2");
    phone.value = phone.value.replace(/(\d)(\d{4})$/, "$1-$2");
}

function toRedFormData(data) {
    data.style = 'background-color: rgb(238, 125, 129)';
}

function resetBackgroundElementForm(form) {
    for(element of form.elements)
        if(element.type != 'submit')
            element.style = 'background-color: transparent';
}

function handleEmptyElements(form) {
    const ans = true;

    for(element of form.elements) {
        if(element.type != 'submit') {
            toRedFormData(element);
            ans = false;
        }
    }

    return ans;
}

function handleNumberKeyTyped(event) {
    return event.keyCode >= 48 && event.keyCode <= 57;
}

function handleAlphaKeyTyped(event) {
    return (event.keyCode >= 65 && event.keyCode <= 90) || (event.keyCode >= 97 && event.keyCode <= 122);
}

async function asyncLoginData(login) {
    const URL = "sUsuario?acao=consultarLogin&filtro="+login;
    const response = await fetch(URL);
    const data = await response.text();
    return data;
}

async function handleLogin(login) {
    return (
        async () => {
            const result = await asyncLoginData(login);
            return result === '';
        }
    )();
}

function handlePassword(password, confirmedPassword) {
    return password === confirmedPassword;
}

async function asyncEmailData(email) {
    const URL = "sUsuario?acao=consultarEmail&filtro="+email;
    const response = await fetch(URL);
    const data = await response.text();
    return data;
}

function handleEmail(email) {
    return (
        async () => {
            const result = await asyncEmailData(email);
            return result === '';
        }
    )();
}

function handleName(name) {
    regex = /^[a-zA-Z]*$/;
    return regex.exec(name);
}

function handleDate(date) {
    const dateNow = new Date();
    const dateUsr = new Date(date);

    return dateNow >= dateUsr;
}

function handleCPF(CPF) {
    regex = /([0-9]{2}[\.]?[0-9]{3}[\.]?[0-9]{3}[\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\.]?[0-9]{3}[\.]?[0-9]{3}[-]?[0-9]{2})/;
    return regex.exec(CPF);
}

function handlePhone(phone) {
    regex = /^\([1-9]{2}\)(?:[2-8]|9[1-9])[0-9]{3}\-[0-9]{4}$/;
    return regex.exec(phone);
}

function handleNumber(number) {
    regex = /^[0-9]*$/;
    return regex.exec(number);
}

async function handleUser() {
    const form = document.getElementById("dUsuario");
    let ans = true;

    resetBackgroundElementForm(form);

    if(! await handleLogin(form.uLogin.value)) {
        toRedFormData(form.uLogin);
        ans = false;
    }
    if(!handlePassword(form.uPassword.value, form.uConfirmedPassword.value)) {
        toRedFormData(form.uPassword);
        toRedFormData(form.uConfirmedPassword);
        ans = false;
    }
    if(! await handleEmail(form.uEmail.value)) {
        toRedFormData(form.uEmail);
        ans = false;
    }
    if(!handleDate(form.uDtNascimento.value)){
        toRedFormData(form.uDtNascimento);
        ans = false;
    }

    return ans;
}