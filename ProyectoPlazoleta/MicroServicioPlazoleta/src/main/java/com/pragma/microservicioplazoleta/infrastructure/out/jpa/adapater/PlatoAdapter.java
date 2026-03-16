package com.pragma.microservicioplazoleta.infrastructure.out.jpa.adapater;

import com.pragma.microservicioplazoleta.domain.model.Categoria;
import com.pragma.microservicioplazoleta.domain.model.Plato;
import com.pragma.microservicioplazoleta.domain.spi.IPlatoRepositorio;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity.CategoriaEntity;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.entity.PlatoEntiy;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.mapper.PlatoEnrityMapper;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.repository.CategoriaRepositorio;
import com.pragma.microservicioplazoleta.infrastructure.out.jpa.repository.PlatoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PlatoAdapter implements IPlatoRepositorio {
    private final PlatoRepositorio platoRepositorio;
    private final PlatoEnrityMapper platoEntityMapper;
    private final CategoriaRepositorio categoriaRepositorio;

    @Override
    public Plato guardarPlato(Plato plato) {
        PlatoEntiy entity = platoEntityMapper.toEntidy(plato);
        if (plato.getCategoria() != null && plato.getCategoria().getId() != null) {
            CategoriaEntity categoriaEntity = categoriaRepositorio.findById(plato.getCategoria().getId())
                    .orElse(null);
            entity.setCategoria(categoriaEntity);
        }
        PlatoEntiy guardado = platoRepositorio.save(entity);
        return obtenerPlatoPorId(guardado.getId()).orElse(null);
    }

    @Override
    public Optional<Plato> obtenerPlatoPorId(Long id) {
        return platoRepositorio.findById(id)
                .map(entity -> {
                    Plato plato = platoEntityMapper.toModel(entity);
                    if (entity.getCategoria() != null) {
                        Categoria categoria = new Categoria();
                        categoria.setId(entity.getCategoria().getId());
                        categoria.setNombre(entity.getCategoria().getNombre());
                        categoria.setDescripcion(entity.getCategoria().getDescripcion());
                        plato.setCategoria(categoria);
                    }
                    return plato;
                });
    }

    @Override
    public List<Plato> listarPlatosPorRestaurante(Long idRestaurante, Long idCategoria, int pagina, int tamano) {
        List<PlatoEntiy> entities;

        if (idCategoria != null) {
            entities = platoRepositorio.findByIdRestauranteAndCategoriaId(idRestaurante, idCategoria);
        } else {
            entities = platoRepositorio.findByIdRestaurante(idRestaurante);
        }

        int inicio = pagina * tamano;
        int fin = Math.min(inicio + tamano, entities.size());

        if (inicio >= entities.size()) return List.of();

        return entities.subList(inicio, fin)
                .stream()
                .map(entity -> {
                    Plato plato = platoEntityMapper.toModel(entity);
                    if (entity.getCategoria() != null) {
                        Categoria categoria = new Categoria();
                        categoria.setId(entity.getCategoria().getId());
                        categoria.setNombre(entity.getCategoria().getNombre());
                        categoria.setDescripcion(entity.getCategoria().getDescripcion());
                        plato.setCategoria(categoria);
                    }
                    return plato;
                })
                .toList();
    }

    @Override
    public boolean categoriaExiste(Long idCategoria) {
        return categoriaRepositorio.existsById(idCategoria);
    }

}
