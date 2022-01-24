# **Desafio Técnico - Back-end: Sistema de Análise de Dados **

## Informações gerais

O projeto na sua totalidade visa executar um sistema de análise de dados que importa muitos arquivos simples. Esse sistema deve conseguir ler todos os arquivos, filtrá-los, analisá-los e emitir um relatório de saída sobre os conteúdos relevantes contidos. 

Também se fazem necessárias diversas validações nos formatos e no conteúdo dos dados embutidos.

Os dados de entrada possuem um formato pré-definido para que possam ser devidamente interpretados. 

Existem três categorias diferentes de dados nos arquivos de entrada, as quais representam três entidades diferentes. 

Cada uma dessas categorias possui seu próprio identificador especial no início do dado para que o interpretador consiga distinguir entre elas. Até porque os conteúdos atribuídos também são variados e, assim, se faz se necessário analisar os seguintes formatos:

| Categoria de Dado | Identificador | Formato de dado |
|:-:|:-:|:-:|
| *`Vendedor`* | *`001`* | *`001çCPFçNameçSalary`* |
| *`Cliente`* | *`002`* | `002çCNPJçNameçBusiness Area` |
| *`Venda`*| *`003`* | `003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name` |



A especificação pede os seguintes pré-requisitos. Todos eles foram devidamente cumpridos. 

* O sistema deve ler dados de um diretório padrão, localizado no caminho *`%HOMEPATH%/data/in`*. 
* O sistema deve ler apenas arquivos contendo a extensão *`.dat`*. Todas as outras extensões devem ser ignoradas no diretório. 
* Após o processamento dos arquivos contidos no diretório, o sistema deve gerar um novo arquivo simples no diretório padrão de saída, localizado no caminho *`%HOMEPATH%/data/out`*.
* O nome do arquivo de saída deve manter o nome do arquivo original e adicionar um campo de verificação *`.done`* antes da extensão *`.dat`*. 
* O padrão do arquivo de saída fica, portanto, da seguinte maneira: *`{flat_file_name}.done.dat`*.
* Para resumir os dados de entrada, o arquivo de saída deve apresentar os seguintes conteúdos na forma de um relatório: 
	* Quantidade de clientes contidos no arquivo de entrada.
	* Quantidade de vendedores contidos no arquivo de entrada.
	* Identificador (ID) da venda mais cara contida no arquivo de entrada.
	* Identificação do pior vendedor contido no arquivo de entrada.
* A aplicação deve estar executando a todo momento, sem interrupções. 
* Sempre que novos arquivos forem inseridos/removidos/alterados, eles devem ser interpretados e gerenciados sem interrupções.

##  Tecnologias utilizadas e passos necessários para instalação

O projeto na totalidade emprega tecnologias que necessitam de instalação/configuração prévia para que a aplicação execute devidamente. 

Assegure-se de seguir corretamente os passos indicados nos links abaixo. Todos os manuais são provenientes das documentações oficiais.


| Aplicação/Plataforma | Manual de Instalação |
|:-:|:-:|
| **Java 11** | <https://openjdk.java.net/install/> |
| **Git** | <https://git-scm.com/book/en/v2/Getting-Started-Installing-Git> |
| **Gradle** | <https://gradle.org/install/> |

##  Detalhes de implementação

* Foram utilizados validadores de `CPF` e `CNPJ` através de dependências externas e mais confiáveis para que a tecnicalidade da validação fosse abstraída do projeto. 
* Não é permitida a inclusão de dados com campos que deveriam ser únicos de suas categorias, como `CPF (Vendedor)`, `CNPJ (Cliente)`, `ID com preço (Item)` e `ID (Venda)` e que já foram previamente cadastrados. 
* Todos os delimitadores recomendados pela especificação foram acatados pela implementação. Além disso, todos eles foram inseridos no arquivo de configuração *`application.properties`* para que seja fácil alterar os formatos em possíveis casos futuros. 
* Entretanto, há uma impossibilidade atual do sistema em interpretar *`Strings`* que possuam de maneira genuína o uso do caractere `ç` nos campos de `name` e `business area`. Isto acontece graças ao delimitador `ç` recomendado.
* A sugestão de implementação seria a alteração do delimitador `ç` para o mesmo caractere no formato `UPPERCASE`. Ou seja, o caractere  `Ç`. Tendo em vista que o caractere original recomendado aparentemente já não seria um problema para uso em língua americana, essa mudança já conseguiria corrigir o mau uso também na língua portuguesa. Afinal, seria muito difícil que algum campo genuíno precisasse desse caractere nesse formato.
* Para a implementação atual, recomenda-se que os campos que necessitem genuinamente do caractere `ç` sejam escritos com o caractere `c` em seu lugar, mesmo não sendo o mais ideal.
* Como também existe o arquivo de configuração para que o usuário possa alterar os delimitadores e identificadores dos dados, há também a possibilidade de modificação sob demanda do próprio usuário.
* Foi implementada uma lógica de geração e verificação de `Hashes` provenientes da função criptográfica de *Hash*: ` SHA-256`. 
* Basicamente, o sistema analisa se os metadados do arquivo estão idênticos e, nesse caso, a `Hash` gerada previamente se manterá idêntica também. Assim, não haverá a necessidade de alteração e sobrescrita/remoção do arquivo de saída pertinente.
* Naturalmente, para que essa lógica faça mais sentido, as `Hashes` são armazenadas de maneira persistente em um arquivo `Files.json` e recuperadas todas as vezes que a aplicação é inicializada.
* Estruturas de *`HashMap`* estão espalhadas pela implementação para que o acesso aos dados seja feito de maneira mais performática. Principalmente na checagem de `Hash` do arquivo ou nas validações dos campos únicos de cada categoria de dado. 
* Mais de um campo na mesma linha são aceitos, desde que sejam separados por pelo menos um espaço. Não há problema caso mais espaços sejam utilizados, pois o sistema consegue interpretar corretamente essa situação. 
* O monitoramento do diretório através da lógica de *`WatchService`* identifica novos eventos (inserção, modificação e/ou remoção de arquivos) e executa toda a chamada da lógica de desserialização dos arquivos novamente. Entretanto, todas as `Hashes` dos arquivos inalterados estarão armazenadas em "cache" e a partir de suas comparações, permitirão que apenas os eventos relevantes sejam gerenciados. 
* Por fim, há outro detalhe técnico atrelado ao formato recomendado pela especificação que pode se tornar problemático. No momento, o dado de `Venda` identifica o vendedor responsável apenas por seu nome conforme solicitado.  Isso acarreta a impossibilidade de uma correta identificação de um vendedor, caso já exista outro vendedor com este mesmo nome. 
* Como sugestão, poder-se-ia trocar o formato para que a identificação do vendedor na `Venda` fosse feita através de seu `CPF`, o qual é um campo único. Contudo, reconhece-se a aparente não praticidade desse tipo de identificação com um campo de difícil memorização. 
* Portanto, outra possibilidade mais plausível e simples: poder-se-ia adicionar um campo de usuário para cada vendedor. Esse campo, sim, deveria ser único já na sua criação e o mesmo seria utilizado na identificação do vendedor no campo de `Venda` sem maiores problemas. 

## Exemplo

Um arquivo de entrada *`testing.dat`*, com os seguintes campos, teria o seguinte arquivo de saída *`testing.done.dat`* gerado no diretório de saída apropriado:

`001ç17091236050çDiegoç50000 001ç25705975031çRenatoç40000.99
002ç07369743000182çJose da SilvaçRural
002ç91993408000167çEduardoPereiraçRural
003ç10ç[1-10-100,2-30-2.50,3-40-3.106]çDiego
003ç08ç[4-35-10,5-33-1.50,6-40-0.10]çRenato
`

<a href="https://ibb.co/4tQfRfg"><img src="https://i.ibb.co/bmf353R/Output-Picture.jpg" alt="Output-Picture" border="0"></a>

<a href="https://imgbb.com/"><img src="https://i.ibb.co/NY0yTGk/Output-Picture-2.jpg" alt="Output-Picture-2" border="0"></a>

