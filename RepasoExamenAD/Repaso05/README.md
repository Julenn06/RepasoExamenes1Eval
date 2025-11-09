# Repaso05 - Sistema de Gestión con Firestore

## 📋 Descripción

Proyecto completo de **repaso para examen** que implementa un sistema de gestión de **Alumnos** y **Cursos** usando **Google Cloud Firestore** como base de datos NoSQL en la nube.

**Arquitectura:** MVC (Model-View-Controller)  
**Base de Datos:** Google Cloud Firestore  
**Lenguaje:** Java 21  
**Dependencias:** Firebase Admin SDK 9.2.0

---

## 🏗️ Estructura del Proyecto

```
Repaso05/
├── pom.xml                          # Configuración Maven + dependencias Firebase
├── serviceAccountKey.json           # Credenciales de Firebase (NO subir a Git)
├── src/
│   ├── model/
│   │   ├── Alumnos.java            # Modelo de datos Alumnos
│   │   └── Curso.java              # Modelo de datos Curso
│   ├── controller/
│   │   ├── DBConnection.java       # Singleton para conexión Firestore
│   │   ├── Controller.java         # Controller genérico
│   │   ├── AlumnosController.java  # CRUD completo de Alumnos
│   │   ├── CursosController.java   # CRUD completo de Cursos
│   │   └── ExportController.java   # Exportación XML/DAT
│   └── view/
│       └── Main.java                # Menú interactivo (CLI)
└── target/                          # Archivos compilados (generado por Maven)
```

---

## 🔥 Configuración de Firestore

### 1. Crear Proyecto en Firebase

1. Ve a [Firebase Console](https://console.firebase.google.com/)
2. Crea un nuevo proyecto (ej. "RepasoExamenAD")
3. Activa **Firestore Database** en modo de prueba o producción
4. Ve a **Configuración del proyecto** → **Cuentas de servicio**
5. Haz clic en **Generar nueva clave privada**
6. Descarga el archivo JSON y renómbralo a `serviceAccountKey.json`
7. Coloca el archivo en la raíz del proyecto `Repaso05/`

### 2. Estructura de Firestore

**Colecciones y Documentos:**

```
Firestore (base de datos)
├── alumnos (colección)
│   ├── doc_id_1 (documento)
│   │   ├── id: "doc_id_1"
│   │   ├── name: "Juan Pérez"
│   │   ├── age: 20
│   │   ├── birthDate: Timestamp(2003-05-15)
│   │   └── idCurso: "curso_abc123"
│   └── doc_id_2 (documento)
│       └── ...
└── cursos (colección)
    ├── curso_abc123 (documento)
    │   ├── id: "curso_abc123"
    │   ├── nombre: "Acceso a Datos"
    │   └── descripcion: "Curso de bases de datos y ficheros"
    └── ...
```

**Relaciones:**
- Un alumno puede tener **una referencia** a un curso (campo `idCurso`)
- Esto permite consultas tipo JOIN: "Todos los alumnos del curso X"

---

## 🚀 Compilar y Ejecutar

### Opción 1: Usando Maven (recomendado)

```bash
# Compilar el proyecto
cd Repaso05
mvn clean compile

# Ejecutar Main
mvn exec:java -Dexec.mainClass="view.Main"
```

### Opción 2: Compilar manualmente con javac

```powershell
# Desde la raíz de Repaso05
cd Repaso05

# Compilar (necesitas las dependencias en el classpath)
# Mejor usar Maven para gestionar dependencias
mvn dependency:copy-dependencies
javac -cp "target/dependency/*" -d target/classes src/model/*.java src/controller/*.java src/view/*.java

# Ejecutar
java -cp "target/classes;target/dependency/*" view.Main
```

---

## 📖 Uso del Sistema

### Menú Principal

Al ejecutar el programa verás:

```
===========================================
  SISTEMA DE GESTIÓN FIRESTORE - REPASO05
===========================================

Conectando a Firestore...
[INFO] Firebase conectado con éxito

╔═══════════════════════════════════════╗
║         MENÚ PRINCIPAL                ║
╚═══════════════════════════════════════╝
1. Gestión de Alumnos
2. Gestión de Cursos
3. Gestión de Relaciones (Alumno-Curso)
4. Exportación de Datos
5. Salir
───────────────────────────────────────
```

### 1. Gestión de Alumnos

**Operaciones disponibles:**
- **Crear:** Agregar nuevo alumno con nombre, edad, fecha de nacimiento
- **Listar:** Ver todos los alumnos registrados
- **Buscar por ID:** Buscar alumno específico por su ID de documento
- **Buscar por nombre:** Búsqueda parcial (case-insensitive)
- **Filtrar por edad:** Rango de edad mínimo y máximo
- **Filtrar por curso:** Ver alumnos de un curso específico
- **Actualizar:** Modificar datos de un alumno existente
- **Eliminar:** Borrar alumno de Firestore

**Ejemplo de creación:**
```
Nombre: María García
Edad: 19
Fecha de nacimiento (dd/MM/yyyy): 15/03/2005
ID del curso (opcional): curso_ad_001

✓ Alumno creado exitosamente con ID: aBc123Xyz
```

### 2. Gestión de Cursos

**Operaciones disponibles:**
- **Crear:** Nuevo curso con nombre y descripción
- **Listar:** Ver todos los cursos
- **Buscar por ID:** Buscar curso específico
- **Actualizar:** Modificar nombre o descripción
- **Eliminar:** Borrar curso (con advertencia sobre referencias)

**Ejemplo de creación:**
```
Nombre del curso: Acceso a Datos
Descripción: Gestión de ficheros, XML, JSON y bases de datos

✓ Curso creado exitosamente con ID: curso_ad_001
```

### 3. Gestión de Relaciones

Demuestra cómo vincular documentos entre colecciones:

- **Asignar curso a alumno:** Establece relación alumno ↔ curso
- **Ver alumnos de un curso:** Lista todos los alumnos matriculados
- **Eliminar asignación:** Quita la referencia del curso

**Ejemplo:**
```
Introduce el ID del alumno: aBc123Xyz
Introduce el ID del curso: curso_ad_001

✓ Curso 'Acceso a Datos' asignado a María García
```

### 4. Exportación de Datos

Exporta datos de Firestore a archivos locales:

**Formatos disponibles:**
- **XML:** Estructura jerárquica con etiquetas, fácil de procesar
- **DAT:** Texto plano separado por `;`

**Archivos generados:**
- `alumnos_export.xml` / `alumnos_export.dat`
- `cursos_export.xml` / `cursos_export.dat`

**Formato XML de alumnos:**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<alumnos>
  <alumno>
    <id>aBc123Xyz</id>
    <nombre>María García</nombre>
    <edad>19</edad>
    <fecha>15/03/2005</fecha>
    <idCurso>curso_ad_001</idCurso>
  </alumno>
</alumnos>
```

**Formato DAT de alumnos:**
```
aBc123Xyz;María García;19;15/03/2005;curso_ad_001
```

---

## 💡 Conceptos Clave para el Examen

### 1. Firestore vs SQL

| Característica | SQL (Relacional) | Firestore (NoSQL) |
|----------------|------------------|-------------------|
| Estructura | Tablas con esquema fijo | Colecciones de documentos JSON |
| Relaciones | Foreign Keys, JOINs | Referencias manuales |
| Escalabilidad | Vertical (más potente servidor) | Horizontal (distribuido) |
| Consultas | SQL complejo | Queries limitadas + filtros client-side |

### 2. Operaciones CRUD en Firestore

```java
// CREATE
DocumentReference docRef = db.collection("alumnos").document();
docRef.set(data).get();

// READ
DocumentSnapshot doc = db.collection("alumnos").document(id).get().get();
Alumnos alumno = documentToAlumno(doc);

// UPDATE
db.collection("alumnos").document(id).update(data).get();

// DELETE
db.collection("alumnos").document(id).delete().get();
```

### 3. Consultas con Filtros

```java
// Filtrar por edad (WHERE)
db.collection("alumnos")
  .whereGreaterThanOrEqualTo("age", 18)
  .whereLessThanOrEqualTo("age", 25)
  .get();

// Filtrar por referencia
db.collection("alumnos")
  .whereEqualTo("idCurso", "curso_ad_001")
  .get();
```

### 4. Relaciones entre Colecciones

**Referencia simple (ID):**
```java
alumno.setIdCurso("curso_ad_001");  // Guardar ID como String
```

**Alternativa (DocumentReference):**
```java
DocumentReference cursoRef = db.collection("cursos").document("curso_ad_001");
data.put("cursoRef", cursoRef);  // Guardar referencia completa
```

### 5. Manejo de Fechas

```java
// Java Date → Firestore Timestamp (automático)
alumno.setBirthDate(new Date());

// Firestore Timestamp → Java Date
Date fecha = document.getDate("birthDate");
```

---

## 🔍 Casos de Uso y Ejemplos

### Caso 1: Crear un alumno y asignarle un curso

```
1. Crear curso "Acceso a Datos"
   → ID generado: curso_ad_001

2. Crear alumno "María García"
   → ID generado: aBc123Xyz

3. Asignar curso a alumno
   → Actualizar campo idCurso de María a "curso_ad_001"

4. Verificar relación
   → Filtrar alumnos por curso_ad_001
   → Resultado: María García
```

### Caso 2: Exportar datos para procesamiento

```
1. Crear varios alumnos con diferentes edades y cursos
2. Exportar alumnos a XML
3. Procesar alumnos_export.xml con otra aplicación o script
4. Importar en herramientas de análisis de datos
```

### Caso 3: Búsqueda y actualización

```
1. Buscar alumnos por nombre "María"
   → Resultado: 3 alumnos encontrados

2. Seleccionar uno por ID
3. Actualizar edad de 19 a 20
4. Verificar cambio listando todos los alumnos
```

---

## ⚠️ Notas Importantes

### Seguridad
- **NUNCA** subas `serviceAccountKey.json` a Git/GitHub
- Añade a `.gitignore`:
  ```
  serviceAccountKey.json
  *.json
  ```

### Limitaciones de Firestore
- **Búsqueda full-text:** No nativa, se hace client-side
- **Índices compuestos:** Necesarios para múltiples `where`
- **Transacciones:** Limitadas a 500 documentos
- **Costos:** Lectura/escritura se cobran por operación

### Mejoras Posibles
1. **Validaciones:** Añadir más controles de entrada
2. **Logging:** Usar SLF4J para logs estructurados
3. **Tests:** JUnit para probar controllers
4. **UI:** Swing/JavaFX en lugar de CLI
5. **Paginación:** Limitar resultados con `.limit(10)`
6. **Índices:** Crear índices en Firebase Console para queries complejas

---

## 📚 Referencias

- [Firebase Admin SDK - Java](https://firebase.google.com/docs/admin/setup)
- [Firestore Documentation](https://firebase.google.com/docs/firestore)
- [Maven Repository - Firebase](https://mvnrepository.com/artifact/com.google.firebase/firebase-admin)

---

## ✅ Checklist para el Examen

- [ ] Entiendo la diferencia entre SQL y NoSQL
- [ ] Sé cómo conectar a Firestore con serviceAccountKey.json
- [ ] Puedo hacer operaciones CRUD (Create, Read, Update, Delete)
- [ ] Entiendo cómo filtrar datos con `where`
- [ ] Sé cómo crear relaciones entre colecciones
- [ ] Puedo exportar datos a XML y DAT
- [ ] Comprendo el patrón MVC aplicado al proyecto
- [ ] Sé manejar fechas con SimpleDateFormat
- [ ] Entiendo operaciones asíncronas con ApiFuture

---

## 🎯 Comandos Rápidos

```bash
# Compilar
mvn clean compile

# Ejecutar
mvn exec:java -Dexec.mainClass="view.Main"

# Ver dependencias
mvn dependency:tree

# Limpiar
mvn clean
```

---

