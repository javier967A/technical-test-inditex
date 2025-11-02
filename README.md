# README

## Descripción del proyecto

Servicio **Spring Boot** que expone un **endpoint REST** para obtener el **precio aplicable** de un producto en una cadena (brand) para una **fecha de aplicación** dada.  
La información se almacena en una **base de datos H2 en memoria** inicializada con los datos del enunciado.

> **Regla de negocio clave:** si existen varias tarifas que aplican en el mismo instante, se selecciona la de **mayor `PRIORITY`** (valor numérico más alto).

---

## Contexto y datos de ejemplo

En la base de datos de comercio electrónico existe la tabla **PRICES** que refleja el precio final (PVP) y la tarifa que aplica a un producto de una cadena entre unas fechas determinadas.

**Estructura y ejemplo:**

```
PRICES
------

BRAND_ID   START_DATE              END_DATE                PRICE_LIST   PRODUCT_ID   PRIORITY   PRICE    CURR
--------------------------------------------------------------------------------------------------------------
1          2020-06-14-00.00.00     2020-12-31-23.59.59     1            35455        0          35.50    EUR
1          2020-06-14-15.00.00     2020-06-14-18.30.00     2            35455        1          25.45    EUR
1          2020-06-15-00.00.00     2020-06-15-11.00.00     3            35455        1          30.50    EUR
1          2020-06-15-16.00.00     2020-12-31-23.59.59     4            35455        1          38.95    EUR
```

**Campos:**

- `BRAND_ID`: identificador de la cadena (ej. **1 = ZARA**).
- `START_DATE`, `END_DATE`: rango de fechas en el que aplica la tarifa.
- `PRICE_LIST`: identificador de la tarifa aplicable.
- `PRODUCT_ID`: identificador del producto.
- `PRIORITY`: desambiguador; si hay solape, **aplica la mayor prioridad**.
- `PRICE`: precio final de venta.
- `CURR`: ISO de la moneda.

---

## Prerrequisitos

- **Java 17** o superior  
- **Maven 3.8** o superior

> La aplicación se ejecuta en el puerto **8081**.

---

## Cómo ejecutar

1) **Clonar el repositorio**
```bash
git clone https://github.com/javier967A/technical-test-inditex.git
cd technical-test-inditex
git switch develop
```

2) **Compilar**
```bash
mvn clean install
```

3) **Arrancar la aplicación**
```bash
mvn spring-boot:run
```

4) **Consola H2 (opcional)**
- URL: `http://localhost:8081/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Usuario: `sa`
- Password: *(vacío)*

5) **Documentación OpenAPI / Swagger**
- API Docs: `http://localhost:8081/v3/api-docs`
- Swagger UI: `http://localhost:8081/swagger-ui.html`

---

## Endpoint

### Obtener precio aplicable
- **URL**: `/api/v1/prices`
- **Método**: `GET`
- **Query params**:
  - `applicationDate` (**requerido**) — formato ISO-8601 `yyyy-MM-dd'T'HH:mm:ss`
  - `productId` (**requerido**)
  - `brandId` (**requerido**)

**Ejemplo de llamada**
```bash
curl "http://localhost:8081/api/v1/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

**Respuesta 200 (ejemplo)**
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```

**Errores**
- **400 Bad Request**: parámetros ausentes o `applicationDate` con formato inválido.
- **404 Not Found**: no existe tarifa aplicable para los parámetros y fecha indicados.

---

## Casos de prueba funcionales (enunciado)

> Se validan las siguientes peticiones con los datos de ejemplo.  
> **Nota:** en los ejemplos se usa el separador `'T'` de ISO-8601, por lo que **no es necesario** aplicar URL-encoding.

### Test 1
**Request**
```bash
curl "http://localhost:8081/api/v1/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```
**Respuesta esperada**
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```

### Test 2
**Request**
```bash
curl "http://localhost:8081/api/v1/prices?applicationDate=2020-06-14T16:00:00&productId=35455&brandId=1"
```
**Respuesta esperada**
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 2,
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00",
  "price": 25.45,
  "currency": "EUR"
}
```

### Test 3
**Request**
```bash
curl "http://localhost:8081/api/v1/prices?applicationDate=2020-06-14T21:00:00&productId=35455&brandId=1"
```
**Respuesta esperada**
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```

### Test 4
**Request**
```bash
curl "http://localhost:8081/api/v1/prices?applicationDate=2020-06-15T10:00:00&productId=35455&brandId=1"
```
**Respuesta esperada**
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 3,
  "startDate": "2020-06-15T00:00:00",
  "endDate": "2020-06-15T11:00:00",
  "price": 30.50,
  "currency": "EUR"
}
```

### Test 5
**Request**
```bash
curl "http://localhost:8081/api/v1/prices?applicationDate=2020-06-16T21:00:00&productId=35455&brandId=1"
```
**Respuesta esperada**
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 4,
  "startDate": "2020-06-15T16:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 38.95,
  "currency": "EUR"
}
```

---

## Notas

- La base de datos es **en memoria**; los datos se reinician en cada arranque.  
- La inicialización se realiza mediante `data.sql` con los registros del enunciado.  
- El proyecto incluye **tests automatizados** que cubren los 5 escenarios anteriores.
