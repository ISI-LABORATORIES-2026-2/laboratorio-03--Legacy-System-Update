# laboratorio-03--Legacy-System-Update

# Migración de Sistema Bancario Legacy C++ a Java 21

## Información del proyecto

**Autor:** Samuel Rojas Amézquita  
**Código:** 160005133  
**Correo institucional:** se.rojasa@unillanos.edu.co  

**Universidad:** Universidad de los Llanos  
**Programa:** Ingeniería de Software  

---

# 1. Descripción del proyecto

Este proyecto corresponde a la migración y rediseño de un sistema bancario desarrollado originalmente en C++ con una arquitectura procedural basada en estructuras (`struct`) y manejo manual de memoria.

El objetivo principal es transformar este sistema legacy hacia una arquitectura orientada a objetos utilizando Java 21, aplicando conceptos fundamentales de Ingeniería de Software:

- Encapsulamiento.
- Herencia.
- Abstracción.
- Polimorfismo.
- Manejo seguro de recursos.
- Separación de responsabilidades.

El sistema representa dos productos financieros:

- Cuenta de ahorros.
- Cuenta corriente.

Cada tipo de cuenta posee reglas de negocio diferentes y estas son modeladas mediante una jerarquía de clases.

---

# 2. Problemas encontrados en el sistema Legacy C++

Durante la auditoría del código original se identificaron varios problemas:

## 2.1 Fugas de memoria

El código original utilizaba memoria dinámica mediante:

```cpp
new CuentaLegacy();
new char[50];
```

Sin embargo, no existían instrucciones de liberación:

```cpp
delete
delete[]
```

Esto ocasionaba que la memoria permaneciera ocupada durante la ejecución del programa.

---

## 2.2 Uso inseguro de punteros y cadenas

El atributo:

```cpp
char* titular;
```

utilizaba un puntero hacia memoria dinámica.

Además, la función:

```cpp
strcpy()
```

permitía copiar cadenas sin validar su tamaño, generando posibles desbordamientos de memoria.

---

## 2.3 Falta de encapsulamiento

El `struct` original permitía modificar directamente sus atributos:

```cpp
saldo
tipoCuenta
limiteSobregiro
```

Esto permitía generar estados inválidos dentro del sistema.

Ejemplo:

```cpp
saldo = -1000000;
```

sin ninguna validación.

---

## 2.4 Falta de polimorfismo

El sistema identificaba el tipo de cuenta mediante:

```cpp
int tipoCuenta;
```

donde:

```
1 = Cuenta de ahorros
2 = Cuenta corriente
```

Esto obligaba a utilizar estructuras como:

```java
if(tipoCuenta == 1)

else if(tipoCuenta == 2)
```

Este diseño dificulta agregar nuevos productos financieros.

---

# 3. Diseño Orientado a Objetos implementado

El nuevo diseño reemplaza la estructura procedural por una jerarquía de clases.

La arquitectura implementada es:

```
                 CuentaBancaria
                       |
        --------------------------------
        |                              |
 CuentaAhorros                  CuentaCorriente
```

---

# 4. Estructura del proyecto

```
SistemaBancarioJava/

│
├── src/
│   │
│   ├── CuentaBancaria.java
│   ├── CuentaAhorros.java
│   ├── CuentaCorriente.java
│   ├── RegistroAuditoriaBancaria.java
│   └── Principal.java
│
└── README.md
```

---

# 5. Descripción de clases

---

# CuentaBancaria.java

Clase abstracta que representa los elementos comunes de cualquier cuenta bancaria.

Contiene:

- Número de cuenta.
- Titular.
- Saldo.

Responsabilidades:

- Mantener información común.
- Proteger el estado interno.
- Definir operaciones obligatorias para las clases hijas.

Métodos principales:

```java
retirar()
```

Permite que cada tipo de cuenta implemente su propia lógica.

```java
aplicarComisionMensual()
```

Define la obligación de calcular cargos mensuales.

---

# CuentaAhorros.java

Representa una cuenta destinada al ahorro.

Reglas implementadas:

- No permite saldo negativo.
- Permite aplicar una tasa de interés.
- Tiene una comisión mensual fija.

Ejemplo:

Si el cliente intenta retirar más dinero del disponible:

```
Saldo = 500

Retiro = 700

Resultado:
Operación rechazada
```

---

# CuentaCorriente.java

Representa una cuenta con capacidad de sobregiro.

Reglas implementadas:

- Permite saldo negativo hasta un límite autorizado.
- Controla el cupo de sobregiro.
- Calcula intereses asociados al dinero utilizado.

Ejemplo:

```
Saldo = 200

Cupo sobregiro = 500

Retiro = 600


Nuevo saldo:

-400
```

El saldo negativo representa una deuda con el banco.

---

# RegistroAuditoriaBancaria.java

Clase encargada del registro de operaciones.

Implementa:

```java
AutoCloseable
```

Esto permite utilizar:

```java
try-with-resources
```

garantizando el cierre seguro del recurso.

Ejemplo:

```java
try(RegistroAuditoriaBancaria auditoria = new RegistroAuditoriaBancaria()){

}
```

Cuando finaliza el bloque, el recurso se libera automáticamente.

---

# Principal.java

Clase encargada de ejecutar una demostración del sistema.

Aquí se crean cuentas:

```java
CuentaAhorros
CuentaCorriente
```

y se realizan operaciones:

- Retiros.
- Aplicación de comisiones.
- Registro de auditoría.

---

# 6. Principios POO aplicados

## Encapsulamiento

Los atributos son privados:

```java
private double saldo;
```

El acceso se controla mediante métodos.

Esto evita modificaciones incorrectas desde otras clases.

---

## Abstracción

La clase:

```java
CuentaBancaria
```

define características generales sin conocer detalles específicos.

---

## Herencia

Las clases:

```java
CuentaAhorros
CuentaCorriente
```

heredan características comunes.

---

## Polimorfismo

Cada clase implementa su propio comportamiento:

Ejemplo:

```java
retirar()
```

En una cuenta de ahorros:

```
No permite saldo negativo.
```

En una cuenta corriente:

```
Permite utilizar sobregiro.
```

El mismo método tiene diferentes comportamientos dependiendo del objeto.

---

# 7. Diferencias entre C++ Legacy y Java

| Característica | C++ Legacy | Java |
|-|-|-|
| Memoria | Manual con new/delete | Garbage Collector |
| Texto | char* | String |
| Diseño | Struct procedural | Clases POO |
| Seguridad | Baja | Mayor control |
| Extensión | Muchos if/else | Polimorfismo |
| Recursos | Liberación manual | AutoCloseable |

---

# 8. Ejecución del proyecto

## Requisitos

- JDK 21 o superior.

Verificar instalación:

```bash
java -version
```

---

## Compilar

Desde la carpeta `src`:

```bash
javac *.java
```

---

## Ejecutar

```bash
java Principal
```

---

# 9. Conclusión

La migración permitió transformar un sistema basado en estructuras y manejo manual de memoria hacia un modelo orientado a objetos más seguro y mantenible.

Los principales beneficios obtenidos fueron:

- Eliminación de fugas de memoria manuales.
- Mayor protección del estado de las cuentas.
- Separación de responsabilidades.
- Facilidad para agregar nuevos tipos de productos financieros.
- Mejor representación de las reglas reales del negocio bancario.

El rediseño demuestra cómo la Programación Orientada a Objetos permite construir sistemas empresariales más escalables y preparados para cambios futuros.
