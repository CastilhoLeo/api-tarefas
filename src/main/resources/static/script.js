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
        const btnDeletar = document.createElement('Button')
        


        novaColunaId.textContent = `${tarefa.id}`
        novaColunaTitulo.textContent = `${tarefa.titulo}`
        novaColunaDataVencimento.textContent = `${tarefa.dataVencimento}`
        novaColunaSituacao.textContent = `${tarefa.situacao}`

        btnDeletar.id = `${tarefa.id}`
        btnDeletar.textContent = "Deletar"
        

        linha.appendChild(novaColunaId)
        linha.appendChild(novaColunaTitulo)
        linha.appendChild(novaColunaDataVencimento)
        linha.appendChild(novaColunaSituacao)
        linha.appendChild(colunaBotoes)
        colunaBotoes.appendChild(btnDeletar)

        btnDeletar.addEventListener('click', function() {
            deletarTarefa(this.id)
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

    salvarTarefa(tarefa)
    
})

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
    fetch(`http://localhost:8080/tarefas/${id}`,{
        method: 'DELETE'
    })
    .then(response => console.log(response))

}


