package org.soulcodeacademy.helpr.controller;

import org.soulcodeacademy.helpr.domain.Cargo;
import org.soulcodeacademy.helpr.domain.dto.CargoDTO;
import org.soulcodeacademy.helpr.services.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CargoController {
    @GetMapping("/oi") //com base em localhost:8080/oi retorna a String. END POINT -> endereço acessado no back end
    public String dizola(){
        return "Batata!";
    }

    @GetMapping("/batata")
    public Integer valor(){
        return 1000;
    }

    @Autowired
    private CargoService cargoService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FUNCIONARIO')")
    @GetMapping("/cargos")
    public List<Cargo> listar(){
        // Requisição -> Controller -> service -> repository -> SELECT * FROM cargo
        return cargoService.listar(); // JSON
    }


    @GetMapping("/cargos/nome")
    public List<Cargo> listarPorNome(@RequestParam String nome){
        return this.cargoService.listarPorNome(nome);
    }

    @GetMapping("/cargos/salario")
    public List<Cargo> listarPorFaixaSalarial(@RequestParam Double valor1, @RequestParam Double valor2){
        return this.cargoService.listarFaixaSalarial(valor1, valor2);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FUNCIONARIO')")
    @GetMapping("/cargos/{idCargo}")
    public Cargo getCargo(@PathVariable Integer idCargo){
        // @PathVariable => extrai do endpoint o valor dinamico
        return this.cargoService.getCargo(idCargo); // JSON
    }

    // Podemos usar o mesmo endpoint para verbos diferentes.
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    @PostMapping("/cargos") // Requisição tipo POST para /cargos
    public Cargo salvar(@Valid @RequestBody CargoDTO cargo){
        // @Requestbody extrai o JSON do corpo e converte para cargo (deserialização)
        Cargo salvo = this.cargoService.salvar(cargo);
        return salvo;
    }

    // Mapeia requisições do verbo PUT
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/cargos/{idCargo}")
    public Cargo atualizar(@PathVariable Integer idCargo, @Valid @RequestBody CargoDTO cargo){

        Cargo atualizar = this.cargoService.atualizar(idCargo, cargo);
        return atualizar; // Resposta para o cliente (cargo atualizado)
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/cargos/{idCargo}")
    public void delete(@PathVariable Integer idCargo){
        this.cargoService.deletar(idCargo);
    }
}
