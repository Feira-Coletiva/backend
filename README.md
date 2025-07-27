# 💾 Backend - Projeto X

## 📂 Estrutura de Pastas

```bash
src/main/java/com/sistema-feira-coletiva/
├── config/  
|   ├── CrosConfig.java 
|   ├── JwtAuthenticationFilter.java
|   ├── SecurityConfig.java
|   └── ...  
├── controller/     # REST Controllers - Endpoints da API
|   ├── AuthController.java
|   ├── ClienteController.java
|   ├── LocalDeRetiradaController.java
|   ├── OfertaController.java
|   ├── ParticipanteController.java
|   ├── ProdutoController.java
|   ├── PublicacaoController.java
|   ├── PedidoController.java
|   ├── VendedorController.java
|   └── ...   
├── service/        # Regras de negócio
|   ├── AuthService.com
|   ├── CategoriaService.com
|   ├── ClienteService.com
|   ├── JwtService.com
|   ├── LocalDeRetiradaService.com
|   ├── OfertaService.com
|   ├── ParticipanteService.com
|   ├── ProdutoService.com
|   ├── PublicacaoService.com
|   ├── PedidoService.com
|   ├── VendedorService.com
|   └── ...  
├── repository/     # Interfaces de acesso a dados (JpaRepository)
|   ├── CategoriaRepository.java
|   ├── ClienteRepository.java
|   ├── LocalDeRetiradaRepository.java
|   ├── OfertaRepository.java
|   ├── ParticipanteRepository.java
|   ├── ProdutoRepository.java
|   ├── PublicacaoRepository.java
|   ├── PedidoRepository.java
|   ├── VendedorRepository.java
|   └── ...  
├── model/          # Entidades JPA (mapeamento de tabelas)
|   ├── domain/
|   |   ├── Categoria
|   |   ├── Cliente
|   |   ├── Etapa
|   |   ├── LocalDeRetirada
|   |   ├── Oferta
|   |   ├── Participante
|   |   ├── Produto
|   |   ├── Publicacao
|   |   ├── Pedido
|   |   ├── UnidadeDeMedida
|   |   ├── Vendedor
|   |   └── ...
|   └── ...  
└── dto/            # Data Transfer Objects (se houver)
    ├── input/
    |   ├── base/
    |   |   ├── CategoriaInputDTO.java
    |   |   └── ...
    |   └── personalizado/
    |       ├── CategoriaInputDTO.java
    |       └── ...
    └── output/
        ├── base/
        |   ├── CategoriaInputDTO.java
        |   └── ...
        └── personalizado/
            ├── CategoriaInputDTO.java
            └── ...



    ├── CategoriaInputDTO.java
    ├── CategoriaOutputDTO.java
    ├── ClienteInputDTO.java
    ├── ClienteOutputDTO.java
    ├── OfertaInputDTO.java
    ├── OfertaOutputDTO.java
    ├── ProdutoInputDTO.java
    ├── ProdutoOutputDTO.java
    ├── ResumoOfertaVendedorDTO.java
    ├── ResumoVendedorOfertaDTO.java
    ├── VendedorInputDTO.java
    ├── VendedorOutputDTO.java
    ├── VendedorSuasOfertasDTO.java
    └── ...  
```
