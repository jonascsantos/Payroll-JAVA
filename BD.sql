CREATE TABLE Funcionario
(
Nome_F varchar(50) NOT NULL,
pk_Cpf_F number(11) primary key,
status varchar(50) NULL
);

CREATE TABLE Setor
(
Cod_S number(4) PRIMARY KEY,
Nome_S varchar(50) NOT NULL,
Descricao varchar(255) NOT NULL
);

CREATE TABLE Cargo
(
Nome_C varchar(50) NOT NULL,
Cod_C number(4) PRIMARY KEY,
Valor_base number(9,2) NOT NULL,
Valor_horaTrab number(9,2) NOT NULL,
Carga_horaria number(9) NOT NULL,
vinculo varchar(50) NOT NULL
);

CREATE TABLE Funcionario_Cargo
(
fk_pk_Cod_C number(4) NOT NULL,
fk_pk_Cpf_F number(11) NOT NULL,
Data_Começo date NOT NULL,
Data_Fim date NULL,
CONSTRAINT fk_pk_Cod_C FOREIGN KEY (fk_pk_Cod_C)
REFERENCES Cargo (Cod_C),
CONSTRAINT fk_pk_Cpf_F FOREIGN KEY (fk_pk_Cpf_F)
REFERENCES Funcionario (pk_Cpf_F),
CONSTRAINT pk_Fun_Cargo PRIMARY KEY (fk_pk_Cod_C,
fk_pk_Cpf_F)
);

CREATE TABLE Funcionario_Setor
(
fk_pk_Cod_S number(6) NOT NULL,
fk_pk_Cpf_F number(11) NOT NULL,
Data_inicio date NOT NULL,
Data_fim date NULL,
CONSTRAINT fk_pkCod_S FOREIGN KEY (fk_pk_Cod_S)
REFERENCES Setor (Cod_S),
CONSTRAINT fk_pkCpf_F FOREIGN KEY (fk_pk_Cpf_F)
REFERENCES Funcionario (pk_Cpf_F),
CONSTRAINT pk_Func_Setor PRIMARY KEY (fk_pk_Cod_S,
fk_pk_Cpf_F)
);

CREATE TABLE Pagamento
(
Fk_pk_Cpf_F number(11) NOT NULL,
Cod_P number(4) NOT NULL,
Data_Pago date NOT NULL,
Fgts_P number(9,2) NULL,
Inss_P number(9,2) NULL,
fgtsAux number(1) NULL,
inssAux number(1) NULL,
AdComissao_P number(9,2) NULL,
AdInsalubridade_P number(9,2) NULL,
AdPericulosidade_P number(9,2) NULL,
AdHoraExtra_P number(9,2) NULL,
horasExtraTrabalhadas number(9) NULL,
CONSTRAINT Fk_pk_CpfF FOREIGN KEY (Fk_pk_Cpf_F)
REFERENCES Funcionario (pk_Cpf_f),
CONSTRAINT pk_Pagamento PRIMARY KEY (Fk_pk_Cpf_F,Cod_P)
);

create sequence setor_seq minvalue 1 maxvalue 9999999999 start with 1 increment by 1 nocache cycle;
create sequence cargo_seq minvalue 1 maxvalue 9999999999 start with 1 increment by 1 nocache cycle;
create sequence pagamento_seq minvalue 1 maxvalue 9999999999 start with 1 increment by 1 nocache cycle;

CREATE OR REPLACE TRIGGER Horario_de_trabalho
BEFORE INSERT OR UPDATE OR DELETE ON Pagamento
BEGIN
IF TO_CHAR (SYSDATE, 'HH24') NOT BETWEEN '08' AND '17' THEN
RAISE_APPLICATION_ERROR(-20205,'Alterações são permitidas apenas no horário de
expediente');
END IF;
END Horario_de_trabalho;


