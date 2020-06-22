CREATE TABLE Categoria (
    cat_cod SERIAL,

    cat_nome VARCHAR(30) NOT NULL,

    CONSTRAINT pk_cat PRIMARY KEY (cat_cod)
);

CREATE TABLE Servico (
    ser_cod SERIAL,

    ser_nome VARCHAR(30) NOT NULL,

    CONSTRAINT pk_ser PRIMARY KEY (ser_cod)
);

CREATE TABLE Usuario (
    usu_login VARCHAR(16) NOT NULL,
    usu_senha VARCHAR(32) NOT NULL,
    usu_nivel INTEGER NOT NULL,
    ser_cod INTEGER NOT NULL,

    usu_nome VARCHAR(50) NOT NULL,
    usu_CPF VARCHAR(14) NOT NULL,
    usu_dtnascimento DATE NOT NULL,
    usu_email VARCHAR(50) NOT NULL,
    usu_telefone VARCHAR(14) NOT NULL,
    usu_endereco VARCHAR(50) NOT NULL,
    usu_foto VARCHAR(200) NOT NULL,
    
    CONSTRAINT pk_usu PRIMARY KEY (usu_login)
);

CREATE TABLE Anuncio (
    anu_cod SERIAL,
    usu_login VARCHAR(14) NOT NULL,
    cat_cod INTEGER NOT NULL,

    anu_titulo VARCHAR(30) NOT NULL,
    anu_descricao VARCHAR(200),
    anu_diastrabalho VARCHAR(30) NOT NULL,
    anu_horarioinicio VARCHAR(5) NOT NULL,
    anu_horariofim VARCHAR(5) NOT NULL,

    CONSTRAINT pk_anu PRIMARY KEY (anu_cod),
    CONSTRAINT fk_anusu FOREIGN KEY (usu_login) REFERENCES Usuario,
    CONSTRAINT fk_anu_cat FOREIGN KEY (cat_cod) REFERENCES Categoria
);

CREATE TABLE Foto (
    fot_cod SERIAL,
    anu_cod INTEGER NOT NULL,
    
    fot_foto VARCHAR(200) NOT NULL,
    
    CONSTRAINT pk_foto PRIMARY KEY (fot_cod),
    CONSTRAINT fk_fotanu FOREIGN KEY (anu_cod) REFERENCES Anuncio
);

CREATE TABLE Mensagem (
    men_cod SERIAL,
    anu_cod INTEGER NOT NULL,
    usu_login VARCHAR(16),

    men_mensagem VARCHAR(500) NOT NULL,

    CONSTRAINT pk_men PRIMARY KEY (men_cod),
    CONSTRAINT fk_men_anu FOREIGN KEY (anu_cod) REFERENCES Anuncio,
    CONSTRAINT fk_men_usu FOREIGN KEY (usu_login) REFERENCES Usuario
);
