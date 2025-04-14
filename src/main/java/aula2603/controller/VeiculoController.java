package aula2603.controller;

import aula2603.model.entity.Veiculo;

import aula2603.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoRepository repository;

    @GetMapping("/list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("veiculos", repository.listarTodos());
        return new ModelAndView("/veiculo/list", model);
    }

    @GetMapping("/form")
    public String formulario(Veiculo veiculo) {
        return "/veiculo/form";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editar(@PathVariable("id") Long id, ModelMap model) {
        Veiculo veiculo = repository.buscarPorId(id);
        model.addAttribute("veiculo", veiculo != null ? veiculo : new Veiculo());
        return new ModelAndView("/veiculo/form", model);
    }

    @PostMapping("/save")
    public String salvar(Veiculo veiculo) {
        repository.salvar(veiculo);
        return "redirect:/veiculo/list";
    }

    @GetMapping("/delete/{id}")
    public String deletar(@PathVariable("id") Long id) {
        repository.deletar(id);
        return "redirect:/veiculo/list";
    }

    @PostMapping("/buscar")
    public ModelAndView buscar(@RequestParam("modelo") String modelo, ModelMap model) {
        List<Veiculo> encontrados = repository.buscarPorModelo(modelo);
        model.addAttribute("veiculos", encontrados.isEmpty() ?
                repository.listarTodos() : encontrados);
        if (encontrados.isEmpty()) {
            model.addAttribute("msgErro", "Nenhum veículo encontrado");
        }
        return new ModelAndView("/veiculo/list", model);
    }
}