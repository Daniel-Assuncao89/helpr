package org.soulcodeacademy.helpr.services.errors;

// Esta classe representa o erro de regra de negocio quando não encontramos cargos, clientes, funcionarios, chamados do banco de dados
public class RecursoNaoEncontradoError extends RuntimeException {
    public RecursoNaoEncontradoError(String message){
        super(message); //  Passamos a mensagem para a RunTime
    }
}
