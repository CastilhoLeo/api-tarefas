document.addEventListener('DomContentLoaded', pesquisaTodasTarefas())

function pesquisaTodasTarefas(){

    fetch('http://localhost:8080/tarefas')
    .then(response=> response.json())
    .then(data=>{
    listarTarefas(data.content)
    }) 
}

function listarTarefas(data){

    const tbody = document.getElementById('dados')
    tbody.innerHTML='';
    

    for(tarefa of data){

        const linha = document.createElement('tr');
        tbody.appendChild(linha)

        const novaColunaId = document.createElement('td')
        const novaColunaTitulo = document.createElement('td')
        const novaColunaDataVencimento = document.createElement('td')
        const novaColunaSituacao = document.createElement('td')
        const colunaBotoes = document.createElement('td')
        var btnDeletar = document.createElement('Button')
        var btnVisualizar = document.createElement('Button')
        


        novaColunaId.textContent = `${tarefa.id}`
        novaColunaTitulo.textContent = `${tarefa.titulo}`
        novaColunaDataVencimento.textContent = `${tarefa.dataVencimento}`
        novaColunaSituacao.textContent = `${tarefa.situacao}`

        btnDeletar.id = `${tarefa.id}`
        btnDeletar.textContent = "Deletar"

        btnVisualizar.id = `${tarefa.id}`
        btnVisualizar.textContent = "Visualizar"
        

        linha.appendChild(novaColunaId)
        linha.appendChild(novaColunaTitulo)
        linha.appendChild(novaColunaDataVencimento)
        linha.appendChild(novaColunaSituacao)
        linha.appendChild(colunaBotoes)
        colunaBotoes.appendChild(btnDeletar)
        colunaBotoes.appendChild(btnVisualizar)

        btnDeletar.addEventListener('click', function() {
            deletarTarefa(this.id)
            location.reload();
    
        })

        btnVisualizar.addEventListener('click', function(){
            modal.showModal();
            visualizarTarefa(this.id)
        })
    }

    
}

const cadastroBtn = document.getElementById('novaTarefa')
const modal = document.getElementById('modal')

cadastroBtn.addEventListener('click', ()=> {
    modal.showModal();
});

const fecharModalBtn = document.getElementById('fechar')
fecharModalBtn.addEventListener('click', ()=>{
    modal.close();
    document.location.reload();
});


const formularioTarefa = document.getElementById('cadastro')

formularioTarefa.addEventListener('submit', function(event){

    event.preventDefault();
    
     const titulo = document.getElementById('tituloForm').value;
     const dataVencimento = document.getElementById('dataVencimentoForm').value;
     const descricao = document.getElementById('descricaoForm').value;
     const situacao = document.getElementById('situacaoForm').value;
    
    const tarefa = {
        titulo: titulo,
        dataVencimento: dataVencimento,
        descricao: descricao,
        situacao: situacao
    };

    if (edicao) {
        editarTarefa(idEdicao, tarefa);
        modal.close();
        location.reload();
    } else {
        salvarTarefa(tarefa);
    }

    edicao = false;
    idEdicao = null;
});

function salvarTarefa(tarefa){
    fetch(`http://localhost:8080/tarefas`,{
        method: 'POST',
        headers :{'Content-Type': 'application/json'},
        body: JSON.stringify(tarefa)
    })
    .then(response => response.json())
    .then(data => console.log(data))

    document.getElementById('cadastro').reset();
}

function deletarTarefa(id){
    
    if (confirm(`Confirma a exclusão?`) == true) {
    
    fetch(`http://localhost:8080/tarefas/${id}`,{
        method: 'DELETE'
    })
    .then(response => console.log(response))

    location.reload();

}};



pesquisar.addEventListener('click', function(event){
    const pesquisar = document.getElementById('pesquisar');
    const situacao = document.getElementById('situacao').value;
    pesquisaSituacao(situacao)
})



function pesquisaSituacao(situacao){
    fetch(`http://localhost:8080/tarefas?situacao=${situacao}`)
    .then(response=> response.json())
    .then(data=>{
    listarTarefas(data.content)
    })
}

let edicao = false;
let idEdicao = null;

function visualizarTarefa(id){
    fetch(`http://localhost:8080/tarefas/${id}`)
    .then(response => response.json())
    .then (tarefa => {
        inserirDadosTarefa(tarefa);

        edicao = true;
        idEdicao = id;
    
    
    });
    
}

function inserirDadosTarefa(tarefa){
    document.getElementById('tituloForm').value = tarefa.titulo
    document.getElementById('dataVencimentoForm').value = tarefa.dataVencimento
    document.getElementById('situacaoForm').value = tarefa.situacao
    document.getElementById('descricaoForm').value = tarefa.descricao
    
}

function editarTarefa(id, tarefa){

    fetch(`http://localhost:8080/tarefas/${id}`, {
        method: 'PUT',
        headers :{'Content-Type': 'application/json'},
        body: JSON.stringify(tarefa)
    }).then(response => response.json())
    .then(data => console.log(data))
       
}

