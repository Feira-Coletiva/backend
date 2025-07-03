# 💾 Backend - Projeto X

## 📂 Estrutura de Pastas

```bash
src/main/java/com/seuprojeto/
├── config/  
|   ├── CrosConfig.java
|   └── ...  
├── controller/     # REST Controllers - Endpoints da API
|   ├── CategoriaController.java
|   ├── ClienteController.java
|   ├── VendedorController.java
|   ├── ExemploController.java
|   └── ...   
├── service/        # Regras de negócio
|   ├── CategoriaService.com
|   ├── ClienteService.com
|   ├── VendedorService.com
|   ├── exemplo.com
|   └── ...  
├── repository/     # Interfaces de acesso a dados (JpaRepository)
|   ├── CategoriaRepository.java
|   ├── ClienteRepository.java
|   ├── VendedorRepository.java
|   ├── ExemploRepository.java
|   └── ...  
├── model/          # Entidades JPA (mapeamento de tabelas)
|   ├── domain/
|   |   ├── Categoria
|   |   ├── Cliente
|   |   ├── Vendedor
|   |   ├── Exemplo
|   |   └── ...
|   └── ...  
└── dto/            # Data Transfer Objects (se houver)
    ├── CategoriaInputDTO.java
    ├── CategoriaOutputDTO.java
    ├── ClienteInputDTO.java
    ├── ClienteOutputDTO.java
    ├── VendedorInputDTO.java
    ├── VendedorOutputDTO.java
    ├── ExemploDTO.java
    └── ...  
```
