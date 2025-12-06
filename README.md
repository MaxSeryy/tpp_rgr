# Торгівельна мережа

Веб-додаток для управління фірмами, постачальниками та товарами.

## Технології

- Spring Boot 3.5.8, Java 21
- PostgreSQL 17 (Docker)
- Thymeleaf, Spring Security, JPA/Hibernate
- Maven, Lombok

## Сутності

| Сутність | Поля | Зв'язки |
|----------|------|---------|
| Firm | id, name, address |  багато Suppliers |
| Supplier | id, name, contactInfo |  багато Products |
| Product | id, name, sku (унікальний), price |  один Supplier |
| User | id, username, password (BCrypt), role | USER / ADMIN |

## Авторизація

| Роль | Доступ |
|------|--------|
| Гість | тільки /login |
| USER | перегляд |
| ADMIN | повний CRUD |

**Тестові акаунти:** `admin` / `admin123`, `user` / `user123`

## Запуск

### Повний Docker

```bash
cp .env.example .env # та налаштувати дані
docker-compose up -d
```

### Локальна розробка (БД в Docker, Java локально)

```powershell
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
```

Додаток: <http://localhost:8080>
