document.addEventListener('DomContentLoaded', pesquisaTodasTarefas())

let edicao = false;
let idEdicao = null;

function listarTarefas(data) {

    const tbody = document.getElementById('dados');
    tbody.innerHTML = '';

    data.forEach(tarefa => {

        const linha = document.createElement('tr');
        tbody.appendChild(linha);

        const novaColunaId = document.createElement('td');
        const novaColunaTitulo = document.createElement('td');
        const novaColunaDataVencimento = document.createElement('td');
        const novaColunaSituacao = document.createElement('td');
        const colunaBotoes = document.createElement('td');
        colunaBotoes.className = "colunaBotoes";

        const btnDeletar = document.createElement('Button');
        const btnVisualizar = document.createElement('Button');
        const btnFinalizarReabrir = document.createElement('Button');

        novaColunaId.textContent = `${tarefa.id}`;
        novaColunaTitulo.textContent = `${tarefa.titulo}`;
        novaColunaDataVencimento.textContent = `${tarefa.dataVencimento}`;
        novaColunaSituacao.textContent = `${tarefa.situacao}`;

        btnDeletar.id = `btnDeletar-${tarefa.id}`;
        btnDeletar.textContent = "Deletar";
        btnDeletar.dataset.id = tarefa.id;
        btnDeletar.className = "btn-Deletar";

        btnVisualizar.id = `btnVisualizar-${tarefa.id}`;
        btnVisualizar.textContent = "Visualizar";
        btnVisualizar.dataset.id = tarefa.id;
        btnVisualizar.className = "btn-Visualizar";

        if (tarefa.situacao === "PENDENTE") {
            btnFinalizarReabrir.id = `btnFinalizar-${tarefa.id}`;
            btnFinalizarReabrir.textContent = "Finalizar";
            btnFinalizarReabrir.dataset.id = tarefa.id;
            btnFinalizarReabrir.className = "btn-Finalizar";
        } else {
            btnFinalizarReabrir.id = `btnReabrir-${tarefa.id}`;
            btnFinalizarReabrir.textContent = "Reabrir";
            btnFinalizarReabrir.dataset.id = tarefa.id;
            btnFinalizarReabrir.className = "btn-Reabrir";
        }

        linha.appendChild(novaColunaId);
        linha.appendChild(novaColunaTitulo);
        linha.appendChild(novaColunaDataVencimento);
        linha.appendChild(novaColunaSituacao);
        linha.appendChild(colunaBotoes);
        colunaBotoes.appendChild(btnFinalizarReabrir);
        colunaBotoes.appendChild(btnDeletar);
        colunaBotoes.appendChild(btnVisualizar);

        btnDeletar.addEventListener('click', function(event) {
            const id = event.target.dataset.id;
            deletarTarefa(id);
            location.reload();
        });

        btnVisualizar.addEventListener('click', function(event) {
            const id = event.target.dataset.id;
            modal.showModal();
            visualizarTarefa(id);
        });

        btnFinalizarReabrir.addEventListener('click', function(event) {
            const id = event.target.dataset.id;
            const isFinalizar = event.target.classList.contains("btn-Finalizar");

            tarefa.situacao = isFinalizar ? "FINALIZADO" : "PENDENTE";

            editarTarefa(id, tarefa);
            location.reload();
        });
    });


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



pesquisar.addEventListener('click', function(event){
    const pesquisar = document.getElementById('pesquisar');
    const situacao = document.getElementById('situacao').value;
    pesquisaSituacao(situacao)
})




function inserirDadosTarefa(tarefa){
    document.getElementById('tituloForm').value = tarefa.titulo
    document.getElementById('dataVencimentoForm').value = tarefa.dataVencimento
    document.getElementById('situacaoForm').value = tarefa.situacao
    document.getElementById('descricaoForm').value = tarefa.descricao
    
}





