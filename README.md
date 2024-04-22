# Gerenciador de Apresentaçao de TCC
Projeto integrador
trabalho da materia de Projeto de Aplicativo Mobile
Professor:
  Romulo
  Lucas

# ALTERAÇÕES FEITAS NAS SERVICES, SAMUEL-> 08/03/2023
Criei dois pacotes dentro das services para melhor organização, um impl(implementação) e outro spec(especificação).

# DELETE DE AQRQUIVOS DUPLICADOS DOS PRIMEIRO REPOSITORIO COM PROBLEMAS, DOUGLAS -> 11/03/2024
Deletei os arquivos do primeiro repositorio feito e renomei o nome da pasta do projeto para gtcc.

# ADICIONADO AS CLASSES SERVICES E REPOSITORYS -> 01/04/2024
Adicionado arquivos das services e repositorys


# Conflitos consertados entre as services e controllers e interfaces  -> 06/04/2024
Services: Implementei as interfaces para alocar a lógica de negócio e adicionei os Generics dentro das interfaces restantes 
Controllers: Adicionei os a classe Optional dentro do Contorller Users para tratar melhor o que o banco nos retorna. Não apliquei ainda para as controllers restantes.    
Consertei alugns conlitos restantes dentro do código, envolvendo o tipo de parametro dentro dos métodos das clases nas services, interfaces e controllers

# Resolvendo conflitos e executando o projeto -> 18/04/2024
Executei o porjeto e obtive erro, resolvi todas as dependencias com o comando mvn clean install -U, e removi o relacionamento que existe na classe users, 
mudei de lugar as configurações do banco para a classe main do projeto, e modifiqui o POM.XML adicei as versões das dependencias, e alterei a rota ApresentantionControllers o metodo 
getAllApresentantionsBancas tinha uma rota mapeada que ja existia, então alterei. 
E removi libs restantes do nitriteDB que causavam problemas no projeto.]

# Removendo a Dependencias Spring Data Rest
Comentando a dependencia do POM.xml.
