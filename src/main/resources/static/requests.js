const baseUrl = `${window.location.protocol}//${window.location.host}`;

function pesquisaTodasTarefas(){

    fetch(`${baseUrl}/tarefas`)
    .then(response=> response.json())
    .then(data=>{
    listarTarefas(data.content)
    }) 
}

function salvarTarefa(tarefa){
    fetch(`${baseUrl}/tarefas`,{
        method: 'POST',
        headers :{'Content-Type': 'application/json'},
        body: JSON.stringify(tarefa)
    })
    .then(response=>{
        if(response.ok){
            response.json()
            alert("Tarefa cadastrada com sucesso!")
            document.getElementById('cadastro').reset();}
            
        else{
             response.json()
            .then(data => {
            alert(data.message)
            console.log(data)
    })
}})

    
}

function deletarTarefa(id){
    
    if (confirm(`Confirma a exclusão?`) == true) {
    
    fetch(`${baseUrl}/tarefas/${id}`,{
        method: 'DELETE'
    })
    .then(response => console.log(response))

    location.reload();

}};

function pesquisaSituacao(situacao){
    fetch(`${baseUrl}/tarefas?situacao=${situacao}`)
    .then(response=> response.json())
    .then(data=>{
    listarTarefas(data.content)
    })
}



function visualizarTarefa(id){
    fetch(`${baseUrl}/tarefas/${id}`)
    .then(response => response.json())
    .then (tarefa => {
        inserirDadosTarefa(tarefa);

        edicao = true;
        idEdicao = id;
    
    });
    
}

function editarTarefa(id, tarefa){

    fetch(`${baseUrl}/tarefas/${id}`, {
        method: 'PUT',
        headers :{'Content-Type': 'application/json'},
        body: JSON.stringify(tarefa)
    }) .then(response=>{
        if(response.ok){
            response.json()
            alert("Tarefa alterada com sucesso!")
            document.getElementById('cadastro').reset();}
            
        else{
             response.json()
            .then(data => {
            alert(data.message)
            console.log(data)
    })
}})
       
}


function editarSituacao(id, tarefa){

    fetch(`${baseUrl}/tarefas/${id}`, {
        method: 'PUT',
        headers :{'Content-Type': 'application/json'},
        body: JSON.stringify(tarefa)
    }) .then(response=>{
        if(response.ok){
            response.json()}
            
        else{
             response.json()
            .then(data => {
            alert(data.message)
            console.log(data)
    })
}})
       
}