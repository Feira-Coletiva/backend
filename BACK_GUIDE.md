# 💾 Backend - Projeto X

## 📂 Estrutura de Pastas

```bash
src/main/java/com/sistema-feira-coletiva/
├── config/  
|   ├── CrosConfig.java
|   └── ...  
├── controller/     # REST Controllers - Endpoints da API
|   ├── CategoriaController.java
|   ├── ClienteController.java
|   ├── OfertaController.java
|   ├── ProdutoController.java
|   ├── VendedorController.java
|   └── ...   
├── service/        # Regras de negócio
|   ├── CategoriaService.com
|   ├── ClienteService.com
|   ├── OfertaService.com
|   ├── ProdutoService.com
|   ├── VendedorService.com
|   └── ...  
├── repository/     # Interfaces de acesso a dados (JpaRepository)
|   ├── CategoriaRepository.java
|   ├── ClienteRepository.java
|   ├── OfertaRepository.java
|   ├── ProdutoRepository.java
|   ├── VendedorRepository.java
|   └── ...  
├── model/          # Entidades JPA (mapeamento de tabelas)
|   ├── domain/
|   |   ├── Categoria
|   |   ├── Cliente
|   |   ├── Oferta
|   |   ├── Produto
|   |   ├── Vendedor
|   |   └── ...
|   └── ...  
└── dto/            # Data Transfer Objects (se houver)
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
