#language: pt
# Author: Bruna
# Version: 1.0
#Encodig: UTF-8

@Gorest @regressivo
Funcionalidade: Criar e editar contas de usuários
  Eu como Administrador do sistema, quero cadastrar, editar e excluir usuários do sistema

  @post
  Cenario: Cadastrar novo usuário API Gorest
    Dado que possuo gorest token válido
    Quando envio um request de cadastro de usuário com dados válidos
    Entao o usuário deve ser criado corretamente
    E o status code do request deve ser 201

  @get
  Cenario: Buscar um usuário existente na API Gorest
    Dado que possuo gorest token válido
    E existe um usuário cadastrado na API
    Quando buscar esse usuário
    Então os dados usuários devem ser retornados
    E o status code do request deve ser 200

  @put
  Cenario: Alterar um usuário existente na API Gorest PUT
    Dado que possuo gorest token válido
    E existe um usuário cadastrado na API
    Quando altero os dados do usuário
    Então o usuário deve ser alterado com sucesso
    E o status code do request deve ser 200

  @patch
  Cenario: Alterar um usuário existente na API Gorest PATCH
    Dado que possuo gorest token válido
    E existe um usuário cadastrado na API
    Quando altero um ou mais dados do usuário
    Então o usuário deve ser alterado com sucesso
    E o status code do request deve ser 200

  @delete
  Cenario: Deletar um usuário existente na API Gorest
    Dado que possuo gorest token válido
    E existe um usuário cadastrado na API
    Quando deleto esse usuário
    Então o usuário é deletado corretamente
    E o status code do request deve ser 204
